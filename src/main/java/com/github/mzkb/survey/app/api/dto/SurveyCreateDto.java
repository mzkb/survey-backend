package com.github.mzkb.survey.app.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.mzkb.survey.app.model.Publisher;
import com.github.mzkb.survey.app.model.Survey;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SurveyCreateDto implements Serializable {

    private String title;
    private String description;

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

    public Survey toSurvey(Publisher publisher) {
        Survey survey = new Survey();

        survey.setTitle(title);
        survey.setDescription(description);
        survey.setPublisher(publisher);

        return survey;
    }
}
