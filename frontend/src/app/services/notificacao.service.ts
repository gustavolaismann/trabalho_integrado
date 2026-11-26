import { Injectable, signal } from '@angular/core';

export interface Mensagem {
  tipo: 'sucesso' | 'erro';
  texto: string;
}

// Serviço simples de notificação: guarda a mensagem exibida no topo da tela (ver app.html).
@Injectable({ providedIn: 'root' })
export class NotificacaoService {
  mensagem = signal<Mensagem | null>(null);
  private timer: ReturnType<typeof setTimeout> | undefined;

  sucesso(texto: string) {
    this.mostrar({ tipo: 'sucesso', texto });
  }

  erro(texto: string) {
    this.mostrar({ tipo: 'erro', texto });
  }

  fechar() {
    this.mensagem.set(null);
  }

  // Mostra a mensagem e a esconde sozinha depois de 6 segundos.
  private mostrar(m: Mensagem) {
    clearTimeout(this.timer);
    this.mensagem.set(m);
    this.timer = setTimeout(() => this.fechar(), 6000);
  }
}
