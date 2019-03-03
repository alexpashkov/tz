(ns tz.kafka
  (:require [clojure.java.io :as io]
            [clojure.tools.logging :as log]
            [me.raynes.fs :as fs]
            [tz.conf :as conf])
  (:import [org.apache.curator.test TestingServer]
           [kafka.server KafkaConfig KafkaServerStartable]))


(defn start-zookeeper []
  (fs/delete-dir "/tmp/zk")
  (let [zk (TestingServer. conf/zookeeper-port (io/file "/tmp/zk"))]
    (log/info "zk started")
    zk))


(defn start-kafka-server []
  (let [config (KafkaConfig. {"zookeeper.connect"                (str conf/zookeeper-host conf/zookeeper-port)
                              "listeners"                        (str "PLAINTEXT:" conf/server-url)
                              "auto.create.topics.enable"        true
                              "offsets.topic.replication.factor" (short 1)
                              "offsets.topic.num.partitions"     (int 1)})
        kafka (KafkaServerStartable. config)]
    (.startup kafka)
    (log/info "kafka started")
    kafka))

(defn start []
  (start-zookeeper)
  (start-kafka-server))

