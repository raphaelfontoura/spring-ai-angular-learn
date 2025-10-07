---

# 🤖 React Native Code Guidelines for GitHub Copilot

This document defines the **best practices, coding standards, and culture** for React Native development in this project.  
GitHub Copilot should use these guidelines as a **foundation for generating clean, consistent, and scalable code**.

---

## 🧩 Project Structure

- Follow a **modular feature-based architecture**:

src/
features/
auth/
home/
profile/
components/
navigation/
hooks/
utils/
services/


- Centralize navigation, theming, and global context.
- Avoid duplicated logic — share it through custom hooks or utils.

---

## ⚛️ React Native Code Standards

### 1. Components
- Always use **Functional Components** with **React Hooks** — no class components.
- Each component should have **a single responsibility**.
- Use **PascalCase** for component names.
- Return **only one root JSX element**.
- Use `React.memo` for pure components.

```tsx
import React, { memo } from 'react';
import { View, Text } from 'react-native';

const UserCard = memo(({ name }: { name: string }) => (
<View>
  <Text>{name}</Text>
</View>
));

export default UserCard;
```

---

### 2. Styling

* Prefer `StyleSheet.create` for static styles.
* For dynamic theming, use **styled-components** or **react-native-paper**.
* Centralize theme configuration (colors, spacing, typography).

```tsx
const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: theme.colors.background,
    padding: theme.spacing.md,
  },
});
```

---

### 3. Hooks

* Always prefix with `use` (e.g., `useAuth`, `useTheme`).
* Hooks should be **reusable** and **contain logic, not UI**.
* Use dependency arrays correctly in `useEffect`.

```tsx
import { useEffect, useState } from 'react';

export function useFetchData(url: string) {
  const [data, setData] = useState(null);
  useEffect(() => {
    fetch(url)
      .then(res => res.json())
      .then(setData);
  }, [url]);
  return data;
}
```

---

### 4. Navigation

* Use **React Navigation**.
* Group route definitions in a `navigation/` directory.
* Ensure **type-safe routes** with TypeScript types.

---

### 5. Typing (TypeScript)

* Always type props, state, and function returns.
* Prefer `type` over `interface` for consistency.
* Use `React.FC<Props>` only when `children` is used.

```tsx
type ButtonProps = {
  label: string;
  onPress: () => void;
};
```

---

### 6. Performance

* Use `FlatList` instead of `map()` for long lists.
* Avoid recreating inline functions → use `useCallback`.
* Cache heavy computations with `useMemo`.
* Optimize rendering with `React.memo` and proper prop handling.

---

### 7. General Best Practices

* Avoid using `any` unless absolutely necessary.
* Remove dead code and unused imports.
* Always follow ESLint and Prettier formatting.
* Use conventional commit messages:

  * `feat:` new feature
  * `fix:` bug fix
  * `refactor:` internal improvement
  * `chore:` maintenance tasks
* Always add tests for pure functions and custom hooks.

---

### 8. Testing

* Use **Jest** + **React Native Testing Library**.
* Name test files as `.test.tsx`.
* Focus on:

  * Component rendering
  * Hook behavior
  * Main app flows

---

### 9. Code Culture

* **Readability > Brevity**
* Document meaningful decisions with comments.
* Use **clear, descriptive variable and function names**.
* Review PRs with empathy and clarity.
* Strive for **simplicity, predictability, and maintainability**.

---

## 🧠 GitHub Copilot Directives

Copilot should:

1. Generate **typed, short, and pure functions**.
2. Prefer **declarative and readable** code.
3. Avoid:

   * `any`, `console.log`, or mock timeouts.
   * Untyped components.
   * Code without error handling.
4. Prioritize:

   * TypeScript
   * Hooks
   * Theme-aware components
   * Code reusability
5. Add explanatory comments for complex logic.

---

## 🛠️ Recommended Tooling

| Purpose          | Tool                                |
| ---------------- | ----------------------------------- |
| Linting          | ESLint + Prettier                   |
| Testing          | Jest + React Native Testing Library |
| State Management | Zustand / Redux Toolkit             |
| Forms            | React Hook Form                     |
| Navigation       | React Navigation v7+                |
| Styling          | Styled Components / StyleSheet      |

---

## 🧾 File Header Example

```tsx
/**
 * @file UserProfile.tsx
 * @description Displays the current logged-in user’s information.
 * @feature Profile
 * @author Your Name
 */
```

---

> ✨ **Summary**
> This document serves as a guideline for both GitHub Copilot and all contributors.
> The goal is to ensure code remains **consistent, performant, and maintainable** across the entire React Native codebase.

