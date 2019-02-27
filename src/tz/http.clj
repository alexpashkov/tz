(ns tz.http
  (:require [compojure.core :refer [defroutes POST GET DELETE]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]))

(defn create-filter [{body :body}]
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    body})

(defn read-filter [{{id :id} :params}] id)

(defn delete-filter [{{id :id} :params}] id)

(defroutes routes-without-middleware
           (POST "/filter" [] create-filter)
           (GET "/filter" [] read-filter)
           (DELETE "/filter" [] delete-filter))

(def routes (wrap-defaults #'routes-without-middleware api-defaults))
