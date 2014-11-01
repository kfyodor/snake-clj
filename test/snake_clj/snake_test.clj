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
    (is (= (move-head :left test-snake) [2 1])))
  (testing "Moves head right"
    (is (= (move-head :right test-snake) [4 1])))
  (testing "Moves head top"
    (is (= (move-head :up test-snake) [3 2])))
  (testing "Moves head bottom")
    (is (= (move-head :down test-snake) [3 0])))

(deftest test-snake-movement
  ; (testing "Moves snake right"
  ;   (is (= (move :right test-snake) [2 1][3 1][4 1])))
  ; We assume that snake has been moving right
  ; I have to figure out how to model snake's movement better
  (testing "Moves snake right"
    (is (= (move :right test-snake) [[2 1][3 1][4 1]])))
  (testing "Moves snake up"
    (is (= (move :up test-snake) [[2 1][3 1][3 2]])))
  (testing "Moves snake down"
    (is (= (move :down test-snake) [[2 1][3 1][3 0]]))))

(deftest test-get-direction
  (testing "direction"
    (is (= (get-direction [[0 0][1 0]]) :right))
    (is (= (get-direction [[0 0][0 1]]) :up))
    (is (= (get-direction [[0 1][0 0]]) :down))
    (is (= (get-direction [[1 0][0 0]]) :left))))

(deftest test-opposite-directions
  (testing "opposite?"
    (is (= (opposite-directions? :left :right) true))
    (is (= (opposite-directions? :right :left) true))
    (is (= (opposite-directions? :up :down) true))
    (is (= (opposite-directions? :down :up) true))
    (is (= (opposite-directions? :up :left) false))
    (is (= (opposite-directions? :right :down) false)) 
    (comment "etc...")))


