
;; less-than-five-starter.rkt

; 
; PROBLEM:
; 
; DESIGN function that consumes a string and determines whether its length is
; less than 5.  Follow the HtDF recipe and leave behind commented out versions 
; of the stub and template.
; 


; The HtDF recipe consists of the following steps:
; Signature, purpose and stub.
; Define examples, wrap each in check-expect.
; Template and inventory.
; Code the function body.
; Test and debug until correct.


;; signature  -- String -> Bool
;; purpose    -- identify whether a given string's length is less than 5
;; examples/tests
(check-expect (less-five "johnny") #false)
(check-expect (less-five "john") #true)
(check-expect (less-five "hey   ") #false)
(check-expect (less-five "") #true)

;; stub
;(define (less-five s) #true)

;; template
;(define (less-five s)
;  (... s))

(define (less-five s)
  (if (< (string-length s) 5)
  #true
  #false))


