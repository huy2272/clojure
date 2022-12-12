(ns db
  (:require [clojure.java.io :as io])
  (:require [clojure.string]))

(defn custHandler [line]
  (let [[custId name address phone] (clojure.string/split line #"|")]
    {:custId custId :name name :address address :phone phone}))

(defn prodHandler [line]
  (let [[prodId description unitCost] (clojure.string/split line #"|")]
    {:prodId prodId :description description :unitCost unitCost}))


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
  (with-open [file (io/reader "sales.txt")]
    (map custHandler (line-seq file))))

(defn total-sales-for-customer [customer-id]
  (println (format "Total sales for customer %d: $XXX" customer-id)))

(defn total-count-for-product [product-id]
  (println (format "Total count for product %d: XXX" product-id)))

