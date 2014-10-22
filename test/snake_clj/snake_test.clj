(ns snake-clj.snake-test
  (:require [clojure.test    :refer :all]))

(use 'snake-clj.snake)

(def test-snake [[1 1][2 1][3 1]])

(deftest test-snake-body
  (testing "Returns correct head"
    (is (= (head test-snake) [3 1])))
  
  (testing "Drops tail"
    (is (= (drop-tail test-snake) [[2 1][3 1]]))))

(deftest test-coords
  (let [h (head test-snake)]
    (testing "Gets x"
      (is (= (get-x h) 3)))
    (testing "Gets y"
      (is (= (get-y h) 1)))))

(deftest test-head-movement
  (testing "Moves head left"
    (is (= (move-head test-snake :left) [2 1])))
  (testing "Moves head right"
    (is (= (move-head test-snake :right) [4 1])))
  (testing "Moves head top"
    (is (= (move-head test-snake :up) [3 2])))
  (testing "Moves head bottom")
    (is (= (move-head test-snake :down) [3 0])))

(deftest test-snake-movement
  ; (testing "Moves snake right"
  ;   (is (= (move :right test-snake) [2 1][3 1][4 1])))
  ; We assume that snake has been moving right
  ; I have to figure out how to model snake's movement better
  (testing "Moves snake right"
    (is (= (move test-snake :right) [[2 1][3 1][4 1]])))
  (testing "Moves snake up"
    (is (= (move test-snake :up) [[2 1][3 1][3 2]])))
  (testing "Moves snake down"
    (is (= (move test-snake :down) [[2 1][3 1][3 0]]))))