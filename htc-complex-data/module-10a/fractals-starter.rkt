(require 2htdp/image)
;; fractals-starter.rkt

; PROBLEM:

; Design a function that consumes a number and produces a Sierpinski
; triangle of that size. Your function should use generative recursion.

;; Constants

(define T-CUTOFF 2)
(define S-CUTOFF 1)
(define MODE "outline")
(define COLOR "light red")
(define RATIO 1.7)
(define THREE 3)

;; Functions

;; Natural -> Image
;; creates a Sierpinski Triangle with side-length s
;; examples/tests
(check-expect (stri T-CUTOFF) (triangle T-CUTOFF MODE COLOR))
(check-expect (stri (* T-CUTOFF RATIO))
              (above (triangle T-CUTOFF MODE COLOR)
                     (beside (triangle T-CUTOFF MODE COLOR) (triangle T-CUTOFF MODE COLOR))))

(define (stri s)
  (cond
    [(<= s T-CUTOFF) (triangle s MODE COLOR)]
    [else
     (local [(define sub-s (/ s RATIO))] 
            (above (stri sub-s) 
                   (beside (stri sub-s) (stri sub-s))))]))

; PROBLEM:
; Design a function to produce a Sierpinski carpet of size s.

;; Natural -> Image
;; creates a Sierpinski Carpet with side-length s
;; examples/tests
(check-expect (scarp S-CUTOFF) (square S-CUTOFF MODE COLOR))
(check-expect
 (scarp (* S-CUTOFF 3))
 (local [(define sub (/ (* S-CUTOFF 3) 3))]
        (above (beside (square sub MODE COLOR) (square sub MODE COLOR) (square sub MODE COLOR))
               (beside (square sub MODE COLOR)
                       (square sub MODE COLOR) ;; no recursion
                       (square sub MODE COLOR))
               (beside (square sub MODE COLOR) (square sub MODE COLOR) (square sub MODE COLOR)))))

(define (scarp s)
  (cond
    [(<= s S-CUTOFF) (square s MODE COLOR)]
    [else (local [(define sub (/ s THREE))]
                 (above (beside (scarp sub) (scarp sub) (scarp sub))
                        (beside (scarp sub) (square sub MODE COLOR) (scarp sub))
                        (beside (scarp sub) (scarp sub) (scarp sub))))]))
