# Clojure REPL Evaluation Skill

Evaluate Clojure code via nREPL using the `clj-nrepl-eval` command.

## Capabilities

- Verify Clojure files compile and load correctly
- Test function behavior interactively
- Debug expressions in a persistent session
- Load and require namespaces with `:reload`
- Validate code changes before committing

## Workflow

### 1. Discover nREPL Servers

```bash
clj-nrepl-eval --discover-ports
```

Shows available nREPL servers (Clojure, Babashka, shadow-cljs, etc.).

### 2. Evaluate Code

```bash
clj-nrepl-eval -p <PORT> "(+ 1 2 3)"
```

### 3. Require Namespaces

Always use `:reload` to pick up recent changes:

```bash
clj-nrepl-eval -p <PORT> "(require '[{{top/ns}}.{{main}} :as {{main}}] :reload)"
```

## Important Options

| Option | Description |
|--------|-------------|
| `-p, --port PORT` | nREPL port (required) |
| `-t, --timeout MS` | Timeout in milliseconds (default: 120000) |
| `-r, --reset-session` | Clear all session state |
| `-d, --discover-ports` | Find running nREPL servers |

## Session Persistence

Session state persists between evaluations. You can require a namespace in one call and use it in subsequent calls.

## Best Practices

1. **Use `:reload`** when requiring namespaces to capture recent file changes
2. **Pass code as arguments** when possible for simple expressions
3. **Use heredoc** for complex multiline code to avoid shell escaping issues
4. **Increase timeout** for long-running operations with `-t`
