package com.github.mzkb.survey.app.model.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mzkb.survey.app.JpaConf;
import com.github.mzkb.survey.app.model.QuestionOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class QuestionOptionConverter implements AttributeConverter<QuestionOption, String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JpaConf.class);

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(QuestionOption options) {
        if (options != null) {
            try {
                return OBJECT_MAPPER.writeValueAsString(options);
            } catch (JsonProcessingException e) {
                LOGGER.warn("Unable to write json for options", e);
            }
        }

        return null;
    }

    @Override
    public QuestionOption convertToEntityAttribute(String options) {
        if (options != null && !options.isEmpty() && !options.isBlank()) {
            try {
                return OBJECT_MAPPER.readValue(options, QuestionOption.class);
            } catch (JsonProcessingException e) {
                LOGGER.warn("Unable to read json for options", e);
            }
        }

        return null;
    }
}
