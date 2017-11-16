(ns user
  (:require [clojure.tools.namespace.repl :as tnr]
            [figwheel-sidecar.repl-api :as repl-api]))

(defn start
  []
  ; (println "I'm starting now")
  (println "Start completed"))

(defn reset []
  (tnr/refresh :after 'user/start))

(defn fig-start []
  (repl-api/start-figwheel!))

(defn fig-stop []
  (repl-api/stop-figwheel!))

(defn cljs-repl []
  (repl-api/cljs-repl))

(println "proto-repl-demo dev/user.clj loaded.")

; (load "../src/isbn_search/core")
; (in-ns 'isbn-search.core)
