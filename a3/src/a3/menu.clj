(ns menu
  (:require [db]))

(defn main-menu []
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
      (= option "1") ((db/display-customer-table) (main-menu))
      (= option "2") ((db/display-product-table) (main-menu))
      (= option "3") ((db/display-sales-table) (main-menu))
      (= option "4") (db/total-sales-for-customer (read-line))
      (= option "5") (db/total-count-for-product (read-line))
      (= option "6") ((println "Good Bye") (System/exit 0))
      :else ((println "Please choose a number from 1 to 6") (main-menu)))))

