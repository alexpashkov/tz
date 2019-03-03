(ns tz.core
  (:require [tz.http :refer [routes]]
            [org.httpkit.server :refer [run-server]]
            [clojure.tools.logging :as log]
            [tz.conf :as conf]
            [tz.filters :as filters])
  (:gen-class))

(defn -main []
  (run-server routes {:port conf/api-port})
  (log/info "server running")
  (filters/poll!))
