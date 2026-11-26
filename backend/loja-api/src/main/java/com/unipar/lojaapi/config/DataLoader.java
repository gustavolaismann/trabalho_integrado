package com.unipar.lojaapi.config;


import com.unipar.lojaapi.model.Categoria;
import com.unipar.lojaapi.model.Perfil;
import com.unipar.lojaapi.model.Produto;
import com.unipar.lojaapi.model.Usuario;
import com.unipar.lojaapi.repository.CategoriaRepository;
import com.unipar.lojaapi.repository.ProdutoRepository;
import com.unipar.lojaapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

// Arquivo para configurar um seed dos usuarios, categorias e produtos
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args){

        // guarda para evitar duplicação caso o banco ja esteja populado
        if (usuarioRepository.count() > 0){
            return;
        }

        // Criaçao e salvamento 2 usuarios
        Usuario admin = new Usuario();
        admin.setLogin("admin");
        admin.setSenha(passwordEncoder.encode("admin123"));
        admin.setPerfil(Perfil.ADMIN);

        Usuario cliente = new Usuario();
        cliente.setLogin("usuario");
        cliente.setSenha(passwordEncoder.encode("user123"));
        cliente.setPerfil(Perfil.COMUM);


        usuarioRepository.saveAll(List.of(admin, cliente));

        // Criação das três categorias guardando o retorno do save()
        Categoria catEletronicos = new Categoria();
        catEletronicos.setNome("Eletrônicos");
        catEletronicos = categoriaRepository.save(catEletronicos);

        Categoria catLivros = new Categoria();
        catLivros.setNome("Livros");
        catLivros = categoriaRepository.save(catLivros);

        Categoria catAcessorios = new Categoria();
        catAcessorios.setNome("Acessórios");
        catAcessorios = categoriaRepository.save(catAcessorios);

        // euCriação e salvamento dos cinco produtos vinculados às categorias salvas
        Produto p1 = new Produto();
        p1.setNome("Notebook Pro");
        p1.setPreco(new BigDecimal("4500.00"));
        p1.setCategoria(catEletronicos);
        p1.setCor("Preto");

        Produto p2 = new Produto();
        p2.setNome("Smartphone X");
        p2.setPreco(new BigDecimal("2800.00"));
        p2.setCategoria(catEletronicos);
        p2.setCor("Verde");

        Produto p3 = new Produto();
        p3.setNome("Livro de Backend");
        p3.setPreco(new BigDecimal("120.00"));
        p3.setCategoria(catLivros);
        p3.setCor("Azul");


        Produto p4 = new Produto();
        p4.setNome("Livro Front");
        p4.setPreco(new BigDecimal("40.00"));
        p4.setCategoria(catLivros);
        p4.setCor("Verde");


        Produto p5 = new Produto();
        p5.setNome("Mouse Sem Fio");
        p5.setPreco(new BigDecimal("85.00"));
        p5.setCategoria(catAcessorios);
        p5.setCor("Azul");

        produtoRepository.saveAll(List.of(p1, p2, p3, p4, p5));
    }
}

