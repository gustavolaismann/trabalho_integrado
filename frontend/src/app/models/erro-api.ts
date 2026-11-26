// Formato de erro padrão devolvido pelo backend.
export interface ErroApi {
  status: number;
  mensagem: string;
  campos?: Record<string, string>;
  timestamp: string;
}
