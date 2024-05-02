(require 2htdp/image)
(require 2htdp/universe)

;; cantor-starter.rkt

;; Constants
(define HEIGHT 20)
(define CUTOFF 2)
(define MODE "solid")
(define COLOR "blue")
(define RATIO (/ 1 3))
(define CUTOFF-IMAGE (rectangle CUTOFF HEIGHT MODE COLOR))
(define SPACING-HEIGHT (/ HEIGHT 2))
(define MTS (empty-scene 600 600))


;; Natural Natural -> Image
;; produces a solid white rectangle with w and h dimensions
;; examples/tests
(check-expect (add-ws 10 10) (rectangle 10 10 MODE "white"))

(define (add-ws w h) (rectangle w h MODE "white"))


; PROBLEM:
; A Cantor Set is another fractal with a nice simple geometry.
; The idea of a Cantor set is to have a bar (or rectangle) of
; a certain width w, then below that are two recursive calls each
; of 1/RATIO the width, separated by a whitespace of 1/RATIO the width.

; So this means that the
;   width of the whitespace   wc  is  (/ w RATIO)
;   width of recursive calls  wr  is  (/ (- w wc) 2)

; To make it look better a little extra whitespace is put between
; the bars.

;;  ==== Sample available at ./cantor-sample.png  ====


; PROBLEM A:
; Design a function that consumes a width and produces a cantor set of 
; the given width.


;; Natural -> Image
;; produces a cantor set with an initial width w
(check-expect (cantor-set CUTOFF) CUTOFF-IMAGE)
(check-expect (cantor-set (/ CUTOFF RATIO)) 
              (local [(define w (/ CUTOFF RATIO))
                      (define wc (* w RATIO))
                      (define ws (/ (- w wc) 2))
                      (define top-rect (rectangle w HEIGHT MODE COLOR))
                      (define vspace (add-ws w SPACING-HEIGHT))
                      (define hspace (add-ws wc SPACING-HEIGHT))]
              (above top-rect
                     vspace 
                     (beside CUTOFF-IMAGE hspace CUTOFF-IMAGE))))

(define (cantor-set w)
   (cond [(<= w CUTOFF) (rectangle w HEIGHT MODE COLOR)]
         [else (local [(define wc (* w RATIO))
                       (define ws (/ (- w wc) 2))
                       (define top-rect (rectangle w HEIGHT MODE COLOR))
                       (define bottom-rect (cantor-set (* w RATIO)))
                       (define vspace (add-ws w SPACING-HEIGHT))
                       (define hspace (add-ws wc SPACING-HEIGHT))]
                        (above top-rect
                               vspace 
                               (beside bottom-rect hspace bottom-rect)))]))
                               

; PROBLEM B:
; Add a second parameter to your function that controls the percentage 
; of the recursive call that is white each time. Calling your new function
; with a second argument of 1/3 would produce the same images as the old 
; function.


;; Natural Number -> Image
;; produces a cantor set with initial with w, suceeding sizes of the set is determined by fr
;; Assumes fr < (1/2).
;; examples/tests

(check-expect (cantor-set-frac CUTOFF (/ 1 3)) CUTOFF-IMAGE)
(check-expect (cantor-set-frac (/ CUTOFF (/ 1 3)) (/ 1 3)) 
              (local [(define w (/ CUTOFF (/ 1 3)))
                      (define fr (/ 1 3))
                      (define wc (* w fr))
                      (define ws (/ (- w wc) 2))
                      (define top-rect (rectangle w HEIGHT MODE COLOR))
                      (define vspace   (add-ws w SPACING-HEIGHT))
                      (define hspace   (add-ws wc SPACING-HEIGHT))
                      (define bottom-rect (cantor-set-frac ws fr))]
                (above top-rect
                       vspace 
                       (beside CUTOFF-IMAGE hspace CUTOFF-IMAGE))))

(check-expect (cantor-set-frac (/ CUTOFF (/ 1 2)) (/ 1 2)) 
              (local [(define w (/ CUTOFF (/ 1 2)))
                      (define fr (/ 1 2))
                      (define wc (* w fr))
                      (define ws (/ (- w wc) 2))
                      (define top-rect (rectangle w HEIGHT MODE COLOR))
                      (define vspace   (add-ws w SPACING-HEIGHT))
                      (define hspace   (add-ws wc SPACING-HEIGHT))
                      (define bottom-rect (cantor-set-frac ws fr))]
                (above top-rect
                       vspace 
                       (beside bottom-rect hspace bottom-rect))))



(define (cantor-set-frac w fr)
   (cond [(<= w CUTOFF) (rectangle w HEIGHT MODE COLOR)]
         [else (local [(define wc (* w fr))
                       (define ws (/ (- w wc) 2))
                       (define top-rect (rectangle w HEIGHT MODE COLOR))
                       (define vspace   (add-ws w SPACING-HEIGHT))
                       (define hspace   (add-ws wc SPACING-HEIGHT))
                       (define bottom-rect (cantor-set-frac ws fr))]
                        (above top-rect
                               vspace 
                               (beside bottom-rect hspace bottom-rect)))]))


; So this means that the
;   width of the whitespace   wc  is  (/ w RATIO)
;   width of recursive calls  wr  is  (/ (- w wc) 2)

; PROBLEM C:
; Now you can make a fun world program that works this way:
;   The world state should simply be the most recent x coordinate of the mouse.
;   
;   The to-draw handler should just call your new cantor function with the
;   width of your MTS as its first argument and the last x coordinate of
;   the mouse divided by that width as its second argument.

;; world state -> recent x coordinate of the mouse
;; to-draw -> (cantor-set-frac (image-width MTS) (mouse-x / image-width MTS))
;; on-mouse -> 


;; Natural -> Natural
;; start the world with (main 500)


(define (main w)
  (big-bang w                           ; Natural
            (to-draw   render-cantor)   ; Natural -> Image
            (on-mouse  mouse-handler))) ; Natural Integer Integer MouseEvent -> Natural


;; Natural -> Image
;; produces a rendering of a cantor set
;; examples/tests
(check-expect (render-cantor 50) (cantor-set-frac (image-width MTS) (/ 50 (image-width MTS))))

(define (render-cantor w) (cantor-set-frac (image-width MTS) (/ w (image-width MTS))))


;; Natural Integer Integer MouseEvent -> Natural
;; produces a new cantor set each time the mouse is clicked
;; examples/tests
(check-expect (mouse-handler 50 10 5 "move") 10)
(check-expect (mouse-handler 50 10 5 "drag") 50)

(define (mouse-handler ws x y me)
  (cond [(mouse=? me "move") x]
        [else ws]))