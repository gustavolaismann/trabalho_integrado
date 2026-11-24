package com.unipar.lojaapi.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank(message = "O login é obrigatório")
        String login,
        @NotBlank(message = "A senha é obrigatória")
        String senha
) {
}
