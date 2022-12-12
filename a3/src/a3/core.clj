(ns a3.core
  (:require [clojure.java.io :as io]))

(defn display-customer-table []
  (println "Displaying customer table...")
  (with-open [file (io/reader "user.txt")]
    (doseq [line (line-seq file)]
      (println line))))

(defn display-product-table []
  (println "Displaying product table...")
  (with-open [file (io/reader "prod.txt")]
    (doseq [line (line-seq file)]
      (println line))))

(defn display-sales-table []
  (println "Displaying sales table...")
  (with-open [file (io/reader "sales.txt")]
    (doseq [line (line-seq file)]
      (println line))))

(defn total-sales-for-customer [customer-id]
  (println (format "Total sales for customer %d: $XXX" customer-id)))

(defn total-count-for-product [product-id]
  (println (format "Total count for product %d: XXX" product-id)))

(defn menu []
  (println "*** Sales Menu ***")
  (println "------------------")
  (println "1. Display Customer Table")
  (println "2. Display Product Table")
  (println "3. Display Sales Table")
  (println "4. Total Sales for Customer")
  (println "5. Total Count for Product")
  (println "6. Exit")
  (println "Enter an option?")

  (let [option (read-line)]
    (cond
      (= option "1") (display-customer-table)
      (= option "2") (display-product-table)
      (= option "3") (display-sales-table)
      (= option "4") (total-sales-for-customer (read-line))
      (= option "5") (total-count-for-product (read-line))
      (= option "6") (println "Exiting..."))))

(menu)


