;; aisle-starter.rkt

; 
; PROBLEM:
; 
; Using the SeatNum data definition below design a function
; that produces true if the given seat number is on the aisle. 
; 


;; Data definitions:

;; SeatNum is Natural [1,32]
;; Interp. Seat numbers in a row, 1 and 32 are aisle seats
(define SN1 1)    ;aisle
(define SN2 12)   ;middle
(define SN3 32)   ;aisle

#;
(define (fn-for-seat-num sn)
  (... sn))

;; Template rules used:
;;   atomic non-distinct: Natural [1, 32]


;; Functions:

;; SeatNum -> Boolean
;; produce true if the given seat number is on the aisle (1 or 32), otherwise false
;; examples/tests
(check-expect (aisle? 1)  #true)
(check-expect (aisle? 32) #true)
(check-expect (aisle? 12) #false)

  
;stub
;(define (aisle? sn) #false)

; template
#;
(define (aisle? sn)
  (... sn))

(define (aisle? sn)
  (or (= 32 sn) (= 1 sn)))
