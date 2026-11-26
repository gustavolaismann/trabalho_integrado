package com.unipar.lojaapi.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErroResponse(
        int status,
        String mensagem,
        Map<String, String> campos,
        LocalDateTime timestamp
){
    // Construtor para erros sem detalhe de campos(404 e outros)
    public ErroResponse(int status, String mensagem, LocalDateTime timestamp){
        this(status, mensagem, null, timestamp);
    }
}