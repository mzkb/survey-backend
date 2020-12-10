package com.github.mzkb.survey.app.model;

import java.io.Serializable;

public class QuestionMultiChoice implements Serializable {

    private String value;
    private int position;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}