package com.github.mzkb.survey.app.dao;

import com.github.mzkb.survey.app.model.Question;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuestionDao extends PagingAndSortingRepository<Question, Long> {
}
