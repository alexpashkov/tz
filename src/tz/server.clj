(ns tz.server
  (:require
    [org.httpkit.server :as http]
    [compojure.core :refer :all]))

(defn app [_]
  (println "req")
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "hello HTTP!"})


(defn run-server []
  (http/run-server (defroutes (GET "/filters")) {:port 3000}))
