(ns snake-clj.matrix-test
  (:require [clojure.test    :refer :all]))

(use 'snake-clj.util.matrix)


(def blank "")
(def size  10)
(def pts1  [[1 0 "#"] [3 0 "#"] [5  0 "#"]])
(def pts2  [[1 0 "#"] [3 0 "#"] [3  0 "#"]])
(def pts3  [[1 0 "#"] [3 0 "#"] [11 0 "#"]])

(deftest test-sparse-rows
  (testing "makes sparse row from points"
    (is (from-sparse-row blank size pts1) ["" "#" "" "#" "" "#" "" "" "" ""])
    (is (from-sparse-row blank size pts2) ["" "#" "" "#" "" "" "" "" "" ""])
    (is (from-sparse-row blank size pts3) ["" "#" "" "#" "" "" "" "" "" ""])))