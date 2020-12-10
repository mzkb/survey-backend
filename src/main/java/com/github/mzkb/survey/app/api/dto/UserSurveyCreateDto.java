package com.github.mzkb.survey.app.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.mzkb.survey.app.model.QuestionResponse;
import com.github.mzkb.survey.app.model.Survey;
import com.github.mzkb.survey.app.model.SurveyResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSurveyCreateDto implements Serializable {

    private String name;
    private String email;
    private String uuid;
    private List<UserQuestionCreateDto> responses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<UserQuestionCreateDto> getResponses() {
        return responses;
    }

    public void setResponses(List<UserQuestionCreateDto> responses) {
        this.responses = responses;
    }

    public SurveyResponse toSurveyResponse(Survey survey) {
        SurveyResponse surveyResponse = new SurveyResponse();
        surveyResponse.setName(name);
        surveyResponse.setEmail(email);
        surveyResponse.setSurvey(survey);
        surveyResponse.setQuestionResponses(toQuestionResponses(surveyResponse, responses));
        return surveyResponse;
    }

    private List<QuestionResponse> toQuestionResponses(SurveyResponse surveyResponse, List<UserQuestionCreateDto> responses) {
        List<QuestionResponse> questionResponses = new ArrayList<>();

        for (UserQuestionCreateDto response : responses) {
            QuestionResponse questionResponse = new QuestionResponse();
            questionResponse.setQuestion(response.getQuestion());
            questionResponse.setResponse(response.getResponse());
            questionResponse.setSurveyResponse(surveyResponse);
            questionResponse.setQuestionUuid(response.getUuid());

            questionResponses.add(questionResponse);
        }

        return questionResponses;
    }
}
