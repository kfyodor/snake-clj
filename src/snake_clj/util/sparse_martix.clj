(ns snake-clj.sparse-matrix)

; (defn each-with-index [func coll]
;   (loop [c coll idx 0]
;     (let [h (first c) 
;           r (rest  c)]
;     (func idx h)
;     (if (seq r) (recur r (inc idx))))))

(defn blank-region
  [n blank]
  (take n (repeat blank)))

(defn distinct-by 
  [f coll]
  (pmap 
    #(let [[_ v] %1] (last v))
    (group-by f coll)))

(defn from-sparse-row
  ([row-size]
    (blank-region row-size ""))

 ([row-size points]
    (from-sparse-row "" row-size points))

  ([blank row-size points]
   (let [filter-fn       (fn [p] 
                           (let [x (first p)]
                             (and (>= x 0) (<  x row-size))))
         distinct-points (distinct-by first points)
         pts             (sort (filter filter-fn distinct-points))]
     (loop [row [] i 0 p pts]
       (let [[x _ v] (first p)
             coord   (or x i)
             value   (if v [v] [])
             b-size  (- coord i)
             r       (rest  p)
             new-row (concat row (blank-region b-size blank) value)]
         (if (seq r)
           (recur  new-row (inc coord) r)
           (concat new-row (blank-region (- row-size coord) blank))))))))

(defn from-sparse
  [size blank pts]
  (let [pts      (group-by second (sort pts))
        rows     (range size)
        merge-fn (fn [m row-num]
                   (let [p (pts row-num) row (or p [])]
                     (merge m row)))
        make-row (partial from-sparse-row blank size)
        sparse-m (reduce merge-fn [] rows)]
    (pmap make-row sparse-m)))