(ns algo-fun.core
    (:require [reagent.core :as reagent :refer [atom]]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]))

;; -------------------------
;; Views

(defn home-page []
  [:div [:h2 "Welcome to algo-fun"]
   [:div [:a {:href "/about"} "go to about page"]]
   [:div [:a {:href "/llama"} "go to llama"]]])

(defn about-page []
  [:div [:h2 "About algo-fun"]
   [:div [:a {:href "/"} "go to the home page"]]])

(defn llama-page []
  [:div
    [:h2 "A picture of a non-famous, but essential llama"]
    [:div [:img {:src "http://www.windmillanimalfarm.co.uk/gallery/llamas_02b.jpg
 "}]]
    [:div "Some plain text about a llama."]
    [:div {:style {:font-family "Interface, sans-serif" :font-weight "bold"}} "Some jazzy llama text"]
    [:div [:a {:href "/"} "go to the home page"]]])

(defn page-head []
  [:div {:style {:position "absolute"
                 :top "0px"
                 :width "100%"
                 :left "0px"
                 :backgroundColor "green"
                 :color "white"
                 :font-family "Interface, sans-serif"
                 :font-weight "bold"
                 :align "left"}}
        "Page header"])
;; -------------------------
;; Routes

(def page (atom #'home-page))

(defn current-page []
  [:div
   [page-head]
   [@page]])

(secretary/defroute "/" []
  (reset! page #'home-page))

(secretary/defroute "/about" []
  (reset! page #'about-page))

(secretary/defroute "/llama" []
  (reset! page #'llama-page))

;; -------------------------
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
