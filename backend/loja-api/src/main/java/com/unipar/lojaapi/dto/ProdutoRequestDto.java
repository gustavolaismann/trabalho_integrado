package com.unipar.lojaapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProdutoRequestDto(
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres.")
        String nome,

        @NotBlank(message = "A cor é obrigatória")
        @Size(min = 2, max = 30, message = "A cor deve ter entre 2 e 30 caracteres.")
        String cor,

        @NotNull(message = "O preço é obrigatório")
        @Positive(message = "O preço deve ser maior que 0")
        BigDecimal preco,

        @NotNull(message = "O id da categoria é obrigatório")
        Long categoriaId
) {
}
