package com.github.mzkb.survey.app.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class QuestionResponse extends BaseEntity implements Serializable {

    // TODO: Track the order so it can be preserved in the response
    // Or just keep relying on the order of submission

    @NotBlank
    @Column(name = "question_uuid", nullable = false, length = 36)
    private String questionUuid;

    @NotNull
    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "response", nullable = true)
    private String response;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_response_id")
    private SurveyResponse surveyResponse;

    public String getQuestionUuid() {
        return questionUuid;
    }

    public void setQuestionUuid(String questionUuid) {
        this.questionUuid = questionUuid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public SurveyResponse getSurveyResponse() {
        return surveyResponse;
    }

    public void setSurveyResponse(SurveyResponse surveyResponse) {
        this.surveyResponse = surveyResponse;
    }
}
