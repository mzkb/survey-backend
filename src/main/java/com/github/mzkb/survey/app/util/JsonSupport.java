package com.github.mzkb.survey.app.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JsonSupport {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonSupport.class);

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String convertToString(Object obj) {
        if (obj != null) {
            try {
                return OBJECT_MAPPER.writeValueAsString(obj);
            } catch (JsonProcessingException e) {
                LOGGER.warn("Unable to write json", e);
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
