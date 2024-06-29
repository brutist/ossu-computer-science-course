(require 2htdp/image)
;; tall-starter.rkt

; 
; PROBLEM:
; 
; DESIGN a function that consumes an image and determines whether the 
; image is tall.
; 
; Remember, when we say DESIGN, we mean follow the recipe.
; 
; Leave behind commented out versions of the stub and template.
; 


; 
; The HtDF recipe consists of the following steps:
; Signature, purpose and stub.
; Define examples, wrap each in check-expect.
; Template and inventory.
; Code the function body.
; Test and debug until correct.


;; signature -- Image -> Boolean
;; produce true if the image is tall (height > width)
;; examples/tests
(check-expect (tall? (rectangle 10 20 "solid" "blue")) #true)
(check-expect (tall? (rectangle 20 20 "solid" "blue")) #false)
(check-expect (tall? (rectangle 30 20 "solid" "blue")) #false)

;; stub
;(define (tall? img) #false)

;; template
;(define (tall? img)
;  (... img))

(define (tall? img)
  (if (> (image-height img) (image-width img))
      #true
      #false))
