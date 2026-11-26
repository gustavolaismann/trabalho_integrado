import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Produto, ProdutoRequest } from '../models/produto';

// Chamadas HTTP de produtos (CRUD e busca).
@Injectable({ providedIn: 'root' })
export class ProdutoService {
  private http = inject(HttpClient);
  private url = `${environment.apiUrl}/produtos`;

  // Sem filtros lista tudo; com nome e/ou cor filtra.
  listar(nome = '', cor = ''): Observable<Produto[]> {
    let params = new HttpParams();
    if (nome) params = params.set('nome', nome);
    if (cor) params = params.set('cor', cor);
    return this.http.get<Produto[]>(this.url, { params });
  }

  buscarPorId(id: number): Observable<Produto> {
    return this.http.get<Produto>(`${this.url}/${id}`);
  }

  criar(dados: ProdutoRequest): Observable<Produto> {
    return this.http.post<Produto>(this.url, dados);
  }

  atualizar(id: number, dados: ProdutoRequest): Observable<Produto> {
    return this.http.put<Produto>(`${this.url}/${id}`, dados);
  }

  excluir(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
