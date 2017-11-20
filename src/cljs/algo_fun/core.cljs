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

(def app-state (atom (range 10)))

(defn grid-page
  "Demonstrates parameterisation of component."
  []
  (let [n (count @app-state)]
    [:svg  {:view-box "0 0 500 500"
            :width 500
            :height 500}
     (for [x (range 10)
           y (range (/ n 10))]
       (let [counter (+ 1 (* y 10) x)]
         [:g
          [:rect {:width 28, :height 28, :fill "#CCCCCC", :x (* x 30) :y (* y 30)}]
          [:text {:x (+ (* x 30) 14)
                  :y (+ (* 30 y) 20)
                  ; :text-length 20 ; set this dependent on length of numbers
                  :text-anchor "middle"
                  :font-size "12"
                  :font-family "Monospace"} counter]]))]))

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
            :style {:width "100"}}]])


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
