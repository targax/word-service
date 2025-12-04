package com.api_palavras.word_service.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "security.config")
public class SecurityConfig {

    private String key;
    private Long expiration;
    private String prefix;
}
