(ns snake-clj.core
  (:require [clojure.core.async :as async]
            [lanterna.screen :as s]))


; utils
(defmacro go-loop-let
  [bindings & body]
  `(async/go-loop []
    (let ~bindings ~@body)
    (recur)))

(defn expand-key 
  [m control]
  (let [keys (first control) value (second control)]
    (reduce 
      #(assoc %1 %2 value) m keys)))

(defn expand-controls [controls]
  (reduce 
    expand-key {} controls))


; state
(def events  (async/chan))
(def screen  (s/get-screen))
(def frame   20)
(def timeout (atom 1000))
(def controls 
  (expand-controls
    {['w' :up]     :up
     [:s :down]   :down
     [:a :left]   :left
     [:d :right]  :right
     [:q :escape] :quit}))

;code

(defn get-control [k]
  (let [t   (type k)
        key (cond
              (instance? java.lang.Character  k) (keyword (str k))
              (instance? clojure.lang.Keyword k) k
              :else 
                (keyword k))]
    (controls key)))

(def world0 
  (atom 
    (vec 
      (take 10 (repeat [])))))

(defn handle-event [key]
  (s/put-string screen 20 10 (str (get-control key))))

(defn run-game []
  (loop []
    (s/redraw screen)
    (Thread/sleep 40)
    (recur)))

(defn keyboard-handler []
  (go-loop-let [key (s/get-key-blocking screen)]
    (async/>! events key)))

(defn events-handler []
  (go-loop-let [event (async/<!! events)]
    (handle-event event)))

(defn -main
  [& args]
  (s/start screen)
  (keyboard-handler)
  (events-handler)
  (run-game)
  (s/stop screen))