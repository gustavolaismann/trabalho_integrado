// Corpo enviado no login.
export interface LoginRequest {
  login: string;
  senha: string;
}

// Resposta do login.
export interface LoginResponse {
  token: string;
  login: string;
  perfil: 'ADMIN' | 'COMUM';
}
