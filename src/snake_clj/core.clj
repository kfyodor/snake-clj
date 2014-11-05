(ns snake-clj.core
  (:require [clojure.core.async :as async]
            [snake-clj.screen   :as s]
            [snake-clj.controls :as controls]
            [snake-clj.command  :as command]
            [snake-clj.snake    :as snake]
            [snake-clj.world    :as world]))

; state
(def keyboard (async/chan 1))
(def events   (async/chan 1))
(def frame-freq 400)
(def ticks)
(def initial-snake [[1 0][2 0][3 0][4 0][5 0]])

;code

(defmacro go-loop-let
  [bindings & body]
  `(async/go-loop []
    (let ~bindings ~@body)
    (recur)))

(defn run-keyboard-listener []
  "Listens keyboard events"
  (go-loop-let [key (s/get-key-blocking)]
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
    (loop [idx 0
           snake initial-snake]

      (let [transformation nil
            new-snake      (if transformation 
                             (transformation snake)
                             (snake/move snake))]
      (s/render new-snake)
      (Thread/sleep frame-freq)
      (recur (inc idx) new-snake)))))

(defn game []
  "Game loop. Blocks main thread until game is over"
  (loop []
    (if (not (@world/world :game-over?)) (recur))))

(defn -main
  [& args]
  (s/start)
  (run-keyboard-listener)
  (run-commands-listener)
  (run-renderer)

  (game))