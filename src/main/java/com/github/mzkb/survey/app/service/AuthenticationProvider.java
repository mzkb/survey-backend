package com.github.mzkb.survey.app.service;

import com.github.mzkb.survey.app.model.Publisher;
import org.springframework.security.core.Authentication;

public interface AuthenticationProvider {

    Authentication getAuthentication();

    Publisher getAuthenticatedPublisher();
}
