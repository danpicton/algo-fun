(ns algo-fun.core
  (:require [clojure.repl :refer [doc source]]
            [tupelo.core :as tup])
  (:gen-class))

(defn eratosthenes
  [n]
  "Returns map of all primes to n, along with sieve 'history'."
  (loop [nums     (range 2 (inc n))
         primes   {:primes [] :sieve-hist []}
         curr-fac 2]
    (let [sieve    #(= (mod % curr-fac) 0)
          caught   (filter sieve nums)
          dropped  (remove sieve nums)]
      (if (empty? nums)
        primes
        (recur dropped
               (-> primes
                   (update-in [:primes] conj (first caught))
                   (update-in [:sieve-hist] into
                                (map #(hash-map :num % :fac curr-fac) caught)))
               (first dropped))))))
