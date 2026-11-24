package com.unipar.lojaapi.repository;

import com.unipar.lojaapi.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByCategoriaId(Long categoriaId);
    List<Produto> findByNomeContainingIgnoreCaseAndCorContainingIgnoreCase(String nome, String cor);
}
