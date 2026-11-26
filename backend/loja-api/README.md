# Loja API (Backend)

API REST de uma loja com **Categoria** (1) → (N) **Produto**, login com **JWT** e perfis de acesso (`ADMIN` e `COMUM`).

Trabalho integrado (Desenvolvimento Backend + Fundamentos de Programação para Web), UNIPAR.

## Tecnologias
- Java 25 e Spring Boot 4
- Spring Web, Spring Data JPA, Bean Validation e Spring Security
- Banco H2 em memória
- JWT (jjwt 0.12)
- Swagger (springdoc-openapi)
- Maven (wrapper incluído)

## Como rodar

### Pré-requisitos
- **JDK 25** instalado. O `pom.xml` define `java.version` como 25. Para usar outra versão (21 ou mais nova), altere `<java.version>` e `<source>`/`<target>` no `pom.xml`.
- Não precisa instalar Maven nem banco de dados.

### Passos
```bash
cd backend/loja-api
./mvnw spring-boot:run
```
No Windows: `mvnw.cmd spring-boot:run`.

A API sobe em **http://localhost:8080**.

Também dá para rodar a classe `LojaApiApplication` pelo IntelliJ.

## Swagger (documentação e testes)
**http://localhost:8080/swagger-ui/index.html**

Para testar rotas protegidas:
1. Em `POST /auth/login`, envie um usuário de teste (abaixo) e copie o `token` da resposta.
2. Clique em **Authorize**, cole **apenas o token** (sem a palavra "Bearer") e confirme.
3. Execute os endpoints normalmente.

## Usuários de teste
Criados automaticamente ao subir a aplicação (`DataLoader`).

| Login | Senha | Perfil | Pode |
|---|---|---|---|
| `admin` | `admin123` | `ADMIN` | Consultar, criar, alterar e excluir |
| `usuario` | `user123` | `COMUM` | Apenas consultar |

## Dados iniciais
**Categorias:** Eletrônicos, Livros, Acessórios.

| Produto | Cor | Preço | Categoria |
|---|---|---|---|
| Notebook Pro | Preto | 4500,00 | Eletrônicos |
| Smartphone X | Verde | 2800,00 | Eletrônicos |
| Livro de Backend | Azul | 120,00 | Livros |
| Livro Front | Verde | 40,00 | Livros |
| Mouse Sem Fio | Azul | 85,00 | Acessórios |

> O banco H2 fica em memória. **Os dados são recriados a cada reinício**, e o que você cadastrar se perde.

## Endpoints

| Método | Rota | Descrição | Acesso |
|---|---|---|---|
| `POST` | `/auth/login` | Autentica e devolve o JWT | Público |
| `GET` | `/categorias` | Lista categorias | Logado |
| `GET` | `/categorias/{id}/produtos` | **Consulta 1:** produtos de uma categoria (filtro no path) | Logado |
| `POST` | `/categorias` | Cria categoria | `ADMIN` |
| `GET` | `/produtos` | Lista produtos. Com `?nome=&cor=` filtra (**Consulta 2**) | Logado |
| `GET` | `/produtos/{id}` | Busca produto por ID | Logado |
| `POST` | `/produtos` | Cria produto | `ADMIN` |
| `PUT` | `/produtos/{id}` | Atualiza produto | `ADMIN` |
| `DELETE` | `/produtos/{id}` | Exclui produto | `ADMIN` |

As rotas protegidas exigem o cabeçalho `Authorization: Bearer <token>`.

### Exemplos
```
POST /auth/login
{ "login": "admin", "senha": "admin123" }
→ { "token": "...", "login": "admin", "perfil": "ADMIN" }

GET /produtos?nome=livro&cor=azul      → Livro de Backend
GET /produtos?cor=verde                → Smartphone X e Livro Front
GET /categorias/2/produtos             → produtos de "Livros"

POST /produtos
{ "nome": "Teclado", "cor": "Preto", "preco": 150.00, "categoriaId": 3 }
```
Os filtros `nome` e `cor` são opcionais, buscam por trecho do texto e não diferenciam maiúsculas de minúsculas.

## Respostas de erro
Todos os erros seguem o mesmo formato:
```json
{
  "status": 404,
  "mensagem": "Produto não encontrado: 999",
  "timestamp": "2026-11-26T10:00:00"
}
```
No `400` (validação), vem também o mapa `campos`:
```json
{
  "status": 400,
  "mensagem": "Dados inválidos",
  "campos": { "preco": "O preço deve ser maior que 0" },
  "timestamp": "2026-11-26T10:00:00"
}
```

| Status | Quando |
|---|---|
| `400` | Dados inválidos no corpo da requisição |
| `401` | Sem token, token inválido, ou login e senha incorretos |
| `403` | Usuário `COMUM` tentando criar, alterar ou excluir |
| `404` | Produto ou categoria inexistente |

## Estrutura
```
com.unipar.lojaapi
├── controller   Rotas REST
├── service      Regras de negócio e transações
├── repository   Acesso ao banco (Spring Data JPA)
├── model        Entidades JPA e enum Perfil
├── dto          Records de entrada e saída (a entidade nunca vai no JSON)
├── mapper       Conversão entre entidade e DTO
├── exception    Exceções, ErroResponse e tratamento global de erros
├── security     JWT (JwtService e JwtFilter) e SecurityConfig (CORS, rotas e perfis)
└── config       DataLoader (seed) e OpenApiConfig (botão Authorize do Swagger)
```

## Configuração
Em `src/main/resources/application.properties`:

| Propriedade | Para que serve |
|---|---|
| `jwt.secret` | Chave de assinatura do token (mínimo de 32 caracteres) |
| `jwt.expiracao-ms` | Validade do token, em milissegundos (`7200000` = 2 horas) |

> A chave do JWT está no arquivo apenas por ser um projeto acadêmico. Em produção, ela deveria vir de uma variável de ambiente.

O CORS está liberado para **http://localhost:4200** (o front em Angular).

## Limitações conhecidas
- Cadastrar duas categorias com o mesmo nome gera erro `500` (a restrição `unique` do banco rejeita, mas não há tratamento específico para `409`).
- Não há cadastro de usuários pela API. Os usuários vêm do seed.
- Não há `PUT` nem `DELETE` para categorias.
