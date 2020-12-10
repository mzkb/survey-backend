package com.github.mzkb.survey.app.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.mzkb.survey.app.model.QuestionResponse;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserQuestionResponse implements Serializable {

    private final String question;
    private final String response;

    public UserQuestionResponse(QuestionResponse questionResponse) {
        this.question = questionResponse.getQuestion();
        this.response = questionResponse.getResponse();
    }

    public String getQuestion() {
        return question;
    }

    public String getResponse() {
        return response;
    }
}
