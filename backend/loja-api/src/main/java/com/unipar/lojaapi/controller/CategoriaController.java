package com.unipar.lojaapi.controller;


import com.unipar.lojaapi.dto.CategoriaRequestDto;
import com.unipar.lojaapi.dto.CategoriaResponseDto;
import com.unipar.lojaapi.dto.ProdutoResponseDto;
import com.unipar.lojaapi.service.CategoriaService;
import com.unipar.lojaapi.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final ProdutoService produtoService;
    private final CategoriaService categoriaService;


    @GetMapping
    public List<CategoriaResponseDto> listarCategorias(){
        return categoriaService.listarCategorias();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaResponseDto criarCategoria(@RequestBody @Valid CategoriaRequestDto categoriaRequestDto){
        return categoriaService.adicionarCategoria(categoriaRequestDto);
    }

    @GetMapping("/{id}/produtos")
    public List<ProdutoResponseDto> listarProdutosPorCategoria(@PathVariable(required = true) Long id){
        return produtoService.listarProdutosPorCategoria(id);
    }

    @GetMapping("/{id}")
    public CategoriaResponseDto buscarCategoriaPorId(@PathVariable(required = true) Long id){
        return categoriaService.buscarCategoriaPorId(id);
    }
}
