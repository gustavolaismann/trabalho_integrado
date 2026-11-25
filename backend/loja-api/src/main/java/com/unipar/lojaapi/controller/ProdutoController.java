package com.unipar.lojaapi.controller;

import com.unipar.lojaapi.dto.ProdutoRequestDto;
import com.unipar.lojaapi.dto.ProdutoResponseDto;
import com.unipar.lojaapi.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

   private final ProdutoService service;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoResponseDto adicionarProduto(@RequestBody @Valid ProdutoRequestDto produtoRequestDto){
        return service.adicionarProduto(produtoRequestDto);
    }


    @GetMapping
    public List<ProdutoResponseDto>listarProdutos(@RequestParam(required = false) String nome,
                                                  @RequestParam(required = false) String cor){
        return service.listarProdutosPorNomeCor(nome, cor);
    }

    @GetMapping("/{id}")
    public ProdutoResponseDto buscarProdutoPorId(@PathVariable(required = true) Long id){
        return service.buscarProdutoPorId(id);
    }


    @PutMapping("/{id}")
    public ProdutoResponseDto atualizarProduto(@PathVariable(required = true) Long id,
                                               @RequestBody @Valid ProdutoRequestDto produtoRequestDto){
        return service.atualizarProduto(id, produtoRequestDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void excluirProduto(@PathVariable(required = true) Long id){
        service.excluirProduto(id);
    }
}
