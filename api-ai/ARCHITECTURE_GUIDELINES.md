# 🧭 Software Architecture & Development Guidelines

Este documento define as **boas práticas, padrões de projeto e diretrizes arquiteturais** que devem ser seguidas por todos os desenvolvedores e agentes de IA (como o GitHub Copilot) ao gerar ou revisar código neste repositório.

Baseado em princípios de **Clean Architecture (Robert C. Martin)**, **Domain-Driven Design (Eric Evans, Vaughn Vernon)** e **Patterns of Enterprise Application Architecture (Martin Fowler)**.

---

## 🧩 1. Princípios Fundamentais

1. **Isolamento de camadas:** cada camada deve ter uma única responsabilidade e depender apenas de camadas mais internas.
2. **Independência tecnológica:** o domínio não deve depender de frameworks, bancos de dados ou bibliotecas externas.
3. **Orientação a domínio:** o código reflete o modelo e a linguagem do negócio.
4. **Testabilidade:** todas as regras de negócio devem poder ser testadas isoladamente.
5. **Código limpo e expressivo:** o nome do código deve comunicar intenção, não apenas função.

---

## 🏗️ 2. Estrutura Arquitetural

O sistema segue o padrão **Hexagonal Architecture (Ports and Adapters)**, dividido em quatro camadas principais:

```
┌─────────────────────────────┐
│         Interface           │  ← (Controllers, REST, CLI, GraphQL)
├─────────────────────────────┤
│        Application          │  ← (Use Cases, Orchestration, Ports)
├─────────────────────────────┤
│          Domain             │  ← (Entities, ValueObjects, DomainServices)
├─────────────────────────────┤
│        Infrastructure       │  ← (Repositories, Adapters, Clients)
└─────────────────────────────┘
```

---

## 📂 3. Estrutura de Diretórios Recomendada

```
src/
 ├─ interface/
 │   └─ rest/
 │       ├─ controllers/
 │       ├─ dto/
 │       └─ mappers/
 │
 ├─ application/
 │   ├─ usecases/
 │   ├─ ports/
 │   └─ services/
 │
 ├─ domain/
 │   ├─ model/
 │   ├─ repository/
 │   ├─ events/
 │   └─ services/
 │
 └─ infrastructure/
     ├─ persistence/
     ├─ clients/
     ├─ adapters/
     └─ configuration/
```

---

## ⚙️ 4. Definições e Responsabilidades por Camada

### 🧩 Domain Layer
- Contém o **modelo de negócio puro**.
- Sem dependências externas.
- Define **Entidades, Value Objects, Serviços de Domínio** e **Eventos de Domínio**.
- Responsável por garantir **invariantes e regras do negócio**.

### ⚙️ Application Layer
- Contém **casos de uso (use cases)** e **ports**.
- Implementa orquestração, mas **não lógica de negócio**.

### 🔌 Infrastructure Layer
- Implementa **adapters concretos** das portas (repositórios, clients, etc.).
- Pode usar frameworks e bibliotecas externas.

### 🌐 Interface Layer (Delivery)
- Define **controladores REST, GraphQL, CLI, gRPC**.
- Converte DTOs ↔ Domain Models.
- Nenhum controller deve conter regra de negócio.

---

## 🧠 5. Padrões de Design

| Conceito | Descrição | Exemplo |
|-----------|------------|---------|
| **Entity** | Possui identidade e ciclo de vida. | `Order`, `User` |
| **Value Object** | Imutável, sem identidade. | `Money`, `CPF` |
| **Repository (Port)** | Interface para persistência. | `OrderRepository` |
| **Domain Service** | Regras que envolvem múltiplas entidades. | `BillingService` |
| **Use Case** | Caso de uso da aplicação. | `CreateOrderUseCase` |
| **Adapter** | Implementação concreta de um Port. | `JpaOrderRepository` |

---

## 🧩 6. Boas Práticas Gerais

- DTOs apenas nas bordas (interface layer).  
- Exceções de domínio e técnicas separadas.  
- Validação de formato → interface; de regra → domínio.  
- Dependências sempre apontam para dentro.  
- Testes unitários obrigatórios para lógica de domínio.  

---

## 🧱 7. Princípios SOLID e Clean Code

- **S** — Uma classe deve ter uma única razão para mudar.  
- **O** — Aberta para extensão, fechada para modificação.  
- **L** — Subclasses devem respeitar contratos.  
- **I** — Interfaces pequenas e específicas.  
- **D** — Dependa de abstrações, não implementações.  

**Regras adicionais para o Copilot:**
- Nomear classes e métodos de forma semântica e alinhada ao domínio.
- Priorizar imutabilidade.
- Não gerar `Utils` genéricos ou classes "Deus".
- Sempre gerar testes junto com novas regras.

---

## 🧪 8. Estratégia de Testes

| Tipo | Camada | Objetivo |
|------|---------|-----------|
| Unit | Domain | Testar regras de negócio isoladas |
| Integration | Infrastructure | Validar repositórios e conexões externas |
| Acceptance | Application / Interface | Validar o comportamento do sistema |

---

## 🧰 9. Convenções de Nomenclatura

| Elemento | Convenção | Exemplo |
|-----------|------------|---------|
| Use Case | `<Action><Entity>UseCase` | `CreateOrderUseCase` |
| Domain Service | `<BusinessConcept>Service` | `PaymentService` |
| Repository | `<Entity>Repository` | `OrderRepository` |
| Adapter | `<Tech><Purpose>Adapter` | `JpaOrderRepositoryAdapter` |
| DTO | `<Entity><Request|Response>DTO` | `OrderRequestDTO` |
| Port | `<Action><Entity>Port` | `SendEmailPort` |

---

## 🤖 10. Instruções para o GitHub Copilot

> **Objetivo:** gerar código de forma consistente com esta arquitetura.

1. Todo código deve respeitar esta estrutura e nomenclatura.  
2. Lógica de negócio pertence **somente ao domínio**.  
3. Sempre sugerir **testes automatizados**.  
4. Respeitar princípios SOLID e Clean Code.  
5. Usar a linguagem ubíqua do domínio.  
6. Nunca gerar dependências do domínio para infraestrutura.  
7. Evitar acoplamento e duplicação de lógica.  

---

## 📚 11. Referências

- *Domain-Driven Design* — Eric Evans  
- *Implementing Domain-Driven Design* — Vaughn Vernon  
- *Clean Architecture* — Robert C. Martin  
- *Patterns of Enterprise Application Architecture* — Martin Fowler  
- *Building Evolutionary Architectures* — Neal Ford
