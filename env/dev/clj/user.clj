(ns user
  (:require [clojure.tools.namespace.repl :as tnr]))

(defn start
  []
  ; (println "I'm starting now")
  (println "Start completed"))

(defn reset []
  (tnr/refresh :after 'user/start))

(println "proto-repl-demo dev/user.clj loaded.")

; (load "../src/isbn_search/core")
; (in-ns 'isbn-search.core)
