;; average-starter.rkt

; PROBLEM:

; Design a function called average that consumes (listof Number) and produces the
; average of the numbers in the list.


;; (listof Number) -> Number
;; produce the average of the given list of numbers
;; examples/tests
(check-expect (average-tr empty) 0)
(check-expect (average-tr (list 1 2 3 4 5)) 3)
(check-expect (average-tr (list 2 8 15 5 5)) (/ (+ 2 8 15 5 5) 5))
(check-expect (average-tr (list 5)) 5)

(define (average-tr lon0)
   ;; acc: Number; sum of the previously seen elements
   ;;      Natural; total number of previously seen elements
   ;; (average-tr (list 1 2 3 4 5)) 0 0)
   ;; (average-tr (list   2 3 4 5)) 1 1)
   ;; (average-tr (list     3 4 5)) 3 2)
   ;; (average-tr (list       4 5)) 6 3)
   ;; (average-tr (list         5)) 10 4)
   ;; (average-tr (list          )) 15 5)
   (local [(define (average-tr lon s n)
            (cond [(empty? lon) (if (= n 0) 0 (/ s n))] ;handles no-element lists
                  [else (average-tr (rest lon) (+ s (first lon)) (add1 n))]))]

   (average-tr lon0 0 0)))