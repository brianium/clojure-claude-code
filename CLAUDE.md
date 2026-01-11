# clojure-claude-code

A deps-new template library for scaffolding Clojure projects with Claude Code integration.

## Project Purpose

This repository provides two deps-new templates:

1. **brianium/clojure-claude-code** - Base Clojure template
2. **brianium/clojure-claude-code-fullstack** - Full-stack Clojure + ClojureScript with Figwheel-main

Both templates configure projects for use with Claude Code via clojure-mcp-light.

## Repository Structure

```
clojure-claude-code/
├── deps.edn                              # Template library (just {:paths ["resources"]})
├── notes/
│   ├── musings.org                       # Original requirements
│   └── plan.org                          # Implementation plan
└── resources/brianium/
    ├── clojure_claude_code/              # Base CLJ template
    │   ├── template.edn                  # deps-new config
    │   ├── build/                        # Files for project root
    │   │   ├── deps.edn
    │   │   ├── README.md
    │   │   ├── CLAUDE.md
    │   │   ├── .gitignore
    │   │   └── .dir-locals.el
    │   ├── src/clj/core.clj              # Main source template
    │   ├── dev/src/clj/{user,dev}.clj    # Dev namespace
    │   ├── test/src/clj/core_test.clj    # Test template
    │   ├── .claude/                      # Claude Code config
    │   │   ├── settings.json             # Paren repair hooks
    │   │   └── skills/clojure-eval/      # REPL eval skill
    │   └── resources/                    # Empty resources dirs
    │
    └── clojure_claude_code_fullstack/    # Full-stack template
        ├── (same as base, plus:)
        ├── build/dev.cljs.edn            # Figwheel config
        ├── src/cljc/shared.cljc          # Shared code
        ├── src/cljs/frontend.cljs        # ClojureScript entry
        └── resources/public/             # Static assets dir
```

## Template Syntax

Templates use deps-new placeholder syntax:

| Placeholder | Example Output | Description |
|-------------|----------------|-------------|
| `{{name}}` | `acme/myapp` | Full qualified name |
| `{{top/ns}}` | `acme` | Top namespace |
| `{{main}}` | `myapp` | Main namespace segment |
| `{{top/file}}` | `acme` | Directory path for top |
| `{{main/file}}` | `myapp` | Filename for main |
| `{{now/year}}` | `2024` | Current year |

The fullstack template uses `<<` `>>` delimiters in `build/` directory to avoid conflicts with the paren-repair hook.

## Testing Templates Locally

```bash
# Test base template
clojure -Sdeps '{:deps {io.github.brianium/clojure-claude-code {:local/root "."}}}' \
  -Tnew create :template brianium/clojure-claude-code \
  :name test/myapp :target-dir /tmp/test-clj

# Test fullstack template
clojure -Sdeps '{:deps {io.github.brianium/clojure-claude-code {:local/root "."}}}' \
  -Tnew create :template brianium/clojure-claude-code-fullstack \
  :name test/myapp :target-dir /tmp/test-fullstack

# Verify generated project works
cd /tmp/test-clj && clojure -X:test
```

## template.edn Reference

Key transform rules used:

```clojure
;; Copy directory, rename files, with namespace substitution
["src/clj" "src/clj/{{top/file}}" {"core.clj" "{{main/file}}.clj"} :only]

;; Copy directory as-is
["dev" "dev"]

;; Copy to root with alternative delimiters (for files using << >>)
["build" "" ["<<" ">>"]]

;; Copy without substitution (binary/raw files)
[".gitignore" ".gitignore" :raw]
```

## Key Integrations

**clojure-mcp-light** (https://github.com/bhauman/clojure-mcp-light):
- `clj-paren-repair-claude-hook` - Paren repair in Claude Code hooks
- `clj-nrepl-eval` - REPL evaluation CLI for Claude Code
- Configured in `.claude/settings.json` and `.claude/skills/clojure-eval/`

**clj-reload** (https://github.com/tonsky/clj-reload):
- Namespace reloading in dev.clj
- Configured in user.clj

**Portal** (https://github.com/djblue/portal):
- Data inspector via tap>
- Opens automatically in dev namespace

**Figwheel-main** (fullstack only):
- ClojureScript hot reloading
- Configured via dev.cljs.edn and :fig/:cljs-build aliases

## Making Changes

When modifying templates:

1. Edit files in `resources/brianium/clojure_claude_code[_fullstack]/`
2. Test by generating a project to /tmp
3. Verify the generated project compiles and tests pass
4. Update both templates if the change applies to both

Common gotchas:
- The paren-repair hook may alter template files with `{{...}}` syntax in .clj/.cljs/.edn files
- Use `<<` `>>` delimiters for files that need protection (configured in template.edn)
- Files in `build/` go to project root; use empty string `""` as destination
