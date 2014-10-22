(ns snake-clj.controls)

(defn expand-key [m control]
  (let [keys  (first control) 
        value (second control)]
    (reduce 
      #(assoc %1 %2 value) m keys)))

(defn expand-controls [controls]
  (reduce 
    expand-key {} controls))

(def controls 
  (expand-controls
    {[:w :up]     :up
     [:s :down]   :down
     [:a :left]   :left
     [:d :right]  :right
     [:q :escape] :quit}))

(defn command [k]
  (let [t   (type k)
        key (cond
              (instance? java.lang.Character  k) (keyword (str k))
              (instance? clojure.lang.Keyword k) k
              :else 
                (keyword k))]
    (controls key)))