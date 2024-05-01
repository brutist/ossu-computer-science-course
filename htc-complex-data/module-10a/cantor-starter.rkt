(require 2htdp/image)
(require 2htdp/universe)

;; cantor-starter.rkt

;; Constants
(define HEIGHT 20)
(define CUTOFF 20)
(define MODE "solid")
(define COLOR "blue")
(define RATIO (/ 1 3))
(define CUTOFF-IMAGE (rectangle CUTOFF HEIGHT MODE COLOR))



;; Natural Natural -> Image
;; produces a solid white rectangle with w and h dimensions
;; examples/tests
(check-expect (add-ws 10 10) (rectangle 10 10 MODE "white"))

(define (add-ws w h) (rectangle (floor w) (floor h) MODE "white"))


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
              (local [(define top-rect (rectangle (/ CUTOFF RATIO) HEIGHT MODE COLOR))
                      (define vspace (add-ws (image-width top-rect) (* HEIGHT RATIO)))
                      (define hspace (add-ws (image-width CUTOFF-IMAGE) (image-height CUTOFF-IMAGE)))]
              (above top-rect
                     vspace 
                     (beside CUTOFF-IMAGE hspace CUTOFF-IMAGE))))

(define (cantor-set w)
   (cond [(<= w CUTOFF) (rectangle w HEIGHT MODE COLOR)]
         [else (local [(define top-rect (rectangle w HEIGHT MODE COLOR))
                       (define bottom-rect (cantor-set (* w RATIO)))
                       (define vspace (add-ws (image-width top-rect) (* HEIGHT RATIO)))
                       (define hspace (add-ws (image-width bottom-rect) (image-height bottom-rect)))]
                        (above top-rect
                               vspace 
                               (beside bottom-rect hspace bottom-rect)))]))
                               
                      
; PROBLEM B:
; Add a second parameter to your function that controls the percentage 
; of the recursive call that is white each time. Calling your new function
; with a second argument of 1/RATIO would produce the same images as the old 
; function.

(check-expect (cantor-set-frac CUTOFF) CUTOFF-IMAGE)
(check-expect (cantor-set-frac (* CUTOFF RATIO)) 
              (local [(define top-rect (rectangle (* CUTOFF RATIO) HEIGHT MODE COLOR))
                      (define vspace (add-ws (image-width top-rect) (/ HEIGHT RATIO)))
                      (define hspace (add-ws (image-width CUTOFF-IMAGE) (image-height CUTOFF-IMAGE)))]
              (above top-rect
                     vspace 
                     (beside CUTOFF-IMAGE hspace CUTOFF-IMAGE))))

(define (cantor-set-frac w fr)
   (cond [(<= w CUTOFF) (rectangle w HEIGHT MODE COLOR)]
         [else (local [(define top-rect (rectangle w HEIGHT MODE COLOR))
                       (define bottom-rect (cantor-set (/ w RATIO)))
                       (define vspace (add-ws (image-width top-rect) (/ HEIGHT RATIO)))
                       (define hspace (add-ws (image-width bottom-rect) (image-height bottom-rect)))]
                        (above top-rect
                               vspace 
                               (beside bottom-rect hspace bottom-rect)))]))


; PROBLEM C:
; Now you can make a fun world program that works this way:
;   The world state should simply be the most recent x coordinate of the mouse.
;   
;   The to-draw handler should just call your new cantor function with the
;   width of your MTS as its first argument and the last x coordinate of
;   the mouse divided by that width as its second argument.

