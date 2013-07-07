(ns ao-doll-smuggler.knapsack-test
  (:require [clojure.test :refer :all]
            [ao-doll-smuggler.knapsack :refer [create-knapsack-solver
                                               collect-included-dolls]]))

;; Example data and results table taken from
;; http://www.cse.msu.edu/~torng/Classes/Archives/cse830.03fall/Lectures/Lecture10.ppt
(let [doll-data [{:name "A" :weight 2 :value 40}
                 {:name "B" :weight 3 :value 50}
                 {:name "C" :weight 1 :value 100}
                 {:name "D" :weight 5 :value 95}
                 {:name "E" :weight 3 :value 30}]
      max-value-fn (create-knapsack-solver doll-data)]

  (deftest test-max-value-as-output-table
   (let [result-empty-0 (max-value-fn 0 0)
         result-A-0 (max-value-fn 1 0)
         result-empty-10 (max-value-fn 0 10)
         result-E-10 (max-value-fn 5 10)
         result-A-1 (max-value-fn 1 1)]
     (is (= 0 result-empty-0))
     (is (= 0 result-A-0))
     (is (= 0 result-empty-10))
     (is (= 245 result-E-10))
     (is (= 0 result-A-1))
     ))

 (deftest test-collect-included-dolls
   (let [max-weight 10
         included-dolls #{{:name "B" :weight 3 :value 50}
                          {:name "C" :weight 1 :value 100}
                          {:name "D" :weight 5 :value 95}}
         result (collect-included-dolls #'max-value-fn
                                        max-weight
                                        doll-data)]
     (is (= result included-dolls)))))
