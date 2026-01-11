# Sample README for Clojure + Claude Code Codebase

## Table of Contents

- [Claude Code REPL Integration](#claude-code-repl-integration)
- [Application Structure](#application-structure)

## Claude Code REPL Integration

This project uses [clojure-mcp-light](https://github.com/bhauman/clojure-mcp-light) to give Claude Code access to the Clojure REPL and automatic delimiter repair for Clojure files.

### Prerequisites

- [Babashka](https://github.com/babashka/babashka) v1.12.212+
- [bbin](https://github.com/babashka/bbin)
- Rust 1.83+ (for parinfer-rust)

### Installation

#### 1. Install parinfer-rust (optional but recommended)

```bash
cargo install --git https://github.com/eraserhd/parinfer-rust
```

#### 2. Install clojure-mcp-light tools

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

#### 3. Configure Claude Code hooks

Add the following to `.claude/settings.json`:

```json
{
  "hooks": {
    "PreToolUse": [
      {
        "matcher": "Write|Edit",
        "hooks": [
          {
            "type": "command",
            "command": "clj-paren-repair-claude-hook --cljfmt"
          }
        ]
      }
    ],
    "PostToolUse": [
      {
        "matcher": "Edit|Write",
        "hooks": [
          {
            "type": "command",
            "command": "clj-paren-repair-claude-hook --cljfmt"
          }
        ]
      }
    ],
    "SessionEnd": [
      {
        "hooks": [
          {
            "type": "command",
            "command": "clj-paren-repair-claude-hook --cljfmt"
          }
        ]
      }
    ]
  }
}
```

### Usage

#### Discover nREPL servers

```bash
clj-nrepl-eval --discover-ports
```

#### Evaluate code

```bash
clj-nrepl-eval -p <port> '(+ 1 2 3)'
```

#### Manual paren repair

```bash
clj-paren-repair path/to/file.clj
```
