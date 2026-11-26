import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { ErroApi } from '../models/erro-api';
import { AuthService } from '../services/auth.service';
import { NotificacaoService } from '../services/notificacao.service';

// Trata todos os erros HTTP em um só lugar e mostra a mensagem na tela.
export const erroInterceptor: HttpInterceptorFn = (req, next) => {
  const notificacao = inject(NotificacaoService);
  const auth = inject(AuthService);
  const router = inject(Router);

  return next(req).pipe(
    catchError((erro: HttpErrorResponse) => {
      if (erro.status === 0) {
        // Status 0 = não chegou ao servidor (backend desligado, CORS, rede).
        notificacao.erro('Não foi possível conectar ao servidor. Verifique sua conexão.');
      } else {
        const corpo = erro.error as Partial<ErroApi> | null;
        let texto = corpo?.mensagem || mensagemPadrao(erro.status);

        // No 400, acrescenta a mensagem de cada campo inválido.
        if (erro.status === 400 && corpo?.campos) {
          const detalhes = Object.entries(corpo.campos).map(([campo, msg]) => `${campo}: ${msg}`);
          texto += ' ' + detalhes.join(' | ');
        }
        notificacao.erro(texto);

        // 401: sessão inválida/expirada, limpa o token e volta para o login.
        if (erro.status === 401) {
          auth.limparSessao();
          router.navigate(['/login']);
        }
      }
      return throwError(() => erro);
    }),
  );
};

// Texto usado quando a API não envia "mensagem".
function mensagemPadrao(status: number): string {
  switch (status) {
    case 400: return 'Dados inválidos.';
    case 401: return 'Sessão inválida ou expirada. Faça login novamente.';
    case 403: return 'Você não tem permissão para esta ação.';
    case 404: return 'Registro não encontrado.';
    case 500: return 'Erro interno no servidor. Tente novamente mais tarde.';
    default: return 'Ocorreu um erro inesperado.';
  }
}
