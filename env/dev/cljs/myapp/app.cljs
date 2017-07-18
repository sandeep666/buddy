(ns ^:figwheel-no-load myapp.app
  (:require [myapp.core :as core]
            [devtools.core :as devtools]))

(enable-console-print!)

(devtools/install!)

(core/init!)
