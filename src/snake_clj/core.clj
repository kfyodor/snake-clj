(ns snake-clj.core
  (:require [clojure.core.async :as async]
            [lanterna.screen    :as s]
            [snake-clj.controls :as controls]
            [snake-clj.command  :as command]))

; state
(def events  (async/chan))
(def screen  (s/get-screen))
(def frame   20)
(def timeout (atom 1000))

;code

(defmacro go-loop-let
  [bindings & body]
  `(async/go-loop []
    (let ~bindings ~@body)
    (recur)))

(def world0 
  (atom 
    (vec 
      (take 10 (repeat [])))))

(defn run-game []
  (loop []
    (s/redraw screen)
    (Thread/sleep 40)
    (recur)))

(defn run-keyboard-listener []
  (go-loop-let [key (s/get-key-blocking screen)]
    (async/>! events key)))

(defn run-commands-listener []
  (go-loop-let [key (async/<!! events)
                com (controls/command key)]
    (command/handle com)))

(defn game-state [])

(defn -main
  [& args]
  (s/start screen)
  (run-keyboard-listener)
  (run-commands-listener)
  (run-game)
  (s/stop screen))