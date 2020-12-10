package com.github.mzkb.survey.app.service;

import com.github.mzkb.survey.app.MailConfig;
import com.github.mzkb.survey.app.model.MailTemplateType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class MailServiceImpl implements MailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);
    private static final Map<String, String> EMPTY = new HashMap<>();

    private final MailConfig mailConfig;
    private final MailTemplateService mailTemplateService;
    private final JavaMailSender javaMailSender;

    public MailServiceImpl(MailConfig mailConfig, MailTemplateService mailTemplateService, JavaMailSender javaMailSender) {
        this.mailConfig = mailConfig;
        this.mailTemplateService = mailTemplateService;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public boolean sendMail(String to, String url, MailTemplateType mailTemplateType) {
        // TODO: Could do better error checking here with regard to
        // input values, the config in mailConfig and the template themselves

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);

        message.setFrom(mailConfig.getFrom());
        message.setReplyTo(mailConfig.getReplyTo());

        String subject = mailTemplateService.getSubject(mailTemplateType, EMPTY);
        message.setSubject(subject);

        Map<String, String> bodyProperties = singletonMap(url);
        String body = mailTemplateService.getBody(mailTemplateType, bodyProperties);
        message.setText(body);

        try {
            javaMailSender.send(message);
            return true;
        } catch (MailParseException | MailSendException | MailAuthenticationException e) {
            LOGGER.warn("Unable to send mail", e);
            return false;
        }
    }

    private Map<String, String> singletonMap(String value) {
        Map<String, String> property = new HashMap<>();
        property.put("url", value);
        return property;
    }
}
