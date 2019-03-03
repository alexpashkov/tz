(ns tz.http
  (:require [compojure.core :refer [defroutes POST GET DELETE]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.util.response :refer [response]]
            [tz.filters :as filters]))

(defn create-filter [{body :body}]
  (println (body "q") (body "topic"))
  (when body
    (let [topic (body "topic")
          q (body "q")]
      (if (and (string? topic)
               (> (count topic) 0)
               (string? q))
        (response (dissoc (filters/create-filter! topic q) :consumer))
        {:status 400}))))

(defn read-filter [{{id :id} :params}]
  (response
    (if (empty? id)
      (->> (filters/get-filters)
           (map #(dissoc % :consumer)))
      (filters/get-messages id))))

(defn delete-filter [{{id :id} :params}]
  (response (filters/delete-filter! id)))

(defroutes routes-without-middleware
           (POST "/filter" [] create-filter)
           (GET "/filter" [] read-filter)
           (DELETE "/filter" [] delete-filter))

(def routes (-> #'routes-without-middleware
                (wrap-defaults api-defaults)
                wrap-json-response
                wrap-json-body))
