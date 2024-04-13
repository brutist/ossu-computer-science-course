(require 2htdp/image)
(require 2htdp/universe)
;; cat-v1.rkt

;; ==== Constants ====

;; UI dimensions
(define WIDTH 600)
(define HEIGHT 300)

;; center cat in y-axis
(define CTR-Y (/ HEIGHT 2))

;; cat-image (currently a place-holder to avoid issues with VS code and github)
(define CAT-IMG (circle 20 "outline" "red"))

;; image of an empty scene the cat will traverse
(define MTS (rectangle WIDTH HEIGHT "outline" "black"))

;; speed of cat in pixels
(define SPEED 8)


;; ==== Data Definition ====
;; Cat-X is Number
;; interp. x-position of cat object

;; example
(define C1 0)             ;leftmost
(define C2 (/ WIDTH 2))   ;center
(define C3 WIDTH)         ;rightmost

#;
(define (fn-for-cat-x p)
  (... p))

;; template rules used:
;;   - atomic non-distinct: Number

;; ==== Functions ====

;; Cat -> Cat
;; start the world with (main 0)
;; 
(define (main c)
  (big-bang c                          ; Cat
            (on-tick   cat-move)       ; Cat -> Cat
            (to-draw   cat-render)))   ; Cat -> Image    



;; Cat->Cat
;; move cat to a new x position by SPEED pixel(s) to the right
;; examples/tests
(check-expect (cat-move 0) (+ SPEED 0))

;; stub
;(define (cat-move c) 0)

; <template from CAT>
(define (cat-move p)
  (+ p SPEED))



;; Cat->Image
;; produce an image in the position given by cat
;; example/tests
(check-expect (cat-render 10) (place-image CAT-IMG 10 CTR-Y MTS))

;; stub
;(define (cat-render p) MTS)

; <template from CAT>
(define (cat-render p)
  (place-image CAT-IMG p CTR-Y MTS))
