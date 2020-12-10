package com.github.mzkb.survey.app.api.endpoint;

import com.github.mzkb.survey.app.api.dto.UserSurveyCreateDto;
import com.github.mzkb.survey.app.api.dto.UserSurveyResponse;
import com.github.mzkb.survey.app.model.Survey;
import com.github.mzkb.survey.app.model.SurveyResponse;
import com.github.mzkb.survey.app.service.SurveyResponseService;
import com.github.mzkb.survey.app.service.SurveyService;
import com.github.mzkb.survey.app.service.exception.NotFoundException;
import com.github.mzkb.survey.app.service.exception.UnauthorizedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class ResponseEndpoint {

    private final SurveyService surveyService;
    private final SurveyResponseService surveyResponseService;

    public ResponseEndpoint(SurveyService surveyService, SurveyResponseService surveyResponseService) {
        this.surveyService = surveyService;
        this.surveyResponseService = surveyResponseService;
    }

    // TODO: Add validation to validate against survey schema
    @PostMapping("/responses")
    public ResponseEntity<UserSurveyResponse> createResponse(@RequestBody UserSurveyCreateDto userSurveyCreateDto) throws NotFoundException {
        Survey survey = surveyService.get(userSurveyCreateDto.getUuid());
        SurveyResponse surveyResponse = userSurveyCreateDto.toSurveyResponse(survey);
        SurveyResponse response = surveyResponseService.create(surveyResponse);

        UserSurveyResponse userSurveyResponse = new UserSurveyResponse(response);
        return new ResponseEntity<>(userSurveyResponse, CREATED);
    }

    @GetMapping("/responses/{uuid}")
    public ResponseEntity<UserSurveyResponse> getResponse(@PathVariable UUID uuid) throws NotFoundException, UnauthorizedException {
        SurveyResponse surveyResponse = surveyResponseService.get(uuid);
        UserSurveyResponse userSurveyResponse = new UserSurveyResponse(surveyResponse);
        return new ResponseEntity<>(userSurveyResponse, OK);
    }
}
