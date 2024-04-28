
;; boolean-list-starter.rkt

;; =================
;; Data definitions:

; 
; PROBLEM A:
; 
; Design a data definition to represent a list of booleans. Call it ListOfBoolean. 
; 


;; ListOfBoolean is one of:
;;   - empty
;;   - (cons Boolean ListOfBoolean)

(define LOB1 empty)
(define LOB2 (cons true empty))
(define LOB3 (cons false (cons true empty)))

(define (fn-for-lob lob)
  (cond [(empty? lob) (...)]
        [else (... (first lob)
                   (fn-for-lob (rest lob)))]))

;; template rules used:
;;   - one of: 2 cases
;;   - atomic distinct: empty
;;   - compound: (cons Boolean ListOfBoolean)
;;   - self reference: (rest lob) is ListOfBoolean



;; =================
;; Functions:

; 
; PROBLEM B:
; 
; Design a function that consumes a list of boolean values and produces true 
; if every value in the list is true. If the list is empty, your function 
; should also produce true. Call it all-true?
; 


;; ListOfBoolean -> Boolean
;; produces true if the list is empty or every value in the list is true, produces false otherwise
;; examples/tests
(check-expect (all-true? empty) true)
(check-expect (all-true? (cons true empty)) true)
(check-expect (all-true? (cons true (cons true empty))) true)
(check-expect (all-true? (cons false (cons true empty))) false)
(check-expect (all-true? (cons false (cons false empty))) false)
(check-expect (all-true? (cons true (cons true empty))) true)

;; stub
#;
(define (all-true? lob) false)


(define (all-true? lob)
  (cond [(empty? lob) true]
        [else (and (first lob) (all-true? (rest lob)))]))
