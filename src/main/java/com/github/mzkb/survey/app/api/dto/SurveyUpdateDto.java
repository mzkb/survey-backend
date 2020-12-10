package com.github.mzkb.survey.app.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.mzkb.survey.app.model.Publisher;
import com.github.mzkb.survey.app.model.Question;
import com.github.mzkb.survey.app.model.Survey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SurveyUpdateDto implements Serializable {

    private String uuid;
    private String title;
    private String description;
    private List<QuestionDto> questions;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<QuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDto> questions) {
        this.questions = questions;
    }

    public Survey toSurvey(Publisher publisher) {
        Survey survey = new Survey();

        survey.setUuid(uuid);
        survey.setTitle(title);
        survey.setDescription(description);
        survey.setPublisher(publisher);
        List<Question> convertedQuestions = new ArrayList<>();
        for (QuestionDto question : questions) {
            convertedQuestions.add(question.toQuestion());
        }
        survey.setQuestions(convertedQuestions);
        return survey;
    }
}
