package com.github.mzkb.survey.app.service;

import com.github.mzkb.survey.app.model.MailTemplateType;

import java.util.Map;

public interface MailTemplateService {

    String getSubject(MailTemplateType mailTemplateType, Map<String, String> properties);

    String getBody(MailTemplateType mailTemplateType, Map<String, String> properties);
}
