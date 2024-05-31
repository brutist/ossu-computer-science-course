(require 2htdp/image)
(require 2htdp/universe)
;; water-balloon-starter.rkt

; PROBLEM:
; 
; In this problem, we will design an animation of throwing a water balloon.  
; When the program starts the water balloon should appear on the left side 
; of the screen, half-way up.  Since the balloon was thrown, it should 
; fly across the screen, rotating in a clockwise fashion. Pressing the 
; space key should cause the program to start over with the water balloon
; back at the left side of the screen. 
; 
; NOTE: Please include your domain analysis at the top in a comment box. 
; 
; Use the following images to assist you with your domain analysis:
; 
; 
; 1)
; 2).
; .
; 3)
; .
; 4)
; 
; .
;     
; 
; Here is an image of the water balloon:
; (define WATER-BALLOON.)
; 
; 
; 
; NOTE: The rotate function wants an angle in degrees as its first 
; argument. By that it means Number[0, 360). As time goes by your balloon 
; may end up spinning more than once, for example, you may get to a point 
; where it has spun 362 degrees, which rotate won't accept. 
; 
; The solution to that is to use the modulo function as follows:
; 
; (rotate (modulo ... 360) (text "hello" 30 "black"))
; 
; where ... should be replaced by the number of degrees to rotate.
; 
; NOTE: It is possible to design this program with simple atomic data, 
; but we would like you to use compound data.



;; === Constants ===

;; UI dimensions
(define HEIGHT 500)
(define WIDTH (* 1.5 HEIGHT))

;; y-axis center
(define CTR-Y (/ HEIGHT 2))

;; reset key <space>
(define RST-KE " ")


;; image of balloon; just a placeholder to avoid issues with VS code and github
(define BAL-IMG (beside            (circle   (/ HEIGHT 10) "solid" "dark yellow")
                        (rotate 90 (triangle (/ HEIGHT 15) "solid" "dark yellow"))))

;; empty scene
(define MTS (rectangle WIDTH HEIGHT "outline" "white"))

;; constant rate of change for the balloon x position in pixels
(define DX 6)

;; constant rate of change for angle of rotation
;; (negative values only for clockwise rotation)
(define DTH -5)

;; === Data Definition ===

(define-struct balloon (x th))
;; Balloon is (make-ballon Natural Integer)
;; interp. a balloon with a position <x> [0, WIDTH] and angle of rotation <th> (0, 360)

;; example
#;
(define B1 (make-ball 10 -4)) ;negative angles will be used to have a clockwise-rotation
                              ;angles are measured from horizontal

#;
(define (fn-for-balloon b)
  (... (balloon-x)         ;Natural
       (balloon-th)))      ;Integer

;; template rules used:
;;   - compound: 2 fields


;; === Functions ===


;; Balloon -> Balloon
;; start the world with (main (make-balloon 0 0))
;; 
(define (main b)
  (big-bang b                          ; Balloon
            (on-tick   next-bal)       ; Balloon -> Balloon
            (to-draw   bal-render)     ; Balloon -> Image
            (on-key    key-handler)))  ; Balloon KeyEvent -> WS



;; th -> th
;; produces an angle that is a smaller negative [0, 360) than the given by a preset rate (DTH)
;; examples/tests
(check-expect (next-th   0)     (+ 0 DTH))
(check-expect (next-th -360) (modulo (+ -360 DTH) -360))
(check-expect (next-th -200) (+ -200 DTH))

;; stub
#;
(define (next-th t) 0)


(define (next-th t)
  (modulo (+ t DTH) -360))

;; template rules used:
;;   - atomic non-distinct: Integer



;; Balloon -> Image
;; produces a rotated BAL-IMG with an angle <balloon-th> from the horizontal
;; examples/tests
(check-expect (rotate-bal (make-balloon 55 -10)) (rotate -10 BAL-IMG))

;; stub
#;
(define (rotate-bal b) (rotate -10 (ellipse 10 20 "solid" "red")))

;; <template from Balloon>
(define (rotate-bal b)
  (rotate (balloon-th b) BAL-IMG)) 




;; Balloon -> Balloon
;; produces a next balloon that moved <DX> pixels left->right [0, WIDTH]
;;         and has a smaller negative angle <th> (clockwise rotation)

;; examples/tests
(check-expect (next-bal (make-balloon 55 0)) (make-balloon (+ 55 DX) (next-th 0)))
(check-expect (next-bal (make-balloon 4 -10)) (make-balloon (+ 4 DX) (next-th -10)))
(check-expect (next-bal (make-balloon 360 -360)) (make-balloon (+ 360 DX) (next-th -360)))

;; stub
#;
(define (next-bal b) (make-balloon 0 0))


;; template from Balloon
(define (next-bal b)
  (make-balloon (+ (balloon-x b) DX) (next-th (balloon-th b))))      



;; Balloon -> Image
;; places an image of balloon with the appropriate rotation and x-position on MTS
;; examples/tests

(check-expect (bal-render (make-balloon 55 0))
              (place-image (rotate-bal (make-balloon 55 0)) 55 CTR-Y MTS))


;; stub
#;
(define (bal-render b) (ellipse 10 20 "solid" "red"))

;; <template from Balloon>
(define (bal-render b)
   (place-image (rotate-bal b) (balloon-x b) CTR-Y MTS))




;; Balloon KeyEvent -> Balloon
;; produce a balloon with [0, 0] balloon-x and balloon-th values when the <space> key is pressed
;; examples/tests
(check-expect (key-handler (make-balloon 10 -10) " ") (make-balloon 0 0))
(check-expect (key-handler (make-balloon  0   0) " ") (make-balloon 0 0))
(check-expect (key-handler (make-balloon 10 -20) "a") (make-balloon 10 -20))
(check-expect (key-handler (make-balloon  0   0) "a") (make-balloon 0 0))

;; stub
#;
(define (key-handler b ke) (make-balloon 10 10))


;; <template from HTDP recipe>
(define (key-handler b ke)
  (cond [(key=? " " ke) (make-balloon 0 0)]
        [else b]))









