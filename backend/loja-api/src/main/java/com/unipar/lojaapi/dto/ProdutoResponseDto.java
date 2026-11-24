package com.unipar.lojaapi.dto;

import java.math.BigDecimal;

public record ProdutoResponseDto(
        Long id,
        String nome,
        String cor,
        BigDecimal preco,
        Long categoriaId,
        String categoriaNome
) {
}
