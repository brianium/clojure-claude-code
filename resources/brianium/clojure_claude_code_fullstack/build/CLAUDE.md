# <<name>>

## Project Overview

This is a full-stack Clojure/ClojureScript project using deps.edn for dependency management.

## Technology Stack

- **Clojure** with deps.edn
- **ClojureScript** with Figwheel-main for hot reloading
- **clj-reload** for backend namespace reloading
- **Portal** for data inspection (tap> integration)
- **Cognitect test-runner** for running tests

## Development Setup

### Starting the Backend REPL

```bash
clj -M:dev
```

This starts a REPL with development dependencies loaded.

### Starting ClojureScript Development

```bash
clj -M:dev:fig:cljs-build
```

This starts Figwheel-main which will:
- Compile ClojureScript
- Start a development server (default: http://localhost:9500)
- Watch for changes and hot-reload

### Backend Development Workflow

1. Start REPL with `clj -M:dev`
2. Load dev namespace: `(dev)`
3. Start the system: `(start)`
4. Make changes to source files
5. Reload: `(reload)`

The `dev` namespace provides:
- `(start)` - Start the development system
- `(stop)` - Stop the system
- `(reload)` - Reload changed namespaces via clj-reload
- `(restart)` - Stop, reload, and start

### Portal

Portal opens automatically when the dev namespace loads. Any `(tap> data)` calls will appear in the Portal UI.

## Project Structure

```
src/clj/          # Clojure backend source files
src/cljc/         # Shared Clojure/ClojureScript code
src/cljs/         # ClojureScript frontend source files
dev/src/clj/      # Development-only source (user.clj, dev.clj)
test/src/clj/     # Test files
resources/        # Resource files
resources/public/ # Static assets served by Figwheel
```

## REPL Evaluation

Use the clojure-eval skill to evaluate code via nREPL.

### Starting an nREPL Server

To start a REPL with nREPL support (required for clojure-eval):

```bash
clj -Sdeps '{:deps {nrepl/nrepl {:mvn/version "1.3.0"}}}' -M:dev -m nrepl.cmdline --port 7888
```

This starts an nREPL server on port 7888 with all dev dependencies loaded.

### Connecting and Evaluating

```bash
clj-nrepl-eval --discover-ports          # Find running REPLs
clj-nrepl-eval -p 7888 "(+ 1 2 3)"       # Evaluate expression
```

**Important:** All REPL evaluation should take place in the `dev` namespace. After connecting, switch to the dev namespace:

```bash
clj-nrepl-eval -p 7888 "(dev)"
```

To reload code after making changes, use clj-reload:

```bash
clj-nrepl-eval -p 7888 "(reload)"
```

## Running Tests

```bash
clj -X:test
```

Or from the REPL (in the dev namespace):

```clojure
(reload)  ; Reload changed namespaces first
(require '[clojure.test :refer [run-tests]])
(run-tests '<<top/ns>>.<<main>>-test)
```

## Adding Dependencies

When adding new dependencies in a REPL-connected environment:

1. **Add to the running REPL first** using `clojure.repl.deps/add-lib`:
   ```clojure
   (clojure.repl.deps/add-lib 'metosin/malli {:mvn/version "0.16.4"})
   ```
   Note: The library name must be quoted.

2. **Confirm the dependency works** by requiring and testing it in the REPL.

3. **Only then add to deps.edn** once confirmed working.

This ensures dependencies are immediately available without restarting the REPL.

## Code Style

- Follow standard Clojure conventions
- Use `cljfmt` formatting (applied automatically via hooks)
- Prefer pure functions where possible
- Put shared code in `src/cljc/` for use in both CLJ and CLJS
- Use `tap>` for debugging output (appears in Portal)

### Namespaced Keywords

Clojure has two syntaxes for namespaced keywords:

**Single colon (`:`)** - Explicit namespace, works anywhere:
```clojure
:my.app.config/timeout    ; Fully qualified namespace
:ui/visible               ; Arbitrary namespace (doesn't need to exist)
:db/id                    ; Common convention for domain markers
```

**Double colon (`::`)** - Auto-resolved namespace:
```clojure
;; In namespace my.app.core:
::key                     ; Expands to :my.app.core/key

;; With required aliases:
(require '[my.app.db :as db])
::db/query                ; Expands to :my.app.db/query
```

**When to use which:**
- Use `:` with explicit namespace when the keyword meaning is independent of the current file
- Use `::` when the keyword is specific to the current namespace
- Use `::alias/key` to reference keywords from required namespaces without typing the full name
- Prefer `:` for spec keys, component IDs, and data that crosses namespace boundaries

## Git Commits

Use conventional commits format:

```
<type>: <description>

[optional body]
```

Types: `feat`, `fix`, `docs`, `style`, `refactor`, `test`, `chore`

Examples:
- `feat: add user authentication`
- `fix: resolve nil pointer in data parser`
- `refactor: simplify database connection logic`
