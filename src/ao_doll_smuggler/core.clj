(ns ao-doll-smuggler.core
  (:require [clojure.tools.cli :refer [cli]]
            [ao-doll-smuggler.loader :refer [get-doll-info get-max-weight]])
  (:gen-class))

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

    (let [contents (slurp input-file)]
      (println "Max weight: " (get-max-weight contents))
      (println "Doll info: " (get-doll-info contents)))
    ))
