(ns myapp.home.view
  (:require [reagent-material-ui.core :as md]
            [reagent.core :as r]
            [datafrisk.core :as d]
            [buddy.core.hash :as bc]

            [ajax.core :refer [GET POST PUT DELETE]]

            [secretary.core :as secretary :refer [dispatch!] :include-macros true]))
(def table-head-style {:style {:background-color "rgb(240,240,240)"}})
(def table-head-text-style {:style {:color "black" :font-size "16px"}})
(def app-db (r/atom {}))

(defn customers-details []
  [:h1 "customers Details"]


  #_[:ul


        (for [author (:author-list @app-db)]
          ^{:key author} [:li [:input {:type     "button" :value (:authorname author)
                                       :on-click #(do (swap! app-db update-in [:current-author] (fn [] author))
                                                      (dispatch! "/view-author"))}]])])

(defn home-page []

  [md/MuiThemeProvider {:mui-theme (md/getMuiTheme (clj->js {:tableRow {:stripeColor "#f0f0f0"}}))}
   [:div {:style {:font-size "15px" :padding-left "20px"}}

    [:h1 "hello"]
    [md/TextField {:floating-label-text "name"   :on-blur #(swap! app-db update-in [:current-customer :name]
                                                                  (fn [] (-> % .-target .-value))) }]
    [:br]
    [md/TextField {:floating-label-text "state"   :on-blur #(swap! app-db update-in [:current-customer :state]
                                                                  (fn [] (-> % .-target .-value))) }]
    [md/RaisedButton {:label "Add name" :secondary true :style {:margin-top "20px" :height "25px"} :label-style {:font-size "10px"}
                      :on-mouse-down #(dispatch! "/customers")
                      #_(POST "/customersDetails" {:params  ( :current-customer @app-db)
                                                                      :handler (fn [response] (dispatch! "/customersDetails"))})
                      }]]])
