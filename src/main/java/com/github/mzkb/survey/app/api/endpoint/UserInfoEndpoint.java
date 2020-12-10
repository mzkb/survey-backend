package com.github.mzkb.survey.app.api.endpoint;

import com.github.mzkb.survey.app.api.dto.PublisherResponse;
import com.github.mzkb.survey.app.model.Publisher;
import com.github.mzkb.survey.app.service.AuthenticationProvider;
import com.github.mzkb.survey.app.service.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
public class UserInfoEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(PublisherEndpoint.class);

    private final AuthenticationProvider authenticationProvider;

    public UserInfoEndpoint(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @GetMapping("/me")
    public ResponseEntity<PublisherResponse> getMe() throws NotFoundException {
        Publisher publisher = authenticationProvider.getAuthenticatedPublisher();
        if (publisher == null) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }

        PublisherResponse publisherResponse = new PublisherResponse(publisher);
        return new ResponseEntity<>(publisherResponse, OK);
    }
}
