(require 2htdp/image)
;; image-area-starter.rkt

; 
; PROBLEM:
; 
; DESIGN a function called image-area that consumes an image and produces the 
; area of that image. For the area it is sufficient to just multiple the image's 
; width by its height.  Follow the HtDF recipe and leave behind commented 
; out versions of the stub and template.
; 



; The HtDF recipe consists of the following steps:
; 
; Signature, purpose and stub.
; Define examples, wrap each in check-expect.
; Template and inventory.
; Code the function body.
; Test and debug until correct

;; test images
(define img1 (rectangle 10 20 "solid" "blue"))
(define img2 (circle 10 "solid" "red"))

;; signature -- Image -> Natural
;; purpose   -- calculates the area of an image (img)
;; examples/tests
(check-expect (image-area img1) (* 10 20))
(check-expect (image-area img2) (* 20 20))

;; stub
;(define (image-area img) 0)

;; template
;(define (image-area img)
;  (... img))

(define (image-area img)
  (* (image-height img) (image-width img)))
