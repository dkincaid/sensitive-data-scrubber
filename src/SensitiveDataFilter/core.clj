(ns SensitiveDataFilter.core
  (:require [clojure.string :as string])
  (:gen-class))

(defn strip-special-chars [input]
  (apply str (filter #(re-find #"[\w\d ]" (str %)) input)))

(defn isSSN? [data]
  "Determines if the given string is a number that looks like a credit card number."
  (let [clean-data (strip-special-chars data)
        char-count (count clean-data)
        area (if (>= char-count 9) (subs clean-data 0 3))
        group (if (>= char-count 9) (subs clean-data 3 5))
        serial (if (>= char-count 9) (subs clean-data 5 9))]
    (cond (not (= char-count 9)) false
          (= area "000") false
          (= area "666") false
          (= (first area) \9) false
          (= group "00") false
          (= serial "0000") false
          (re-find #"\d{3}-\d{2}-\d{4}" data) true
          (re-find #"\d{9}" clean-data) true
          :default false)))

(defn isITIN? [data]
  "Determines if the given string contains a number that looks like an Individual Taxpayer Identification Number (ITIN)"
  (let [clean-data (strip-special-chars data)
        char-count (count clean-data)
        group-num (Integer/parseInt (if (>= char-count 9) (subs clean-data 3 5) "0"))]
    (cond (< group-num 70) false
          (not (= (first clean-data) \9)) false
          (re-find #"\d{3}-\d{2}-\d{4}" data) true
          (re-find #"\d{9}" clean-data) true
          :default false)))

(defn verify-luhn [number]
  "Implementation of Luhn's algorithm checksum verification used for credit card numbers.
   (https://en.wikipedia.org/wiki/Luhn_algorithm)"
  (let [ number-rev (reverse number)
        even-indexes (map str (keep-indexed #(if (odd? %1) %2 ) number-rev))
        odd-indexes (map str (keep-indexed #(if (even? %1) %2) number-rev))
        dbl-evens (map #(str (* 2 (Integer/parseInt %))) even-indexes)
        sum (reduce + (map #(Integer/parseInt (str %)) (apply str (concat dbl-evens odd-indexes))))]
    (= (mod sum 10) 0)))

(defn isCCN? [data]
  "Determines if the given string contains a number that looks like a credit card number."
  (let [clean-data (strip-special-chars data)
        char-count (count clean-data)]
    (cond (or (< char-count 12) (> char-count 19)) false
          (verify-luhn clean-data) true
          :default false)))

(defn remove-sensitive-data [value]
  "Check the given string for any string of digits that looks like a social security number,
individual taxpayer identification number or credit card number"
  (let [candidates (re-seq #"\d[\d\p{Punct}]+" value)
        filterSSN (filter isSSN? candidates)
        filterCCN (filter isCCN? candidates)
        filterITIN (filter isITIN? candidates)
        badnums (concat filterSSN filterCCN filterITIN)]
    (reduce #(string/replace %1 %2 "") value badnums)))

(defn -main [arg]
  (println "Removing sensitive data from:" arg)
  (println "Result:" (remove-sensitive-data arg)))

