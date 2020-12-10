package com.github.mzkb.survey.app.service;

import com.github.mzkb.survey.app.dao.SurveyResponseDao;
import com.github.mzkb.survey.app.model.MailTemplateType;
import com.github.mzkb.survey.app.model.Survey;
import com.github.mzkb.survey.app.model.SurveyResponse;
import com.github.mzkb.survey.app.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class SurveyResponseServiceImpl implements SurveyResponseService {

    private final String baseUrl;
    private final MailService mailService;
    private final SurveyResponseDao surveyResponseDao;

    public SurveyResponseServiceImpl(@Value("${website.url:http://localhost:4200}") String baseUrl,
                                     MailService mailService, SurveyResponseDao surveyResponseDao) {
        this.baseUrl = baseUrl;
        this.mailService = mailService;
        this.surveyResponseDao = surveyResponseDao;
    }

    @Override
    public SurveyResponse create(SurveyResponse surveyResponse) {
        return create(surveyResponse, true);
    }

    @Override
    public SurveyResponse create(SurveyResponse surveyResponse, boolean sendMail) {
        SurveyResponse response = surveyResponseDao.save(surveyResponse);

        String url = baseUrl + "/response/" + response.getUuid().toString();

        if (sendMail) {
            mailService.sendMail(response.getEmail(), url, MailTemplateType.SURVEY_DONE);
        }

        return response;
    }

    @Override
    public SurveyResponse get(UUID uuid) throws NotFoundException {
        SurveyResponse byUuid = surveyResponseDao.findByUuid(uuid.toString());
        if (byUuid == null) {
            throw new NotFoundException();
        }

        return byUuid;
    }

    @Override
    public List<SurveyResponse> getForSurvey(Survey survey) {
        List<SurveyResponse> surveyResponses = surveyResponseDao.findAllBySurvey(survey);
        if (surveyResponses == null) {
            return new ArrayList<>();
        }

        return surveyResponses;
    }
}
