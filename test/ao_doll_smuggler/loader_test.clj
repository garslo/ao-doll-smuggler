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

(deftest test-get-max-weight-single-line-input
  "Tests the get-max-weight function with single line inputs."
  (letfn [(run-single-test [weight]
            (let [input-string (build-max-weight-string weight)
                  result (get-max-weight input-string)]
              (is (= weight result))))]
    (run-single-test 10)
    (run-single-test 20)))

(deftest test-get-max-weight-multiple-line-input
  "Tests the get-max-weight function with multi-line inputs."
  (letfn [(run-single-test [weight input-string]
            (let [result (get-max-weight input-string)]
              (is (= weight result))))]
    (run-single-test 20
                     (str "max weight: " 20 "\n"
                          "\n"
                          "Lines of Data\n"
                          "foo 100   43"))))

(defn build-doll-string [name weight value]
  (str name " " weight " " value))

(deftest test-doll-info-line?
  (is (doll-info-line? "foo 10 34"))
  (is (not (doll-info-line? "foo1 12 44")))
  (is (not (doll-info-line? "forty-three 14 99")))
  (is (not (doll-info-line? "brew 103.2 9"))))

(deftest test-get-doll-info-from-line
  (letfn [(run-single-test [name weight value]
            (let [doll-string (build-doll-string name weight value)
                  result (get-doll-info-from-line doll-string)]
              (is (and (= name (result :name))
                       (= weight (result :weight))
                       (= value (result :value))))))]
    (run-single-test "odysseus" 12 1000)))

(deftest test-get-doll-info
  (let [doll-data (str (build-doll-string "barney" 10 32) "\n"
                       (build-doll-string "junior" 2 10))
        result (get-doll-info doll-data)]
    (is (= result
           '({:name "barney" :weight 10 :value 32}
             {:name "junior" :weight 2 :value 10})))))
