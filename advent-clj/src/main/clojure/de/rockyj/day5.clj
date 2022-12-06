(ns de.rockyj.day5
  (:require
   [de.rockyj.utils :as utils]
   [clojure.string :as str]))

(def stack1 (atom ["G" "J" "W" "R" "F" "T" "Z"]))
(def stack2 (atom ["M" "W" "G"]))
(def stack3 (atom ["G" "H" "N" "J"]))
(def stack4 (atom ["W" "N" "C" "R" "J"]))
(def stack5 (atom ["M" "V" "Q" "G" "B" "S" "F" "W"]))
(def stack6 (atom ["C" "W" "V" "D" "T" "R" "S"]))
(def stack7 (atom ["V" "G" "Z" "D" "C" "N" "B" "H"]))
(def stack8 (atom ["C" "G" "M" "N" "J" "S"]))
(def stack9 (atom ["L" "D" "J" "C" "W" "N" "P" "G"]))
(def all-stacks {1 stack1 2 stack2 3 stack3 4 stack4 5 stack5 6 stack6 7 stack7 8 stack8 9 stack9})
;; (def stack1 (atom ["N" "Z"]))
;; (def stack2 (atom ["D" "C" "M"]))
;; (def stack3 (atom ["P"]))
;; (def all-stacks {1 stack1 2 stack2 3 stack3})

(defn move-item-on-by-one [num src-name dest-name]
  ;; (prn "---------------------")
  ;; (prn @stack1)
  ;; (prn @stack2)
  ;; (prn @stack3)
  ;; (prn (str "move " num " from " src-name " to " dest-name))
  (dotimes [_ num]
    (let [src (all-stacks src-name) dest (all-stacks dest-name) lst (first @src)]
      (swap! src (fn [v] (subvec v 1)))
      (swap! dest (fn [v] (into [lst] v))))))
    ;; (prn @stack1)
    ;; (prn @stack2)
    ;; (prn @stack3)))

(defn move-items-by-bulk [num src-name dest-name]
  ;; (prn "---------------------")
  ;; (prn @stack1)
  ;; (prn @stack2)
  ;; (prn @stack3)
  ;; (prn (str "move " num " from " src-name " to " dest-name))
  (let [src (all-stacks src-name) dest (all-stacks dest-name)]
    (swap! dest (fn [v] (into (vec (take num @src)) v)))
    (swap! src (fn [v] (subvec v num)))))
  ;; (prn @stack1)
  ;; (prn @stack2)
  ;; (prn @stack3))

(defn move-crates-1 [txt]
  (let [lines (str/split-lines txt)]
    (doseq [line lines]
      (when (re-matches #"^move(.*)" line)
        (let [match (re-matches #"^move (\d+) from (\d) to (\d)" line)]
          (move-item-on-by-one
           (Integer/parseInt (nth match 1))
           (Integer/parseInt (nth match 2))
           (Integer/parseInt (nth match 3))))))))

;; repetitive - but who cares
(defn move-crates-2 [txt]
  (let [lines (str/split-lines txt)]
    (doseq [line lines]
      (when (re-matches #"^move(.*)" line)
        (let [match (re-matches #"^move (\d+) from (\d) to (\d)" line)]
          (move-items-by-bulk
           (Integer/parseInt (nth match 1))
           (Integer/parseInt (nth match 2))
           (Integer/parseInt (nth match 3))))))))

(defn part-1 []
  (let [raw-input (utils/read-file "day5_2.txt")]
    (move-crates-1 raw-input))
  (str/join "" (map (fn [k] (first @(all-stacks k))) (sort (keys all-stacks)))))

(defn part-2 []
  (let [raw-input (utils/read-file "day5_2.txt")]
    (move-crates-2 raw-input))
  (str/join "" (map (fn [k] (first @(all-stacks k))) (sort (keys all-stacks)))))