package com.github.mzkb.survey.app.api.endpoint;

import com.github.mzkb.survey.app.api.dto.*;
import com.github.mzkb.survey.app.model.Publisher;
import com.github.mzkb.survey.app.model.Survey;
import com.github.mzkb.survey.app.service.AuthenticationProvider;
import com.github.mzkb.survey.app.service.SurveyResponseService;
import com.github.mzkb.survey.app.service.SurveyService;
import com.github.mzkb.survey.app.service.exception.NotFoundException;
import com.github.mzkb.survey.app.service.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

// TODO: Add validation
@RestController
public class SurveyEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(SurveyEndpoint.class);

    private final SurveyService surveyService;
    private final SurveyResponseService surveyResponseService;
    private final AuthenticationProvider authenticationProvider;

    public SurveyEndpoint(SurveyService surveyService, SurveyResponseService surveyResponseService, AuthenticationProvider authenticationProvider) {
        this.surveyService = surveyService;
        this.surveyResponseService = surveyResponseService;
        this.authenticationProvider = authenticationProvider;
    }

    @PostMapping("/surveys")
    public ResponseEntity<SurveyResponse> newSurvey(@RequestBody SurveyCreateDto surveyCreateDto) {
        Publisher publisher = authenticationProvider.getAuthenticatedPublisher();
        if (publisher == null) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }

        Survey survey = surveyCreateDto.toSurvey(publisher);
        SurveyResponse surveyResponse = new SurveyResponse(surveyService.create(survey));
        return new ResponseEntity<>(surveyResponse, CREATED);
    }

    @PutMapping("/surveys")
    public ResponseEntity<SurveyResponse> updateSurvey(@RequestBody SurveyUpdateDto surveyUpdateDto) throws NotFoundException, UnauthorizedException {
        Publisher publisher = authenticationProvider.getAuthenticatedPublisher();
        if (publisher == null) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }

        Survey survey = surveyUpdateDto.toSurvey(publisher);
        SurveyResponse surveyResponse = new SurveyResponse(surveyService.update(publisher, survey));
        return new ResponseEntity<>(surveyResponse, OK);
    }

    @GetMapping("/surveys/{uuid}")
    public ResponseEntity<SurveyResponse> getSurvey(@PathVariable UUID uuid) throws NotFoundException {
        // TODO: Do we need  JWT to get this survey

        Survey survey = surveyService.get(uuid);
        SurveyResponse surveyResponse = new SurveyResponse(survey);
        return new ResponseEntity<>(surveyResponse, OK);
    }

    @GetMapping("/surveys/{uuid}/responses")
    public ResponseEntity<UserSurveyResponse[]> getSurveyResponses(@PathVariable UUID uuid) throws NotFoundException, UnauthorizedException {
        Publisher publisher = authenticationProvider.getAuthenticatedPublisher();
        if (publisher == null) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }

        Survey survey = surveyService.get(publisher, uuid);

        List<com.github.mzkb.survey.app.model.SurveyResponse> surveyResponses = surveyResponseService.getForSurvey(survey);

        UserSurveyResponse[] userSurveyResponses = new UserSurveyResponse[surveyResponses.size()];
        int i = 0;
        for (com.github.mzkb.survey.app.model.SurveyResponse surveyResponse : surveyResponses) {
            userSurveyResponses[i++] = new UserSurveyResponse(surveyResponse);
        }

        UserSurveyResponse[] userSurveyResponseOrEmpty = Objects.requireNonNullElseGet(userSurveyResponses, () -> new UserSurveyResponse[0]);
        return new ResponseEntity<>(userSurveyResponseOrEmpty, OK);
    }

    @PostMapping("/surveys/{uuid}")
    public ResponseEntity<?> postSurvey(@PathVariable UUID uuid, @RequestBody SurveySendDto surveySendDto) throws NotFoundException, UnauthorizedException {
        Publisher publisher = authenticationProvider.getAuthenticatedPublisher();
        if (publisher == null) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }

        boolean send = surveyService.send(publisher, uuid, surveySendDto.getEmail());
        if (send) {
            return new ResponseEntity<>(OK);
        }

        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/surveys")
    public ResponseEntity<SurveyResponse[]> getSurveys() {
        Publisher publisher = authenticationProvider.getAuthenticatedPublisher();
        if (publisher == null) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }

        List<Survey> surveys = surveyService.getAll(publisher);
        SurveyResponse[] surveyResponses = new SurveyResponse[surveys.size()];
        int i = 0;
        for (Survey survey : surveys) {
            surveyResponses[i++] = new SurveyResponse(survey);
        }

        SurveyResponse[] surveyResponsesOrEmpty = Objects.requireNonNullElseGet(surveyResponses, () -> new SurveyResponse[0]);
        return new ResponseEntity<>(surveyResponsesOrEmpty, OK);
    }

    @DeleteMapping("/surveys/{uuid}")
    public ResponseEntity<?> deleteSurvey(@PathVariable UUID uuid) throws NotFoundException, UnauthorizedException {
        Publisher publisher = authenticationProvider.getAuthenticatedPublisher();
        if (publisher == null) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }

        surveyService.delete(publisher, uuid);
        return new ResponseEntity<>(OK);
    }
}
