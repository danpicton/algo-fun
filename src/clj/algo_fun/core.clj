(ns algo-fun.core
  (:require [clojure.repl :refer [doc source]])
            ; [tupelo.core :as tup])
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

#_(defn levenshtein  ; WIP
    [s1 s2]
    (let [length-s1 (count s1)
          length-s2 (count s2)
          update-grid (fn [] nil)] ; this bit needs completing
      (loop [grid (vec (repeat length-b))
             x 0
             y 0]
       (cond
         (>= y length-b
             grid)
         (> x length-a
            (recur grid 0 (inc y)))
         (<= x length-a
             (recur (update-grid x y) (inc x) y))))))
