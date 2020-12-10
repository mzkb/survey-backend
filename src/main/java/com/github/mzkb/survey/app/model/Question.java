package com.github.mzkb.survey.app.model;

import com.github.mzkb.survey.app.model.converter.QuestionOptionConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Question extends BaseEntity implements Serializable {

    @NotNull
    @Column(name = "question", nullable = false)
    private String question;

    @Convert(converter = QuestionOptionConverter.class)
    @Column(name = "question_options", nullable = true, length = 2048)
    private QuestionOption options;

    @NotNull
    @Column(name = "required", nullable = false)
    private boolean required;

    @NotNull
    @Column(name = "position", nullable = false)
    private int position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public QuestionOption getOptions() {
        return options;
    }

    public void setOptions(QuestionOption options) {
        this.options = options;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int order) {
        this.position = order;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }
}
