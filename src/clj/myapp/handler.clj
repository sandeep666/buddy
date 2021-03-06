(ns myapp.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [myapp.layout :refer [error-page]]
            [myapp.routes.home :refer [home-routes]]
            [myapp.routes.services :refer [service-routes]]
            [compojure.route :as route]
            [myapp.env :refer [defaults]]
            [mount.core :as mount]
            [myapp.middleware :as middleware]
            [clojure.tools.logging :as log]
            [myapp.config :refer [env]]))

(mount/defstate init-app
                :start ((or (:init defaults) identity))
                :stop  ((or (:stop defaults) identity)))

(defn init
  "init will be called once when
   app is deployed as a servlet on
   an app server such as Tomcat
   put any initialization code here"
  []
  (doseq [component (:started (mount/start))]
    (log/info component "started")))

(defn destroy
  "destroy will be called when your application
   shuts down, put any clean up code here"
  []
  (doseq [component (:stopped (mount/stop))]
    (log/info component "stopped"))
  (shutdown-agents)
  (log/info "myapp has shut down!"))

(def app-routes
  (routes
    (-> #'home-routes
        (wrap-routes middleware/wrap-csrf)
        (wrap-routes middleware/wrap-formats))
    #'service-routes
    (route/not-found
      (:body
        (error-page {:status 404
                     :title "page not found"})))))


(def app (middleware/wrap-base #'app-routes))
