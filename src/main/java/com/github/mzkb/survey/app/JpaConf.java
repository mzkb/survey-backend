package com.github.mzkb.survey.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan("com.github.mzkb.survey.app.model")
@EnableJpaRepositories("com.github.mzkb.survey.app")
public class JpaConf {
    private static final Logger LOGGER = LoggerFactory.getLogger(JpaConf.class);
}
