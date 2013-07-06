(ns ao-doll-smuggler.loader
  (:require [clojure.string :refer [split trim]])
  (:gen-class))

(defn get-max-weight [data]
  (let [data-sections (split data #":")
(defn max-weight-line? [line]
  (let [max-weight-regex #"^\s*max weight:\s*\d+\s*$"]
    (re-find max-weight-regex line)))
        weight-string (trim (last data-sections))]
    (Integer/parseInt weight-string)))
