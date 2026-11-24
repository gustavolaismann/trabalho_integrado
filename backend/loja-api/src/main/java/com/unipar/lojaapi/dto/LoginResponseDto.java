package com.unipar.lojaapi.dto;

public record LoginResponseDto(
        String token,
        String login,
        String perfil) {
}
