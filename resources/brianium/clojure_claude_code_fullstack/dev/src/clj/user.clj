(ns user
  (:require [clj-reload.core :as reload]))

(reload/init
 {:dirs ["src/clj" "dev/src/clj" "test/src/clj"]
  :no-reload '#{user}})

(defn dev
  "Load and switch to the dev namespace."
  []
  (require 'dev)
  (in-ns 'dev))
