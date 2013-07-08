(ns ao-doll-smuggler.knapsack-test
  (:require [clojure.test :refer :all]
            [ao-doll-smuggler.knapsack :refer [max-value
                                               collect-dolls-in-solution]]
            [ao-doll-smuggler.loader :refer [get-doll-info
                                             get-max-weight]]))

;; Example data and results table taken from
;; http://www.cse.msu.edu/~torng/Classes/Archives/cse830.03fall/Lectures/Lecture10.ppt
(let [doll-data [{:name "A" :weight 2 :value 40}
                 {:name "B" :weight 3 :value 50}
                 {:name "C" :weight 1 :value 100}
                 {:name "D" :weight 5 :value 95}
                 {:name "E" :weight 3 :value 30}]]

  (deftest test-max-value-as-output-table
   (let [result-empty-0 (max-value doll-data 0 1)
         result-A-0 (max-value doll-data 1 1)
         result-empty-10 (max-value doll-data 0 11)
         result-E-10 (max-value doll-data 5 11)
         result-A-1 (max-value doll-data 1 2)
         result-B-3 (max-value doll-data 2 4)]
     (is (= 0 result-empty-0))
     (is (= 0 result-A-0))
     (is (= 0 result-empty-10))
     (is (= 245 result-E-10))
     (is (= 50 result-B-3))
     (is (= 0 result-A-1))))

  (deftest test-collect-dolls-in-solution
    (let [max-weight 10
          included-dolls #{{:name "B" :weight 3 :value 50}
                           {:name "C" :weight 1 :value 100}
                           {:name "D" :weight 5 :value 95}}
          result (collect-dolls-in-solution doll-data
                                            max-weight)]
    (is (= result included-dolls)))))
