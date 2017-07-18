(ns myapp.core
  (:require [reagent.core :as r]
            [reagent.session :as session]
            [datafrisk.core :as d]
            [reagent-material-ui.core :as md]
            [secretary.core :as secretary :include-macros true]
            [goog.events :as events]
            [goog.history.EventType :as HistoryEventType]
            [markdown.core :refer [md->html]]
            [myapp.ajax :refer [load-interceptors!]]
            [ajax.core :refer [GET POST]]
            [buddy.core.hash :as bc]
            [myapp.home.view :refer [home-page app-db customers-details]])
  (:import goog.History))

(defn nav-link [uri title page collapsed?]
  [:li.nav-item
   {:class (when (= page (session/get :page)) "active")}
   [:a.nav-link
    {:href uri
     :on-click #(reset! collapsed? true)} title]])

#_(defn navbar []
  (let [
        muitheme (md/getMuiTheme)
        color (aset muitheme "tableRow" "stripeColor" "rgba(255, 0, 0, 0.4)")]
    [md/MuiThemeProvider {:mui-theme muitheme}
     [:div]]))




(defn page []
  [:div
   [(session/get :current-page)]
   [d/DataFriskShell @app-db]])

;; -------------------------
;; Routes
(secretary/set-config! :prefix "#")

(secretary/defroute "/" []
  (session/put! :current-page home-page))

(secretary/defroute "/customers" []
  (session/put! :current-page customers-details))

;; -------------------------
;; History
;; must be called after routes have been defined
(defn hook-browser-navigation! []
  (doto (History.)
        (events/listen
          HistoryEventType/NAVIGATE
          (fn [event]
              (secretary/dispatch! (.-token event))))
        (.setEnabled true)))

;; -------------------------
;; Initialize app
(defn fetch-docs! []
  (GET "/docs" {:handler #(session/put! :docs %)}))

(defn mount-components []
  (r/render [#'navbar] (.getElementById js/document "navbar"))
  (r/render [#'page] (.getElementById js/document "app")))

(defn init! []
  (load-interceptors!)
  (fetch-docs!)
  (hook-browser-navigation!)
  (mount-components))
