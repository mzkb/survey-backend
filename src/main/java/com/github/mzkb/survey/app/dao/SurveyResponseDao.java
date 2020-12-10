package com.github.mzkb.survey.app.dao;

import com.github.mzkb.survey.app.api.dto.UserSurveyResponse;
import com.github.mzkb.survey.app.model.Survey;
import com.github.mzkb.survey.app.model.SurveyResponse;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.UUID;

public interface SurveyResponseDao extends PagingAndSortingRepository<SurveyResponse, Long> {
    SurveyResponse findByUuid(String uuid);

    List<SurveyResponse> findAllBySurvey(Survey survey);
}
