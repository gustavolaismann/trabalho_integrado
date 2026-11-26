import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Categoria } from '../models/categoria';
import { Produto } from '../models/produto';

// Chamadas HTTP de categorias.
@Injectable({ providedIn: 'root' })
export class CategoriaService {
  private http = inject(HttpClient);
  private url = `${environment.apiUrl}/categorias`;

  listar(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(this.url);
  }

  listarProdutos(id: number): Observable<Produto[]> {
    return this.http.get<Produto[]>(`${this.url}/${id}/produtos`);
  }
}
