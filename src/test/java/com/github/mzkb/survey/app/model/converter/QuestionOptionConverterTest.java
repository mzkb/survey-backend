package com.github.mzkb.survey.app.model.converter;

import com.github.mzkb.survey.app.model.QuestionOption;
import com.github.mzkb.survey.app.model.QuestionOptionText;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;


// TODO: Add more test cases
public class QuestionOptionConverterTest {

    @Test
    public void shouldConvertJsonToObject() {
        QuestionOptionConverter qop = qop();
        String json = "{\"type\":\"text\",\"minLength\":8,\"maxLength\":256}";
        QuestionOption questionOption = qop.convertToEntityAttribute(json);
        assertTrue(questionOption instanceof QuestionOptionText);
    }

    private QuestionOptionConverter qop() {
        return new QuestionOptionConverter();
    }
}