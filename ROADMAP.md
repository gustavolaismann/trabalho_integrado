# 🗺️ Roadmap: Trabalho Integrado (Backend + Frontend)

> **UNIPAR · 4º Período** · Desenvolvimento Backend + Fundamentos de Programação para Web (Frontend)
> **Tema:** 🛒 Loja (Categoria 1:N Produto) · **Grupo:** individual (limite do enunciado: até 3)

---

## 📌 Visão geral

| Item | Definição |
|---|---|
| **Backend** | Spring Boot + Spring Data JPA + Spring Security (JWT) |
| **Frontend** | Angular consumindo a API do backend |
| **Banco** | H2 (dev). Pode trocar por MySQL ou PostgreSQL depois |
| **Entidades** | `Categoria` (1) → (N) `Produto`, mais `Usuario` (login) |
| **CRUD completo** | `Produto` |
| **Entrega** | Link do git (com README) e vídeo de até 5 min |

### 🔗 Endpoints planejados

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/auth/login` | Autentica e devolve o token JWT |
| `GET` | `/categorias` | Lista categorias (alimenta o select) |
| `GET` | `/categorias/{id}/produtos` | **Consulta 1:** filtro no path |
| `GET` | `/produtos?nome=&cor=` | **Consulta 2:** múltiplos parâmetros |
| `GET` | `/produtos` | Lista produtos |
| `GET` | `/produtos/{id}` | Busca por ID |
| `POST` | `/produtos` | Cria |
| `PUT` | `/produtos/{id}` | Atualiza |
| `DELETE` | `/produtos/{id}` | Exclui |

---

## 📊 Progresso geral

- [ ] **Parte A:** Backend *(em andamento: A0 e A1 concluídas, A2 parcial)*
- [ ] **Parte B:** Frontend
- [ ] **Parte C:** Entrega

> 📍 **Parou em:** A2, faltam os 3 repositories (`CategoriaRepository`, `ProdutoRepository`, `UsuarioRepository`).

> 💡 O front consome a API, então termine pelo menos o CRUD e o login do backend antes de avançar muito no Angular.

---

## 🟦 PARTE A: BACKEND (Spring Boot)

### A0. Definições
- [X] Confirmar o tema **Loja** 
- [X] Desenhar o modelo de dados (Categoria, Produto, Usuario) com seus atributos → ver [MODELAGEM.md](MODELAGEM.md)
- [X] Listar os endpoints no papel
- [X] Criar o repositório git com as pastas `/backend` e `/frontend`
- [X] Conferir se o `.gitignore` está na raiz **antes do primeiro commit** (já cobre `.DS_Store`)

### A1. Projeto base
- [X] Gerar o projeto em [start.spring.io](https://start.spring.io) (Web, Data JPA, H2, Validation, Security)
- [X] Subir a aplicação vazia sem erros
- [X] Criar os pacotes `controller`, `service`, `repository`, `model`, `dto`

### A2. Entidades e Repository
- [x] Entidade `Categoria` com `@OneToMany`
- [x] Entidade `Produto` com `@ManyToOne`
- [x] Entidade `Usuario` (login, senha e perfil)
- [x] Enum `Perfil` (`ADMIN`, `COMUM`)
- [ ] `CategoriaRepository`
- [ ] `ProdutoRepository`
- [ ] `UsuarioRepository`

> 📚 **Estudar:** mapeamento JPA, lado dono do relacionamento, lazy e eager.

### A3. DTOs (`record`)
- [ ] `CategoriaRequest` e `CategoriaResponse`
- [ ] `ProdutoRequest` e `ProdutoResponse`
- [ ] `LoginRequest` e `LoginResponse` (token)
- [ ] Conferir se nenhuma entidade é usada como corpo de requisição ou resposta

### A4. Service e Controller (CRUD de Produto)
- [ ] Criar (`POST`)
- [ ] Listar (`GET`)
- [ ] Buscar por ID (`GET /{id}`)
- [ ] Atualizar (`PUT`)
- [ ] Excluir (`DELETE`)
- [ ] Regras de negócio na camada **Service** (ex.: preço > 0, categoria existente)
- [ ] Validações com Bean Validation (`@NotBlank`, `@Positive`...)
- [ ] `@ControllerAdvice` tratando 400 e 404 com mensagens claras
- [ ] CRUD básico de Categoria (ao menos listar e criar)

### A5. Consultas com filtros
- [ ] `GET /categorias/{id}/produtos` (filtro no **path**)
- [ ] `GET /produtos?nome=&cor=` (**múltiplos parâmetros**)
- [ ] Testar combinações (só nome, só cor, ambos, nenhum)

### A6. Login e segurança
- [ ] `PasswordEncoder` (BCrypt) para as senhas
- [ ] `POST /auth/login` devolvendo o JWT
- [ ] Filtro JWT validando o token nas requisições
- [ ] `SecurityFilterChain` protegendo os endpoints
- [ ] Usuário inicial criado (`data.sql` ou seed)
- [ ] ⭐ **Extra (0,5):** perfis `ADMIN` e `COMUM` (ex.: só admin exclui)

> 📚 **Estudar:** JWT, filtros do Spring Security, autenticação e autorização.

### A7. CORS e documentação
- [ ] Liberar CORS para `http://localhost:4200`
- [ ] Adicionar Swagger (springdoc-openapi)
- [ ] Testar todos os endpoints no Swagger ou Postman
- [ ] README do backend explicando como rodar

---

## 🟥 PARTE B: FRONTEND (Angular)

### B1. Base do projeto
- [ ] Instalar Node e Angular CLI
- [ ] Criar o projeto Angular
- [ ] Estruturar as pastas `pages/`, `services/`, `guards/`, `interceptors/`, `models/`
- [ ] Configurar as rotas
- [ ] Criar o menu de navegação

> 📚 **Estudar:** componentes, standalone ou módulos, roteamento.

### B2. Login e segurança
- [ ] `AuthService` (login, logout, guardar o token)
- [ ] Tela de login
- [ ] `HttpInterceptor` enviando `Authorization: Bearer <token>`
- [ ] `AuthGuard` redirecionando para o login sem sessão
- [ ] Botão de **logout** no menu

> 📚 **Estudar:** interceptors, guards, `localStorage`.

### B3. Serviços de API
- [ ] Interfaces dos modelos (`Produto`, `Categoria`)
- [ ] `ProdutoService` com `HttpClient`
- [ ] `CategoriaService` com `HttpClient`
- [ ] ⚠️ Nenhuma chamada HTTP dentro de componentes

### B4. CRUD de Produto
- [ ] Tela de **listagem**
- [ ] Tela de **detalhes** (busca por ID)
- [ ] **Formulário** de cadastro
- [ ] **Formulário** de edição (reaproveitando o mesmo componente)
- [ ] **Exclusão** com confirmação

> 📚 **Estudar:** Reactive Forms, `ActivatedRoute`, Observables.

### B5. Relacionamento 1:N
- [ ] Select de categorias carregado da API no formulário de produto
- [ ] Exibir o nome da categoria na listagem e nos detalhes

### B6. Consultas com filtros
- [ ] Tela de produtos de uma categoria (consulta no **path**)
- [ ] Formulário de busca com nome e cor (**múltiplos parâmetros**)

### B7. Validação e feedback
- [ ] Validar campos obrigatórios antes de enviar
- [ ] Mensagens de **sucesso**
- [ ] Mensagens de erro **400**
- [ ] Mensagens de erro **401** (logout e redirecionamento)
- [ ] Mensagens de erro **404**
- [ ] Mensagem de erro genérica para 500 e falha de rede
- [ ] Interceptor de erros centralizado

### B8. Finalização do front
- [ ] Remover qualquer mock ou dado fixo
- [ ] Testar o fluxo completo com o backend rodando
- [ ] README do frontend explicando como rodar

---

## 🟩 PARTE C: ENTREGA

### C1. Repositório
- [ ] Código do backend e do frontend no git
- [ ] README geral com os passos para rodar cada parte
- [ ] Usuário e senha de teste documentados no README
- [ ] Link do repositório conferido (acesso público ou permitido ao professor)

### C2. Vídeo (até 5 min)
- [ ] Roteiro escrito e cronometrado
- [ ] Mostrar o **código** (camadas, DTOs, services)
- [ ] Mostrar o **Swagger/Postman**
- [ ] Mostrar o **front**: login
- [ ] Mostrar o **front**: CRUD completo
- [ ] Mostrar o **front**: relacionamento (select de categoria)
- [ ] Mostrar o **front**: consulta no path
- [ ] Mostrar o **front**: consulta com filtros
- [ ] Vídeo com no máximo 5 min

### C3. Revisão final
- [ ] Todos os requisitos do **Backend** conferidos
- [ ] Todos os requisitos do **Frontend** conferidos
- [ ] Entrega feita no prazo

---

## ✅ Conferência dos requisitos dos professores

### Backend
- [ ] Spring Data JPA
- [ ] Camadas Repository, Service e Controller
- [ ] DTOs com `record` (sem entidades expostas)
- [ ] CRUD completo de pelo menos um recurso
- [ ] Relacionamento 1:N persistido
- [ ] Consulta com filtro no path
- [ ] Consulta com múltiplos parâmetros
- [ ] Login
- [ ] ⭐ Perfis admin/comum (extra)

### Frontend
- [ ] Angular
- [ ] Todos os dados vêm da API (sem mock)
- [ ] CORS configurado
- [ ] Login com token, rotas protegidas e logout
- [ ] CRUD completo com confirmação de exclusão
- [ ] Relacionamento 1:N na interface
- [ ] As duas consultas com filtro
- [ ] Validação e mensagens de sucesso e erro
- [ ] Código organizado (components, services, rotas)
- [ ] Menu e navegação

---

