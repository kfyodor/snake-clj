(defproject snake_clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url         "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main snake-clj.core

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clojure-lanterna "0.9.4"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [org.clojure/core.match "0.2.1"]
                 [clatrix "0.4.0"]]
  :profiles {:dev {:plugins [[com.jakemccrary/lein-test-refresh "0.5.2"]]}})