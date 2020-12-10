package com.github.mzkb.survey.app.dev;

import com.github.mzkb.survey.app.dao.QuestionDao;
import com.github.mzkb.survey.app.model.*;
import com.github.mzkb.survey.app.service.PublisherService;
import com.github.mzkb.survey.app.service.SurveyResponseService;
import com.github.mzkb.survey.app.service.SurveyService;
import com.github.mzkb.survey.app.service.exception.AlreadyExistsException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Profile("dev")
public class TestData implements ApplicationRunner {

    private final PublisherService publisherService;
    private final SurveyService surveyService;
    private final SurveyResponseService surveyResponseService;
    private final QuestionDao questionDao;

    public TestData(PublisherService publisherService, SurveyService surveyService,
                    SurveyResponseService surveyResponseService, QuestionDao questionDao) {

        this.publisherService = publisherService;
        this.surveyService = surveyService;
        this.surveyResponseService = surveyResponseService;
        this.questionDao = questionDao;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Test Data Setup:     [START]");

        Publisher publisher = createPublisher();
        System.out.println("\t Publisher:   [DONE]");

        Survey survey = createSurvey(publisher);
        System.out.println("\t Survey Url: http://localhost:8080/surveys/" + survey.getUuid());
        System.out.println("\t Survey   :   [DONE]");

        Question shortTextQuestion = createTextQuestion(survey);
        Question radioQuestion = createRadioQuestion(survey);
        System.out.println("\t Questions:   [DONE]");

        SurveyResponse response = createResponse(survey, shortTextQuestion, radioQuestion);
        System.out.println("\t Response Url: http://localhost:8080/responses/" + response.getUuid());
        System.out.println("\t Response :   [DONE]");

        System.out.println("Test Data Setup:     [END]");

        System.out.println();
        System.out.println("Swagger Docs: http://localhost:8080/v2/api-docs");
        System.out.println("Swagger UI  : http://localhost:8080/swagger-ui/index.html");
    }

    private Publisher createPublisher() throws AlreadyExistsException {
        Publisher publisher = new Publisher();
        publisher.setName("Publisher Test");
        publisher.setEmail("publisher-test@mailinator.com");
        publisher.setPassword("password10");

        System.out.println("\t Publisher Email: " + publisher.getEmail());
        System.out.println("\t Publisher Passw: " + publisher.getPassword());
        return publisherService.create(publisher);
    }

    private Survey createSurvey(Publisher publisher) {
        Survey survey = new Survey();
        survey.setTitle("Survey Test");
        survey.setDescription("Pre-populated test survey");
        survey.setPublisher(publisher);
        return surveyService.create(survey);
    }

    private Question createTextQuestion(Survey survey) {
        Question question = new Question();
        question.setQuestion("Write something...");
        question.setPosition(1);
        question.setRequired(true);
        QuestionOptionText questionOptionText = new QuestionOptionText();
        questionOptionText.setMinLength(8);
        questionOptionText.setMaxLength(128);
        question.setOptions(questionOptionText);
        question.setSurvey(survey);
        return questionDao.save(question);
    }

    private Question createRadioQuestion(Survey survey) {
        Question question = new Question();
        question.setQuestion("Select something...");
        question.setPosition(2);
        question.setRequired(true);
        QuestionOptionRadio questionOptionRadio = new QuestionOptionRadio();
        questionOptionRadio.setRandomize(false);

        QuestionMultiChoice choice1 = new QuestionMultiChoice();
        choice1.setValue("Choice 1");
        choice1.setPosition(1);

        QuestionMultiChoice choice2 = new QuestionMultiChoice();
        choice2.setValue("Choice 2");
        choice2.setPosition(2);

        questionOptionRadio.setChoices(Arrays.asList(choice1, choice2));
        question.setOptions(questionOptionRadio);
        question.setSurvey(survey);
        return questionDao.save(question);
    }

    private SurveyResponse createResponse(Survey survey, Question... questions) {
        SurveyResponse surveyResponse = new SurveyResponse();
        surveyResponse.setName("Response Test");
        surveyResponse.setEmail("response-test@mailinator.com");
        List<QuestionResponse> questionResponses = new ArrayList<>();
        for (Question question : questions) {
            QuestionResponse questionResponse = new QuestionResponse();
            questionResponse.setQuestionUuid(question.getUuid());
            questionResponse.setQuestion(question.getQuestion());
            questionResponse.setResponse(selectOrGenerateResponse(question));
            questionResponse.setSurveyResponse(surveyResponse);
            questionResponses.add(questionResponse);
        }
        surveyResponse.setQuestionResponses(questionResponses);
        surveyResponse.setSurvey(survey);
        return surveyResponseService.create(surveyResponse, false);
    }

    private String selectOrGenerateResponse(Question question) {
        QuestionOption options = question.getOptions();

        if (options instanceof QuestionOptionText) {
            return "1234567890";
        }

        if (options instanceof QuestionOptionRadio) {
            QuestionOptionRadio radio = (QuestionOptionRadio) options;
            List<QuestionMultiChoice> choices = radio.getChoices();

            return choices.iterator().next().getValue();
        }

        return null;
    }
}
