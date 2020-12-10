package com.github.mzkb.survey.app.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.mzkb.survey.app.model.*;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionDto implements Serializable {

    private String uuid;
    private String question;
    private QuestionOptionDto options;
    private boolean required;
    private int order;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public QuestionOptionDto getOptions() {
        return options;
    }

    public void setOptions(QuestionOptionDto options) {
        this.options = options;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Question toQuestion() {
        Question question = new Question();
        question.setUuid(this.uuid);
        question.setQuestion(this.question);
        question.setOptions(toQuestionOptions(this.options));
        question.setRequired(this.required);
        question.setPosition(this.order);
        return question;
    }

    private QuestionOption toQuestionOptions(QuestionOptionDto options) {
        if (options == null) {
            return null;
        }

        if (options instanceof QuestionOptionNumberDto) {
            QuestionOptionNumberDto qpnd = (QuestionOptionNumberDto) options;

            QuestionOptionNumber questionOptionNumber = new QuestionOptionNumber();
            questionOptionNumber.setMinValue(qpnd.getMinValue());
            questionOptionNumber.setMaxValue(qpnd.getMaxValue());
            return questionOptionNumber;
        }

        if (options instanceof QuestionOptionTextDto) {
            QuestionOptionTextDto qotd = (QuestionOptionTextDto) options;

            QuestionOptionText questionOptionText = new QuestionOptionText();
            questionOptionText.setMinLength(qotd.getMinLength());
            questionOptionText.setMaxLength(qotd.getMaxLength());
            return questionOptionText;
        }

        if (options instanceof QuestionOptionRadioDto) {
            QuestionOptionRadioDto qord = (QuestionOptionRadioDto) options;

            QuestionOptionRadio questionOptionRadio = new QuestionOptionRadio();
            questionOptionRadio.setRandomize(qord.isRandomize());
            questionOptionRadio.setChoices(qord.getChoices());
            return questionOptionRadio;
        }

        // TODO: Populate the above fully
        return null;
    }
}
