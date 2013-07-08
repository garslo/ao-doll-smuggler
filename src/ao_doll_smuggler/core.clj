(ns ao-doll-smuggler.core
  (:require [clojure.java.io :refer [as-file]]
            [ao-doll-smuggler.loader :refer [get-doll-info
                                             get-max-weight]]
            [ao-doll-smuggler.knapsack :refer [collect-dolls-in-solution]])
  (:gen-class))

(defn print-solution [dolls]
  "Prints `dolls' as a basic formatted table."
  (println "packed dolls:")
  (println)
  (println (format "%s%10s%8s" "name" "weight" "value"))
  (doseq [doll dolls]
    (let [name (doll :name)
          weight (doll :weight)
          value (doll :value)]
      (println (format "%s\t%s\t%s" name weight value)))))

(defn invalid-input? [weight doll-data]
  "Predicate determining if `weight' and `doll-data' are acceptable
   inputs."
  (or (not weight)
      (empty? doll-data)))

(defn invalid-file? [filename]
  "Predicate determining if `filename' is usable."
  (or (empty? filename)
      (not (.exists (as-file filename)))))

(defn die-if-invalid [args]
  "Determines if `args' contains a valid file. If not, prints a
   message and kills the program."
  (if (or (empty? args)
          (invalid-file? (first args)))
    (do
      (println "Please specify a valid input file.")
        (System/exit 0))))

(defn -main
  "Entry point to the doll smuggling knapsack problem."
  [& args]
  (die-if-invalid args)
  (let [input-file (first args)
        contents (slurp input-file)
        max-weight (get-max-weight contents)
        doll-data (get-doll-info contents)]
        (if (invalid-input? max-weight doll-data)
          (do
            (println "Malformed input file.")
            (System/exit 0)))
        (print-solution (collect-dolls-in-solution doll-data
                                                   max-weight))))
