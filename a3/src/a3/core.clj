(ns core
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

(defn replace-cust-ids [sales-fname customers]
  (with-open [rdr (io/reader sales-fname)]
    (->> (line-seq rdr)
         (map #(clojure.string/split % #"\|"))
         (map (fn [[sales-id cust-id prod-id item-count]]
                [sales-id (get customers cust-id) prod-id item-count]))
         (map (partial apply str))
         (clojure.string/join "\n"))))

(defn replace-prod-ids [sales-fname products]
  (with-open [rdr (io/reader sales-fname)]
    (->> (line-seq rdr)
         (map #(clojure.string/split % #"\|"))
         (map (fn [[sales-id cust-id prod-id item-count]]
                [sales-id cust-id (get products prod-id) item-count]))
         (map (partial apply str))
         (clojure.string/join "\n"))))

(defn replace-both-ids [sales-fname customers products]
  (with-open [rdr (io/reader sales-fname)]
    (->> (line-seq rdr)
         (map #(clojure.string/split % #"\|"))
         (map (fn [[sales-id cust-id prod-id item-count]]
                [sales-id (get customers cust-id) (get products prod-id) item-count]))
         (map (partial apply str))
         (clojure.string/join "\n"))))

(def customers (parse-customers "cust.txt"))
(def replaced (replace-cust-ids "sales.txt" customers))
(println replaced)
(println "Products")
(def products (parse-products "prod.txt"))
(def replaced2 (replace-prod-ids "sales.txt" products))
(println replaced2)
(println "Both")
(def replaced3 (replace-both-ids "sales.txt" customers products))
(println replaced3)