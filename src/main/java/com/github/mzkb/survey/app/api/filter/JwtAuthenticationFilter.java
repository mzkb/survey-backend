package com.github.mzkb.survey.app.api.filter;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mzkb.survey.app.SecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final SecurityConfig securityConfig;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(SecurityConfig securityConfig, AuthenticationManager authenticationManager) {
        this.securityConfig = securityConfig;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UsernameAndPassword usernameAndPassword = new ObjectMapper()
                    .readValue(request.getInputStream(), UsernameAndPassword.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            usernameAndPassword.getEmail(),
                            usernameAndPassword.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException("Unable to attempt authentication", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authentication) throws IOException {

        Date expiresAt = new Date(System.currentTimeMillis() + securityConfig.getExpirationTime());

        String token = JWT.create()
                .withSubject(((User) authentication.getPrincipal()).getUsername())
                .withExpiresAt(expiresAt)
                .sign(HMAC512(securityConfig.getSecret().getBytes()));


        // TODO: This is hacky. JSON should be handled better
        // And the response should probably be improved
        PrintWriter responseWriter = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        responseWriter.print(new JwtResponse(token, expiresAt).toJsonString());
        responseWriter.flush();
    }

    // TODO: Extract this class out?
    public static class UsernameAndPassword implements Serializable {
        private String email;
        private String password;

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
    }

    // TODO: Extract this class out?
    public static class JwtResponse implements Serializable {
        private final String jwt;
        private final long expires;

        public JwtResponse(String jwt, Date expires) {
            this.jwt = jwt;
            this.expires = expires.getTime();
        }

        public String getJwt() {
            return jwt;
        }

        public long getExpires() {
            return expires;
        }

        public String toJsonString() {
            return "{" +
                    "\"jwt\": \"" + jwt + "\"," +
                    "\"expires\": \"" + expires + "\"" +
                    '}';
        }
    }
}