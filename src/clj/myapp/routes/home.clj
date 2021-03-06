(ns myapp.routes.home
  (:require [myapp.layout :as layout]
            [myapp.db.core :as db]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]
            ))

(defn home-page []
  (layout/render "home.html"))

(defroutes home-routes
  (GET "/" []
       (home-page))
  (GET "/docs" []
       (-> (response/ok (-> "docs/docs.md" io/resource slurp))
           (response/header "Content-Type" "text/plain; charset=utf-8")))
  (POST "/customersDetails" [] (fn [request] {:response (db/save-customer! (:params request))}))
  (GET "/customers" [] (db/get-customers)))
