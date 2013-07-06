(ns ao-doll-smuggler.loader-test
  (:require [clojure.test :refer :all]
            [ao-doll-smuggler.loader :refer :all]))

(deftest test-max-weight-line?
  "Tests the basic functionality of max-weight-line? predicate."
  (is (max-weight-line? "max weight: 40"))
  (is (max-weight-line? "max weight:      23"))
  (is (not (max-weight-line? "max weight 40")))
  (is (not (max-weight-line? "foo bar: 100"))))

(defn build-max-weight-string [max-weight]
  "Builds a properly formatted maximum weight string acceptable to
   the max-weight-line? function."
  (str "max weight: " max-weight))

(deftest test-get-max-weight-from-line
  "Tests the basic functionality of the get-max-weight-from-line
   function."
  (letfn [(run-single-test [weight]
            (let [weight-string (build-max-weight-string weight)]
                (is (= weight
                       (get-max-weight-from-line weight-string)))))]
    (run-single-test 10)
    (run-single-test 5)))

;;; TODO: Add failure tests
(deftest test-get-max-weight-single-line-input
  "Tests the get-max-weight function with single line inputs."
  (letfn [(run-single-test [weight]
            (let [input-string (build-max-weight-string weight)
                  result (get-max-weight input-string)]
              (is (= weight result))))]
    (run-single-test 10)
    (run-single-test 20)))
