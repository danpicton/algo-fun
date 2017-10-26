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
  (let [vb (str "0 0 " n " " n)]
      (fn []
        [:svg  {:view-box vb
                :width 500
                :height 500}
         (for [x (range n)
               y (range n)]
           [:rect {:width 0.9, :height 0.9, :fill "purple", :x x :y y}])])))

(def page (atom (#'grid-page-2 15)))

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
