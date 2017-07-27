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

(defn gcd
  [m n]
  "Returns the greatest common divisor of m and n."
  (if (= 0 n)
    m
    (recur n (mod m n))))

(defn totient
  [n]
  "Returns the count of coprime numbers to n."
  (let [gcd #(if (= 0 %2)
               %
               (recur %2 (mod % %2)))
        nums (range 1 (inc n))]
    (count
      (filter #(= 1 (gcd n %)) nums))))

(defn levenshtein
  [s1 s2]
  (let [[shortest-string longest-string]   (sort-by count [s1 s2]) ; not sure if this is necessary
        s-vec                              (vec shortest-string)
        l-vec                              (vec longest-string)
        transpose-matrix                   #(apply mapv vector %)]


    (loop []))) ; s-vec l-vec edit-distance
    ;(vector "l: " l-vec " s: " s-vec)))
