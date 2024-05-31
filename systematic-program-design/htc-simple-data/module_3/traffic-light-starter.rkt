(require 2htdp/image)
(require 2htdp/universe)

;; traffic-light-starter.rkt

; 
; PROBLEM:
; 
; Design an animation of a traffic light. 
; 
; Your program should show a traffic light that is red, then green, 
; then yellow, then red etc. For this program, your changing world 
; state data definition should be an enumeration.
; 
; Here is what your program might look like if the initial world 
; state was the red traffic light:
; .
; Next:
; .
; Next:
; .
; Next is red, and so on.
; 
; To make your lights change at a reasonable speed, you can use the 
; rate option to on-tick. If you say, for example, (on-tick next-color 1) 
; then big-bang will wait 1 second between calls to next-color.
; 
; Remember to follow the HtDW recipe! Be sure to do a proper domain
; analysis before starting to work on the code file.
; 
; Note: If you want to design a slightly simpler version of the program,
; you can modify it to display a single circle that changes color, rather
; than three stacked circles. 




;; === Constants ===

;; UI dimensions
(define WIDTH 50)
(define HEIGHT (* WIDTH 2.9))

;; emtpy scene
(define MTS (rectangle WIDTH HEIGHT "solid" "black"))

;; x-axis center of the MTS
(define CTR-X (/ WIDTH 2))

;; y-positions of the traffic lights againts MTS
(define Y1 (/ HEIGHT 6))
(define Y2 (* (/ HEIGHT 6) 3))
(define Y3 (* (/ HEIGHT 6) 5))

;; traffic light images
(define RED-LIGHT (place-images
   (list (circle (/ WIDTH 2.5) "solid" "red")
         (circle (/ WIDTH 2.5) "outline" "yellow")
         (circle (/ WIDTH 2.5) "outline" "green"))
   (list (make-posn CTR-X Y1)
         (make-posn CTR-X Y2)
         (make-posn CTR-X Y3))
   MTS))

(define YELLOW-LIGHT (place-images
   (list (circle (/ WIDTH 2.5) "outline" "red")
         (circle (/ WIDTH 2.5) "solid" "yellow")
         (circle (/ WIDTH 2.5) "outline" "green"))
   (list (make-posn CTR-X Y1)
         (make-posn CTR-X Y2)
         (make-posn CTR-X Y3))
   MTS))

(define GREEN-LIGHT (place-images
   (list (circle (/ WIDTH 2.5) "outline" "red")
         (circle (/ WIDTH 2.5) "outline" "yellow")
         (circle (/ WIDTH 2.5) "solid" "green"))
   (list (make-posn CTR-X Y1)
         (make-posn CTR-X Y2)
         (make-posn CTR-X Y3))
   MTS))

;; === Data Definition ===
;; LightState is one of:
;;   - "red"
;;   - "yellow"
;;   - "green"
;; interp. the color of the traffic light

;; <examples are redundant in enumerations>

(define (fn-for-light-state c)
  (cond [(string=? c "red") (...)]
        [(string=? c "yellow") (...)]
        [(string=? c "green") (...)]))

;; template rules used:
;;  - one of: 3 cases:
;;  - atomic distinct: "red"
;;  - atomic distinct: "yellow"
;;  - atomic distinct: "green"


;; === Functions ===

;; LightState -> LightState
;; start the world with (main ls)
;;   !ls should be a LightState!
;; 
(define (main ls)
  (big-bang ls                           ; LightState
            (on-tick   next-light 1)     ; LightState -> LightState
            (to-draw   light-render)))   ; LightState -> Image
          

;; LightState->LightState
;; produces the next light of a traffic light (red->green->yellow)
;; examples/test
(check-expect (next-light "red") "green")
(check-expect (next-light "yellow") "red")
(check-expect (next-light "green") "yellow")

;; stub
#;
(define (next-light s) "")

;; <template from LightState>
(define (next-light c)
  (cond [(string=? c "red") "green"]
        [(string=? c "yellow") "red"]
        [(string=? c "green") "yellow"]))


;; LightState->Image
;; produces an image from the given light state
;; examples/tests 
(check-expect (light-render "red")    (place-images
   (list (circle (/ WIDTH 2.5) "solid" "red")
         (circle (/ WIDTH 2.5) "outline" "yellow")
         (circle (/ WIDTH 2.5) "outline" "green"))
   (list (make-posn CTR-X Y1)
         (make-posn CTR-X Y2)
         (make-posn CTR-X Y3))
   MTS))

(check-expect (light-render "yellow") (place-images
   (list (circle (/ WIDTH 2.5) "outline" "red")
         (circle (/ WIDTH 2.5) "solid" "yellow")
         (circle (/ WIDTH 2.5) "outline" "green"))
   (list (make-posn CTR-X Y1)
         (make-posn CTR-X Y2)
         (make-posn CTR-X Y3))
   MTS))

(check-expect (light-render "green") (place-images
   (list (circle (/ WIDTH 2.5) "outline" "red")
         (circle (/ WIDTH 2.5) "outline" "yellow")
         (circle (/ WIDTH 2.5) "solid" "green"))
   (list (make-posn CTR-X Y1)
         (make-posn CTR-X Y2)
         (make-posn CTR-X Y3))
   MTS))

;; stub
#;
(define (light-render c) (circle 10 "solid" "red"))

;; <template from LightState>
(define (light-render c)
  (cond [(string=? c "red")    RED-LIGHT]
        [(string=? c "yellow") YELLOW-LIGHT]
        [(string=? c "green")  GREEN-LIGHT]))
