(ns algo-fun.core
    (:require [reagent.core :as reagent :refer [atom]]
              [algo-fun.algos :as algos]))

(println "Printing from src/cljs/algo_fun/core.cljs")

(def era-10 (algos/eratosthenes 10))
(def era-20 (algos/eratosthenes 20))
(def era-30 (algos/eratosthenes 30))

(def app-state (atom era-10))

(defn grid-page
  "Demonstrates parameterisation of component."
  []
  (let [n (count (:sieve-hist @app-state))]
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
   [:svg {:width 300 :height 60}
     [:g
       [:rect {:x 0
               :y 0
               :width 120
               :height 45
               :fill "#CCCC00"
               :on-click (fn [e]
                           (swap! @app-state assoc {} (algos/eratosthenes (.-value (.getElementById js/document "sieve-size")))))}]
              ;  :on-click #(println (.-value (.getElementById js/document "sieve-size")))}]
       [:text {:x 60 :y 25 :text-anchor "middle" :font-size "12" :font-family "Calibri"} "Sieve on"]]
     [:foreignObject {:x 125 :y 0}
       [:div {:xmlns "http://www.w3.org/1999/xhtml"}
        [:input {:type "text"
                 :id "sieve-size"
                 :style {:border "1px solid red"
                         :background-color "#DDDDDD"
                         :width 158
                         :height 40
                         :font-size 25
                         :font-family "Calibri"
                         :padding-left "7px"
                         :padding-right "7px"}}]]]]])

(defn page-root
  ([]
   [:div
     [:h2 "Sieve of Eratosthenes"]
     (input-and-button)
     [:div (grid-page)]]))


; (defn current-page []
;   [:div [page-root]])

(reagent/render [page-root] (.getElementById js/document "app"))
