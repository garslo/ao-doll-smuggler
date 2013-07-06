(ns ao-doll-smuggler.loader
  (:require [clojure.string :refer [split trim]])
  (:gen-class))

(defn get-max-weight [data]
  (let [data-sections (split data #":")
        weight-string (trim (last data-sections))]
    (Integer/parseInt weight-string)))
