(require 2htdp/image)
(require 2htdp/universe)

;; making-rain-filtered-starter.rkt

; 
; PROBLEM:
; 
; Design a simple interactive animation of rain falling down a screen. Wherever we click,
; a rain drop should be created and as time goes by it should fall. Over time the drops
; will reach the bottom of the screen and "fall off". You should filter these excess
; drops out of the world state - otherwise your program is continuing to tick and
; and draw them long after they are invisible.
; 
; In your design pay particular attention to the helper rules. In our solution we use
; these rules to split out helpers:
;   - function composition
;   - reference
;   - knowledge domain shift
;   
;   
; NOTE: This is a fairly long problem.  While you should be getting more comfortable with 
; world problems there is still a fair amount of work to do here. Our solution has 9
; functions including main. If you find it is taking you too long then jump ahead to the
; next homework problem and finish this later.
; 
; 


;; Make it rain where we want it to.

;; =================
;; Constants:

(define WIDTH  300)
(define HEIGHT 300)

(define SPEED 1)

(define DROP (ellipse 4 8 "solid" "lightblue"))

(define MTS (rectangle WIDTH HEIGHT "solid" "white"))

;; =================
;; Data definitions:

(define-struct drop (x y))
;; Drop is (make-drop Integer Integer)
;; interp. A raindrop on the screen, with x and y coordinates.


(define D1 (make-drop 10 30))

#;
(define (fn-for-drop d)
  (... (drop-x d) 
       (drop-y d)))

;; Template Rules used:
;; - compound: 2 fields



;; ListOfDrop is one of:
;;  - empty
;;  - (cons Drop ListOfDrop)
;; interp. a list of drops

(define LOD1 empty)
(define LOD2 (cons (make-drop 10 20) (cons (make-drop 3 6) empty)))

#;
(define (fn-for-lod lod)
  (cond [(empty? lod) (...)]
        [else
         (... (fn-for-drop (first lod))
              (fn-for-lod (rest lod)))]))

;; Template Rules used:
;; - one-of: 2 cases
;; - atomic distinct: empty
;; - compound: (cons Drop ListOfDrop)
;; - reference: (first lod) is Drop
;; - self reference: (rest lod) is ListOfDrop

;; =================
;; Functions:

;; ListOfDrop -> ListOfDrop
;; start rain program by evaluating (main empty)
(define (main lod)
  (big-bang lod
    (on-mouse handle-mouse)   ; ListOfDrop Integer Integer MouseEvent -> ListOfDrop
    (on-tick  next-drops)     ; ListOfDrop -> ListOfDrop
    (to-draw  render-drops))) ; ListOfDrop -> Image


;; ListOfDrop Integer Integer MouseEvent -> ListOfDrop
;; if mevt is "button-down" add a new drop at that position
;; examples/tests
(check-expect (handle-mouse empty 10 52 "button-down")
              (cons (make-drop 10 52) empty))

(check-expect (handle-mouse (cons (make-drop 20 60) (cons (make-drop 15 25) empty)) 20 60 "button-down")
              (cons (make-drop 20 60) (cons (make-drop 20 60) (cons (make-drop 15 25) empty))))

(check-expect (handle-mouse (cons (make-drop 40 70) (cons (make-drop 80 30) empty)) 10 50 "drag")
              (cons (make-drop 40 70) (cons (make-drop 80 30) empty)))

#;
(define (handle-mouse lod x y mevt) empty) ; stub

(define (handle-mouse lod x y mevt)
  (cond [(mouse=? mevt "button-down") (cons (make-drop x y) lod)]
        [else lod]))
  

;; Drop -> Boolean
;; produce true if the drop must be filtered,
;;            filtered means moved past the screen (y > [HEIGHT + HEIGHT of drop])
;; examples/tests
(check-expect (filter? (make-drop 10 10)) false)
(check-expect (filter? (make-drop 10 HEIGHT)) false)

#;
(define (filter? d) d) ;stub

(define (filter? d)
  (> (drop-y d) (+ HEIGHT (image-height DROP))))


;; Drop -> Drop
;; move the drop in y axis by SPEED pixels
;; examples/tests
(check-expect (move-drop (make-drop 10 10)) (make-drop 10 (+ 10 SPEED)))
(check-expect (move-drop (make-drop 10 HEIGHT)) (make-drop 10 (+ HEIGHT SPEED)))

#;
(define (move-drop d) d) ;stub

(define (move-drop d)
  (make-drop (drop-x d) (+ (drop-y d) SPEED)))



;; ListOfDrop -> ListOfDrop
;; produce a filtered list of drops
;; examples/tests

(check-expect (filter-drops empty) empty)

(check-expect (filter-drops (cons (make-drop 20 60) (cons (make-drop 15 25) empty)))            
              (cons (make-drop 20 60) (cons (make-drop 15  25) empty)))    ;all drops within screen

(check-expect (filter-drops (cons (make-drop 40 70) (cons (make-drop 80 (+ HEIGHT 20)) empty)))        
              (cons (make-drop 40 70) empty))                              ;one drop went out-of-bounds

(check-expect (filter-drops (cons (make-drop 40 70) (cons (make-drop 80 HEIGHT) empty)))        
              (cons (make-drop 40 70) (cons (make-drop 80 HEIGHT) empty)))  
#;
(define (filter-drops lod) empty) ;stub

(define (filter-drops lod)
  (cond [(empty? lod) empty]                                ;base case
        [(filter? (first lod))                              ;drop must be filtered
         (filter-drops (rest lod))] 
        [else                                               ;drop retained
         (cons (first lod) (filter-drops (rest lod)))]))


;; ListOfDrop -> ListOfDrop
;; produce a list of drops with all drop's y is increased by SPEED
;; examples/tests
(check-expect (advance-drops empty) empty)
(check-expect (advance-drops (cons (make-drop 40 70) (cons (make-drop 80 HEIGHT) empty)))
              (cons (make-drop 40 (+ 70 SPEED)) (cons (make-drop 80 (+ HEIGHT SPEED)) empty)))
(check-expect (advance-drops (cons (make-drop 20 60) (cons (make-drop 15 25) empty)))            
              (cons (make-drop 20 (+ 60 SPEED)) (cons (make-drop 15 (+ 25 SPEED)) empty)))

#;
(define (advance-drops lod) empty) ;stub

(define (advance-drops lod)
  (cond [(empty? lod) empty]
        [else
         (cons (move-drop (first lod)) (advance-drops (rest lod)))]))
  


;; ListOfDrop -> ListOfDrop
;; produce a filtered and ticked list of drops
;; examples/tests
(check-expect (next-drops empty) empty)

(check-expect (next-drops (cons (make-drop 20 60) (cons (make-drop 15 25) empty)))            ;all drops within screen
              (cons (make-drop 20 (+ 60 SPEED)) (cons (make-drop 15 (+ 25 SPEED)) empty)))

(check-expect (next-drops (cons (make-drop 40 70) (cons (make-drop 80 (+ HEIGHT 30)) empty))) ;one-drop went out-of-bounds
              (cons (make-drop 40 (+ 70 SPEED)) empty))

#;
(define (next-drops lod) empty) ; stub

(define (next-drops lod)
  (cond [(empty? lod) empty]
        [else (advance-drops (filter-drops lod))]))




;; ListOfDrop -> Image
;; Render the drops onto MTS
;; examples/tests3

(check-expect (render-drops empty) MTS)
(check-expect (render-drops (cons (make-drop 40 70) (cons (make-drop 80 (+ HEIGHT 30)) empty)))
              (place-image DROP 40 70 (place-image DROP 80 (+ HEIGHT 30) MTS)))

(check-expect (render-drops (cons (make-drop 20 60) (cons (make-drop 15 25) empty)))
              (place-image DROP 20 60 (place-image DROP 15 25 MTS)))


#;
(define (render-drops lod) empty) ; stub

(define (render-drops lod)
  (cond [(empty? lod) MTS]
        [else
         (place-image DROP
                      (drop-x (first lod))
                      (drop-y (first lod))
                      (render-drops (rest lod)))]))
