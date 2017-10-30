(ns algo-fun.core
    (:require [reagent.core :as reagent :refer [atom]]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]
              [algo-fun.algos :as algos]))

;; -------------------------
;; Views

(def era-10 (algos/eratosthenes 10))
(def era-20 (algos/eratosthenes 20))
(def era-30 (algos/eratosthenes 30))

(defn home-page []
  [:div [:h2 "Welcome to algo-fun"]
   [:div [:a {:href "/about"} "go to about page"]]])

(defn grid-page []
  (let [vb "0 0 3 3"]
      [:svg  {:view-box vb
               :width 500
               :height 500}
        [:rect {:width 0.9, :height 0.9, :fill "purple", :x 0, :y 0}]
        [:rect {:width 0.9, :height 0.9, :fill "purple", :x 0, :y 1}]
        [:rect {:width 0.9, :height 0.9, :fill "purple", :x 0, :y 2}]
        [:rect {:width 0.9, :height 0.9, :fill "purple", :x 1, :y 0}]
        [:rect {:width 0.9, :height 0.9, :fill "purple", :x 1, :y 1}]
        [:rect {:width 0.9, :height 0.9, :fill "purple", :x 1, :y 2}]
        [:rect {:width 0.9, :height 0.9, :fill "purple", :x 2, :y 0}]
        [:rect {:width 0.9, :height 0.9, :fill "purple", :x 2, :y 1}]
        [:rect {:width 0.9, :height 0.9, :fill "purple", :x 2, :y 2}]]))

(defn grid-page-2
  "Demonstrates parameterisation of component."
  [n]
  ; (fn []
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
                :font-family "Monospace"} counter]]))])

(defn input-and-button
  []
  [:div
   [:svg {:width 300 :height 60}
     [:g
       [:rect {:x 0 :y 0 :width 120 :height 45 :fill "#CCCC00" :on-click #(println "clicked")}]
       [:text {:x 60 :y 25 :text-anchor "middle" :font-size "12" :font-family "Calibri"} "Sieve on"]]
     [:g
       [:rect {:x 125 :y 0 :width 150 :height 45 :fill "gray"}]
       [:foreignObject {:x 130 :y 5}
         [:div {:xmlns "http://www.w3.org/1999/xhtml"}]
         [:input {:type "text" :border "none"}]]]]])

(defn page-root
  []
  [:div
     [:h2 "Sieve of Eratosthenes"]
     (input-and-button)
     (grid-page-2 15)])

(def page (atom page-root))
; (def page (atom (#'grid-page-2 15)))

; (def page (atom #'home-page))

(defn current-page []
  [:div [@page]])
;; Initialize app

(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (accountant/configure-navigation!
    {:nav-handler
     (fn [path]
       (secretary/dispatch! path))
     :path-exists?
     (fn [path]
       (secretary/locate-route path))})
  (accountant/dispatch-current!)
  (mount-root))
