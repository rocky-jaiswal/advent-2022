(ns de.rockyj.day3
  (:require
   [de.rockyj.utils :as utils]
   [clojure.string :as str]))

(defn char-range [start end]
  (map char (range (int start) (inc (int end)))))

(defn char-priority []
  (let [small-case (char-range \a \z) big-case (char-range \A \Z)]
    (merge (zipmap (map str small-case) (range 1 27))
           (zipmap (map str big-case) (range 27 53)))))

(defn find-common [items]
  (let [priority-map (char-priority)]
    (->> items
         ((fn [line] (str/split line #"")))
         ((fn [line] (partition (/ (count line) 2) line)))
         ((fn [[lst1 lst2]] (filter (fn [e] (contains? (set lst1) e)) lst2)))
         (first)
         (map str)
         (map priority-map))))

(defn find-common-in-group [items]
  (->> items
       ((fn [[lst1 lst2 lst3]] (filter (fn [e] (and (contains? (set lst1) e) (contains? (set lst3) e))) lst2)))))

(defn parse-lines-1 [file-input]
  (let [elems (str/split-lines file-input)]
    (->> elems
         (map find-common)
         (flatten))))

(defn parse-lines-2 [file-input]
  (let [elems (str/split-lines file-input) priority-map (char-priority)]
    (->> elems
         (partition 3)
         (map find-common-in-group)
         (map first)
         (map str)
         (map priority-map))))

(defn part-1 []
  (let [raw-input (utils/read-file "day3.txt")]
    (->> raw-input
         (parse-lines-1)
         (reduce +))))

(defn part-2 []
  (let [raw-input (utils/read-file "day3.txt")]
    (->> raw-input
         (parse-lines-2)
         (reduce +))))