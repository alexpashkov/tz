(ns tz.filters
  (:refer-clojure :exclude [get]))

(def ^:private filters (atom {:filters {}
                              :last-id 0}))

(defn- add-filter [filters filter]
  (-> filters
      (assoc-in [:filters (:last-id filters)] filter)
      (update :last-id inc)))

(defn add [filter]
  (swap! filters add-filter filter))

(defn get
  ([]
   (:filters @filters))
  ([filter-id]
   (get-in @filters [:filters filter-id])))

(defn delete [filter-id]
  (swap! filters update :filters dissoc filter-id))
