;  PROBLEM 1:

;  Assuming the use of at least one accumulator, design a function that consumes a list of strings,
;  and produces the length of the longest string in the list. 

;; (listof String) -> String
;; produce the longest string in the list
;; examples/tests
(check-expect (longest-str empty) 0)
(check-expect (longest-str (list "list" "stop" "take")) 4)
(check-expect (longest-str (list "list" "numbers" "sequence")) 8)
(check-expect (longest-str (list "list" "numbers" "sequence" "function" "definition")) 10)

(define (longest-str los0)
   ;; acc: rsf - Natural; the length of the longest word so far
   (local [(define (longest-str los rsf)
            (cond [(empty? los) rsf]
                  [else (if (< rsf (string-length (first los)))
                            (longest-str (rest los) (string-length (first los)))
                            (longest-str (rest los) rsf))]))]
               
   (longest-str los0 0)))



;  PROBLEM 2:

;  The Fibbonacci Sequence https://en.wikipedia.org/wiki/Fibonacci_number is 
;  the sequence 0, 1, 1, 2, 3, 5, 8, 13,... where the nth element is equal to 
;  n-2 + n-1. 

;  Design a function that given a list of numbers at least two elements long, 
;  determines if the list obeys the fibonacci rule, n-2 + n-1 = n, for every 
;  element in the list. The sequence does not have to start at zero, so for 
;  example, the sequence 4, 5, 9, 14, 23 would follow the rule. 
 

;; (listof Numbers) -> Boolean
;; produce true if the given list follows the fibonacci sequence
;; Assume: list has at least two elements
;;         it follows fibonacci sequence if ln-2 + ln-1 = ln in the entire sequence,
;;             doesn't have to start at 0 or 1
(check-expect (fibonacci? (list 4 5)) true)
(check-expect (fibonacci? (list 4 5 9 14 23)) true)
(check-expect (fibonacci? (list 4 4 9 14 23)) false)
(check-expect (fibonacci? (list 0 1 1 2 3 5 8 13 21 34 55)) true)

(define (fibonacci? lon0)
   ;; acc: n-1 - Number; the number immediately before the (first lon) in lon0
   ;;      n-2 - Number; the number two places away from (first lon) in lon0
   ;;      p   - Natural; 1-based index of (first lon) in lon0
   (local [(define (fibonacci? lon n-1 n-2 p)
              (cond [(empty? lon) true]
                    [(< p 3) (if (< p 2)
                                 (fibonacci? (rest lon) n-1 (first lon) (add1 p))
                                 (fibonacci? (rest lon) (first lon) n-2 (add1 p)))]
                    [else (if (= (+ n-1 n-2) (first lon))
                              (fibonacci? (rest lon) (first lon) n-1 (add1 p))
                              false)]))]
                  
   (fibonacci? lon0 0 0 1)))



;  PROBLEM 3:
  
;  Refactor the function below to make it tail recursive.  
  


;; Natural -> Natural
;; produces the factorial of the given number
(check-expect (fact 0) 1)
(check-expect (fact 3) 6)
(check-expect (fact 5) 120)


(define (fact n)
  (cond [(zero? n) 1]
        [else 
         (* n (fact (sub1 n)))]))



;  PROBLEM 4:
  
;  Recall the data definition for Region from the Abstraction Quiz. Use a worklist 
;  accumulator to design a tail recursive function that counts the number of regions 
;  within and including a given region. 
;  So (count-regions CANADA) should produce 7



(define-struct region (name type subregions))
;; Region is (make-region String Type (listof Region))
;; interp. a geographical region

;; Type is one of:
;; - "Continent"
;; - "Country"
;; - "Province"
;; - "State"
;; - "City"
;; interp. categories of geographical regions

(define VANCOUVER (make-region "Vancouver" "City" empty))
(define VICTORIA (make-region "Victoria" "City" empty))
(define BC (make-region "British Columbia" "Province" (list VANCOUVER VICTORIA)))
(define CALGARY (make-region "Calgary" "City" empty))
(define EDMONTON (make-region "Edmonton" "City" empty))
(define ALBERTA (make-region "Alberta" "Province" (list CALGARY EDMONTON)))
(define CANADA (make-region "Canada" "Country" (list BC ALBERTA)))

#;
(define (fn-for-region r)
  (local [(define (fn-for-region r)
            (... (region-name r)
                 (fn-for-type (region-type r))
                 (fn-for-lor (region-subregions r))))
          
          (define (fn-for-type t)
            (cond [(string=? t "Continent") (...)]
                  [(string=? t "Country") (...)]
                  [(string=? t "Province") (...)]
                  [(string=? t "State") (...)]
                  [(string=? t "City") (...)]))
          
          (define (fn-for-lor lor)
            (cond [(empty? lor) (...)]
                  [else 
                   (... (fn-for-region (first lor))
                        (fn-for-lor (rest lor)))]))]
    (fn-for-region r)))
