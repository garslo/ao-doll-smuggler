(ns ao-doll-smuggler.loader
  (:require [clojure.string :refer [split split-lines trim]])
  (:gen-class))


(defn max-weight-line? [line]
  "A predicate determining if `line' is formatted as a 'max weight'
   line."
  (let [max-weight-regex #"^\s*max weight:\s*\d+\s*$"]
    (re-find max-weight-regex line)))

(defn get-max-weight-from-line [line]
  "Assuming `line' is a 'max weight' line (verified by
   `max-weight-line?', extracts the indicated weight as an integer."
  (let [data-sections (split line #":")
        weight-string (trim (last data-sections))]
    (Integer/parseInt weight-string)))

(defn get-max-weight [data]
  "Given `data', a string with the contents of a suitable input file,
   returns the maximum weight as an integer."
  (let [lines (split-lines data)
        weight-line (first (filter #'max-weight-line? lines))]
    (get-max-weight-from-line weight-line)))

(defn doll-info-line? [line]
  "A predicate determining if `line' is formatted as a doll
  information line."
  ;; A doll info line looks like "<alphabetic_name> <digits> <digits>"
  (let [doll-info-regex #"^\s*[a-zA-Z]+\s+\d+\s+\d+"]
   (re-find doll-info-regex line)))
