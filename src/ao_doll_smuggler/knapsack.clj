(ns ao-doll-smuggler.knapsack
  (:gen-class))

(defn position->index [position]
  (dec position))


(defn invalid-position? [position]
  (< position 1))


(defn invalid-weight? [weight]
  (<= weight 0))


(defn invalid-position-or-weight? [position weight]
  (or (invalid-position? position)
      (invalid-weight? weight)))


(defn get-doll [doll-data position]
  "Retrieves the doll stored in `doll-data' at position `position'."
  (let [index (position->index position)]
    (nth doll-data index nil)))


;;; Mutual recursion between the following two and `max-value'
(declare max-with-doll)
(declare max-without-doll)

(defn raw-max-value [doll-data position current-weight]
  "The maximum value function for `doll-data' using the first
   `position' dolls at `current-weight' max weight.

   This function is the core of the 0/1-knapsack algorithm. Its
   purpose is to provide values for a `position'x`max-weight'
   table. Rather than use an explicit table, we memoize this
   function below and use the memoized function as a table."
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
  "Returns the maximum value obtainable with the doll at `position'
   with max weight `current-weight' placed in the knapsack."
  (let [doll (get-doll doll-data position)
        new-position (- position 1)
        new-weight (- current-weight (doll :weight))]
      (max-value doll-data new-position new-weight)))


(defn max-without-doll [doll-data position current-weight]
  "Returns the maximum value obtainable without the doll at `position'
   with max weight `current-weight' in the knapsack."
  (let [new-position (- position 1)]
    (max-value doll-data new-position current-weight)))


(defn create-knapsack-solver [doll-data]
  (fn [position max-weight]
    (max-value doll-data position max-weight)))

(defn doll-is-included? [doll-data position weight]
  "Determines if the doll at `position' and `weight' is included in
   the optimal knapsack solution."
  (not (= (max-value doll-data position weight)
          (max-value doll-data (- position 1) weight))))


;;; TODO: This is ugly
(defn collect-dolls-in-solution [doll-data max-weight]
  "Returns a set containing the dolls in `doll-data' which comprise
   the optimal knapsack solution."
  (letfn
      [(collect-dolls [position weight acc]
         (let [doll (get-doll doll-data position)]
           (cond
            (or (= weight 0) (= position 0)) acc
            (doll-is-included? doll-data position weight) (collect-dolls
                                                           (dec position)
                                                           (- weight
                                                              (doll :weight))
                                                           (conj acc doll))
            true (collect-dolls (dec position)
                                                   weight
                                                   acc))))]
    (collect-dolls (count doll-data) max-weight #{})))
