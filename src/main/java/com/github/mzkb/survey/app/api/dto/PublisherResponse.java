package com.github.mzkb.survey.app.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.mzkb.survey.app.model.Publisher;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PublisherResponse implements Serializable {

    private final String uuid;
    private final String created;
    private final String updated;
    private final String name;
    private final String email;

    public PublisherResponse(Publisher publisher) {
        this.uuid = publisher.getUuid();
        this.created = publisher.getCreatedAsString();
        this.updated = publisher.getUpdatedAsString();
        this.name = publisher.getName();
        this.email = publisher.getEmail();
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

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
