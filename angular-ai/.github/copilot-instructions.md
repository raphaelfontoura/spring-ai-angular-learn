# GitHub Copilot Instructions - Angular AI Chat Application

## Project Overview

This is an **Angular 20+ chat application** with a Spring Boot backend integration. The frontend communicates with AI services via HTTP proxy to `localhost:8080/api`.

## Architecture Patterns

### 1. Standalone Components (Angular 20+)
- Use **standalone components** exclusively - no `NgModule` declarations
- Components must explicitly list their imports: `imports: [CommonModule, MatButtonModule, ...]`
- Bootstrap components directly in `app.config.ts`

### 2. Zoneless Change Detection
- Project uses `provideZonelessChangeDetection()` - avoid Zone.js patterns
- Use **signals** for reactive state: `signal()`, `computed()`, `effect()`
- Prefer signals over traditional observables for component state

### 3. Component Structure Pattern
```typescript
@Component({
  selector: 'app-feature-name',
  imports: [/* explicit imports */],
  templateUrl: './feature-name.html',
  styleUrl: './feature-name.scss'  // Note: styleUrl (singular)
})
export class FeatureName {
  private readonly service = inject(ServiceName);
  protected readonly state = signal(initialValue);
}
```

## Development Workflows

### Running the Application
```bash
npm start  # Uses proxy.conf.js to route /api to localhost:8080
ng serve   # Alternative without proxy
```

### Key Scripts
- `npm test` - Karma/Jasmine unit tests
- `ng build` - Production build with optimization
- `ng generate component` - Use for new standalone components

### Backend Integration
- API calls go through `/api` prefix (proxied to Spring Boot)
- `ChatService` handles HTTP communication with `ChatResponse` interface
- Error handling includes fallback messages for service failures

## Project-Specific Conventions

### File Organization
```
src/app/
├── app.config.ts          # Application bootstrap
├── app.routes.ts          # Route definitions with lazy loading
├── chat/
│   ├── chat-service.ts    # HTTP service for backend
│   ├── chat-response.ts   # Response interface
│   └── simple-chat/       # Chat component module
```

### Service Patterns
- Use `inject()` function instead of constructor injection
- Services are `providedIn: 'root'` singletons
- HTTP requests return typed observables: `Observable<ChatResponse>`

### Template Patterns
- Use new control flow syntax: `@for`, `@if` instead of `*ngFor`, `*ngIf`
- Material Design components with consistent naming
- ViewChild with `!` assertion for post-init access

### State Management
- Local component state uses **signals**
- Loading states: `isLoading = signal(false)`
- Arrays/lists: `messages = signal([...])` with `.update()` method
- No external state management (NgRx) currently used

## Technology Stack

- **Angular 20.3** with zoneless change detection
- **Angular Material 20.2** for UI components
- **SCSS** styling (configured in angular.json)
- **TypeScript 5.9** with strict mode enabled
- **RxJS 7.8** for HTTP handling
- **Prettier** with custom Angular HTML parser

## Development Guidelines

### When creating new features:
1. Generate standalone components with Angular CLI
2. Use signal-based state management
3. Inject services with `inject()` function
4. Follow the `chat/` module structure for organization
5. Add proper TypeScript interfaces for data models
6. Include error handling for HTTP requests

### When working with the chat system:
- Use `ChatService.sendChatMessage()` for backend communication
- Handle both success and error responses
- Implement loading states with signals
- Include local fallback mode for development

### TypeScript Configuration:
- Strict mode enabled with comprehensive type checking
- Experimental decorators enabled for Angular
- No implicit returns or fallthrough cases allowed