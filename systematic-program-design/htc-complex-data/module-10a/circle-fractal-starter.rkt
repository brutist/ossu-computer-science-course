(require 2htdp/image)

;; circle-fractal-starter.rkt

; 
; PROBLEM :
; 
; Design a function that will create the following fractal: 
;;       ./circle-fractal.png

; Each circle is surrounded by circles that are two-fifths smaller. 

; You can build these images using the convenient beside and above functions
; if you make your actual recursive function be one that just produces the
; top leaf shape. You can then rotate that to produce the other three shapes.
; 
; You don't have to use this structure if you are prepared to use more
; complex place-image functions and do some arithmetic. But the approach
; where you use the helper is simpler.
; 
; Include a termination argument for your design.


;; =================
;; Constants:

(define STEP (/ 2 5))
(define TRIVIAL-SIZE 5)
(define MODE "solid")
(define COLOR "blue")


;; Natural -> Image
;; produces a circle fractal with radius r, 
;;       each circle is surrounded by four two-fifths smaller circles
;; examples/tests
(check-expect (circle-fractal TRIVIAL-SIZE) (circle TRIVIAL-SIZE MODE COLOR))
(check-expect (circle-fractal (/ TRIVIAL-SIZE (sqr STEP))) 
              (local [(define main (circle (/ TRIVIAL-SIZE (sqr STEP)) MODE COLOR))
                      (define sub1 (circle (/ TRIVIAL-SIZE STEP) MODE COLOR))
                      (define sub2 (circle TRIVIAL-SIZE MODE COLOR))
                      (define leaf (above sub2 (beside sub2 sub1 sub2)))
                      (define top-leaf leaf)
                      (define left-leaf (rotate 90 leaf))
                      (define right-leaf (rotate -90 leaf))
                      (define bottom-leaf (rotate 180 leaf))]
              (above top-leaf 
                     (beside left-leaf main right-leaf)
                     bottom-leaf)))

(define (circle-fractal r)
  (cond [(<= r TRIVIAL-SIZE) (circle r MODE COLOR)]
        [else (local [(define main (circle r MODE COLOR))
                      (define top-leaf (draw-leaf (* r STEP)))
                      (define left-leaf (rotate 90 top-leaf))
                      (define right-leaf (rotate -90 top-leaf))
                      (define bottom-leaf (rotate 180 top-leaf))]
                (above top-leaf 
                       (beside left-leaf main right-leaf)
                       bottom-leaf))]))


;; Problem
;; Construct a three-part terminationargument for scarp.

;; Base case:      (<= r TRIVIAL-SIZE)

;; Reduction step: (* r STEP)

;; Argument that repeated application of reduction step will eventually
;;  reach the base case:

;; As long as 0 < STEP < 1 and 0 < TRIVIAL-SIZE, repeated (* r STEP)
;; will eventually reach the base case (<= r TRIVIAL-SIZE).


;; Natural -> Image
;; produces an image of a valid leaf of a circle fractal
;; examples/tests
(check-expect (draw-leaf TRIVIAL-SIZE) (circle TRIVIAL-SIZE MODE COLOR))
(check-expect (draw-leaf (/ TRIVIAL-SIZE STEP)) 
              (local [(define sub (circle TRIVIAL-SIZE MODE COLOR))
                      (define main (circle (/ TRIVIAL-SIZE STEP) MODE COLOR))]
                     (above sub (beside sub main sub))))

(define (draw-leaf r)
   (cond [(<= r TRIVIAL-SIZE) (circle r MODE COLOR)]
         [else (local [(define sub (draw-leaf (* r STEP)))
                       (define left-sub (rotate 90 sub))
                       (define right-sub (rotate -90 sub))
                       (define main (circle r MODE COLOR))]
                      (above sub (beside left-sub main right-sub)))]))