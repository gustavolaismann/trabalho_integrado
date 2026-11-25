package com.unipar.lojaapi.mapper;

import com.unipar.lojaapi.dto.ProdutoRequestDto;
import com.unipar.lojaapi.dto.ProdutoResponseDto;
import com.unipar.lojaapi.model.Categoria;
import com.unipar.lojaapi.model.Produto;

public class ProdutoMapper {

    // Converte entidade -> record
    public static ProdutoResponseDto toResponse(Produto entidade){
        return new ProdutoResponseDto(
                entidade.getId(),
                entidade.getNome(),
                entidade.getCor(),
                entidade.getPreco(),
                entidade.getCategoria().getId(),
                entidade.getCategoria().getNome()
        );
    }

    // Converte record -> entidade
    public static Produto toEntity(ProdutoRequestDto dto, Categoria categoria){
        Produto produto = new Produto();
        produto.setNome(dto.nome());
        produto.setCor(dto.cor());
        produto.setPreco(dto.preco());
        produto.setCategoria(categoria);
        return produto;

    }

    // Atualiza a entidade existente com os dados do record
    public static Produto atualizar(Produto produto, ProdutoRequestDto dto, Categoria categoria){
        produto.setNome(dto.nome());
        produto.setCor(dto.cor());
        produto.setPreco(dto.preco());
        produto.setCategoria(categoria);
        return produto;
    }
}
