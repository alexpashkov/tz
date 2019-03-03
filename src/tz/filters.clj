(ns tz.filters
  (:refer-clojure :exclude [get])
  (:require [clojure.string :refer [lower-case]]
            [clojure.tools.logging :as log]
            [tz.conf :as conf])
  (:import [org.apache.kafka.clients.consumer ConsumerConfig KafkaConsumer]
           [org.apache.kafka.common.serialization StringDeserializer]))

(def ^:private filters (atom {:filters {}
                              :last-id 0}))

(defn consumer
  [group-id topic]
  (let [consumer-props
        {ConsumerConfig/BOOTSTRAP_SERVERS_CONFIG,        conf/server-url
         ConsumerConfig/GROUP_ID_CONFIG,                 group-id
         ConsumerConfig/KEY_DESERIALIZER_CLASS_CONFIG,   StringDeserializer
         ConsumerConfig/VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer
         ConsumerConfig/AUTO_OFFSET_RESET_CONFIG,        "latest"}]
    (doto (KafkaConsumer. consumer-props) (.subscribe [topic]))))

(defn- add-filter [filters topic q]
  (let [id (-> filters :last-id inc)
        id-str (str id)
        filter {:id       id-str
                :topic    topic
                :q        (lower-case q)
                :consumer (consumer id-str topic)
                :messages []}]
    (-> filters
        (assoc-in [:filters id-str] filter)
        (assoc :last-id id))))

(defn- add-message [{q :q :as filter} message]
  (if (.contains (lower-case message) q)
    (update filter :messages conj message)
    filter))

(defn create-filter! [topic q]
  (let [{id      :last-id
         filters :filters} (swap! filters add-filter topic q)]
    (filters (str id))))

(defn get-filter [id]
  (get-in @filters [:filters id]))

(defn get-filters [] (vals (@filters :filters)))

(defn get-messages [id]
  (get-in @filters [:filters id :messages] []))

(defn delete-filter! [id]
  (when-let [filter (get-filter id)]
    (.close (filter :consumer))
    (swap! filters update :filters dissoc id)
    (dissoc filter :consumer)))

(defn poll! []
  (while true
    (doseq [[id {consumer :consumer}] (@filters :filters)]
      (try (let [records (.poll consumer 100)]              ;; not sure about error-handling here
             (doseq [record records]
               (swap! filters update-in [:filters id] add-message (.value record))))
           (catch Exception e (log/error e))))))
