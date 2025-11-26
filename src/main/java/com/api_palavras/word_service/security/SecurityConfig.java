package com.api_palavras.word_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // desabilita CSRF
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()  // libera todos os endpoints
                )
                .httpBasic(Customizer.withDefaults()) // não necessário, mas evita erros
                .formLogin(form -> form.disable());  // desabilita tela de login

        return http.build();
    }
}

