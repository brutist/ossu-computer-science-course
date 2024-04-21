(require 2htdp/image)

;; boxify-starter.rkt

; 
; PROBLEM:
; 
; Use the How to Design Functions (HtDF) recipe to design a function that consumes an image, 
; and appears to put a box around it. Note that you can do this by creating an "outline" 
; rectangle that is bigger than the image, and then using overlay to put it on top of the image. 
; For example:
; 
; (boxify (ellipse 60 30 "solid" "red")) should produce .
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

;; images for tests
(define image1 (ellipse 60 30 "solid" "red"))
(define image2 (triangle 30 "solid" "red"))
(define image3 (circle 10 "solid" "red"))


;; Signature -- Image -> Image
;; Purpose   -- put a rectangular outline on an image
;; Examples/tests
(check-expect (boxify image1) (overlay image1 (rectangle (+ (image-width image1) 2) (+ (image-height image1) 2) "outline" "black")))
(check-expect (boxify image2) (overlay image2 (rectangle (+ (image-width image2) 2) (+ (image-height image2) 2) "outline" "black")))
(check-expect (boxify image3) (overlay image3 (rectangle (+ (image-width image3) 2) (+ (image-height image3) 2) "outline" "black")))

;; Stub
;(define (boxify img) .)

;; Template
;(define (boxify img)
;  (... outlined-img))

(define (boxify img)
  (overlay img (rectangle (+ (image-width img) 2) (+ (image-height img) 2) "outline" "black")))

