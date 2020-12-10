package com.github.mzkb.survey.app.api.endpoint;

import com.github.mzkb.survey.app.api.dto.PublisherCreateDto;
import com.github.mzkb.survey.app.api.dto.PublisherResponse;
import com.github.mzkb.survey.app.api.dto.PublisherUpdateDto;
import com.github.mzkb.survey.app.model.Publisher;
import com.github.mzkb.survey.app.service.AuthenticationProvider;
import com.github.mzkb.survey.app.service.PublisherService;
import com.github.mzkb.survey.app.service.exception.AlreadyExistsException;
import com.github.mzkb.survey.app.service.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

// This is not a admin endpoint
// Only the person associated with this account (jwt token) can make changes
@RestController
public class PublisherEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(PublisherEndpoint.class);

    private final PublisherService publisherService;
    private final AuthenticationProvider authenticationProvider;

    public PublisherEndpoint(PublisherService publisherService, AuthenticationProvider authenticationProvider) {
        this.publisherService = publisherService;
        this.authenticationProvider = authenticationProvider;
    }

    @PostMapping("/publishers")
    public ResponseEntity<PublisherResponse> newPublisher(@RequestBody PublisherCreateDto publisherCreateDto) throws AlreadyExistsException {
        PublisherResponse publisherResponse = new PublisherResponse(publisherService.create(publisherCreateDto.toPublisher()));
        return new ResponseEntity<>(publisherResponse, CREATED);
    }

    @PutMapping("/publishers")
    public ResponseEntity<PublisherResponse> updatePublisher(@RequestBody PublisherUpdateDto publisherUpdateDto) throws NotFoundException {
        Publisher publisher = authenticationProvider.getAuthenticatedPublisher();
        if (publisher == null) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }

        // TODO: Implement. Update the publisher associated with the JWT
        return new ResponseEntity<>(OK);
    }

    @GetMapping("/publishers")
    public ResponseEntity<PublisherResponse> getPublisher() throws NotFoundException {
        Publisher publisher = authenticationProvider.getAuthenticatedPublisher();
        if (publisher == null) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }

        PublisherResponse publisherResponse = new PublisherResponse(publisher);
        return new ResponseEntity<>(publisherResponse, OK);
    }

    @DeleteMapping("/publishers")
    public ResponseEntity<?> deletePublisher(@PathVariable UUID uuid) throws NotFoundException {
        Publisher publisher = authenticationProvider.getAuthenticatedPublisher();
        if (publisher == null) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }

        publisherService.delete(publisher.getUuid());
        return new ResponseEntity<>(OK);
    }
}
