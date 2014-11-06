(ns snake-clj.util.sparse-matrix)

; (defn each-with-index [func coll]
;   (loop [c coll idx 0]
;     (let [h (first c) 
;           r (rest  c)]
;     (func idx h)
;     (if (seq r) (recur r (inc idx))))))

(defn from-sparse-row
  ([row-size]
    (from-sparse-row "" row-size []))

  ([row-size points] ; here's an exception
    (from-sparse-row "" row-size points))

  ([blank row-size points] ; TODO: remove dupes
   (let [blank-region (fn [n] (take n (repeat blank)))
         filter-fn    (fn [p] 
                        (let [x (first p)]
                          (and (>= x 0) (<  x row-size))))
         pts          (sort (filter filter-fn points))]
     (loop [row [] i 0 p pts]
       (let [[x _ v] (first p)
             b-size  (if x (- x i) i)
             r       (rest  p)
             new-row (concat row (blank-region b-size) [v])]
         (if (seq r)
           (recur  new-row (inc x) r)
           (concat new-row (blank-region (- row-size x)))))))))

(defn from-sparse
  [size blank pts]
  (let [pts      (group-by second (sort pts))
        rows     (range size)
        merge-fn (fn [m row-num]
                   (let [p (pts row-num) row (or p [])]
                     (merge m row)))
        make-row (partial (from-sparse-row blank size))
        sparse-m (reduce merge-fn [] rows)]
    (pmap make-row sparse-m)))