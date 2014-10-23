(ns snake-clj.snake)

(defn get-x [point] (first  point))
(defn get-y [point] (second point))

; TODO: this should be in global atom

; (def snake
;   "Initial snake coords"
;   (atom [[0, 0][1, 0][2, 0]]))

; (def direction
;   "Default: right"
;   (atom :right))

; (defn set-direction! [new-direction]
;   (reset! direction new-direction))

(defn head      [snake] (last snake))
(defn drop-tail [snake] (drop 1 snake))

(defn normalize 
  [snake]
  "TODO coords normalization before rendering
        should in different module I guess
  "
  snake)

(defn check-world-around
  [snake]
  "TODO check for apples and snake parts
        should be in different module too"
  snake)

(defmulti move-head
  (fn [snake direction] direction))

(defmethod move-head :left
  [snake, _]
  [(->> snake head get-x dec)
   (->> snake head get-y)])

(defmethod move-head :right
  [snake, _]
  [(->> snake head get-x inc)
   (->> snake head get-y)])

(defmethod move-head :up
  [snake, _]
  [(->> snake head get-x)
   (->> snake head get-y inc)])

(defmethod move-head :down
  [snake, _]
  [(->> snake head get-x) 
   (->> snake head get-y dec)])

(defn- append-head
  [direction snake]
  (let [new-head (move-head snake direction)]
    (conj (vec snake) new-head)))

(defn move
  [snake direction]
  "TODO snake cannot move backwards"
  (let [head (partial append-head direction)]
    (->>
      snake
      drop-tail
      head
      normalize
      check-world-around)))

(defn move! [])
  ; (swap! snake move))