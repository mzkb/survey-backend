package com.github.mzkb.survey.app.api.advice;

import com.github.mzkb.survey.app.service.exception.AlreadyExistsException;
import com.github.mzkb.survey.app.service.exception.NotFoundException;
import com.github.mzkb.survey.app.service.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class RestExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<?> vehicleNotFound(NotFoundException e, WebRequest request) {
        return new ResponseEntity<>(NOT_FOUND);
    }

    @ExceptionHandler(value = {AlreadyExistsException.class})
    public ResponseEntity<?> vehicleNotFound(AlreadyExistsException e, WebRequest request) {
        return new ResponseEntity<>(CONFLICT);
    }

    @ExceptionHandler(value = {UnauthorizedException.class})
    public ResponseEntity<?> vehicleNotFound(UnauthorizedException e, WebRequest request) {
        return new ResponseEntity<>(UNAUTHORIZED);
    }
}
