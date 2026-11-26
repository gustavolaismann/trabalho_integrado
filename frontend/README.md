# Loja Web (Frontend Angular)

Frontend do trabalho integrado (UNIPAR). Consome a API Spring Boot de produtos e categorias.
Feito com Angular 21, componentes standalone, rotas, Reactive Forms e HttpClient (sem bibliotecas de UI).

## Pré-requisitos
- Node.js 22.22+ ou 24.15+ (testado com Node 24) e npm 11
- Backend rodando em `http://localhost:8080`

## Como executar
```bash
npm install
npx ng serve
```
Acesse: http://localhost:4200

Para gerar a versão de produção: `npx ng build` (saída em `dist/`).

## URL do backend
Fica em um único lugar: `src/environments/environment.ts` (campo `apiUrl`). Para mudar, edite esse arquivo.
O backend precisa liberar CORS para `http://localhost:4200`.

## Usuários de teste
| Usuário | Senha | Perfil |
|---------|-------|--------|
| admin | admin123 | ADMIN (cria, edita e exclui) |
| usuario | user123 | COMUM (somente consulta) |

## Estrutura de pastas (`src/app`)
- `models/`: interfaces TypeScript que representam o JSON da API (Produto, Categoria, login, erro).
- `services/`: únicos arquivos que fazem chamadas HTTP (`produto`, `categoria`, `auth`) e o serviço de mensagens na tela (`notificacao`).
- `guards/`: `auth.guard.ts` bloqueia rotas sem token e redireciona para `/login`.
- `interceptors/`: `auth.interceptor.ts` envia o token em toda requisição; `erro.interceptor.ts` trata erros (400, 401, 403, 404, 500 e falha de rede) em um só lugar.
- `pages/`: telas (login, lista, detalhe, formulário de produto, produtos por categoria, busca).
- `app.routes.ts`: rotas. `app.config.ts`: configuração do HttpClient e interceptors. `app.html`: menu e área de mensagens.
- `src/environments/`: URL da API.
