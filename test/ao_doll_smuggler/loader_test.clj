(ns ao-doll-smuggler.loader-test
  (:require [clojure.test :refer :all]
            [ao-doll-smuggler.loader :refer :all]))

(deftest test-get-max-weight-single-line-input
  (let [max-weight 10
        input-string (str "max weight: " max-weight)
        result (get-max-weight input-string)]
    (is (= max-weight result))))
