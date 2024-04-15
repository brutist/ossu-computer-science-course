;PROBLEM:

;Design a World Program with Compound Data. You can be as creative as you like,
;but keep it simple. Above all, follow the recipes!
;You must also stay within the scope of the first part of the course.
;Do not use language features we have not seen in the videos. 

;If you need inspiration, you can choose to create a program that allows you
;to click on a spot on the screen to create a star, which then grows over time.
;If you click again the first star is replaced by a new one at the new position.

;You should do all your design work in DrRacket. Following the step-by-step recipe
;in DrRacket will help you be sure that you have a quality solution.

(require 2htdp/image)
(require 2htdp/universe)

;; === Constants ===

;; UI dimensions
(define HEIGHT 500)
(define WIDTH (/ HEIGHT 1.3))

;; empty scene
(define MTS (rectangle WIDTH HEIGHT 80 "light blue"))

;; delta of flower's growth per tick
(define DG 1.01)

;; delta of flower's angle of rotation per tick
(define DTH -1)

;; image of flower
(define CENTER (circle 15 "solid" "light orange"))
(define PETAL (ellipse 20 50 "solid" "purple"))

(define PETALS (overlay/offset (rotate -8 PETAL) 0 (- (image-height PETAL) 8) 
                      (above (overlay/xy (rotate 70 PETAL) (image-height PETAL) 5 (rotate -90 PETAL))
                      (beside (rotate -45 PETAL) (rotate 45 PETAL)))))

(define FLOWER (scale 0.2 (overlay/offset CENTER 0 -5 PETALS)))


;; === Data Definition ===

(define-struct flower (th g x y))
;; Flower is (make-flower Number Number)
;; interp. a flower with <th> [0, -360) degrees of rotation from horizontal and <g> growth factor
;;         with an x,y as position coordinates

;; example
(define F1 (make-flower -20 10 10 10)) ;rotated -20 degrees from horizontal and 10 times bigger than default


(define (fn-for-flower f)
  (... (flower-th)        ; Number
       (flower-g)         ; Number
       (flower-x)         ; Natural
       (flower-y)))         ; Natural

       
;; template rules used:
;;   - compound: 4 fields



;; === Functions ===


;; Flower -> Flower
;; start the world with (main)

(define f (make-flower 0 0.5 20 20))

(define (main f)
  (big-bang f                           ; Flower
            (on-tick   next-flower)     ; Flower -> Flower
            (to-draw   flower-render)   ; Flower -> Image
            (on-mouse  mouse-handler))) ; WS Integer Integer MouseEvent -> WS
            



;; Number -> Number
;; increases a Number (n) by DTH [0, -360), if resulting n is <= -360 then produces ((n + DTH) modulo -360)
;; examples/tests
(check-expect (rotate-th -359) (modulo (+ -359 DTH) -360))
(check-expect (rotate-th -200) (modulo (+ -200 DTH) -360))

;; stub
#;
(define (rotate-th t) 0)

(define (rotate-th t)
  (modulo (+ t DTH) -360))


;; Flower -> Image
;; increases the scale of FLOWER image by flower-g
;; examples/tests
(check-expect (scale-flower (make-flower -10 5 10 10)) (scale (* 5 DG) FLOWER))

;; stub
#;
(define (scale-flower f) (circle 10 "solid" "red"))

(define (scale-flower f)
  (scale (* (flower-g f) DG) FLOWER))


;; Flower -> Flower
;; increases a flower's <th> by DTH and growth <g> by a factor DG
;; examples/tests
(check-expect (next-flower(make-flower -10 5 10 10)) (make-flower (+ -10 DTH) (* 5 DG) 10 10 ))
(check-expect (next-flower(make-flower -359 5 8 50)) (make-flower (- DTH -1)  (* 5 DG)  8 50))

;; stub
#;
(define (next-flower f) (make-flower 0 0))

;; <template from Flower>
(define (next-flower f)
  (make-flower (rotate-th (flower-th f)) (* (flower-g f) DG) (flower-x f) (flower-y f)))  



;; Flower -> Image
;; produce an image of a flower with appropriate angle of rotation <th> and scale <g>
;; examples/tests
(check-expect (flower-render (make-flower -20 1 5 HEIGHT))
              (place-image (rotate -20 (scale-flower (make-flower -20 1 5 HEIGHT))) 5 HEIGHT MTS))

(check-expect (flower-render (make-flower -359 5 20 30))
              (place-image (rotate -359 (scale-flower (make-flower -359 5 20 30))) 20 30 MTS))

;; stub
#;
(define (flower-render f) (circle 10 "solid" "light blue"))

                    
(define (flower-render f)
  (place-image (rotate (flower-th f) (scale-flower f)) (flower-x f) (flower-y f) MTS))       



;; Flower Integer Integer MouseEvent -> Flower
;; creates a new flower with the following attibutes (make-flower 0 1 x y) on mouse left-click x,y position
;; examples/tests
(check-expect (mouse-handler (make-flower -20 1 5 HEIGHT) 10 20 "button-down")
              (make-flower 0 0.5 10 20))


;; stub
#;
(define (mouse-handler f x y me) (make-flower 0 1 0 0))

(define (mouse-handler f x y me)
  (cond [(mouse=? me "button-down") (make-flower 0 0.5 x y)]
        [else f]))




