package com.github.mzkb.survey.app.service;

import com.github.mzkb.survey.app.model.MailTemplateType;

public interface MailService {

    boolean sendMail(String to, String url, MailTemplateType mailTemplateType);
}
