# 🎤 Roteiro da apresentação

> Vídeo de **até 5 minutos**, para as duas matérias (Backend e Frontend).
> Deve mostrar: **código + Swagger/Postman + front**, com login, CRUD, relacionamento e as duas consultas com filtro.
>
> Este arquivo tem duas partes: o **roteiro cronometrado** (o que falar) e o **glossário** (o que estudar antes de gravar).
> Itens marcados com 🔧 dependem de partes ainda não feitas (JWT). Complete quando terminar.

---

## 🎬 Parte 1: Roteiro cronometrado

### 0:00 a 0:30: Abertura
- Apresente-se e diga o tema: **Loja**, com **Categoria** (1) → (N) **Produto**.
- Uma frase sobre a arquitetura: *"API REST em Spring Boot com JPA e JWT, consumida por um front em Angular."*
- Mostre o repositório no GitHub (`backend/` e `frontend/`) e cite os READMEs.

### 0:30 a 2:00: Backend (código)
Mostre as pastas na IDE, uma frase por pacote:

| Pacote | O que dizer |
|---|---|
| `model` | Entidades JPA. `Produto` tem `@ManyToOne` para `Categoria`, que é o lado dono do 1:N (a chave estrangeira fica em `produto`). |
| `repository` | Interfaces do Spring Data. As consultas saem do **nome do método**, como `findByCategoriaId`. |
| `dto` | `record` de entrada e saída. A entidade nunca vai para o JSON. |
| `mapper` | Converte entre entidade e DTO. Não acessa o banco. |
| `service` | Regras de negócio e busca da categoria (`404` se não existir). Usa `@Transactional`. |
| `controller` | Rotas REST, `@Valid` e códigos de status (`201`, `204`). |
| `exception` | `RecursoNaoEncontradoException`, `ErroResponse` e `GlobalExceptionHandler` (`404` e `400`). |
| `security` | 🔧 JWT, filtro, CORS e `SecurityConfig`. |
| `config` | `DataLoader`, que cria os dados de teste ao subir. |

**Abra 2 arquivos e explique de verdade (os mais importantes):**
1. `ProdutoService`: mostre o `adicionarProduto`. A categoria é buscada no Service e entregue pronta ao mapper.
2. `ProdutoRepository`: mostre as duas consultas por nome de método.

### 2:00 a 3:00: Swagger (ou Postman)
Abra `http://localhost:8080/swagger-ui/index.html` e mostre:
1. 🔧 `POST /auth/login` e o token retornado. Depois use o botão **Authorize**.
2. `GET /categorias/{id}/produtos`: **consulta 1**, filtro no path.
3. `GET /produtos?nome=livro&cor=azul`: **consulta 2**, vários parâmetros.
4. Um erro de propósito: `POST /produtos` com `preco: -5` e `nome` vazio. Mostre o `400` com o mapa `campos`.
5. `GET /produtos/999`: mostre o `404` com a mensagem.

### 3:00 a 4:30: Frontend
Navegue pelo front rodando em `http://localhost:4200`:
1. **Sem login:** tente abrir `/produtos` e mostre o redirecionamento para `/login` (guard).
2. **Login** com `admin` / `admin123`.
3. **Listagem** e **detalhes** de um produto.
4. **Cadastro:** abra o formulário, mostre o select de categorias vindo da API (**relacionamento 1:N**). Salve com um campo vazio para mostrar a validação, depois salve certo.
5. **Edição** e **exclusão com confirmação**.
6. **Produtos por categoria** (consulta 1) e **Buscar produtos** com nome e cor (consulta 2).
7. **Logout.** Se der tempo, entre com `usuario` / `user123` e mostre que os botões de criar, editar e excluir somem.

Mostre rápido o código do front em **3 arquivos**: `auth.interceptor.ts` (envia o token), `auth.guard.ts` (protege as rotas) e um service (`produto.service.ts`, onde ficam as chamadas HTTP).

### 4:30 a 5:00: Fechamento
- Resuma os requisitos atendidos: camadas, DTOs com `record`, 1:N, duas consultas, login e validação.
- Cite um ponto de melhoria. Exemplo: *"Faltou tratar nome de categoria duplicado, que hoje gera erro 500 e deveria ser 409."*

> ⏱️ **Dica:** cronometre uma gravação de teste. Se passar de 5 min, corte a edição, a exclusão e a explicação dos pacotes menos importantes.

---

## ✅ Checklist antes de gravar
- [ ] Backend rodando sem erros, com o seed carregado
- [ ] Front rodando (`npx ng serve`) e conectado ao backend
- [ ] Swagger abrindo
- [ ] Usuários de teste anotados: `admin` / `admin123` e `usuario` / `user123`
- [ ] Produto de exemplo para os filtros (`nome=livro&cor=azul`)
- [ ] Abas já abertas na ordem do roteiro
- [ ] Fonte da IDE e do navegador com zoom legível
- [ ] Notificações do Mac desligadas
- [ ] Teste de gravação cronometrado

---

## 📚 Parte 2: Glossário (o que estudar antes de gravar)

Estes são os temas sobre os quais você teve dúvida ao longo do trabalho. Se você perguntou, provavelmente não viu em aula, e o professor pode perguntar. Para cada um há uma explicação curta para você dizer com suas palavras.

### DTO e `record`
- **O que é:** objeto simples que carrega só os dados que entram ou saem da API.
- **Por que usar:** a entidade fica restrita ao banco. Mudar a tabela não quebra a API, e campos sensíveis (como `senha`) não vazam.
- **Por que `record`:** só carrega dados, não muda depois de criado. O compilador gera construtor e getters.
- **Exemplo seu:** `ProdutoResponseDto` mostra `categoriaNome`, que não existe na tabela `produto`.

### Mapper
- **O que é:** um **tradutor** entre entidade e DTO. Recebe o que o Service entrega e converte.
- **Ponto-chave:** o mapper **não busca nada no banco**. Por isso o `toEntity` de produto recebe a `Categoria` já buscada pelo Service.
- **Por que no Service:** a busca é regra de negócio, porque decide o que fazer quando a categoria não existe (`404`).

### Relacionamento 1:N (`@OneToMany` e `@ManyToOne`)
- **Lado dono:** quem tem a chave estrangeira. Aqui é `Produto`, com a coluna `categoria_id`.
- **`mappedBy`:** em `Categoria`, indica que o relacionamento já está mapeado do outro lado.
- **`LAZY`:** a categoria só é carregada quando você acessa. Por isso o `toResponse` roda dentro do Service, com transação aberta.

### `Optional`
- **O que é:** uma caixa que pode estar vazia ou ter um valor.
- **Onde aparece:** `findById` e `findByLogin`, quando buscam **um** registro que pode não existir.
- **Uso:** `.orElseThrow(...)` lança a exceção se estiver vazio, e vira `404`.
- **Por que as listas não usam:** uma busca por vários resultados devolve lista vazia, nunca `null`.

### `@Transactional` e dirty checking
- **O que é:** agrupa as operações do método em uma transação do banco.
- **`readOnly = true`:** nas leituras, para deixar claro e permitir otimização.
- **Dirty checking:** no `atualizarProduto`, o produto buscado fica gerenciado pelo Hibernate. Ao fim da transação, ele percebe a mudança e gera o `UPDATE` sozinho, sem `save`.

### `.toList()` e `Collectors.toList()`
- `.toList()` (Java 16 ou mais novo) devolve lista **imutável** e é mais curto.
- `Collectors.toList()` devolve lista modificável e exige o import.
- Para devolver na resposta, a imutável basta.

### Tratamento de erros (três classes, três papéis)
| Classe | Papel | Tipo |
|---|---|---|
| `RecursoNaoEncontradoException` | O Service **lança** quando algo não existe | Exceção |
| `GlobalExceptionHandler` | **Captura** a exceção e monta a resposta HTTP | `@RestControllerAdvice` |
| `ErroResponse` | O **JSON** do erro que o cliente recebe | `record` |

- **Fluxo:** Service lança → handler captura → devolve `ErroResponse` com o status.
- **`400`:** o `@Valid` rejeita o DTO e o Spring lança `MethodArgumentNotValidException`. O handler monta um mapa `campo → mensagem`.

### `HashMap`
- Estrutura de **chave e valor**, como um dicionário. Aqui: nome do campo → mensagem de erro.
- Cada chave aparece uma vez. Não garante ordem. Vira objeto JSON.
- `Map` é a interface, `HashMap` a implementação.

### Dependências do Jakarta
- `jakarta.persistence` e `jakarta.validation` não aparecem no `pom.xml`, mas existem.
- Vêm de dentro dos starters (`starter-data-jpa`, `starter-validation`), como dependências transitivas.
- Tutoriais antigos usam `javax`, que não vale mais.

### CORS
- O navegador bloqueia chamadas entre origens diferentes (`localhost:4200` para `localhost:8080`).
- O backend precisa autorizar a origem, os métodos e os cabeçalhos.
- **Preflight:** antes de `PUT`, `DELETE` ou requisições com `Authorization`, o navegador faz um `OPTIONS` de verificação. Por isso `OPTIONS` está na lista de métodos.
- Fica no **Security**, e não no controller, porque o filtro de segurança age antes.

### CSRF
- Proteção contra requisições forjadas feita por navegador com sessão.
- Em API que usa token JWT no cabeçalho, ela é desnecessária e fica **desligada**.
- Foi o que causou o `403` nos `POST` do Swagger.

### BCrypt e `PasswordEncoder`
- Senha nunca vai em texto puro para o banco, e sim como **hash**.
- BCrypt é lento de propósito e usa um **sal** aleatório. Por isso a mesma senha gera hashes diferentes, e mesmo assim `matches` consegue conferir.
- `encode` gera o hash (no seed) e `matches` confere (no login).
- `@Bean` registra o `BCryptPasswordEncoder` para o Spring injetar.

### `CommandLineRunner` e o seed
- Roda uma vez quando a aplicação termina de subir. Usado no `DataLoader` para criar usuários, categorias e produtos.
- O H2 em memória **zera a cada reinício**, por isso o seed.
- A guarda `count() > 0` evita duplicar dados se o banco persistir.
- A ordem importa: categorias primeiro, porque o produto precisa de uma categoria já salva.

### 🔧 JWT (completar quando fizer)
- Token assinado que o servidor gera no login e o cliente envia em cada requisição (`Authorization: Bearer ...`).
- O servidor não guarda sessão (`stateless`).
- Três peças: `JwtService` (gera e valida), `JwtFilter` (lê o token a cada requisição) e `SecurityConfig` (quais rotas exigem login).
- Complete aqui: tempo de expiração, o que vai dentro do token, como o perfil vira permissão.

### Frontend (Angular)
- **Standalone components e rotas:** cada tela é um componente, ligado a uma rota.
- **Service:** só ele fala com a API. Nenhum componente usa `HttpClient`.
- **Interceptor:** código que roda em toda chamada HTTP. O de autenticação põe o token no cabeçalho. O de erro mostra a mensagem do `ErroResponse` e trata o `401`.
- **Guard:** bloqueia a rota se não houver token e redireciona para `/login`.
- **Reactive Forms:** formulário com validação declarada no código, antes de enviar.

---

## ❓ Perguntas que o professor pode fazer
1. Por que não devolver a entidade direto no controller?
2. Onde fica a chave estrangeira no seu 1:N, e por quê?
3. Por que a categoria é buscada no Service e não no mapper?
4. O que acontece se o produto não existir? Mostre o fluxo do erro.
5. Por que a senha é guardada com BCrypt?
6. Como o front sabe que o usuário está logado? O que acontece se o token expirar?
7. Por que o CORS foi necessário?
8. As consultas com filtro: como o Spring monta a query só pelo nome do método?
9. Como o front lida com os erros `400`, `401` e `404`?

---

## 🤖 Sobre o uso de IA
O front foi gerado por um agente de IA, e o backend foi feito com acompanhamento de um assistente de IA, com você escrevendo e revisando o código.

Confira a regra das disciplinas sobre uso de IA. Se houver, siga-a. Se não houver, avisar os professores é a opção mais segura. No vídeo, uma frase como *"usei um assistente de IA como apoio nos estudos e para gerar o esqueleto do front, e revisei o código"* é honesta e evita problema depois.

---

## 📝 Anotações livres
-
