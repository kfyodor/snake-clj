

(defn each-with-index [func coll]
  (loop [c coll idx 0]
    (let [h (first c) 
          r (rest  c)]
    (func idx h)
    (if (seq r) (recur r (inc idx))))))

(defn from-sparse-row
  ([row-size]
    (row-from-sparse "" row-size []))

  ([row-size points] ; here's an exception
    (row-from-sparse "" row-size points))

  ([blank row-size points]
   (let [blank-region (fn [n] (take n (repeat blank)))
         pts (sort ; TODO: figure out how to efficiently remove duplicates
               (filter 
                 (fn [p]
                   (let [x (first p)]
                     (and (>= x 0) (<  x row-size))))
                 points))]
     (loop [row [] i 0 p pts]
       (let [[x _ v] (first p)
             b-size  (if x (- x i) i)
             r       (rest  p)
             new-row (concat row (blank-region b-size) [v])]
         (if (seq r)
           (recur  new-row (inc x) r)
           (concat new-row (blank-region (- row-size x)))))))))

; (defn from-sparse
;   ([row-size]
;      (from-sparse "" row-size row-size []))

;   ([row-size col-size]
;      (from-sparse "" row-size col-size []))

;   ([row-size col-size points]
;      (from-sparse "" row-size col-size points))

;   ([blank row-size col-size points]
;      (let [pts      (partition-by second points)]
;        loop [rows [] p pts i row-size])))


