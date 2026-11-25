package com.unipar.lojaapi.controller;


import com.unipar.lojaapi.dto.ProdutoResponseDto;
import com.unipar.lojaapi.service.CategoriaService;
import com.unipar.lojaapi.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final ProdutoService produtoService;
    private final CategoriaService categoriaService;


    @GetMapping("/{id}/produtos")
    public List<ProdutoResponseDto> listarProdutosPorCategoria(@PathVariable(required = true) Long id){
        return produtoService.listarProdutosPorCategoria(id);
    }
}
