
;; double-starter.rkt

;; =================
;; Data definitions:

; 
; Remember the data definition for a list of numbers we designed in Lecture 5f:
; (if this data definition does not look familiar, please review the lecture)
; 


;; ListOfNumber is one of:
;;  - empty
;;  - (cons Number ListOfNumber)
;; interp. a list of numbers
(define LON1 empty)
(define LON2 (cons 60 (cons 42 empty)))
#;
(define (fn-for-lon lon)
  (cond [(empty? lon) (...)]
        [else
         (... (first lon)
              (fn-for-lon (rest lon)))]))

;; Template rules used:
;;  - one of: 2 cases
;;  - atomic distinct: empty
;;  - compound: (cons Number ListOfNumber)
;;  - self-reference: (rest lon) is ListOfNumber

;; =================
;; Functions:

; 
; PROBLEM:
; 
; Design a function that consumes a list of numbers and doubles every number 
; in the list. Call it double-all.
; 



;; ListOfNumber -> ListOfNumber
;; takes in a list of numbers and doubles every number in that list
;; examples/tests
(check-expect (double-all empty) empty)
(check-expect (double-all (cons 1 empty)) (cons (* 2 1) empty))
(check-expect (double-all (cons 4.1 (cons 2 empty))) (cons (* 4.1 2) (cons (* 2 2) empty)))

;; stub
#;
(define (double-all lon) (cons 0.0001 (cons 0.100123 empty)))


(define (double-all lon)
  (cond [(empty? lon) empty]
        [else
         (cons (* (first lon) 2) (double-all (rest lon)))]))
