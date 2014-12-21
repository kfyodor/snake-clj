(ns snake-clj.core
  (:require [snake-clj.screen   :as s]
            [snake-clj.controls :as controls]
            [snake-clj.command  :as command]
            [snake-clj.snake    :as snake]
            [snake-clj.world    :as world]))

; state
(def frame-freq 300)
(def ticks)
(def initial-snake [[1 0][2 0][3 0][4 0][5 0]])
(def direction (atom :right))

(defn run-keyboard-listener []
  "Listens keyboard events"
  (future
    (loop []
      (let [k (s/get-key-blocking)]
        (reset! direction (controls/command k)))
      (recur))))

(defn run-renderer []
  "Rendering thread"

  (future
    (loop [idx 0
           snake initial-snake]
      (let [new-snake (snake/move @direction snake)]
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
  (run-renderer)

  (game))