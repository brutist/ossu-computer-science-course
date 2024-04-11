
;; area-starter.rkt

; 
; PROBLEM:
; 
; DESIGN a function called area that consumes the length of one side 
; of a square and produces the area of the square.
; 
; Remember, when we say DESIGN, we mean follow the recipe.
; 
; Leave behind commented out versions of the stub and template.
; 

; 
; The HtDF recipe consists of the following steps:
; 1. Signature, purpose and stub.
; 2. Define examples, wrap each in check-expect.
; 3. Template and inventory.
; 4. Code the function body.
; 5. Test and debug until correct.



;; Signature --  Number -> Number
;; Purpose   --  produce the area of a square from the length of a side
(check-expect (area 4) 16)
(check-expect (area 1.5) 2.25) 

;; Stub
;(define (area side) 0)

;; Template
;(define (area side)
;  (... n))

(define (area side)
  (sqr side))
