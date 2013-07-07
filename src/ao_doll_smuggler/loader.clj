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
   `max-weight-line?'), extracts the indicated weight as an integer."
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

(defn get-doll-info-from-line [line]
  "Retrieves the doll information from `line'. Returns a
   hash-map containing the doll's information.

   Example:
      (get-doll-info-from-line \"fred 10 43\")
      ; => {:name \"fred\" :weight 10 :value 43}"
  (let [doll-info (split line #"\s+")
        ;; TODO: clojure allows unpacking!
        name (nth doll-info 0)
        weight (Integer/parseInt (nth doll-info 1))
        value (Integer/parseInt (nth doll-info 2))]
    (hash-map :name name :weight weight :value value)))

(defn get-doll-info [data]
  "Given `data', a string with the contents of a suitable input file,
  returns a (LIST OR VECTOR?) of the doll information."
  (let [lines (split-lines data)
        doll-lines (filter #'doll-info-line? lines)]
    (map #'get-doll-info-from-line doll-lines)))
