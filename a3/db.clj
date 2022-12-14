(ns db
  (:require [clojure.java.io :as io])
  (:require [clojure.string]))

;; Option 1
(defn display-customer-table []
  (println "Displaying customer table...")
  (with-open [file (io/reader "cust.txt")]
    (doseq [line (line-seq file)]
      (println line))))

;; Option 2
(defn display-product-table []
  (println "Displaying product table...")
  (with-open [file (io/reader "prod.txt")]
    (doseq [line (line-seq file)]
      (println line))))

;; Functions to map prod-id and cust-id to their respective names

;; Map cust-id to name
(defn parse-customers [fname]
  (with-open [rdr (io/reader fname)]
    (->> (line-seq rdr)
         (map #(clojure.string/split % #"\|"))
         (map (fn [[cust-id name _ _]] [cust-id name]))
         (into {}))))

;; Map prod-id to description
(defn parse-products [fname]
  (with-open [rdr (io/reader fname)]
    (->> (line-seq rdr)
         (map #(clojure.string/split % #"\|"))
         (map (fn [[prod-id item-name _]] [prod-id item-name]))
         (into {}))))

;; Replace all cust-id with customer-name, replace all prod-id with prod-name
;; Returns a string
(defn replace-both-ids [sales-fname customers products]
  (with-open [rdr (io/reader sales-fname)]
    (->> (line-seq rdr)
         (map #(clojure.string/split % #"\|"))
         (map (fn [[sales-id cust-id prod-id item-count]]
                [sales-id "|" (get customers cust-id) "|" (get products prod-id) "|" item-count]))
         (map (partial apply str))
         (clojure.string/join "\n"))))

(def customers (parse-customers "cust.txt"))
(def products (parse-products "prod.txt"))
(def replaced (replace-both-ids "sales.txt" customers products))

(defn display-sales-table []
  (println "Displaying sales table...")
  (println replaced))

;;Functions to calculate total sales 
;; Returns the customer-id for the given string
(defn name-to-id [fname]
  (with-open [rdr (io/reader fname)]
    (->> (line-seq rdr)
         (map #(clojure.string/split % #"\|"))
         (map (fn [[cust-id name _ _]] [name cust-id]))
         (into {}))))

(defn parse-data [filename]
  (map #(clojure.string/split % #"\|")
       (clojure.string/split (slurp filename) #"\n")))

(defn total-sales [sales products cust-id]
  (let [total-value (filter #(= (second %) (str cust-id)) sales)]
    (reduce + (map (fn [[_, _, prod-id, count]]
                     (* (Double/parseDouble count) (Double/parseDouble (nth (first (filter #(= (first %) prod-id) products)) 2))))
                   total-value))))

;; Functions for total count for products
;; Returns the product-id for the given string
(defn prod-to-id [fname]
  (with-open [rdr (io/reader fname)]
    (->> (line-seq rdr)
         (map #(clojure.string/split % #"\|"))
         (map (fn [[prod-id description _]] [description prod-id]))
         (into {}))))

(defn total-count [sales prod-id]
  (let [total-value (filter #(= (nth % 2) (str prod-id)) sales)]
    (reduce + (map read-string (map (fn [[_, _, _, count]] count) total-value)))))


