(ns ao-doll-smuggler.core
  (:require [clojure.tools.cli :refer [cli]])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  ;; work around dangerous default behaviour in Clojure
  (alter-var-root #'*read-eval* (constantly false))
  (let [[options args banner]
        (cli args
             ["-i" "--input-file" "Doll input file"])]
    (println "input file: " (options :input-file)))
  )
;  (with-command-line args
;    "Stuff goes here"
;    [[input-file "Doll input file to use"]]
;    (println input-file)))
