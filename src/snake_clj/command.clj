(ns snake-clj.command)

(defmulti  handle (fn [command] command))

(defmethod handle :default 
  [_]
  :not-a-command)

(defmethod handle :up 
  [_]
  "pass")

(defmethod handle :down 
  [_]
  "pass")

(defmethod handle :left 
  [_]
  "pass")

(defmethod handle :right 
  [_]
  "pass")

(defmethod handle :down 
  [_]
  "pass")

(defmethod handle :quit 
  [_]
  "pass")