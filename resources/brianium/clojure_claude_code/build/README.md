# {{name}}

A Clojure project with Claude Code integration.

## Prerequisites

- [Clojure CLI](https://clojure.org/guides/install_clojure) 1.11+
- [Babashka](https://github.com/babashka/babashka) v1.12.212+
- [bbin](https://github.com/babashka/bbin)
- Rust 1.83+ (for parinfer-rust)

## Claude Code Setup

This project uses [clojure-mcp-light](https://github.com/bhauman/clojure-mcp-light) for Claude Code REPL integration and automatic paren repair.

### 1. Install parinfer-rust (optional but recommended)

```bash
cargo install --git https://github.com/eraserhd/parinfer-rust
```

### 2. Install clojure-mcp-light tools

```bash
# Paren repair hook for Claude Code
bbin install https://github.com/bhauman/clojure-mcp-light.git --tag v0.2.1

# nREPL evaluation CLI
bbin install https://github.com/bhauman/clojure-mcp-light.git --tag v0.2.1 \
  --as clj-nrepl-eval --main-opts '["-m" "clojure-mcp-light.nrepl-eval"]'

# On-demand paren repair
bbin install https://github.com/bhauman/clojure-mcp-light.git --tag v0.2.1 \
  --as clj-paren-repair --main-opts '["-m" "clojure-mcp-light.paren-repair"]'
```

## Development

### Start the REPL

```bash
clj -M:dev
```

### Development Workflow

```clojure
;; Switch to dev namespace
(dev)

;; Start the system (opens Portal)
(start)

;; After making changes, reload namespaces
(reload)

;; Full restart if needed
(restart)
```

### Portal

Portal opens automatically and receives all `tap>` output. Use `(tap> data)` anywhere in your code for debugging.

## Testing

```bash
clj -X:test
```

## REPL Evaluation (for Claude Code)

Discover running nREPL servers:

```bash
clj-nrepl-eval --discover-ports
```

Evaluate expressions:

```bash
clj-nrepl-eval -p <PORT> "(require '[{{top/ns}}.{{main}}] :reload)"
clj-nrepl-eval -p <PORT> "({{main}}/some-function)"
```

## Project Structure

```
src/clj/{{top/file}}/      # Main source files
dev/src/clj/               # Development namespace (user.clj, dev.clj)
test/src/clj/{{top/file}}/ # Test files
resources/                 # Resource files
```

## License

Copyright © {{now/year}}

Distributed under the Eclipse Public License version 1.0.
