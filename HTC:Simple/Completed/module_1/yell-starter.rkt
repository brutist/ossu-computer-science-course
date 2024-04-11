
;; yell-starter.rkt

; 
; PROBLEM:
; 
; DESIGN a function called yell that consumes strings like "hello" 
; and adds "!" to produce strings like "hello!".
; 
; Remember, when we say DESIGN, we mean follow the recipe.
; 
; Leave behind commented out versions of the stub and template.
; 



; The HtDF recipe consists of the following steps:
; Signature, purpose and stub.
; Define examples, wrap each in check-expect.
; Template and inventory.
; Code the function body.
; Test and debug until correct.


;; signature -- String -> String
;; purpose   -- add "!" to a given string
;; examples/tests
(check-expect (yell "hello") "hello!")
(check-expect (yell "") "!")
(check-expect (yell "Jonathan!") "Jonathan!!")

;; stub
;(define (yell s) "")

;; template
;(define (yell s)
;  (... s))


(define (yell s)
  (string-append s "!"))
