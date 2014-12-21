(ns snake-clj.snake
  (:require [clojure.core.match :as m]))

(defn get-x [point] (first  point))
(defn get-y [point] (second point))

(defn head      [snake] (last   snake))
(defn drop-tail [snake] (drop 1 snake))

(defn get-direction
  [snake]
  (let [before-head (last (drop-last snake))
        [x1 y1]     (head snake)
        [x2 y2]     before-head]
    (m/match [(compare x1 x2) (compare y1 y2)]
      [ 1  0] :right
      [ 0 -1] :up
      [-1  0] :left
      [ 0  1] :down)))

(defn opposite-directions?
  [old-direction new-direction]
  (let [rules      #{[:left :right] [:up :down]}
        directions [old-direction new-direction]]
    (or (contains? rules [old-direction new-direction])
        (contains? rules [new-direction old-direction]))))

(defmulti move-head
  (fn [direction snake] direction))

(defmethod move-head :left
  [_, snake]
  [(->> snake head get-x dec)
   (->> snake head get-y)])

(defmethod move-head :right
  [_, snake]
  [(->> snake head get-x inc)
   (->> snake head get-y)])

(defmethod move-head :up
  [_, snake]
  [(->> snake head get-x)
   (->> snake head get-y dec)])

(defmethod move-head :down
  [_, snake]
  [(->> snake head get-x)
   (->> snake head get-y inc)])

(defn- append-head
  [direction snake]
  (let [new-head (move-head direction snake)]
    (conj (vec snake) new-head)))

(defn move
  ([snake]
   (let [direction (get-direction snake)]
     (move direction direction snake)))

  ([direction snake]
   (let [old-direction (get-direction snake)]
     (move old-direction direction snake)))

  ([old-direction new-direction snake]
   (let [opposite     (opposite-directions? new-direction old-direction)
         direction    (if opposite old-direction new-direction)
         get-new-head (partial append-head direction)]
     (->>
       snake
       drop-tail
       get-new-head))))