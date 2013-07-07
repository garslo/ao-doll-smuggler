(ns ao-doll-smuggler.knapsack
  (:gen-class))

(defn position->index [position]
  (- position 1))

(defn index->position [index]
  (+ index 1))

(defn invalid-position? [position]
  (< position 1))

(defn invalid-weight? [weight]
  (<= weight 0))

(defn invalid-position-or-weight? [position weight]
  (or (invalid-position? position)
      (invalid-weight? weight)))

(defn get-doll [doll-data position]
  (let [index (position->index position)]
    (nth doll-data index nil)))

;;; Mutual recursion between the following two and `max-value'
(declare max-with-doll)
(declare max-without-doll)

(defn raw-max-value [doll-data position current-weight]
  (let [doll (get-doll doll-data position)
        doll-can-fit? (fn []
                        (> current-weight (doll :weight)))]
    (if (invalid-position-or-weight? position current-weight)
      0
      (if (doll-can-fit?)
        (max (max-without-doll doll-data
                               position
                               current-weight)
             (+ (doll :value)
                (max-with-doll doll-data
                               position
                               current-weight)))
        (max-without-doll doll-data
                          position
                          current-weight)))))

;;; For efficiency. This implicitly creates the table necessary for a
;;; dynamic programming algorithm.
(def max-value (memoize raw-max-value))

(defn max-with-doll [doll-data position current-weight]
  (let [doll (get-doll doll-data position)
        new-position (- position 1)
        new-weight (- current-weight (doll :weight))]
      (max-value doll-data new-position new-weight)))

(defn max-without-doll [doll-data position current-weight]
  (let [new-position (- position 1)]
    (max-value doll-data new-position current-weight)))

(defn create-knapsack-solver [doll-data]
  (fn [position max-weight]
    (max-value doll-data position max-weight)))

(defn doll-is-included? [doll-data position weight]
  (not (= (max-value doll-data position weight)
          (max-value doll-data (- position 1) weight))))

(defn collect-dolls-in-solution [doll-data max-weight]
  (letfn [(collect-dolls [position weight acc]
            (let [doll (get-doll doll-data position)]
                (cond
                 (or (= weight 0) (= position 0)) acc
                 (doll-is-included? doll-data
                                    position
                                    weight) (collect-dolls
                                                      (dec position)
                                                      (- weight
                                                         (doll :weight))
                                               (conj acc doll))
                 true (collect-dolls (dec position)
                                     weight
                                     acc))))]
    (collect-dolls (count doll-data) max-weight #{})))
