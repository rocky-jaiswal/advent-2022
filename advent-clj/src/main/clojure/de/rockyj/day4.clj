(ns de.rockyj.day4
  (:require
   [de.rockyj.utils :as utils]
   [clojure.string :as str]
   [clojure.set :as set]))

(defn filter-subsets [items]
  (->> items
       ((fn [line] (str/split line #"\,")))
       (map (fn [rng] (str/split rng #"\-")))
       (map (fn [rng] (map parse-long rng)))
       (map (fn [rng] (range (first rng) (inc (last rng)))))
       (map set)
       ((fn [[rng1 rng2]] (or (set/subset? rng1 rng2) (set/subset? rng2 rng1))))))

(defn filter-overlaps [items]
  (->> items
       ((fn [line] (str/split line #"\,")))
       (map (fn [rng] (str/split rng #"\-")))
       (map (fn [rng] (map parse-long rng)))
       (map (fn [rng] (range (first rng) (inc (last rng)))))
       (map set)
       ((fn [[rng1 rng2]] (set/intersection rng1 rng2)))
       (count)
       ((fn [s] (> s 0)))))

(defn parse-lines-1 [file-input]
  (let [elems (str/split-lines file-input)]
    (->> elems
        ;;  (filter filter-subsets) --> part 1
         (filter filter-overlaps) ;; --> part 2
         (count))))

(defn part-1 []
  (let [raw-input (utils/read-file "day4.txt")]
    (->> raw-input
         (parse-lines-1))))