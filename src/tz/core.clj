(ns tz.core
  (:require [tz.http :refer [routes]]
            [org.httpkit.server :refer [run-server]]
            [clojure.tools.logging :as log])
  (:gen-class))

(defn -main []
  (run-server routes {:port 3000})
  (log/info "server running"))
