package com.github.mzkb.survey.app.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.mzkb.survey.app.model.Publisher;

import java.io.Serializable;

// TODO: Add validation
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublisherCreateDto implements Serializable {

    private String name;
    private String email;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Publisher toPublisher() {
        Publisher publisher = new Publisher();
        publisher.setName(name);
        publisher.setEmail(email);
        publisher.setPassword(password);
        return publisher;
    }
}
