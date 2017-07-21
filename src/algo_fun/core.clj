(ns algo-fun.core
  (:require [clojure.repl :refer [doc source]])
  (:gen-class))

(defn eratosthenes
  [n]
  "Returns map of all primes to n, along with sieved non-primes."
  (loop [nums     (range 2 (inc n))
         primes   []
         curr-fac 2]
    (let [sieve    #(= (mod % curr-fac) 0)
          caught   (filter sieve nums)
          dropped  (remove sieve nums)]
      (if (empty? nums)
        primes
        (recur dropped
               (into primes
                     (map #(hash-map :num % :fac curr-fac))
                     caught)
               (first nums))))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
