package com.api_palavras.word_service.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {


    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("Palavra-Service API")
                        .version("1.0.0")
                        .description("""
                                Serviço responsável pela gestão de Palavras em uma arquitetura de microserviços.
                                - Gerenciar o cadastro de Etiquetas (CRUD);
                                - Validar a existência de uma Palavra para o Relationship-Service;
                                - Expor dados básicos para outros microsserviços;
                                """)
                        .contact(new Contact()
                                .name("Bruno Targa")
                                .email("Bruno.Targa@gft.com"))
                )
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT"))
                )
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName)); // ⚡ obrigando JWT
    }
}
