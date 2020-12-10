package com.github.mzkb.survey.app.service;

import com.github.mzkb.survey.app.model.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationProviderImpl.class);

    private final PublisherService publisherService;

    public AuthenticationProviderImpl(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public Publisher getAuthenticatedPublisher() {
        // Returning null for cases where the account is not found as we rely on the caller handle this
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }

        String name = authentication.getName();
        if (StringUtils.isEmpty(name)) {
            return null;
        }

        return publisherService.getByEmail(name);
    }
}
