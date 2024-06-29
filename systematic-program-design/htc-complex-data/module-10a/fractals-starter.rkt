(require 2htdp/image)
;; fractals-starter.rkt

; PROBLEM:

; Design a function that consumes a number and produces a Sierpinski
; triangle of that size. Your function should use generative recursion.

;; Constants

(define CUTOFF 2)
(define MODE "outline")
(define COLOR "red")
(define RATIO 1.7)
(define THREE 3)

;; Functions

;; Natural -> Image
;; creates a Sierpinski Triangle with side-length s
;; examples/tests
(check-expect (stri CUTOFF) (triangle CUTOFF MODE COLOR))
(check-expect (stri (* CUTOFF RATIO))
              (above (triangle CUTOFF MODE COLOR)
                     (beside (triangle CUTOFF MODE COLOR) (triangle CUTOFF MODE COLOR))))

;; Problem
;; Construct a three-part terminationargument for stri.

;; Base case:      (<= s CUTOFF)

;; Reduction step: (/ s RATIO)

;; Argument that repeated application of reduction step will eventually
;;  reach the base case:

;; As long as the CUTOFF > 0 and RATIO > 1, repeated division of s by RATIO
;;   will eventually produce <= CUTOFF.


(define (stri s)
  (cond
    [(<= s CUTOFF) (triangle s MODE COLOR)]
    [else
     (local [(define sub-s (/ s RATIO))] 
            (above (stri sub-s) 
                   (beside (stri sub-s) (stri sub-s))))]))

; PROBLEM:
; Design a function to produce a Sierpinski carpet of size s.

;; Natural -> Image
;; creates a Sierpinski Carpet with side-length s
;; examples/tests
(check-expect (scarp CUTOFF) (square CUTOFF MODE COLOR))
(check-expect
 (scarp (* CUTOFF 3))
 (local [(define sub (scarp CUTOFF))
         (define blk (square CUTOFF MODE COLOR))]
        (above (beside sub sub sub)
               (beside sub blk sub)
               (beside sub sub sub))))

;; Problem
;; Construct a three-part terminationargument for scarp.

;; Base case:      (<= s CUTOFF)

;; Reduction step: (/ s THREE)

;; Argument that repeated application of reduction step will eventually
;;  reach the base case:

;; As long as the CUTOFF > 0 and THREE > 1, repeated division of s by THREE
;;   will eventually produce <= CUTOFF.

(define (scarp s)
  (cond
    [(<= s CUTOFF) (square s MODE COLOR)]
    [else (local [(define sub (scarp (/ s THREE)))
                  (define blk (square (/ s THREE) MODE COLOR))]
                 (above (beside sub sub sub)
                        (beside sub blk sub)
                        (beside sub sub sub)))]))
