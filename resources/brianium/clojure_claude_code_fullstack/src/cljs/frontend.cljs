(ns {{top/ns}}.frontend
  (:require [{{top/ns}}.shared :as shared]))

(defn init
  "Initialize the application."
  []
  (js/console.log (shared/greeting "ClojureScript")))

;; Initialize on load
(init)
