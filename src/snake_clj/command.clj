(ns snake-clj.command)

(defmulti  handle (fn [events command] command))

(defmethod handle :default
  [_, _]
  :not-a-command)

(defmethod handle :up
  [events, _]
  "pass")

(defmethod handle :down
  [events, _]
  "pass")

(defmethod handle :left
  [events, _]
  "pass")

(defmethod handle :right
  [events, _]
  "pass")

(defmethod handle :down
  [events, _]
  "pass")

(defmethod handle :quit
  [_, _]
  "pass")