(require drracket-wakatime)

;; double-starter.rkt

; 
; PROBLEM:
; 
; Design a function that consumes a number and produces twice that number. 
; Call your function double. Follow the HtDF recipe and leave behind commented 
; out versions of the stub and template.
; 


; The HtDF recipe consists of the following steps:
; Signature, purpose and stub.
; Define examples, wrap each in check-expect.
; Template and inventory.
; Code the function body.
; Test and debug until correct.



;; signature -- Number -> Number
;; purpose   -- doubles the input number
;; examples/tests
(check-expect (double 2) (* 2 2))
(check-expect (double 3.2) (* 2 3.2))
(check-expect (double 40.212) (* 2 40.212))

;; stub
;(define (double n) 0)

;; template
;(define (double n)
;  (... n))

(define (double n)
  (* 2 n))





