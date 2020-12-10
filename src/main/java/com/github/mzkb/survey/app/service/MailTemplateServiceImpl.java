package com.github.mzkb.survey.app.service;

import com.github.mzkb.survey.app.model.MailTemplateType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

@Service
@Transactional
public class MailTemplateServiceImpl implements MailTemplateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MailTemplateServiceImpl.class);

    @Override
    public String getSubject(MailTemplateType mailTemplateType, Map<String, String> properties) {
        // TODO: Back this using a real templating system like Freemarker
        switch (mailTemplateType) {
            case SURVEY_TAKE:
                return "Please take the survey!";
            case SURVEY_DONE:
                return "Thank you. Survey completed!";
        }

        return null;
    }

    @Override
    public String getBody(MailTemplateType mailTemplateType, Map<String, String> properties) {
        // TODO: Back this using a real templating system like Freemarker
        switch (mailTemplateType) {
            case SURVEY_TAKE:
                return populatePlaceholders("Please take survey at {{url}}", properties);
            case SURVEY_DONE:
                return populatePlaceholders("Survey done.\nPlease see response at {{url}}", properties);
        }

        return null;
    }

    private String populatePlaceholders(String inWithPlaceholders, Map<String, String> properties) {
        String replaced = inWithPlaceholders;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            replaced = replaced.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }

        return replaced;
    }
}
