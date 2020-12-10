package com.github.mzkb.survey.app.service;

import com.github.mzkb.survey.app.model.Publisher;
import com.github.mzkb.survey.app.service.exception.AlreadyExistsException;
import com.github.mzkb.survey.app.service.exception.NotFoundException;

import java.util.UUID;

public interface PublisherService {

    Publisher create(Publisher publisher) throws AlreadyExistsException;

    Publisher get(UUID uuid) throws NotFoundException;

    Publisher get(String uuid) throws NotFoundException;

    void delete(UUID uuid) throws NotFoundException;

    void delete(String uuid) throws NotFoundException;

    Publisher getByEmail(String email);
}
