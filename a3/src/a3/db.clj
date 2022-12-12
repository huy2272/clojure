(ns db
  (:require [clojure.java.io :as io])
  (:require [clojure.string]))

(defn parse-customers [fname]
  (with-open [rdr (io/reader fname)]
    (->> (line-seq rdr)
         (map #(clojure.string/split % #"\|"))
         (map (fn [[cust-id name _ _]] [cust-id name]))
         (into {}))))

(defn parse-products [fname]
  (with-open [rdr (io/reader fname)]
    (->> (line-seq rdr)
         (map #(clojure.string/split % #"\|"))
         (map (fn [[prod-id item-name _]] [prod-id item-name]))
         (into {}))))

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

(defn display-customer-table []
  (println "Displaying customer table...")
  (with-open [file (io/reader "cust.txt")]
    (doseq [line (line-seq file)]

      (println line))))

(defn display-product-table []
  (println "Displaying product table...")
  (with-open [file (io/reader "prod.txt")]
    (doseq [line (line-seq file)]
      (println line))))

(defn display-sales-table []
  (println "Displaying sales table...") 
  (println replaced))

(defn total-sales-for-customer [customer-id]
  (println (format "Total sales for customer %d: $XXX" customer-id)))

(defn total-count-for-product [product-id]
  (println (format "Total count for product %d: XXX" product-id)))

