package com.github.mzkb.survey.app.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = QuestionOptionNumberDto.class, name = "number"),
        @JsonSubTypes.Type(value = QuestionOptionTextDto.class, name = "text"),
        @JsonSubTypes.Type(value = QuestionOptionRadioDto.class, name = "radio"),
})
public abstract class QuestionOptionDto implements Serializable {
}
