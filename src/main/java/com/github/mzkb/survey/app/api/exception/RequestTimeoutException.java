package com.github.mzkb.survey.app.api.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.REQUEST_TIMEOUT;

@ResponseStatus(REQUEST_TIMEOUT)
public class RequestTimeoutException extends RuntimeException {
    private static final long serialVersionUID = 7512873261278562299L;
}
