(ns snake-clj.screen
  (:require [clojure.string    :as string])
  (:require [lanterna.screen   :as scr]))

(def screen
  (delay (scr/get-screen)))

(def size 20)

(defn get-key-blocking []
  (scr/get-key-blocking @screen))

(defn start []
  (scr/start @screen))

(defn stop []
  (scr/stop @screen))

(defn render [data]
  (scr/redraw @screen))