(ns algo-fun.prod
  (:require [algo-fun.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

; (core/init!)
