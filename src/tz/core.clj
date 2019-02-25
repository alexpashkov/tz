(ns tz.core
  (:require [tz.server :refer [run-server]])
  (:gen-class))

(defn -main [] (run-server))
