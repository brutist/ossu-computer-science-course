;; designing-with-lists-1-starter.rkt

; 
; PROBLEM:
; 
; You've been asked to design a program having to do with all the owls
; in the owlery.
; 
; (A) Design a data definition to represent the weights of all the owls. 
;     For this problem call it ListOfNumber.
; (B) Design a function that consumes the weights of owls and produces
;     the total weight of all the owls.
; (C) Design a function that consumes the weights of owls and produces
;     the total number of owls.
;     


;; === Data Definition ===

;; ListOfNumber is one of:
;;  - empty
;;  - (cons Number ListOfNumber)
;; interp. a list of owl weights in kg

(define LON-1 empty)
(define LON-2 (cons 5.4 empty))
(define LON-3 (cons 3.4 (cons 4.5 empty)))


#;
(define (fn-for-lon lon)
  (cond [(empty? lon) (...)]
        [else (... (first lon)
                   (fn-for-lon (rest lon)))]))


;; template rules used:
;;  - one of: 2 cases
;;  - atomic distinct: empty
;;  - compound: (cons Number ListOfNumber)
;;  - self-reference: (rest lon) is ListOfNumber



;; === Functions ===

;; ListOfNumber -> Number
;; produces the sum from the given ListOfNumber , produces 0 if the ListOfNumber is empty
;; examples/tests
(check-expect (sum empty) 0)
(check-expect (sum (cons 4 (cons 5.4 empty))) (+ 5.4 4))
(check-expect (sum (cons 0 empty)) 0)
(check-expect (sum (cons 3 (cons 3.4 ( cons 0 empty)))) (+ 3.4 3 0))

;; stub
#;
(define (sum lon) 10)

(define (sum lon)
  (cond [(empty? lon) 0]
        [else (+ (first lon) (sum (rest lon)))]))


;; ListOfNumber -> Natural
;; produces the number of elements in a given ListOfNumber, produces 0 if the ListOfNumber is empty
;; examples/tests
(check-expect (total empty) 0)
(check-expect (total (cons 4.1 (cons 0 empty))) 2)

;; stub
#;
(define (total lon) 200)

(define (total lon)
  (cond [(empty? lon) 0]
        [else (+ 1 (total (rest lon)))]))