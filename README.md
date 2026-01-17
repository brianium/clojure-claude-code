# clojure-claude-code

A [deps-new](https://github.com/seancorfield/deps-new) template for scaffolding Clojure projects configured for [Claude Code](https://claude.ai/code).

## Features

- **Claude Code integration** via [clojure-mcp-light](https://github.com/bhauman/clojure-mcp-light)
  - Automatic paren/indent repair on file edits
  - REPL evaluation skill for Claude Code
- **Hot reloading** via [clj-reload](https://github.com/tonsky/clj-reload)
- **Data inspection** via [Portal](https://github.com/djblue/portal) with tap> integration
- **Testing** via [Cognitect test-runner](https://github.com/cognitect-labs/test-runner)
- **Emacs support** with `.dir-locals.el` for CIDER

## Templates

| Template | Description |
|----------|-------------|
| `brianium/clojure-claude-code` | Base Clojure project |
| `brianium/clojure-claude-code-fullstack` | Clojure + ClojureScript with [Figwheel-main](https://figwheel.org/) |

## Prerequisites

### Required

- [Clojure CLI](https://clojure.org/guides/install_clojure) 1.11+
- [deps-new](https://github.com/seancorfield/deps-new) installed as a tool:
  ```bash
  clojure -Ttools install-latest :lib io.github.seancorfield/deps-new :as new
  ```

### For Claude Code Integration

- [Babashka](https://github.com/babashka/babashka) v1.12.212+
- [bbin](https://github.com/babashka/bbin)
- [Rust](https://www.rust-lang.org/tools/install) 1.83+ (for parinfer-rust)

#### Install parinfer-rust (optional but recommended)

```bash
cargo install --git https://github.com/eraserhd/parinfer-rust
```

#### Install clojure-mcp-light tools

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

## Usage

### Create a new project

```bash
# Base Clojure project
clojure -Sdeps '{:deps {io.github.brianium/clojure-claude-code {:git/tag "v0.1.4" :git/sha "d9e9550"}}}' \
  -Tnew create :template brianium/clojure-claude-code :name myorg/myapp

# Full-stack Clojure + ClojureScript project
clojure -Sdeps '{:deps {io.github.brianium/clojure-claude-code {:git/tag "v0.1.4" :git/sha "d9e9550"}}}' \
  -Tnew create :template brianium/clojure-claude-code-fullstack :name myorg/myapp
```

### Generated project structure

**Base template:**
```
myapp/
├── .claude/
│   ├── settings.json              # Claude Code hooks
│   └── skills/clojure-eval/       # REPL evaluation skill
├── src/clj/myorg/myapp.clj        # Main source
├── dev/src/clj/
│   ├── user.clj                   # REPL entry point
│   └── dev.clj                    # Dev system with Portal
├── test/src/clj/myorg/myapp_test.clj
├── deps.edn
├── .dir-locals.el                 # Emacs CIDER config
├── CLAUDE.md                      # Project guide for Claude
└── README.md
```

**Fullstack template** adds:
```
├── src/cljc/myorg/shared.cljc     # Shared CLJ/CLJS code
├── src/cljs/myorg/frontend.cljs   # ClojureScript entry
├── dev.cljs.edn                   # Figwheel build config
└── resources/public/              # Static assets
```

## Development Workflow

### Start the REPL

```bash
cd myapp
clj -M:dev
```

### In the REPL

```clojure
(dev)       ; Switch to dev namespace (opens Portal)
(start)     ; Start the system
(reload)    ; Reload changed namespaces
(restart)   ; Full restart
```

### ClojureScript (fullstack only)

```bash
clj -M:dev:fig:cljs-build
```

Opens http://localhost:9500 with hot reloading.

### Run tests

```bash
clj -X:test
```

## Claude Code Usage

Once in a project, Claude Code can evaluate Clojure via the bundled skill:

```bash
# Discover running REPLs
clj-nrepl-eval --discover-ports

# Switch to dev namespace (all evaluation should happen here)
clj-nrepl-eval -p <PORT> "(in-ns 'dev)"

# Reload changed namespaces
clj-nrepl-eval -p <PORT> "(reload)"

# Evaluate code
clj-nrepl-eval -p <PORT> "(myapp/some-function)"
```

The `.claude/settings.json` hooks automatically repair parens/indentation when Claude edits Clojure files.

## License

Copyright © 2026

Distributed under the Eclipse Public License version 1.0.
