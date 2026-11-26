import { HttpClient } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { environment } from '../../environments/environment';
import { LoginRequest, LoginResponse } from '../models/auth';

// Cuida do login, do token (localStorage) e do perfil do usuário.
@Injectable({ providedIn: 'root' })
export class AuthService {
  private http = inject(HttpClient);
  private router = inject(Router);

  // Signals: a tela (menu, botões) atualiza sozinha quando mudam.
  private token = signal<string | null>(localStorage.getItem('token'));
  private perfil = signal<string | null>(localStorage.getItem('perfil'));

  logado = computed(() => !!this.token());
  ehAdmin = computed(() => this.perfil() === 'ADMIN');

  login(dados: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${environment.apiUrl}/auth/login`, dados).pipe(
      tap((resp) => {
        localStorage.setItem('token', resp.token);
        localStorage.setItem('perfil', resp.perfil);
        this.token.set(resp.token);
        this.perfil.set(resp.perfil);
      }),
    );
  }

  // Apaga o token e o perfil (usado no logout e no erro 401).
  limparSessao() {
    localStorage.removeItem('token');
    localStorage.removeItem('perfil');
    this.token.set(null);
    this.perfil.set(null);
  }

  logout() {
    this.limparSessao();
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return this.token();
  }
}
