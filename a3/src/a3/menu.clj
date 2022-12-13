(ns menu
  (:require [db])
  (:require clojure.string))

(def customer-id (db/name-to-id "cust.txt"))
(def product-id (db/prod-to-id "prod.txt"))
(def products-data (db/parse-data "prod.txt"))
(def sales (db/parse-data "sales.txt"))

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
      (= option "4") ((println "Please note that the names are case-sensitive")
                      (print "Enter customer name: ") 
                      (flush)
                      (let [input (clojure.string/join (read-line))]
                        (println (str "Total sales for customer " input ": $" (db/total-sales sales products-data (get customer-id input)))))
                      (main-menu))
      (= option "5") ((println "Please note that the names are case-sensitive")
                      (print "Enter product name: ") 
                      (flush)
                      (let [input (clojure.string/join (read-line))]
                        (println (str "\nTotal count for product " input ": " (db/total-count sales (get product-id input)))))
                      (main-menu))
      (= option "6") ((println "Good Bye") (System/exit 0))
      :else ((println "Please choose a number from 1 to 6") (main-menu)))))

