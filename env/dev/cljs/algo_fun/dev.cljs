(ns ^:figwheel-no-load algo-fun.dev
  (:require
    [algo-fun.core :as core]
    [devtools.core :as devtools]))

(devtools/install!)

(enable-console-print!)

(core/init!)
