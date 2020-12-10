package com.github.mzkb.survey.app.service;

import com.github.mzkb.survey.app.model.Survey;
import com.github.mzkb.survey.app.model.SurveyResponse;
import com.github.mzkb.survey.app.service.exception.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface SurveyResponseService {

    SurveyResponse create(SurveyResponse surveyResponse);

    SurveyResponse create(SurveyResponse surveyResponse, boolean sendMail);

    SurveyResponse get(UUID uuid) throws NotFoundException;

    List<SurveyResponse> getForSurvey(Survey survey);
}
