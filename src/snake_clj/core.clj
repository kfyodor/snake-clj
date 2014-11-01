(ns snake-clj.core
  (:require [clojure.core.async :as async]
            [lanterna.screen    :as s]
            [snake-clj.controls :as controls]
            [snake-clj.command  :as command]
            [snake-clj.snake    :as snake]
            [snake-clj.world    :as world]))

; state
(def keyboard (async/chan 1))
(def events   (async/chan 1))
(def screen   (delay
                (s/get-screen)))
(def frame-freq 40)
(def ticks)

;code

(defmacro go-loop-let
  [bindings & body]
  `(async/go-loop []
    (let ~bindings ~@body)
    (recur)))

(defn run-keyboard-listener []
  "Listens keyboard events"
  (go-loop-let [key (s/get-key-blocking @screen)]
    (async/>! keyboard key)))

(defn run-commands-listener []
  "Listens to parsed commands that 
   came from keyboard listener"
  (go-loop-let [key (async/<!! keyboard)
                com (controls/command key)]
    (command/handle com events)))

(defn run-renderer []
  "Rendering thread"
  (async/go []
    (loop []
      (s/redraw @screen)
      (Thread/sleep frame-freq)
      (recur))))

(defn game []
  "Game loop. Blocks main thread until game is over"
  (loop []
    (if (not (@world/world :game-over?)) (recur))))

(defn -main
  [& args]
  (s/start @screen)
  (run-keyboard-listener)
  (run-commands-listener)
  (run-renderer)

  (game)

  (s/stop screen))