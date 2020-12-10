package com.github.mzkb.survey.app.api.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ResponseStatus(INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends RuntimeException {
    private static final long serialVersionUID = -4623776626627532110L;
}
