package com.example.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    @Autowired
    private final JwtAthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(cors -> cors.disable())
            .sessionManagement(sessionManagement ->sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests 
                .requestMatchers(HttpMethod.POST, "/api/v1/auth/register/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/auth/authenticate/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/public/user/**").authenticated()
                //.requestMatchers(HttpMethod.GET, "/api/public/user/getUser/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/v1/auth/logout/**").authenticated()
                .anyRequest().denyAll()).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);                                                                      
        return http.build(); 
    }
}