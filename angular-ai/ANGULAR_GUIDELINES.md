# ⚙️ Angular Frontend Development Guidelines

Este documento define as **boas práticas, padrões de projeto e diretrizes de desenvolvimento** para aplicações **Angular**.  
Foi criado para orientar tanto desenvolvedores humanos quanto IAs (como o GitHub Copilot) a produzir código consistente, escalável e de alta qualidade.

Baseado em referências oficiais da comunidade Angular, **Google Angular Style Guide**, e práticas difundidas por **John Papa**, **Todd Motto** e **Minko Gechev**.

---

## 🧭 1. Princípios Fundamentais

1. **Single Responsibility:** cada componente, serviço ou módulo deve ter uma única responsabilidade clara.  
2. **Componentização:** priorize reuso e encapsulamento através de componentes.  
3. **Reactive Programming:** use RxJS e programação reativa de forma consistente.  
4. **Dependency Injection:** evite instanciar dependências diretamente; use o sistema de injeção do Angular.  
5. **Testabilidade:** todo código deve ser projetado para ser testável com unit tests e testes de integração.  
6. **Change Detection consciente:** minimize triggers desnecessários do ciclo de detecção.  

---

## 🏗️ 2. Estrutura de Projeto Recomendada

```
src/
 ├─ app/
 │   ├─ core/               # Serviços, guards e interceptors globais
 │   ├─ shared/             # Componentes, pipes e diretivas reutilizáveis
 │   ├─ features/           # Módulos de funcionalidades
 │   │   ├─ users/
 │   │   │   ├─ components/
 │   │   │   ├─ pages/
 │   │   │   ├─ services/
 │   │   │   └─ users.module.ts
 │   │   └─ orders/
 │   ├─ state/              # Gerenciamento global de estado (NgRx, Signals, etc.)
 │   ├─ app-routing.module.ts
 │   ├─ app.component.ts
 │   └─ app.module.ts
 │
 ├─ assets/
 ├─ environments/
 ├─ styles/
 └─ main.ts
```

---

## 📚 3. Organização por Módulo (Feature-Based Structure)

Cada módulo de feature deve conter **componentes, serviços e rotas próprios**, isolados do restante da aplicação.

Exemplo:

```
users/
 ├─ components/
 │   └─ user-card/
 │       ├─ user-card.component.ts
 │       ├─ user-card.component.html
 │       ├─ user-card.component.scss
 │       └─ user-card.component.spec.ts
 ├─ pages/
 │   └─ user-list/
 │       ├─ user-list.page.ts
 │       ├─ user-list.page.html
 │       ├─ user-list.page.scss
 │       └─ user-list.page.spec.ts
 ├─ services/
 │   └─ user.service.ts
 ├─ users.module.ts
 └─ users-routing.module.ts
```

---

## 🎨 4. Boas Práticas de Componentes

- Prefira **componentes “presentacionais” (dumb)** para exibir dados e **componentes “container” (smart)** para controlar lógica.
- Use **OnPush change detection** sempre que possível.
- Utilize **@Input()** e **@Output()** para comunicação entre componentes.
- Evite lógica de negócio em componentes — delegue para **services**.
- Use **trackBy** em *ngFor para otimizar performance.
- Evite `any`; prefira **tipos e interfaces explícitas**.

---

## 🧩 5. Boas Práticas de Serviços

- Serviços são **singletons** e devem conter lógica de negócio e integração com APIs.  
- Use **HttpClient** e **interceptors** para tratamento de erros, autenticação e logging.  
- Não manipule o DOM diretamente; use o Renderer2 ou diretivas.  
- Centralize chamadas HTTP em serviços específicos (ex: `UserService`, `OrderService`).  
- Sempre **tipar o retorno** dos métodos (`Observable<User[]>`, `Observable<void>`, etc.).  

---

## 🔄 6. RxJS e Programação Reativa

- Use **Observables** em vez de Promises quando possível.  
- Sempre **desinscreva** (`unsubscribe`) ou use **async pipe**.  
- Utilize **operadores puros** (`map`, `filter`, `switchMap`, `catchError`) para evitar efeitos colaterais.  
- Prefira **BehaviorSubject** para estados locais e **NgRx** ou **Signals** para estados globais.  
- Nomeie streams com o sufixo `$` (ex: `users$`, `isLoading$`).  

---

## 🧱 7. Estilo e Convenções de Código

| Elemento | Convenção | Exemplo |
|-----------|------------|---------|
| Component | `kebab-case` | `user-card.component.ts` |
| Service | `camelCase` com sufixo `Service` | `userService` |
| Interface | Prefixo `I` | `IUser`, `IOrder` |
| Observable | Sufixo `$` | `users$`, `isLoading$` |
| Diretiva | Prefixo de contexto | `appHighlight` |
| Módulo | Sufixo `Module` | `UsersModule` |

---

## 🧪 8. Testes

- Todo componente, pipe e serviço deve ter um **teste unitário**.  
- Use **Jasmine** e **Karma** para testes unitários.  
- Utilize **TestBed** para inicializar componentes e módulos.  
- Para mocks de API, use **HttpTestingController**.  
- Prefira **tests de comportamento (BDD)** com descrições claras:  
  ```typescript
  it('deve exibir lista de usuários quando carregada com sucesso', () => { ... });
  ```

---

## 🚀 9. Performance e Otimização

- Utilize **lazy loading** para módulos de feature.  
- Evite funções anônimas diretamente em templates.  
- Prefira **ChangeDetectionStrategy.OnPush**.  
- Use **trackBy** em `*ngFor`.  
- Habilite **PreloadingStrategy** personalizada se necessário.  
- Utilize **Angular CLI budgets** para limitar tamanho de bundles.  

---

## 🔐 10. Segurança

- Use **DomSanitizer** para conteúdos dinâmicos.  
- Habilite **Content Security Policy (CSP)**.  
- Evite `innerHTML` sempre que possível.  
- Utilize **HttpInterceptor** para injeção de headers de segurança.  
- Nunca armazene tokens no `localStorage`; prefira `sessionStorage` ou cookies HttpOnly.  

---

## 🎨 11. Estilo e UI

- Utilize **Angular Material** ou **TailwindCSS** conforme o padrão do projeto.  
- Prefira componentes **reutilizáveis e acessíveis (ARIA-compliant)**.  
- Nomeie classes CSS com **BEM (Block Element Modifier)**.  
- Centralize estilos em **SCSS globais** e **variáveis de tema**.  
- Componentes devem usar **ViewEncapsulation.Emulated** (padrão).  

---

## 🧩 12. Gerenciamento de Estado

### Opções suportadas:
- **NgRx** → quando o estado for global e complexo.  
- **Signals (Angular 17+)** → para estados locais e reatividade simples.  
- **BehaviorSubject** → em serviços de estado (store services).  

### Princípios:
- Estado é **imutável**.  
- Cada ação deve ser **idempotente e previsível**.  
- Nunca manipule o estado diretamente no componente.  

---

## 🧠 13. Instruções para o GitHub Copilot

> **Objetivo:** gerar código Angular limpo, modular e alinhado ao guia oficial.

1. Cada feature deve ser um **módulo isolado**.  
2. Componentes devem ter HTML, SCSS e Spec próprios.  
3. Evitar lógica de negócio em componentes.  
4. Sempre gerar testes unitários junto ao código.  
5. Priorizar tipagem forte (`interfaces`, `types`).  
6. Usar `async pipe` e `OnPush`.  
7. Respeitar as convenções de nomes e estrutura.  
8. Criar comentários apenas quando agregarem clareza.  

---

## 📚 14. Referências

- [Angular Style Guide (John Papa)](https://angular.io/guide/styleguide)  
- [Angular Official Documentation](https://angular.dev/)  
- [RxJS Documentation](https://rxjs.dev/)  
- [NgRx Documentation](https://ngrx.io/)  
- [Angular CLI Configuration](https://angular.io/cli)  
- [Accessibility in Angular](https://angular.io/guide/accessibility)
