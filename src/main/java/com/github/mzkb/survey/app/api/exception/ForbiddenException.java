package com.github.mzkb.survey.app.api.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@ResponseStatus(FORBIDDEN)
public class ForbiddenException extends RuntimeException {
    private static final long serialVersionUID = -5813424315787325707L;
}
