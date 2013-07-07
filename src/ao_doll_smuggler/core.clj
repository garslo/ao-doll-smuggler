(ns ao-doll-smuggler.core
  (:require; [clojure.tools.cli :refer [cli]]
            [clojure.java.io :refer [as-file]]
            [ao-doll-smuggler.loader :refer [get-doll-info
                                             get-max-weight]]
            [ao-doll-smuggler.knapsack :refer [collect-dolls-in-solution]])
  (:gen-class))

(defn print-solution [dolls]
  (println "packed dolls:")
  (println)
  (println (format "%s%10s%8s" "name" "weight" "value"))
  (doseq [doll dolls]
    (let [name (doll :name)
          weight (doll :weight)
          value (doll :value)]
      (println (format "%s\t%s\t%s" name weight value)))))

(defn invalid-input? [weight doll-data]
  (or (not weight)
      (empty? doll-data)))

(defn invalid-file? [filename]
  (or (empty? filename)
      (not (.exists (as-file filename)))))

(defn die-if-invalid [args]
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
