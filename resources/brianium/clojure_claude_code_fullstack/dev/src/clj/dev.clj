(ns dev
  (:require [clojure.pprint :refer [pprint]]
            [clj-reload.core :as reload]
            [portal.api :as p]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Portal Setup (reload-safe)
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defonce portal (p/open))
(defonce _setup-tap (add-tap #'p/submit))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; System Lifecycle
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def config
  "System configuration."
  {:environment "development"})

(defn start
  "Start the development system."
  ([]
   (start config))
  ([c]
   (tap> {:event :system/start :config c})
   :started))

(defn stop
  "Stop the development system."
  []
  (tap> {:event :system/stop})
  :stopped)

(defn suspend
  "Suspend the system before namespace reload."
  []
  (tap> {:event :system/suspend}))

(defn resume
  "Resume the system after namespace reload."
  [c]
  (tap> {:event :system/resume :config c}))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Reloading
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn reload
  "Reload changed namespaces.

  This is the preferred way to reload during development. Consider binding
  this to a keyboard shortcut in your editor (e.g., C-c r in Emacs)."
  []
  (reload/reload))

(defn restart
  "Full restart: stop, reload, and start."
  []
  (stop)
  (reload)
  (start))

;; clj-reload hooks
(defn before-ns-unload []
  (suspend))

(defn after-ns-reload []
  (resume config))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Development Utilities
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn show-config
  "Display current configuration."
  []
  (pprint config))
