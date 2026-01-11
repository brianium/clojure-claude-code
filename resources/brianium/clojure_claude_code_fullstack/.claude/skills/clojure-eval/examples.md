# clj-nrepl-eval Examples

## Discovery

Find running nREPL servers:

```bash
clj-nrepl-eval --discover-ports
```

Check which ports are currently connected:

```bash
clj-nrepl-eval --connected-ports
```

## Basic Evaluation

Simple expression:

```bash
clj-nrepl-eval -p 7888 "(+ 1 2 3)"
```

## Requiring Namespaces

Load a project namespace with reload to get latest changes:

```bash
clj-nrepl-eval -p 7888 "(require '[{{top/ns}}.{{main}} :as {{main}}] :reload)"
```

Then call functions from it:

```bash
clj-nrepl-eval -p 7888 "({{main}}/some-function arg1 arg2)"
```

## Multiline Code with Heredoc

For complex expressions, use heredoc to avoid shell escaping:

```bash
clj-nrepl-eval -p 7888 <<'EOF'
(let [data {:name "test" :value 42}]
  (println "Processing:" data)
  (update data :value inc))
EOF
```

## Verifying File Changes

After editing a file, reload and test:

```bash
clj-nrepl-eval -p 7888 "(require '[{{top/ns}}.{{main}}] :reload)"
```

Check for compilation errors - a successful reload returns `nil`.

## Running Tests

```bash
clj-nrepl-eval -p 7888 "(require '[clojure.test :refer [run-tests]])"
clj-nrepl-eval -p 7888 "(require '[{{top/ns}}.{{main}}-test] :reload)"
clj-nrepl-eval -p 7888 "(run-tests '{{top/ns}}.{{main}}-test)"
```

## Session Management

Reset session state if things get corrupted:

```bash
clj-nrepl-eval -p 7888 --reset-session "(println \"Fresh session\")"
```

## Development Workflow

Typical edit-test cycle:

1. Edit source file
2. Reload namespace: `clj-nrepl-eval -p 7888 "(require '[ns] :reload)"`
3. Test changes: `clj-nrepl-eval -p 7888 "(ns/my-function test-data)"`
4. Repeat
