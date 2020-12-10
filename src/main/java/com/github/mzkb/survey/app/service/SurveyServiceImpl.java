package com.github.mzkb.survey.app.service;

import com.github.mzkb.survey.app.dao.QuestionDao;
import com.github.mzkb.survey.app.dao.SurveyDao;
import com.github.mzkb.survey.app.model.MailTemplateType;
import com.github.mzkb.survey.app.model.Publisher;
import com.github.mzkb.survey.app.model.Question;
import com.github.mzkb.survey.app.model.Survey;
import com.github.mzkb.survey.app.service.exception.NotFoundException;
import com.github.mzkb.survey.app.service.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class SurveyServiceImpl implements SurveyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SurveyServiceImpl.class);

    private final String baseUrl;
    private final MailService mailService;
    private final SurveyDao surveyDao;
    private final QuestionDao questionDao;

    public SurveyServiceImpl(@Value("${website.url:http://localhost:4200}") String baseUrl,
                             MailService mailService, SurveyDao surveyDao, QuestionDao questionDao) {
        this.baseUrl = baseUrl;
        this.mailService = mailService;
        this.surveyDao = surveyDao;
        this.questionDao = questionDao;
    }

    @Override
    public Survey create(Survey survey) {
        return surveyDao.save(survey);
    }

    @Override
    public Survey get(String uuid) throws NotFoundException {
        Survey byUuid = surveyDao.findByUuid(uuid);
        if (byUuid == null) {
            throw new NotFoundException();
        }

        return byUuid;
    }

    @Override
    public Survey get(UUID uuid) throws NotFoundException {
        return get(uuid.toString());
    }

    @Override
    public Survey get(Publisher publisher, UUID uuid) throws NotFoundException, UnauthorizedException {
        Survey byUuid = surveyDao.findByUuid(uuid.toString());
        if (byUuid == null) {
            throw new NotFoundException();
        }

        checkIfSurveyBelongsToPublisher(publisher, byUuid.getPublisher());
        return byUuid;
    }

    @Override
    public void delete(Publisher publisher, UUID uuid) throws NotFoundException, UnauthorizedException {
        Survey byUuid = surveyDao.findByUuid(uuid.toString());
        if (byUuid == null) {
            throw new NotFoundException();
        }

        checkIfSurveyBelongsToPublisher(publisher, byUuid.getPublisher());

        surveyDao.delete(byUuid);
    }

    @Override
    public List<Survey> getAll(Publisher publisher) {
        List<Survey> surveys = surveyDao.findAllByPublisher(publisher);
        if (surveys == null) {
            return new ArrayList<>();
        }

        return surveys;
    }

    @Override
    public Survey update(Publisher publisher, Survey survey) throws NotFoundException, UnauthorizedException {
        Survey byUuid = surveyDao.findByUuid(survey.getUuid());
        if (byUuid == null) {
            throw new NotFoundException();
        }

        checkIfSurveyBelongsToPublisher(publisher, byUuid.getPublisher());

        // Update the survey
        byUuid.setTitle(survey.getTitle());
        byUuid.setDescription(survey.getDescription());
        byUuid = surveyDao.save(byUuid);

        Map<String, Question> existing = new HashMap<>();
        byUuid.getQuestions().forEach(q -> existing.put(q.getUuid(), q));

        List<Question> toUpdate = new ArrayList<>();

        // Create/Update the questions
        List<Question> questions = survey.getQuestions();
        for (Question question : questions) {
            if (question.getUuid() != null) {
                Question q = existing.get(question.getUuid());
                q.setQuestion(question.getQuestion());
                q.setOptions(question.getOptions());
                q.setRequired(question.isRequired());
                q.setPosition(question.getPosition());
                toUpdate.add(q);
            } else {
                question.setSurvey(byUuid);
                toUpdate.add(question);
            }
        }

        questionDao.saveAll(toUpdate);

        return byUuid;
    }

    @Override
    public boolean send(Publisher publisher, UUID uuid, String email) throws NotFoundException, UnauthorizedException {
        Survey byUuid = surveyDao.findByUuid(uuid.toString());
        if (byUuid == null) {
            throw new NotFoundException();
        }

        checkIfSurveyBelongsToPublisher(publisher, byUuid.getPublisher());

        String url = baseUrl + "/survey/" + uuid.toString();

        return mailService.sendMail(email, url, MailTemplateType.SURVEY_TAKE);
    }

    private void checkIfSurveyBelongsToPublisher(Publisher fromToken, Publisher fromSurvey) throws UnauthorizedException {
        if (!fromToken.getUuid().equals(fromSurvey.getUuid())) {
            throw new UnauthorizedException();
        }
    }
}
