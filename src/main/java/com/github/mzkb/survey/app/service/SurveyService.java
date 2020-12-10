package com.github.mzkb.survey.app.service;

import com.github.mzkb.survey.app.model.Publisher;
import com.github.mzkb.survey.app.model.Survey;
import com.github.mzkb.survey.app.service.exception.NotFoundException;
import com.github.mzkb.survey.app.service.exception.UnauthorizedException;

import java.util.List;
import java.util.UUID;

public interface SurveyService {

    Survey create(Survey survey);

    Survey get(String uuid) throws NotFoundException;

    Survey get(UUID uuid) throws NotFoundException;

    Survey get(Publisher publisher, UUID uuid) throws NotFoundException, UnauthorizedException;

    void delete(Publisher publisher, UUID uuid) throws NotFoundException, UnauthorizedException;

    List<Survey> getAll(Publisher publisher);

    Survey update(Publisher publisher, Survey survey) throws NotFoundException, UnauthorizedException;

    boolean send(Publisher publisher, UUID uuid, String email) throws NotFoundException, UnauthorizedException;
}
