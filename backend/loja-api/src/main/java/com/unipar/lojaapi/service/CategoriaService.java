package com.unipar.lojaapi.service;

import com.unipar.lojaapi.dto.CategoriaRequestDto;
import com.unipar.lojaapi.dto.CategoriaResponseDto;
import com.unipar.lojaapi.exception.RecursoNaoEncontradoException;
import com.unipar.lojaapi.mapper.CategoriaMapper;
import com.unipar.lojaapi.model.Categoria;
import com.unipar.lojaapi.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {


    private final CategoriaRepository categoriaRepository;

    @Transactional
    public CategoriaResponseDto adicionarCategoria(CategoriaRequestDto categoriaRequestDto){
        Categoria categoria = CategoriaMapper.toEntity(categoriaRequestDto);

        Categoria salvo = categoriaRepository.save(categoria);
        return CategoriaMapper.toResponse(salvo);
    }

    @Transactional(readOnly = true)
    public CategoriaResponseDto buscarCategoriaPorId(Long id){
        return categoriaRepository.findById(id).map(CategoriaMapper::toResponse)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada: " + id));

    }

    @Transactional(readOnly = true)
    public List<CategoriaResponseDto> listarCategorias(){
        return categoriaRepository.findAll().stream().map(CategoriaMapper::toResponse).toList();

    }


}
