(ns {{top/ns}}.{{main}}-test
  (:require [clojure.test :refer [deftest is testing]]
            [{{top/ns}}.{{main}} :as {{main}}]))

(deftest greet-test
  (testing "greet returns a greeting message"
    (is (= "Hello, World!" ({{main}}/greet "World")))
    (is (= "Hello, Clojure!" ({{main}}/greet "Clojure")))))
