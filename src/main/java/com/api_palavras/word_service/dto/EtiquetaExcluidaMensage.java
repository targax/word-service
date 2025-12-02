package com.api_palavras.word_service.dto;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.Instant;

public record EtiquetaExcluidaMensage(
        Long idEtiqueta,
        Instant timestamp
) {

}
