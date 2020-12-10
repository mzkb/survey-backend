package com.github.mzkb.survey.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PasswordServiceImpl implements PasswordService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordServiceImpl.class);

    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordServiceImpl() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String hash(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean verify(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
}
