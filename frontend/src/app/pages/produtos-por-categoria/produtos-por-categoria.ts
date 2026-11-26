import { CurrencyPipe } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Categoria } from '../../models/categoria';
import { Produto } from '../../models/produto';
import { CategoriaService } from '../../services/categoria.service';

// Consulta 1: escolhe uma categoria e lista os produtos dela.
@Component({
  selector: 'app-produtos-por-categoria',
  imports: [RouterLink, CurrencyPipe],
  templateUrl: './produtos-por-categoria.html',
})
export class ProdutosPorCategoria {
  private categoriaService = inject(CategoriaService);

  categorias = signal<Categoria[]>([]);
  produtos = signal<Produto[]>([]);
  consultou = signal(false);

  constructor() {
    this.categoriaService.listar().subscribe((lista) => this.categorias.set(lista));
  }

  // Chamado quando o usuário muda o select.
  escolher(valor: string) {
    this.consultou.set(false);
    this.produtos.set([]);
    if (!valor) {
      return;
    }
    this.categoriaService.listarProdutos(Number(valor)).subscribe((lista) => {
      this.produtos.set(lista);
      this.consultou.set(true);
    });
  }
}
