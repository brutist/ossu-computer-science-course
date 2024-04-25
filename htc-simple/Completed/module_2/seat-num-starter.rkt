;; seat-num-starter.rkt

; 
; PROBLEM:
; 
; Imagine that you are designing a program to manage ticket sales for a
; theatre. (Also imagine that the theatre is perfectly rectangular in shape!) 
; 
; Design a data definition to represent a seat number in a row, where each 
; row has 32 seats. (Just the seat number, not the row number.)
;  



;; type comment -- seatNum is Natural [1, 32]
;; interp. corresponds to seat number in each row, 1 and 32 are end seats.
;; examples
(define SN1 1)     ; first seat
(define SN2 16)    ; middle seat
(define SN3 32)    ; last seat


(define (fn-for-seat-num sn)
  (... sn)
  )

;; template rules used:
;;  - atomic non-distinct: Natural [1, 32]