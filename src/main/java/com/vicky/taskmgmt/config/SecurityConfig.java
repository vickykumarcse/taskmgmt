package com.vicky.taskmgmt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf().disable() // Disable CSRF protection (consider enabling it in production)
        .authorizeHttpRequests()
            .anyRequest().permitAll() // Permit all requests
            .and()
        .formLogin() // Optionally enable form-based authentication
        .and()
        .httpBasic(); // Optionally enable basic authentication

    return http.build();
    }
}
