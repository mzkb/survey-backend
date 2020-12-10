package com.github.mzkb.survey.app.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.mzkb.survey.app.model.QuestionResponse;
import com.github.mzkb.survey.app.model.Survey;
import com.github.mzkb.survey.app.model.SurveyResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSurveyResponse implements Serializable {

    private final String uuid;
    private final String name;
    private final String email;
    private final String title;
    private final String description;
    private final List<UserQuestionResponse> responses;

    public UserSurveyResponse(SurveyResponse surveyResponse) {
        this.uuid = surveyResponse.getUuid();
        this.name = surveyResponse.getName();
        this.email = surveyResponse.getEmail();

        Survey survey = surveyResponse.getSurvey();
        this.title = survey.getTitle();
        this.description = survey.getDescription();

        this.responses = new ArrayList<>();
        List<QuestionResponse> questionResponses = surveyResponse.getQuestionResponses();
        for (QuestionResponse questionResponse : questionResponses) {
            responses.add(new UserQuestionResponse(questionResponse));
        }
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<UserQuestionResponse> getResponses() {
        return responses;
    }
}
