(ns de.rockyj.utils
  (:require [clojure.java.io :as io]))

(defn read-file [filePath]
  (slurp (io/resource filePath)))