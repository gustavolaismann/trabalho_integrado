import { CurrencyPipe } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Produto } from '../../models/produto';
import { ProdutoService } from '../../services/produto.service';

// Consulta 2: busca produtos por nome e/ou cor.
@Component({
  selector: 'app-busca-produtos',
  imports: [ReactiveFormsModule, RouterLink, CurrencyPipe],
  templateUrl: './busca-produtos.html',
})
export class BuscaProdutos {
  private fb = inject(FormBuilder);
  private produtoService = inject(ProdutoService);

  produtos = signal<Produto[]>([]);
  consultou = signal(false);

  form = this.fb.nonNullable.group({ nome: '', cor: '' });

  buscar() {
    const { nome, cor } = this.form.getRawValue();
    this.produtoService.listar(nome.trim(), cor.trim()).subscribe((lista) => {
      this.produtos.set(lista);
      this.consultou.set(true);
    });
  }
}
