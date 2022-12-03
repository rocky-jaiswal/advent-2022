(ns de.rockyj.day2
  (:require
   [de.rockyj.utils :as utils]
   [clojure.string :as str]
   [clojure.core.match :refer [match]]))

;; (defonce weights-1 {"A" 1 "B" 2 "C" 3})
(defonce weights-2 {"X" 1 "Y" 2 "Z" 3})
(defonce score     {"X" 0 "Y" 3 "Z" 6})

(defn who-won [opp me]
  (match [opp me]
    ["A" "X"] 3
    ["A" "Y"] 6
    ["A" "Z"] 0
    ["B" "X"] 0
    ["B" "Y"] 3
    ["B" "Z"] 6
    ["C" "X"] 6
    ["C" "Y"] 0
    ["C" "Z"] 3))

(defn calc-my-draw [opp result]
  (match [opp result]
    ["A" "X"] "Z"
    ["A" "Y"] "X"
    ["A" "Z"] "Y"
    ["B" "X"] "X"
    ["B" "Y"] "Y"
    ["B" "Z"] "Z"
    ["C" "X"] "Y"
    ["C" "Y"] "Z"
    ["C" "Z"] "X"))

(defn calc-score [pair]
  (let [me (last pair) opponent (first pair)]
    (+ (who-won opponent me) (weights-2 me))))

(defn calc-score-2 [pair]
  (let [opponent (first pair) result (last pair) my-draw (calc-my-draw opponent result)]
    (+ (score result) (weights-2 my-draw))))

(defn parse-lines [file-input]
  (let [elems (str/split-lines file-input)]
    (->> elems
         (map (fn [elem] (str/split elem #"\s"))))))

(defn part-1 []
  (let [raw-input (utils/read-file "day2.txt")]
    (->> raw-input
         (parse-lines)
         (map calc-score)
         (reduce +))))

(defn part-2 []
  (let [raw-input (utils/read-file "day2.txt")]
    (->> raw-input
         (parse-lines)
         (map calc-score-2)
         (reduce +))))