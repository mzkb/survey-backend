package com.github.mzkb.survey.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("radio")
public class QuestionOptionRadio extends QuestionOption implements Serializable {
    private List<QuestionMultiChoice> choices;
    private boolean randomize;

    public List<QuestionMultiChoice> getChoices() {
        return choices;
    }

    public void setChoices(List<QuestionMultiChoice> choices) {
        this.choices = choices;
    }

    public boolean isRandomize() {
        return randomize;
    }

    public void setRandomize(boolean randomize) {
        this.randomize = randomize;
    }
}
