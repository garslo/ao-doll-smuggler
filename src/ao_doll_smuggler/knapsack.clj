(ns ao-doll-smuggler.knapsack
  (:require )
  (:gen-class))

(def example-doll-info
  [{:name "omicron" :weight 100 :value 150}
   {:name "fry" :weight 30 :value 1}
   {:name "farnsworth" :weight 10 :value 59}
   {:name "bender" :weight 89 :value 13}])

(defn pseudo-knapsack [max-weight doll-info]

  (defn weight [index]
    (let [doll (nth doll-info index)]
      (doll :weight)))

  (defn value [index]
    (let [doll (nth doll-info index)]
      (doll :value)))

  (defn max-value [index max-weight]
    (if (= index 0)
      0
      (if (> max-weight (weight index))
        (max (max-value (- index 1) max-weight)
             (+ (value index)
                (max-value (- index 1) (- max-weight (weight index)))))
        (max-value (- index 1) (weight index)))))
  max-value)
