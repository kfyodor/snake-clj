(ns snake-clj.world)

(def world
  (atom
    { :game_over? false 
      :timeout    1000}))