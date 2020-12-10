# Step 0: Global defaults
ARG USER_HOME_DIR="/app"
ARG BASE_DIR="."

# Step 1: Setup Maven
FROM openjdk:11.0.7-jdk-slim AS maven
RUN apt-get update && apt-get -qq -y install curl tar bash

ARG MAVEN_VERSION=3.6.3
ENV MAVEN_HOME /usr/share/maven

RUN mkdir -p $MAVEN_HOME && \
curl -fsSL http://apache.osuosl.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz | tar -xzC $MAVEN_HOME --strip-components=1 && \
ln -s $MAVEN_HOME/bin/mvn /usr/bin/mvn

ARG USER_HOME_DIR
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"

ENTRYPOINT ["/usr/bin/mvn"]

# Step 2: Build the project using Maven
FROM maven AS builder

ARG USER_HOME_DIR
RUN mkdir -p $USER_HOME_DIR/src/main
WORKDIR $USER_HOME_DIR

ARG BASE_DIR
COPY $BASE_DIR/pom.xml $USER_HOME_DIR
RUN mvn dependency:go-offline

COPY $BASE_DIR/src/main $USER_HOME_DIR/src/main
RUN mvn -T 1C install

# Step 3: Build the clean app image
FROM openjdk:11.0.7-jre-slim
RUN apt-get update && apt-get -qq -y install fontconfig

ARG USER="app"
ARG GROUP="app"
ARG GID=1000
ARG UID=1000
RUN groupadd -g $GID $GROUP && useradd -u $UID -g $GROUP -s /bin/sh $USER

ARG USER_HOME_DIR
RUN mkdir -p $USER_HOME_DIR/logs && \
    chown -R $USER $USER_HOME_DIR

VOLUME $USER_HOME_DIR/logs
WORKDIR $USER_HOME_DIR
USER $USER

COPY --from=builder $USER_HOME_DIR/target/app-*.jar $USER_HOME_DIR/app.jar

EXPOSE 8080
CMD ["java", "-jar", "-Dspring.profiles.active=dev", "/app/app.jar"]