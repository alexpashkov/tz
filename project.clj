(defproject tz "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [http-kit "2.3.0"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-json "0.4.0"]
                 [org.clojure/tools.logging "0.4.0"]
                 [ch.qos.logback/logback-classic "1.2.3"]
                 [me.raynes/fs "1.4.6"]
                 [org.apache.kafka/kafka_2.11 "1.0.0" :exclusions [org.slf4j/slf4j-log4j12
                                                                   log4j/log4j]]
                 [org.slf4j/log4j-over-slf4j "1.7.25"]
                 [org.apache.curator/curator-test "2.8.0"]
                 [org.apache.kafka/kafka-clients "1.0.0"]]
  :plugins [[lein-cljfmt "0.6.4"]]
  :main ^:skip-aot tz.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
