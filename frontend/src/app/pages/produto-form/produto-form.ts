import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Categoria } from '../../models/categoria';
import { CategoriaService } from '../../services/categoria.service';
import { NotificacaoService } from '../../services/notificacao.service';
import { ProdutoService } from '../../services/produto.service';

// Formulário usado para cadastrar (/produtos/novo) e editar (/produtos/:id/editar).
@Component({
  selector: 'app-produto-form',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './produto-form.html',
})
export class ProdutoForm {
  private fb = inject(FormBuilder);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private produtoService = inject(ProdutoService);
  private categoriaService = inject(CategoriaService);
  private notificacao = inject(NotificacaoService);

  categorias = signal<Categoria[]>([]);
  enviando = signal(false);

  // Se a rota tem :id estamos editando; senão, cadastrando.
  id = Number(this.route.snapshot.paramMap.get('id')) || null;

  // Mesmas regras de validação do backend.
  form = this.fb.group({
    nome: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
    cor: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(30)]],
    preco: this.fb.control<number | null>(null, [Validators.required, Validators.min(0.01)]),
    categoriaId: this.fb.control<number | null>(null, Validators.required),
  });

  constructor() {
    // Categorias do <select> vêm de GET /categorias.
    this.categoriaService.listar().subscribe((lista) => this.categorias.set(lista));

    if (this.id) {
      this.produtoService.buscarPorId(this.id).subscribe({
        next: (p) => this.form.patchValue(p),
        error: () => this.router.navigate(['/produtos']),
      });
    }
  }

  salvar() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    const dados = this.form.getRawValue() as {
      nome: string; cor: string; preco: number; categoriaId: number;
    };

    this.enviando.set(true);
    const requisicao = this.id
      ? this.produtoService.atualizar(this.id, dados)
      : this.produtoService.criar(dados);

    requisicao.subscribe({
      next: () => {
        this.notificacao.sucesso(this.id ? 'Produto atualizado com sucesso.' : 'Produto cadastrado com sucesso.');
        this.router.navigate(['/produtos']);
      },
      // A mensagem de erro já é exibida pelo erroInterceptor.
      error: () => this.enviando.set(false),
    });
  }
}
