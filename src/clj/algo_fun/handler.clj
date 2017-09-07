(ns algo-fun.handler
  (:require ; omitted as in compojure-api
            ;[compojure.core :refer [GET defroutes
            ; [compojure.route :refer [not-found resources]]
            [compojure.route :refer [resources]]
            [hiccup.page :refer [include-js include-css html5]]
            [algo-fun.middleware :refer [wrap-middleware]]
            [config.core :refer [env]]
            ;; added to integrate API functionality
            [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]))

(def mount-target
  [:div#app
      [:h3 "ClojureScript has not been compiled!"]
      [:p "please run "
       [:b "lein figwheel"]
       " in order to start the compiler"]])

(defn head []
  [:head
   [:meta {:charset "utf-8"}]
   [:meta {:name "viewport"
           :content "width=device-width, initial-scale=1"}]
   (include-css (if (env :dev) "/css/site.css" "/css/site.min.css"))])

(defn loading-page []
  (html5
    (head)
    [:body {:class "body-container"}
     mount-target
     (include-js "/js/app.js")]))


(defroutes app-routes
  (GET "/" [] (loading-page))
  (GET "/about" [] (loading-page))

  (resources "/")
  (not-found "Not Found"))

(def app (wrap-middleware #'app-routes))
