(ns de.rockyj.day1
  (:require
   [de.rockyj.utils :as utils]
   [clojure.string :as str]))

(defn sum [arr]
  (reduce + (map (fn [e] (parse-long e)) arr)))

(defn parse-lines [file-input]
  (let [elems (str/split-lines file-input)]
    (->> elems
         (partition-by (fn [s] (not (re-matches #"\d+" s))))
         (map (fn [arr] (sum arr)))
         (remove nil?))))

(defn part-1 []
  (let [raw-input (utils/read-file "day1.txt")]
    (->> raw-input
         (parse-lines)
         (apply max)
         (println))))

(defn part-2 []
  (let [raw-input (utils/read-file "day1.txt")]
    (->> raw-input
         (parse-lines)
         (sort)
         (reverse)
         (take 3)
         (reduce +)
         (println))))