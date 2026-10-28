# 📌 API de Lista de Tarefas (Todolist API)
`Projeto desenvolvido com o propósito principal de aprendizado e aprimoramento das habilidades técnicas.`

Este é um projeto simples de API RESTful desenvolvido com Spring Boot para gerenciamento de tarefas. A API permite criar, listar, atualizar e excluir tarefas, oferecendo funcionalidades essenciais de segurança e persistência de dados.

O desenvolvimento deste projeto contou com o auxílio de uma inteligência artificial (Gemini), que forneceu orientações detalhadas sobre o passo a passo para construir a API utilizando Java, sem fornecer códigos prontos. Dessa forma, o aprendizado foi mais prático e profundo, fomentando a compreensão sobre a construção de APIs RESTful com Spring Boot.

---
## 🚀 Tecnologias Utilizadas
O projeto utiliza as seguintes tecnologias e frameworks:

**Spring Boot:** Framework principal para construção da aplicação Java. Simplifica o desenvolvimento de aplicações baseadas em Spring.

**Spring Web:** Módulo do Spring Boot para construir aplicações web, incluindo APIs RESTful.

**Spring Data JPA:** Abstração para simplificar o acesso a bancos de dados relacionais usando JPA (Java Persistence API) e Hibernate.

**H2 Database:** Banco de dados relacional leve embutido, configurado para modo de arquivo (./data/todolist), garantindo a persistência dos dados entre as reinicializações da aplicação. Inclui um console web para visualização de dados.

**Spring Security:** Framework robusto para autenticação e autorização, protegendo os endpoints da API com autenticação básica em memória e controle de acesso baseado em papéis (roles).

**Lombok:** Biblioteca que reduz o código boilerplate em classes Java, gerando automaticamente getters, setters, construtores, etc.

**Maven:** Ferramenta de automação de construção e gerenciamento de dependências do projeto.

**Insomnia:** Clientes HTTP utilizados para testar os endpoints da API.

---

## 🛠️ Configuração e Execução

### Pré-requisitos
- **Java 17+**
- **Apache Maven 3.x**
- **IDE** com suporte a Java/Spring (IntelliJ IDEA, Eclipse, VS Code, etc.)

---
### Passos para execução

**Passos**
1. Clone o repositório:

```bash
git clone https://github.com/AndrewGRM/gerenciador-tarefas-api.git
cd gerenciador-tarefas-api
```

2. Importe o projeto na sua IDE:

  - Abra sua IDE e importe o projeto Maven (pom.xml).

  - Aguarde a IDE baixar todas as dependências.

3. Execute a aplicação:

  - Navegue até a classe principal `TodolistApiApplication.java` (`src/main/java/com/example/todolistapi/TodolistApiApplication.java`).

  - Execute o método main desta classe.

  - A aplicação será iniciada na porta 8080 por padrão.

---
## 🗄️ H2 Database Console

Após iniciar a aplicação, você pode acessar o console web do H2 para visualizar e gerenciar o banco de dados persistido.

  - URL do Console: `http://localhost:8080/h2-console`

  - Configurações de Conexão:
      - JDBC URL: `jdbc:h2:file:./data/todolist`
      - User Name: `sa`
      - Password: (deixe em branco)

  - Clique em `Connect`.

Os dados criados via API serão persistidos na pasta ./data do seu projeto.

---
## 🔒 Segurança (Spring Security)
A API utiliza autenticação básica em memória com dois usuários pré-configurados:

  - Usuário `admin`:
      - Username: `admin`
      - Password: `admin`
      - Roles: `ADMIN` (acesso total: leitura, criação, atualização, exclusão)

  - Usuário `user`:
      - Username: `user`
      - Password: `user`
      - Roles: `USER` (acesso limitado: apenas leitura)

### Regras de Autorização:
  - `GET /tasks` e `GET /tasks/{id}`: Acessível por `USER` e `ADMIN` role.

  - `POST /tasks`, `PUT /tasks/{id}`, `DELETE /tasks/{id}`: Acessível apenas por `ADMIN` role.

  - Qualquer outra requisição: Exige autenticação.

  - `GET /h2-console/**`: Acesso público (sem autenticação) para o console do H2.

---
# 🌐 Endpoints da API
Todos os endpoints estão sob o caminho base /tasks. Use um cliente HTTP como Insomnia ou Postman para interagir com a API.

**Tarefas**
  - `GET /tasks`
     - Descrição: Lista todas as tarefas.
     - Autenticação: Requer USER ou ADMIN role.

  - `GET /tasks/{id}`
      - Descrição: Busca uma tarefa pelo ID.
      - Autenticação: Requer USER ou ADMIN role.

  - `POST /tasks`
      - Descrição: Cria uma nova tarefa.
      - Autenticação: Requer ADMIN role.
      - Validação: Campos title e description são obrigatórios e possuem restrições de tamanho.
      - Corpo da Requisição (JSON):

```JSON
{
    "title": "Comprar mantimentos",
    "description": "Leite, pão, ovos, frutas",
    "completed": false
}
```

  - `PUT /tasks/{id}`
    - Descrição: Atualiza uma tarefa existente pelo ID.
    - Autenticação: Requer ADMIN role.
    - Corpo da Requisição (JSON): (mesmo formato do POST, com os campos a serem atualizados)

  - `DELETE /tasks/{id}`
      - Descrição: Exclui uma tarefa pelo ID.
      - Autenticação: Requer ADMIN role.

---
# ⚠️ Tratamento de Exceções
A API implementa um tratamento de exceções global para fornecer respostas de erro consistentes:

  - `400 Bad Request:` Para erros de validação (ex: `title` ou `description` vazios em `POST/PUT`).
  - `404 Not Found:` Quando um recurso (tarefa) não é encontrado pelo ID.
  - `500 Internal Server Error:` Para erros inesperados no servidor.
  - `401 Unauthorized:` Requisição sem autenticação para endpoint protegido.
  - `403 Forbidden:` Requisição com autenticação, mas sem a role necessária.

---
# 🙏 Suporte
Este projeto foi desenvolvido com o auxílio fundamental do Gemini, uma ferramenta de inteligência artificial. O Gemini forneceu suporte valioso ao:

  - Esclarecer dúvidas técnicas sobre o Spring Boot, Spring Security e outros conceitos.
  - Explicar detalhes de classes, métodos e anotações do framework.
  - Guiar o desenvolvimento em um passo a passo detalhado, permitindo que eu construísse o código de forma autônoma e compreendesse cada etapa, sem a necessidade de ver o código pronto antecipadamente.

