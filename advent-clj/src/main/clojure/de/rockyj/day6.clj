(ns de.rockyj.day6
  (:require
   [de.rockyj.utils :as utils]
   [clojure.string :as str]))

(def encountered (atom []))
(def mark (atom nil))

(defn chop-off [v ch]
  (let [ixof (.indexOf @encountered ch)]
    (conj (subvec v (inc ixof)) ch)))

(defn find-marker-1 [txt num]
  (let [chars (str/split txt #"")
        idx (range (inc (count chars)))]
    (doseq [[ch ix] (map list chars idx)]
      (when (< (count @encountered) num)
        (swap! mark (fn [_] ix))
        (if (str/includes? (str/join @encountered) ch)
          (swap! encountered (fn [v] (chop-off v ch)))
          (swap! encountered (fn [v] (conj v ch))))))))

(defn part-2 []
  (let [raw-input (utils/read-file "day6.txt")]
    (find-marker-1 raw-input 14))
  (inc @mark))

(defn part-1 []
  (let [raw-input (utils/read-file "day6.txt")]
    (find-marker-1 raw-input 4))
  (inc @mark))