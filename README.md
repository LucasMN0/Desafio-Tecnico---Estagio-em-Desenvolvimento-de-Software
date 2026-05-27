# Task Manager API 📋

API REST para gerenciamento de tarefas corporativas, desenvolvida como desafio técnico para vaga de estágio em desenvolvimento de software.

---

## 📋 Visão Geral

Sistema de gerenciamento de tarefas (TODO list corporativo) que permite criar, listar, editar e acompanhar o status de tarefas. Possui cálculo automático de tarefas atrasadas com base na data limite.

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão | Função |
|------------|--------|--------|
| Java | 21 (LTS) | Linguagem principal |
| Spring Boot | 3.2.5 | Framework web |
| Maven | 3.6.x | Gerenciamento de dependências |
| H2 Database | - | Banco de dados em arquivo |
| Lombok | - | Redução de boilerplate |
| Springdoc OpenAPI | 2.3.0 | Documentação Swagger |
| JUnit 5 + Mockito | - | Testes automatizados |
| Docker + Docker Compose | - | Containerização |

---

## ✅ Pré-requisitos

### Para rodar localmente:
- Java 21+
- Maven 3.6+

### Para rodar com Docker:
- Docker
- Docker Compose

---

## 🚀 Como Executar

### Opção 1 - Local (Maven)

```bash
# Clonar o repositório
git clone https://github.com/LucasMN0/Desafio-Tecnico---Estagio-em-Desenvolvimento-de-Software.git
cd task-manager

# Rodar a aplicação
mvn spring-boot:run
```

Acesse: `http://localhost:8080/api/tasks`

### Opção 2 - Docker

```bash
# Clonar o repositório
git clone https://github.com/LucasMN0/Desafio-Tecnico---Estagio-em-Desenvolvimento-de-Software.git
cd task-manager

# Build e iniciar
docker compose up --build
```

Acesse: `http://localhost:8080/api/tasks`

---

## 📚 Documentação da API

### Swagger UI

Acesse: `http://localhost:8080/swagger-ui.html` 
### Endpoints disponíveis

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/tasks` | Criar nova tarefa |
| GET | `/api/tasks` | Listar todas as tarefas com e sem filtros de busca|
| GET | `/api/tasks/{id}` | Buscar tarefa por ID |
| PUT | `/api/tasks/{id}` | Editar tarefa completa |
| PATCH | `/api/tasks/{id}/status` | Alterar apenas o status |

### Status disponíveis
- `A_FAZER`
- `EM_PROGRESSO`
- `ATRASADO` (calculado automaticamente)
- `CONCLUIDO`

### Exemplo de request (POST /api/tasks)

```json
{
  "titulo": "Implementar autenticação",
  "descricao": "Adicionar JWT ao projeto",
  "status": "A_FAZER",
  "responsavel": "Lucas",
  "dataLimite": "2026-06-15"
}
```

### Exemplo de response

```json
{
  "id": 1,
  "titulo": "Implementar autenticação",
  "descricao": "Adicionar JWT ao projeto",
  "status": "A_FAZER",
  "responsavel": "Lucas",
  "dataCriacao": "2026-05-26T10:30:00",
  "dataLimite": "2026-06-15"
}
```

---

## 🧪 Como Testar

### Testes automatizados
```bash
mvn clean test
```

### H2 Console (banco de dados)

Acesse: `http://localhost:8080/h2-console` 
- **JDBC URL:** `jdbc:h2:file:./data/taskdb`
- **User:** `sa`
- **Password:** (vazio)

### Coleção Postman
Importe o arquivo `postman/task-manager-collection.json` no Postman para ter todos os endpoints prontos para teste.

---

## 🏗️ Decisões Técnicas

### Por que H2 em vez de PostgreSQL?
H2 não requer instalação ou configuração externa, permitindo foco total na lógica de negócio durante o desenvolvimento. A arquitetura com JPA garante que a migração para PostgreSQL seria trivial - apenas configurações no `application.yml`, zero impacto no código Java.

### Por que arquitetura em camadas?
A separação em Controller → Service → Repository garante responsabilidades claras:
- **Controller**: recebe requisições e delega
- **Service**: contém toda a lógica de negócio
- **Repository**: único ponto de acesso ao banco

### Por que DTOs separados da entidade?
Evita expor detalhes internos do banco de dados na API. O `TaskRequestDTO` controla o que o usuário pode enviar, e o `TaskResponseDTO` controla o que a API retorna.

### Por que status ATRASADO calculado em memória?
O status ATRASADO depende da data de hoje, que muda constantemente. Calculá-lo em tempo de execução garante que sempre esteja correto, sem necessidade de jobs agendados para atualizar o banco.

---

## ⚠️ Limitações Conhecidas

- Sem autenticação ou autorização de usuários
- Sem paginação na listagem de tarefas
- Banco H2 no Docker não persiste dados entre containers (sem volume configurado)

---

## 🔮 Próximos Passos

- Implementar autenticação com JWT
- Migrar para PostgreSQL com Docker Compose
- Adicionar paginação na listagem
- Implementar soft delete (deletar tarefa)
- Adicionar notificações de tarefas próximas do vencimento

---

## 🤖 Uso de IA no Desenvolvimento

Durante o desenvolvimento, utilizei Claude (Anthropic) e Claude Code para:

### Aceleração de Setup:
- Configuração inicial do Spring Boot e dependências
- Configuração do Swagger/OpenAPI
- Geração de boilerplate (estrutura de pastas, configurações)
- Docker e docker-compose

### Trabalho em Conjunto (IA auxiliando, eu decidindo e revisando):
- Modelagem da entidade Task e DTOs
- Implementação do Controller e endpoints
- Exception handlers e validações

### Implementado por mim:
- Lógica de negócio do Service layer
- Cálculo automático do status ATRASADO
- Testes automatizados (cenários definidos e implementados por mim)
- Testes manuais no Postman
- Correção de bugs encontrados durante os testes
- Refatoração do método de busca por ID (DRY principle)

### O que NÃO foi feito por IA:
- Decisões arquiteturais
- Definição dos endpoints e contratos
- Lógica de negócio crítica
- Cenários de teste
- Filtros e buscas
- Identificação e correção de bugs

> A IA foi uma ferramenta de produtividade. Todo código foi revisado, compreendido e adaptado conforme necessário.