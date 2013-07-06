(ns ao-doll-smuggler.loader-test
  (:require [clojure.test :refer :all]
            [ao-doll-smuggler.loader :refer :all]))

(deftest test-max-weight-line?
  (is (max-weight-line? "max weight: 40"))
  (is (max-weight-line? "max weight:      23"))
  (is (not (max-weight-line? "max weight 40")))
  (is (not (max-weight-line? "foo bar: 100"))))

(defn build-max-weight-string [max-weight]
  (str "max weight: " max-weight))

(deftest test-get-max-weight-from-line
  (letfn [(run-single-test [weight]
            (let [weight-string (build-max-weight-string weight)]
                (is (= weight
                       (get-max-weight-from-line weight-string)))))]
    (run-single-test 10)
    (run-single-test 5)))
(deftest test-get-max-weight-single-line-input
  (let [max-weight 10
        input-string (str "max weight: " max-weight)
        result (get-max-weight input-string)]
    (is (= max-weight result))))
