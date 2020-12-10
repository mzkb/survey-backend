package com.github.mzkb.survey.app;


import com.github.mzkb.survey.app.api.filter.JwtAuthenticationFilter;
import com.github.mzkb.survey.app.api.filter.JwtAuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@EnableWebSecurity
public class WebSecurityConf extends WebSecurityConfigurerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSecurityConf.class);

    private static final String SIGN_UP_URL = "/publishers";
    private static final String GET_SURVEY = "/surveys/*";
    private static final String GET_SURVEY_RESPONSE = "/responses/*";
    private static final String SURVEY_RESPONSE = "/responses";
    private static final String SWAGGER = "/v2/api-docs";
    private static final String SWAGGER_UI = "/swagger-ui/**";
    private static final String SWAGGER_RESOURCES = "/swagger-resources/**";

    private final SecurityConfig securityConfig;
    private final UserDetailsService userDetailsService;

    public WebSecurityConf(SecurityConfig securityConfig, UserDetailsService userDetailsService) {
        this.securityConfig = securityConfig;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf().disable().authorizeRequests()
                .antMatchers(SWAGGER).permitAll()
                .antMatchers(SWAGGER_UI).permitAll()
                .antMatchers(SWAGGER_RESOURCES).permitAll()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                .antMatchers(HttpMethod.GET, GET_SURVEY).permitAll()
                .antMatchers(HttpMethod.GET, GET_SURVEY_RESPONSE).permitAll()
                .antMatchers(HttpMethod.POST, SURVEY_RESPONSE).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(securityConfig, authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(securityConfig, authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
