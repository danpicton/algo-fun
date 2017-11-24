(ns algo-fun.core
    (:require [reagent.core :as reagent :refer [atom]]
              [algo-fun.algos :as algos]))

(enable-console-print!)

(println "Printing from src/cljs/algo_fun/core.cljs")

(def era-10 (algos/eratosthenes 10))
(def era-20 (algos/eratosthenes 20))
(def era-30 (algos/eratosthenes 30))

(def html-colours ["MediumSpringGreen" "Coral" "MediumAquaMarine" "Orchid"
                   "Gold" "Plum" "Yellow" "YellowGreen" "Crimson" "Orange"
                   "Light Salmon"])

(def app-state (atom ()))

(defn grid-page
  "Demonstrates parameterisation of component."
  []
  (let [numbers   (count @app-state)]
        ; sieve-map (algos/eratosthenes @app-state)
        ; primes    (:primes sieve-map)]
    [:svg  {:view-box "0 0 500 500"
            :width 500
            :height 500}
     (for [x (range 10)
           y (range (/ numbers 10))]
       (let [counter    (+ 1 (* y 10) x)
             curr-box   (get @app-state (keyword (str "box-" counter)))]
         (println (:col curr-box))
         (if (<= counter numbers)
           [:g
            [:rect {:width 28, :height 28, :fill (or (:col curr-box) "#CCCCCC"), :x (* x 30) :y (* y 30)}]
            [:text {:x (+ (* x 30) 14)
                    :y (+ (* 30 y) 20)
                    ; :text-length 20 ; set this dependent on length of numbers
                    :text-anchor "middle"
                    :font-size "12"
                    :font-family "Monospace"} counter]])))]))


(defn input-and-button
  []
  [:div
   [:button
     {:on-click (fn [e]
                 (swap! app-state #(range (.-value (.getElementById js/document "sieve-size")))))}
              ;  :on-click #(println (.-value (.getElementById js/document "sieve-size")))}]
    "Sieve on"]
   [:input {:type "text"
            :id "sieve-size"
            :style {:width "100px"}}]])



(defn page-root
  ([]
   [:div
     [:h2 "Sieve of Eratosthenes"]
     (input-and-button)
     [:br]
     [:div (grid-page)]]))

(defn on-js-reload []
  (reagent/render [page-root] (.getElementById js/document "app")))

; (defn current-page []
;   [:div [page-root]])

(reagent/render [page-root] (.getElementById js/document "app"))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; REPL experiments
#_(let [r (range 5)]
    (apply hash-map (interleave (map #(keyword (str "k" %)) r) r)))
;; or use zipmap

#_(let [r (range 5)]
    (zipmap (map #(keyword (str "box-" %)) r)))

#_(assoc-in {} [:cats :dogs] "1")

;; intended state
#_{:box-1 {:val 1} :box-2 {:val 2 :col "Orchid"} :box-3 {:val 3 :col "Coral"}}

;; swap app-state with very basic state map
#_(swap! algo-fun.core/app-state #(hash-map :box-1 {:val 1} :box-2 {:val 2 :col "Orchid"} :box-3 {:val 3 :col "Coral"}))

;; filter factorials based on prime (2 in this case)
#_(let [e6 (algo-fun.algos/eratosthenes 6)] ,,,
    (filter #(= (:fac %) 2) (:sieve-hist e6)))

;; using a for to iterate over the output...
;; only prints for now, but you can do what you want with each map
#_(let [e6 (algo-fun.algos/eratosthenes 6)]
    (for [sieve (:sieve-hist e6)]
      (println "Number: " (:num sieve) " and factorial " (:fac sieve))))

;; initial attempt at building structure to create state map with 1 second
;; intervals.
#_(let [in-val     30
        sieve-map  (algos/eratosthenes in-val)
        primes (:primes sieve-map)
        col-map (zipmap (map #(keyword (str %)) primes) html-colours)
        pretend-state (atom {})]
    (for [current-num     (map inc (range in-val))]
      (if (contains? (set primes) current-num)
        ; (println (str current-num " - prime"))
        (swap! pretend-state
               #(assoc @pretend-state
                       (keyword (str "box-" current-num))
                       {:val current-num
                        :col (get col-map (keyword (str current-num)))}))
        (swap! pretend-state
               #(assoc @pretend-state
                       (keyword (str "box-" current-num))
                       {:val current-num
                        :col nil})))))
        ; (println current-num))))
