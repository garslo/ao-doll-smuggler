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

        doll-can-fit? (fn []
                        (> max-weight doll-weight))]
    ;; Start of 0/1 knapsack algorithm
    (if (invalid-position-or-weight? position max-weight)
      0
      (if (doll-can-fit?)
        (max (max-without-this-doll)
             (+ (value position doll-data) (max-with-this-doll)))
        (max-without-this-doll)))))

(defn create-knapsack-solver [doll-data]
  (fn [position max-weight]
    (max-value doll-data position max-weight)))

