package com.github.mzkb.survey.app.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
public class Survey extends BaseEntity implements Serializable {

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = true, length = 1024)
    private String description;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
    private List<Question> questions;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
    private List<SurveyResponse> responses;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<SurveyResponse> getResponses() {
        return responses;
    }

    public void setResponses(List<SurveyResponse> responses) {
        this.responses = responses;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
}
