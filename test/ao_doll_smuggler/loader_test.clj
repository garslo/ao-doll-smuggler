(ns ao-doll-smuggler.loader-test
  (:require [clojure.test :refer :all]
            [ao-doll-smuggler.loader :refer :all]))

;
;(deftest test-get-max-weight-multiple-line-input
;  (let [max-weight 20
;        input-string (str "max weight: " max-weight "\n"
;                          "\n"
;                          "Lines of Data\n"
;                          "foo 100   43")
;        result (get-max-weight input-string)]
;    (is (= max-weight result))))
;
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

;;; TODO: Add failure tests
(deftest test-get-max-weight-single-line-input
  (letfn [(run-single-test [weight]
            (let [input-string (build-max-weight-string weight)
                  result (get-max-weight input-string)]
              (is (= weight result))))]
    (run-single-test 10)
    (run-single-test 20)))
