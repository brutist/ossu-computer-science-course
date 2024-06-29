(require 2htdp/image)

;; wide-only-starter.rkt


;; Constants

(define W1 (rectangle 20 5 "solid" "black"))
(define W2 (rectangle 10 2 "solid" "purple"))
(define S1 (square 2 "solid" "lightgreen"))
(define S2 (square 5 "solid" "blue"))

(define L1 (list W1 W2))
(define L2 (list S1 S2))
(define L3 (list W1 W2 S1 S2))
(define L4 (list W1 S1 S2))

; PROBLEM:
; Use the built in version of filter to design a function called wide-only 
; that consumes a list of images and produces a list containing only those 
; images that are wider than they are tall.

(check-expect (wide-only L1) L1)
(check-expect (wide-only L2) empty)
(check-expect (wide-only L3) L1)
(check-expect (wide-only L4) (list W1))

(define (wide-only loi) (local [(define (p i) (> (image-width i) (image-height i)))]
                                (filter p loi)))