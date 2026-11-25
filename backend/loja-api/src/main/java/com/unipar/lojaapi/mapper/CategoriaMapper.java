package com.unipar.lojaapi.mapper;

import com.unipar.lojaapi.dto.CategoriaRequestDto;
import com.unipar.lojaapi.dto.CategoriaResponseDto;
import com.unipar.lojaapi.model.Categoria;

public class CategoriaMapper {

    // Converte entidade -> record
    public static CategoriaResponseDto toResponse(Categoria entidade){
        return new CategoriaResponseDto(
                entidade.getId(),
                entidade.getNome(),
                entidade.getDescricao()
        );
    }
    // Converte record -> entidade
    public static Categoria toEntity(CategoriaRequestDto dto){
        Categoria categoria = new Categoria();
        categoria.setNome(dto.nome());
        categoria.setDescricao(dto.descricao());
        return categoria;
    }


}
