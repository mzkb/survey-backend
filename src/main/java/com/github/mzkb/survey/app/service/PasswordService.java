package com.github.mzkb.survey.app.service;

public interface PasswordService {

    String hash(String rawPassword);

    boolean verify(String rawPassword, String hashedPassword);
}
