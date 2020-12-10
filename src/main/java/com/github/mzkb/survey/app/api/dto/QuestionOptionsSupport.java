package com.github.mzkb.survey.app.api.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class QuestionOptionsSupport {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionOptionsSupport.class);

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String convertToString(Object options) {
        if (options != null) {
            try {
                return OBJECT_MAPPER.writeValueAsString(options);
            } catch (JsonProcessingException e) {
                LOGGER.warn("Unable to write json for options", e);
            }
        }

        return null;
    }

    public static <T> T convertToClass(String json, Class<T> type) {
        if (json != null && !json.isEmpty() && !json.isBlank()) {
            try {
                return OBJECT_MAPPER.readValue(json, type);
            } catch (JsonProcessingException e) {
                LOGGER.warn("Unable to read json", e);
            }
        }

        return null;
    }
}
