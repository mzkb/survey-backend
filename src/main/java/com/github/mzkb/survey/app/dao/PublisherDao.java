package com.github.mzkb.survey.app.dao;

import com.github.mzkb.survey.app.model.Publisher;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PublisherDao extends PagingAndSortingRepository<Publisher, Long> {
    Publisher findByUuid(String uuid);

    Publisher findByEmail(String email);
}
