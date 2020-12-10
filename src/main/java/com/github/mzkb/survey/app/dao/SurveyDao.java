package com.github.mzkb.survey.app.dao;

import com.github.mzkb.survey.app.model.Publisher;
import com.github.mzkb.survey.app.model.Survey;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SurveyDao extends PagingAndSortingRepository<Survey, Long> {
    Survey findByUuid(String uuid);

    List<Survey> findAllByPublisher(Publisher publisher);
}
