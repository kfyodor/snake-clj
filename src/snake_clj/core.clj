(ns snake-clj.core
  (:require [clojure.core.async :as async]
            [lanterna.screen    :as s]
            [snake-clj.controls :as controls]
            [snake-clj.command  :as command]
            [snake-clj.snake    :as snake]))

; state
(def events     (async/chan))
(def screen     (delay
                  (s/get-screen)))
(def frame-freq 40)
(def timeout    (atom 1000))

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

(defn run-keyboard-listener []
  (go-loop-let [key (s/get-key-blocking @screen)]
    (async/>! events key)))

(defn run-commands-listener []
  (go-loop-let [key (async/<!! events)
                com (controls/command key)]
    (command/handle com)))

(defn run-game-state []
  (go-loop-let [snake (snake/move!)]
    (println snake)
    (Thread/sleep @timeout)))

(defn run-game []
  (run-game-state)
  (loop []
    (s/redraw @screen)
    (Thread/sleep frame-freq)
    (recur)))

(defn -main
  [& args]
  (s/start @screen)
  (run-keyboard-listener)
  (run-commands-listener)
  (run-game)
  (s/stop screen))