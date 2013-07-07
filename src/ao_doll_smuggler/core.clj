(ns ao-doll-smuggler.core
  (:require [clojure.tools.cli :refer [cli]]
            [ao-doll-smuggler.loader :refer [get-doll-info
                                             get-max-weight]]
            [ao-doll-smuggler.knapsack :refer [collect-dolls-in-solution]])
  (:gen-class))

(defn print-solution [dolls]
  (doseq [doll dolls]
    (let [name (doll :name)
          weight (doll :weight)
          value (doll :value)]
      (println name "\t" weight "\t" value))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  ;; work around dangerous default behaviour in Clojure
  (alter-var-root #'*read-eval* (constantly false))
  (let [[options args banner] (cli args
                                   ["-i" "--input-file" "Doll input file"])
        input-file (first args)]

    (if (not input-file)
      (do
        (println "Please specify an input file.")
        (System/exit 0)))

    (let [contents (slurp input-file)
          max-weight (get-max-weight contents)
          doll-data (get-doll-info contents)
          dolls (collect-dolls-in-solution doll-data
                                           max-weight)]
      (print-solution dolls))))
