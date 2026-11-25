package com.unipar.lojaapi.service;

import com.unipar.lojaapi.dto.ProdutoRequestDto;
import com.unipar.lojaapi.dto.ProdutoResponseDto;
import com.unipar.lojaapi.exception.RecursoNaoEncontradoException;
import com.unipar.lojaapi.mapper.ProdutoMapper;
import com.unipar.lojaapi.model.Categoria;
import com.unipar.lojaapi.model.Produto;
import com.unipar.lojaapi.repository.CategoriaRepository;
import com.unipar.lojaapi.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor // Anotação para não ter que criar um constructor dos repositórios
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;


    private Categoria buscarCategoria(Long id){
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada: " + id));
    }

    @Transactional
    public ProdutoResponseDto adicionarProduto(ProdutoRequestDto dto){
       Categoria categoria = buscarCategoria(dto.categoriaId());

       // Cria um produto a partir do DTO e categoria encontrada
       Produto produto = ProdutoMapper.toEntity(dto, categoria);

       // Salva produto e pega o retorno do repositorio
       Produto salvo = produtoRepository.save(produto);

       // Retorna o produto mascarado (dto)
       return ProdutoMapper.toResponse(salvo);
    }

    @Transactional
    public ProdutoResponseDto atualizarProduto(Long id, ProdutoRequestDto dto){

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado: " + id));

        Categoria categoria = buscarCategoria(dto.categoriaId());

        // chama mapper que realiza o set
        Produto atualizado = ProdutoMapper.atualizar(produto, dto, categoria);

        // Retorna o produto atualizado mascarado (dto)
        return ProdutoMapper.toResponse(atualizado);
    }


    @Transactional(readOnly = true)
    public ProdutoResponseDto buscarProdutoPorId(Long id){
        return produtoRepository.findById(id).map(ProdutoMapper::toResponse)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado: " + id));
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponseDto> listarProdutos(){
        return produtoRepository.findAll().stream().map(ProdutoMapper::toResponse).toList();
    }

    @Transactional
    public void excluirProduto(Long id){

       if (!produtoRepository.existsById(id)){
            throw new RecursoNaoEncontradoException("Produto não encontrado pelo id: " + id);
       }

       produtoRepository.deleteById(id);
    }
}
