(ns ao-doll-smuggler.loader
  (:require [clojure.string :refer [split split-lines trim]])
  (:gen-class))


(defn max-weight-line? [line]
  (let [max-weight-regex #"^\s*max weight:\s*\d+\s*$"]
    (re-find max-weight-regex line)))

(defn get-max-weight-from-line [line]
  (let [data-sections (split line #":")
        weight-string (trim (last data-sections))]
    (Integer/parseInt weight-string)))

(defn get-max-weight [data]
  (let [lines (split-lines data)
        weight-line (first (filter #'max-weight-line? lines))]
    (get-max-weight-from-line weight-line)))
