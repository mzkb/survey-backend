package com.github.mzkb.survey.app.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.mzkb.survey.app.model.Question;
import com.github.mzkb.survey.app.model.QuestionMultiChoice;
import com.github.mzkb.survey.app.model.QuestionOption;
import com.github.mzkb.survey.app.model.Survey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SurveyResponse implements Serializable {

    private final String uuid;
    private final String created;
    private final String updated;
    private final String title;
    private final String description;
    private final List<QuestionDto> questions;
    private final List<UserSurveyResponse> responses;

    public SurveyResponse(Survey survey) {
        this.uuid = survey.getUuid();
        this.created = survey.getCreatedAsString();
        this.updated = survey.getUpdatedAsString();
        this.title = survey.getTitle();
        this.description = survey.getDescription();
        this.questions = new ArrayList<>();

        List<Question> questions = survey.getQuestions() == null ? new ArrayList<>() : survey.getQuestions();
        for (Question question : questions) {
            QuestionDto questionDto = new QuestionDto();
            questionDto.setUuid(question.getUuid());
            questionDto.setQuestion(question.getQuestion());

            QuestionOptionDto options = toQuestionOptionDto(question.getOptions());
            if (options instanceof QuestionOptionRadioDto) {
                QuestionOptionRadioDto questionOptionRadioDto = (QuestionOptionRadioDto) options;
                List<QuestionMultiChoice> choices = questionOptionRadioDto.getChoices();
                if (choices != null) {
                    if (questionOptionRadioDto.isRandomize()) {
                        Collections.shuffle(choices);
                    } else {
                        choices.sort(Comparator.comparingInt(QuestionMultiChoice::getPosition));
                    }
                }
            }
            questionDto.setOptions(options);
            questionDto.setRequired(question.isRequired());
            questionDto.setOrder(question.getPosition());
            this.questions.add(questionDto);
        }

        this.questions.sort(Comparator.comparingInt(QuestionDto::getOrder));

        this.responses = new ArrayList<>();
        // TODO: Rename some of these classes to make better sense
        List<com.github.mzkb.survey.app.model.SurveyResponse> responses = survey.getResponses() == null ? new ArrayList<>() : survey.getResponses();
        for (com.github.mzkb.survey.app.model.SurveyResponse response : responses) {
            UserSurveyResponse userSurveyResponse = new UserSurveyResponse(response);
            this.responses.add(userSurveyResponse);
        }
    }

    public String getUuid() {
        return uuid;
    }

    public String getCreated() {
        return created;
    }

    public String getUpdated() {
        return updated;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<QuestionDto> getQuestions() {
        return questions;
    }

    public List<UserSurveyResponse> getResponses() {
        return responses;
    }

    private QuestionOptionDto toQuestionOptionDto(QuestionOption options) {
        if (options == null) {
            return null;
        }
        // TODO: Terribly inefficient. Refactor this
        String jsonAsString = QuestionOptionsSupport.convertToString(options);
        return QuestionOptionsSupport.convertToClass(jsonAsString, QuestionOptionDto.class);
    }
}
