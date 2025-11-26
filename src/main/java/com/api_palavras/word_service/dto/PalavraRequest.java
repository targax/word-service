package com.api_palavras.word_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PalavraRequest(
        @NotBlank(message = "O campo termo é obrigatório")
        @Size(min = 2, message = "A palavra deve ter ao menos 2 caracteres")
        String termo
) {}
