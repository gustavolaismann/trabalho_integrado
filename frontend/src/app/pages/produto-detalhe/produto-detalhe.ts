import { CurrencyPipe } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Produto } from '../../models/produto';
import { AuthService } from '../../services/auth.service';
import { NotificacaoService } from '../../services/notificacao.service';
import { ProdutoService } from '../../services/produto.service';

// Mostra os detalhes de um produto (busca por ID).
@Component({
  selector: 'app-produto-detalhe',
  imports: [RouterLink, CurrencyPipe],
  templateUrl: './produto-detalhe.html',
})
export class ProdutoDetalhe {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private produtoService = inject(ProdutoService);
  private notificacao = inject(NotificacaoService);
  auth = inject(AuthService);

  produto = signal<Produto | null>(null);

  constructor() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.produtoService.buscarPorId(id).subscribe({
      next: (p) => this.produto.set(p),
      // Erro (ex.: 404) já foi mostrado pelo interceptor; volta para a lista.
      error: () => this.router.navigate(['/produtos']),
    });
  }

  excluir(produto: Produto) {
    if (!confirm(`Deseja realmente excluir o produto "${produto.nome}"?`)) {
      return;
    }
    this.produtoService.excluir(produto.id).subscribe(() => {
      this.notificacao.sucesso('Produto excluído com sucesso.');
      this.router.navigate(['/produtos']);
    });
  }
}
