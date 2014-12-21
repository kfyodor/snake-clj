(ns snake-clj.screen
  (:require [clojure.string        :as string])
  (:require [snake-clj.util.matrix :as m])
  (:require [lanterna.screen       :as scr]))

(defn each-with-index [func coll]
  (loop [c coll idx 0]
    (let [h (first c)
          r (rest  c)]
    (func idx h)
    (if (seq r) (recur r (inc idx))))))

(def screen
  (delay (scr/get-screen)))

(def size 50)

(defn get-key-blocking []
  (scr/get-key-blocking @screen))

(defn start []
  (scr/start @screen))

(defn stop []
  (scr/stop @screen))

(defn render [data]
  (let [snake  (pmap #(conj % "#") data)
        matrix (m/from-sparse size " " snake)]
    (each-with-index
      (fn [idx row]
        (scr/put-string @screen 0 idx (apply str row)))
      matrix))
  (scr/redraw @screen))