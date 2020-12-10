# Survey App
Simple survey web application.

## Features
* Create a publisher
* Create a survey
* Update a survey
* Send a survey
* Take a survey
* Get responses

## Technology Stack
* Maven 3.6.3
* Java 11
* Docker
* H2

## Dependencies
* Spring Boot
* Spring MVC
* Spring Security
* Logback
* details can be found in "pom.xml"

## Usage
### Sending Mail
To support mail sending capabilities a 'username' and 'password' needs to be provided for the Gmail SMTP server.  
These values can be configured in the "application.yml" file.
  
### Using Maven Wrapper

#### Pre-requisite
* Java 11 installed and on the PATH

#### Build
```shell script
./mvnw clean install
```

#### Run
```shell script
java -jar ./target/app-0.0.1-SNAPSHOT.jar
```

### Docker

#### Pre-requisite
* Docker installed and available

#### Build
```shell script
docker build --tag survey:latest .
```

#### Run
```shell script
docker run --rm \
  -p 8080:8080 \
  -t \
  --name survey \
  survey:latest
```

## Design
Details on the technical details:
### Overall
![High Level Overall Architecture](survey-high-level.png?raw=true "High Level Overall Architecture")
### Database
#### High Level Entity Relationship Diagram
![High Level Entity Relationship Diagram](survey-er-diagram.png?raw=true "High Level Entity Relationship Diagram")
#### Summary
* a publisher has many surveys
* a survey has many questions
* a survey has many responses
* a survey response has many question-response pairs

Each question can have various options which will be stored in the text field "questions_options".  
The data in this field will be saved as JSON. 

#### Tables
Column definitions provided in MySQL format.

##### Publisher
| Column        | Definition    | 
| ------------- | ------------- |
| id            | BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY  |
| uuid          | VARCHAR(36) NOT NULL  |
| created       | DATETIME(3) NOT NULL  |
| updated       | DATETIME(3) NOT NULL  |
| name          | VARCHAR(255) NOT NULL  |
| email         | VARCHAR(255) NOT NULL  |
| password      | VARCHAR(255) NOT NULL  |

* uuid: unique
* email: unique

##### Survey
| Column        | Definition    |
| ------------- | ------------- |
| id            | BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY  |
| uuid          | VARCHAR(36) NOT NULL  |
| created       | DATETIME(3) NOT NULL  |
| updated       | DATETIME(3) NOT NULL  |
| title         | VARCHAR(255) NOT NULL  |
| description   | VARCHAR(1024)  |
| publisher_id  | BIGINT UNSIGNED  |

* uuid: unique
* publisher_id: index

##### Question
| Column        | Definition    |
| ------------- | ------------- |
| id            | BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY  |
| uuid          | VARCHAR(36) NOT NULL  |
| created       | DATETIME(3) NOT NULL  |
| updated       | DATETIME(3) NOT NULL  |
| question      | VARCHAR(255) NOT NULL  |
| question_options | VARCHAR(2048) NOT NULL  |
| required      | BIT NOT NULL  |
| position      | INT UNSIGNED NOT NULL  |
| survey_id     | BIGINT UNSIGNED  |

* uuid: unique
* survey_id: foreign key

##### SurveyResponse
| Column        | Definition    |
| ------------- | ------------- |
| id            | BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY  |
| uuid          | VARCHAR(36) NOT NULL  |
| created       | DATETIME(3) NOT NULL  |
| updated       | DATETIME(3) NOT NULL  |
| name          | VARCHAR(255) NOT NULL  |
| email         | VARCHAR(255) NOT NULL  |
| survey_id     | BIGINT UNSIGNED  |

* uuid: unique
* survey_id: foreign key

##### SurveyResponse
| Column        | Definition    |
| ------------- | ------------- |
| id            | BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY  |
| uuid          | VARCHAR(36) NOT NULL  |
| created       | DATETIME(3) NOT NULL  |
| updated       | DATETIME(3) NOT NULL  |
| question_uuid | VARCHAR(36) NOT NULL  |
| question      | VARCHAR(255) NOT NULL  |
| response      | VARCHAR(255) |
| survey_response_id | BIGINT UNSIGNED  |

* uuid: unique
* survey_response_id: foreign key

### API
#### High Level Endpoints
| Endpoint          | Operations     | Comments      |
| -------------     | -------------  | ------------- |
| /login            | POST           | Get a JWT     |
| /me               | GET            | Get info on the publisher |
| /publishers       | POST, PUT, GET, DELETE  | CRUD for the publisher |
| /surveys          | POST, PUT, GET | Create, Update and Get list of surveys |
| /surveys/:uuid    | GET, DELETE    | Get or Delete a survey |
| /surveys/:uuid    | POST           | Send out a survey |
| /responses        | POST           | Record a survey response |
| /responses/:uuid  | GET            | Get a recorded response for a survey |

#### Security
JWT will be used for protecting the endpoints.

The flow requiring login will be as follows:
1. Any access to protected pages will cause the frontend to check its local storage for a valid JWT.
3. If not found the user will be presented with the login page
4. Upon entering the username and password a login request will be sent to the backend
5. If authenticated successfully on the backend a valid JWT token is returned
6. The frontend will store this in local storage and the user is deemed as being logged in

The flow itself looks like:
![Happy Login Path](survey-happy-login-path.png?raw=true "Happy Login Path")

The normal flow when a valid token is present on the frontend is as follows:
1. Any access to protected pages will cause the frontend to check its local storage for a valid JSWT
2. If found the access is allowed (and any required API calls are made)

The flow itself looks like:
![Happy Access Path](survey-happy-token-path.png?raw=true "Happy Access Path")
#### Documentation
API documentation will be provided using Swagger:  

*Swagger Docs:* http://localhost:8080/v2/api-docs  
*Swagger UI  :* http://localhost:8080/swagger-ui/index.html

### Validation
Validation is to be performed at the following layers to ensure data integrity:
* Endpoint layer (in the input) to catch incorrect data
* Service layer to catch issues like already existing publishers or for more complex logic that requires access to the database
* Database layer (using constraints)
 
### Testing
Testing to be performed based on the following outline*
* Unit tests to test single units of code like helper functions or service classes (or anything with significant logic but is fairly self-contained)
* Endpoints using the features available from Spring
* E2E tests that access the API externally and use a request/response model (for example using Postman or the REST Assured framework)

## Current Implementation
### Missing
#### API Security
* Support for roles in the JWT
* Support for revoking JWT tokens (i.e. logout)
#### API Documentation
* Support for JWT authentication
#### Validation
* Validation in the endpoint and service layer
#### Testing
* Unit test cases
* E2E test cases
### Improvements
* Make sending of the mail async
* Hard coded mail templates needs to be refactored
