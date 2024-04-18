
;; odd-from-n-starter.rkt

;  PROBLEM:
;  
;  Design a function called odd-from-n that consumes a natural number n, and produces a list of all 
;  the odd numbers from n down to 1. 
;  
;  Note that there is a primitive function, odd?, that produces true if a natural number is odd.
;  



;; Natural -> ListOfNatural
;; produces a list of all the odd numbers from n to 1, inclusive.
;; examples/tests
(check-expect (odd-from-n 0) empty)
(check-expect (odd-from-n 1) (cons 1 empty))
(check-expect (odd-from-n 5) (cons 5 (cons 3 (cons 1 empty))))

;; stub
#;
(define (odd-from-n n) empty)

(define (odd-from-n n)
  (cond [(zero? n) empty]                              ;base case
        [(odd? n) (cons n (odd-from-n (sub1 n)))]      ;odd case
        [else (odd-from-n (sub1 n))]))                 ;even case

