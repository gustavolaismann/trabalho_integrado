import { CurrencyPipe } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Produto } from '../../models/produto';
import { AuthService } from '../../services/auth.service';
import { NotificacaoService } from '../../services/notificacao.service';
import { ProdutoService } from '../../services/produto.service';

// Lista todos os produtos e permite ver, editar e excluir (com confirmação).
@Component({
  selector: 'app-produto-lista',
  imports: [RouterLink, CurrencyPipe],
  templateUrl: './produto-lista.html',
})
export class ProdutoLista {
  private produtoService = inject(ProdutoService);
  private notificacao = inject(NotificacaoService);
  auth = inject(AuthService);

  produtos = signal<Produto[]>([]);

  constructor() {
    this.carregar();
  }

  carregar() {
    this.produtoService.listar().subscribe((lista) => this.produtos.set(lista));
  }

  excluir(produto: Produto) {
    if (!confirm(`Deseja realmente excluir o produto "${produto.nome}"?`)) {
      return;
    }
    this.produtoService.excluir(produto.id).subscribe(() => {
      this.notificacao.sucesso('Produto excluído com sucesso.');
      this.carregar();
    });
  }
}
