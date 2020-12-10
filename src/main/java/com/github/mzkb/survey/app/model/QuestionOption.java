package com.github.mzkb.survey.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = QuestionOptionNumber.class, name = "number"),
        @JsonSubTypes.Type(value = QuestionOptionText.class, name = "text"),
        @JsonSubTypes.Type(value = QuestionOptionRadio.class, name = "radio"),
})
public abstract class QuestionOption implements Serializable {
}
