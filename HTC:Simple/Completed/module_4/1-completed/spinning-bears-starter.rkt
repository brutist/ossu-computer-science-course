;; spinning-bears-starter.rkt

(require 2htdp/image)
(require 2htdp/universe)

; PROBLEM:
; 
; In this problem you will design another world program. In this program the changing 
; information will be more complex - your type definitions will involve arbitrary 
; sized data as well as the reference rule and compound data. But by doing your 
; design in two phases you will be able to manage this complexity. As a whole, this problem 
; will represent an excellent summary of the material covered so far in the course, and world 
; programs in particular.
; 
; This world is about spinning bears. The world will start with an empty screen. Clicking
; anywhere on the screen will cause a bear to appear at that spot. The bear starts out upright,
; but then rotates counterclockwise at a constant speed. Each time the mouse is clicked on the 
; screen, a new upright bear appears and starts spinning.
; 
; So each bear has its own x and y position, as well as its angle of rotation. And there are an
; arbitrary amount of bears.
; 
; To start, design a world that has only one spinning bear. Initially, the world will start
; with one bear spinning in the center at the screen. Clicking the mouse at a spot on the
; world will replace the old bear with a new bear at the new spot. You can do this part 
; with only material up through compound. 
; 
; Once this is working you should expand the program to include an arbitrary number of bears.
; 
; Here is an image of a bear for you to use: .



;; === Constants ===

;; UI dimensions
(define HEIGHT 500)
(define WIDTH (* HEIGHT 1.5))


;; bear image (placeholder to avoid problems with github and VScode)
(define BEAR-IMG
  (overlay/align "middle" "bottom"
                 (ellipse 30 30 "solid" "lightbrown")
                 (overlay/align "middle" "top"
                                (circle 30 "solid" "yellow")
                                (beside/align "bottom"
                                              (circle 12 "solid" "lightblue")
                                              (rectangle 20 1  "solid" "white")
                                              (circle 12 "solid" "lightblue")))))


;; empty scene
(define MTS (empty-scene WIDTH HEIGHT))             


;; speed of rotation in degree angle per tick
(define DTH 4)



;; === Data Definitions ===

(define-struct position (x y))
;; position is (make-ball Natural Natural)
;; interp. x and y coordiantes

(define POS1 (make-position 5 10))    
(define POS2 (make-position 0 HEIGHT))

(define (fn-for-position p)
  (... (position-x p)         ;Natural
       (position-y p)))       ;Natural

;; template rules used:
;;   - compound: 2 fields



(define-struct bear (p th))
;; bear is (make-bear Position Number)
;; interp. a bear with position <p> and <th> degrees of rotation from horizontal

(define BR1 (make-bear (make-position 4 5) 20))       ;located in (4, 5) with 20 degree spin 
(define BR2 (make-bear (make-position 0 HEIGHT) 0))   ;located in (0, HEIGHT) with 0 degree spin

(define (fn-for-bear b)
  (... (fn-for-position (bear-p b))    ;Position
       (bear-th b)))                   ;Number

;; template rules used:
;;   - compound: 2 fields




;; ListOfBear is one of:
;;   - empty
;;   - (cons Bear ListOfBear)
;; interp. list of bears

(define LOB-1 empty)
(define LOB-2 (cons BR1 empty))
(define LOB-3 (cons BR2 (cons BR1 empty)))

(define (fn-for-lob lob)
  (cond [(empty? lob) (...)]                   ;base case
        [else (... (fn-for-bear(first lob))    ;Bear
                   (fn-for-lob (rest lob)))])) ;natural recursion

;; template rules used:
;;   - one of: 2 cases
;;   - atomic distinct: empty
;;   - compound: (cons Bear ListOfBear)
;;   - reference: (first los) is Bear
;;   - self-reference: (rest los) is ListOfBear




;; === Functions ===

;; ListOfBear -> ListOfBear
;; start the world with (main f)
;;

(define f empty)
(define (main f)
  (big-bang f                     ; ListOfBear
    (on-tick   lob-move)          ; ListOfBear -> ListOfBear
    (to-draw   lob-render)        ; ListOfBear -> Image
    (on-mouse  click-handler)))   ; ListOfBear Integer Integer MouseEvent -> ListOfBear


;; Number -> Number
;; produces [(DTH + input number) modulo 360]
;; examples/tests
(check-expect (next-th 0) DTH)
(check-expect (next-th 360) DTH)
(check-expect (next-th 359) (- DTH 1))

;; stub
#;
(define (next-th t) 0)

(define (next-th t)
  (modulo (+ DTH t) 360))



;; Bear -> Bear
;; increases the rotation of the bear by DTH while retaining its position
;; examples/tests
(check-expect (bear-move BR1) (make-bear (make-position 4 5) (+ 20 DTH)))
(check-expect (bear-move BR2) (make-bear (make-position 0 HEIGHT) (+ 0 DTH)))
(check-expect (bear-move (make-bear (make-position 20 HEIGHT) 360))
              (make-bear (make-position 20 HEIGHT) DTH))

;; stub
#;
(define (bear-move b) (make-bear (make-position 0 0) 0))

(define (bear-move b)
  (make-bear (bear-p b) (next-th (bear-th b))))    



;; ListOfBear -> ListOfBear
;; increases the rotation of all bears in the list by DTH while retaining its positions
;; examples/tests
(check-expect (lob-move LOB-1) empty)
(check-expect (lob-move LOB-2) (cons (bear-move BR1) empty))
(check-expect (lob-move LOB-3) (cons (bear-move BR2) (cons (bear-move BR1) empty)))


(define (lob-move lob)
  (cond [(empty? lob) empty]                   
        [else (cons (bear-move(first lob))    
                    (lob-move (rest lob)))])) 



;; Bear -> Image
;; produces BEAR-IMG with <th> degrees of rotation
;; examples/tests
(check-expect (bear->img BR1) (rotate (bear-th BR1) BEAR-IMG))
(check-expect (bear->img BR2) (rotate (bear-th BR2) BEAR-IMG))

;; stub
#;
(define (bear->img b) (square 0 "solid" "white"))

(define (bear->img b)
  (rotate (bear-th b) BEAR-IMG))





;; Bear -> Image
;; place BEAR-IMG in appropriate (x,y) position with <th> degree of rotation
;; examples/tests
(check-expect (bear-render BR1)
              (place-image (bear->img BR1) (position-x (bear-p BR1)) (position-y (bear-p BR1)) MTS))

;; stub
#;
(define (bear-render b) (square 0 "solid" "white"))


(define (bear-render b)
  (place-image (bear->img b)
               (position-x (bear-p b))
               (position-y (bear-p b))
               MTS))                




;; ListOfBear -> Image
;; produces BEAR-IMGs of the ListOfBears with the appropriate degrees of rotation
;; examples/tests
(check-expect (lob-render LOB-1) MTS)
(check-expect (lob-render LOB-3)
              (place-image (bear->img BR2)
                           (position-x (bear-p BR2))
                           (position-y (bear-p BR2))
                           (bear-render BR1)))


(define (lob-render lob)
  (cond [(empty? lob)  MTS]                   
        [else (place-image (bear->img (first lob))
                           (position-x (bear-p (first lob)))
                           (position-y (bear-p (first lob)))
                           (lob-render (rest lob)))])) 



;; ListOfBear Integer Integer MouseEvent -> ListOfBear
;; produces a ListOfBear with a new bear with 0 spin in the mouse-cursor x and y position 
;; examples/tests
(check-expect (click-handler LOB-1 5 10 "button-down")
              (cons (make-bear (make-position 5 10) 0) LOB-1))

(check-expect (click-handler LOB-2 (/ WIDTH 2) (/ HEIGHT 2) "button-down")
              (cons (make-bear (make-position (/ WIDTH 2) (/ HEIGHT 2)) 0) LOB-2))

(check-expect (click-handler LOB-3 (/ WIDTH 3) (/ HEIGHT 4) "button-down")
              (cons (make-bear (make-position (/ WIDTH 3) (/ HEIGHT 4)) 0) LOB-3))

;; stub
#;
(define (click-handler lob x y me) (make-bear (make-position 0 0) 0))      

(define (click-handler lob x y me)
  (cond [(mouse=? me "button-down") (cons (make-bear (make-position x y) 0) lob)]
        [else lob]))

