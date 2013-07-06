(ns ao-doll-smuggler.loader-test
  (:require [clojure.test :refer :all]
            [ao-doll-smuggler.loader :refer :all]))

(deftest test-max-weight-line?
  (is (max-weight-line? "max weight: 40"))
  (is (max-weight-line? "max weight:      23"))
  (is (not (max-weight-line? "max weight 40")))
  (is (not (max-weight-line? "foo bar: 100"))))
(deftest test-get-max-weight-single-line-input
  (let [max-weight 10
        input-string (str "max weight: " max-weight)
        result (get-max-weight input-string)]
    (is (= max-weight result))))
