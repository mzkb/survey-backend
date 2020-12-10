package com.github.mzkb.survey.app.api.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@ResponseStatus(SERVICE_UNAVAILABLE)
public class ServiceUnavailableException extends RuntimeException {
    private static final long serialVersionUID = -9023335873603583533L;
}
