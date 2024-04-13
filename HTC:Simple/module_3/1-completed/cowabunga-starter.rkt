(require 2htdp/image)
(require 2htdp/universe)
; 
; PROBLEM:
; 
; As we learned in the cat world programs, cats have a mind of their own. When they 
; reach the edge they just keep walking out of the window.
; 
; Cows on the other hand are docile creatures. They stay inside the fence, walking
; back and forth nicely.
; 
; Design a world program with the following behaviour:
;    - A cow walks back and forth across the screen.
;    - When it gets to an edge it changes direction and goes back the other way
;    - When you start the program it should be possible to control how fast a
;      walker your cow is.
;    - Pressing space makes it change direction right away.
;    
; To help you here are two pictures of the right and left sides of a lovely cow that 
; was raised for us at Brown University.
; 
; .     .
; 
; Once your program works here is something you can try for fun. If you rotate the
; images of the cow slightly, and you vary the image you use as the cow moves, you
; can make it appear as if the cow is waddling as it walks across the screen.
; 
; Also, to make it look better, arrange for the cow to change direction when its
; nose hits the edge of the window, not the center of its body.
; 


;; shows a moving cow back and forth automatically turning or using space key
;; === CONSTANTS ===
;; UI dimensions
(define HEIGHT 500)
(define WIDTH (+ HEIGHT 200))

;; y-axis UI center
(define CTR-Y (/ HEIGHT 2))

;; placeholder for cow (to avoid issues with VS code and github)
(define BODY (add-curve (ellipse 50 40 "outline" "black")
                        -15 30 0 1/3
                          0 15 0 1/3
                          "black"))
(define HEAD (rotate 30(wedge 10 300 "outline" "black")))

(define R-COW (beside BODY HEAD))
(define L-COW (flip-horizontal R-COW))


;; empty scene
(define MTS (rectangle WIDTH HEIGHT "outline" "white"))

;; === Data Definitions ===

(define-struct cow (x dirc sp))
;; Cow is (make-cow Number String Natural)
;; interp. a cow at position <x> in x-axis going to <dirc> direction at speed <sp> in pixels
;;      - dirc is one of:
;;      - "left"
;;      - "right"

(define C1 (make-cow 1 "right" 5))          ;cow at left-most going to the right
(define C2 (make-cow WIDTH "right" 5))      ;cow at right-most colliding to the screen
(define C3 (make-cow WIDTH "left" 0))       ;cow at right-most, stationary

(define (fn-for-cow c)
  (... (cow-x)            ;Number
       (cow-dirc)         ;String
       (cow-sp)))         ;Natural

;; template rules used:
;;   - compound: 3 fields


;; === Functions ===

;; Cow -> Cow
;; start the world with (main sp)
;; 
;; sp is Natural
;; interp. speed in pixels
;; 
(define (main sp)
  (big-bang (make-cow 1 "right" sp)    ; Cow
            (on-tick   move-cow)       ; Cow -> Cow
            (to-draw   cow-render)))   ; Cow -> Image
            


;; Cow->Cow
;; turns the cow to the opposite direction (cow-dirc) 

;; examples/tests
(check-expect (turn-cow (make-cow (/ WIDTH 2) "right" 5)) (make-cow (/ WIDTH 2) "left" 5))
(check-expect (turn-cow (make-cow (/ WIDTH 2) "left" 5)) (make-cow (/ WIDTH 2) "right" 5))

;; stub
#;
(define (turn-cow c) (make-cow (/ WIDTH 2) "left" 5))


;; <template from Cow>
(define (turn-cow c)
  (if (string=? (cow-dirc c) "right")
      (make-cow (cow-x c) "left" (cow-sp c))
      (make-cow (cow-x c) "right" (cow-sp c))))


;; Cow->Cow
;; moves the cow to the appropriate direction by (sp) pixels. if the cow hits the screen,
;; it turns to the opposite dirc and retains its x position
;; examples
(check-expect (move-cow (make-cow 0 "left" 5))
              (make-cow 0 "right" 5))                    ;cow that hits the left screen and turns right

(check-expect (move-cow (make-cow WIDTH "right" 5))  
              (make-cow WIDTH "left" 5))                 ;cow that hits the right screen and turns left

(check-expect (move-cow (make-cow (/ WIDTH 2) "right" 5))  
              (make-cow (+ (/ WIDTH 2) 5) "right" 5))     ;cow continue to move right

(check-expect (move-cow (make-cow (/ WIDTH 2) "left" 5))  
              (make-cow (- (/ WIDTH 2) 5) "left" 5))      ;cow continue to move left

(check-expect (move-cow (make-cow (/ WIDTH 2) "left" 0))  
              (make-cow (/ WIDTH 2) "left" 0))             ;cow stays in place (zero sp)

;; stub
#;
(define (move-cow c) (make-cow 4 "right" 5))

;; <template from Cow>

(define (move-cow c)
  (cond [(= (cow-sp c) 0)                                           ;cow stays in place
        c] 
        [(and (<= (cow-x c) 0) (string=? (cow-dirc c) "left"))      ;cow hits left screen
        (turn-cow c)]
        [(and (>= (cow-x c) WIDTH) (string=? (cow-dirc c) "right")) ;cow hits right screen 
        (turn-cow c)]
        [(string=? (cow-dirc c) "right")                            ;cow continue to move right
        (make-cow (+ (cow-x c) (cow-sp c)) "right" (cow-sp c))]
        [(string=? (cow-dirc c) "left")                             ;cow continue to move left
        (make-cow (- (cow-x c) (cow-sp c)) "left" (cow-sp c))]))


;; Cow->Image
;; produce an image of rendering of cow the appropriate MTS position
;; examples/tests
(check-expect (cow-render (make-cow (/ WIDTH 2) "left" 5))
              (place-image L-COW (/ WIDTH 2) CTR-Y MTS))

(check-expect (cow-render (make-cow (/ WIDTH 2) "right" 5))
              (place-image R-COW (/ WIDTH 2) CTR-Y MTS))

;; stub
#;
(define (cow-render c) (circle 10 "solid" "black"))

;; <template from Cow>
(define (cow-render c)
  (if (string=? (cow-dirc c) "right")
      (place-image R-COW (cow-x c) CTR-Y MTS)
      (place-image L-COW (cow-x c) CTR-Y MTS)))       

