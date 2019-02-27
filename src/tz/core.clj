(ns tz.core
  (:require [tz.http :refer [routes]]
            [org.httpkit.server :refer [run-server]])
  (:gen-class))

(defn -main []
  (run-server routes {:port 3000})
  (println "server is running"))
