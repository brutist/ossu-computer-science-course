(require 2htdp/universe)
(require 2htdp/image)

;; Space Invaders


;; Constants:

(define WIDTH  300)
(define HEIGHT 500)

(define INVADER-X-SPEED 1.5)  ;speeds (not velocities) in pixels per tick
(define INVADER-Y-SPEED 1.5)
(define TANK-SPEED 2)
(define MISSILE-SPEED 10)
(define MAX-MISSILES 100)
(define HIT-RANGE 10)

(define INVADE-RATE 100)

(define BACKGROUND (empty-scene WIDTH HEIGHT))

(define INVADER
  (overlay/xy (ellipse 10 15 "outline" "blue")              ;cockpit cover
              -5 6
              (ellipse 20 10 "solid"   "blue")))            ;saucer

(define TANK
  (overlay/xy (overlay (ellipse 28 8 "solid" "black")       ;tread center
                       (ellipse 30 10 "solid" "green"))     ;tread outline
              5 -14
              (above (rectangle 5 10 "solid" "black")       ;gun
                     (rectangle 20 10 "solid" "black"))))   ;main body

(define TANK-HEIGHT/2 (/ (image-height TANK) 2))
(define TANK-Y (- HEIGHT TANK-HEIGHT/2))

(define MISSILE (ellipse 5 15 "solid" "red"))



;; Data Definitions:

(define-struct game (invaders missiles tank))
;; Game is (make-game  (listof Invader) (listof Missile) Tank)
;; interp. the current state of a space invaders game
;;         with the current invaders, missiles and tank position

;; Game constants defined below Missile data definition

#;
(define (fn-for-game s) 
  (... (fn-for-loinvader (game-invaders s))
       (fn-for-lom (game-missiles s))
       (fn-for-tank (game-tank s))))



(define-struct tank (x dir))
;; Tank is (make-tank Number Integer[-1, 1])
;; interp. the tank location is x, HEIGHT - TANK-HEIGHT/2 in screen coordinates
;;         the tank moves TANK-SPEED pixels per clock tick left if dir -1, right if dir 1

(define T0 (make-tank (/ WIDTH 2) 1))   ;center going right
(define T1 (make-tank 50 1))            ;going right
(define T2 (make-tank 50 -1))           ;going left

#;
(define (fn-for-tank t)
  (... (tank-x t) (tank-dir t)))



(define-struct invader (x y dx))
;; Invader is (make-invader Number Number Number)
;; interp. the invader is at (x, y) in screen coordinates
;;         the invader along x by dx pixels per clock tick

(define I1 (make-invader 150 100 12))           ;not landed, moving right
(define I2 (make-invader 150 HEIGHT -10))       ;exactly landed, moving left
(define I3 (make-invader 150 (+ HEIGHT 10) 10)) ;> landed, moving right


#;
(define (fn-for-invader invader)
  (... (invader-x invader) (invader-y invader) (invader-dx invader)))


(define-struct missile (x y))
;; Missile is (make-missile Number Number)
;; interp. the missile's location is x y in screen coordinates

(define M1 (make-missile 150 300))                               ;not hit I1
(define M2 (make-missile (invader-x I1) (+ (invader-y I1) 10)))  ;exactly hit I1
(define M3 (make-missile (invader-x I1) (+ (invader-y I1)  5)))  ;> hit I1

#;
(define (fn-for-missile m)
  (... (missile-x m) (missile-y m)))



(define G0 (make-game empty empty T0))
(define G1 (make-game empty empty T1))
(define G2 (make-game (list I1) (list M1) T1))
(define G3 (make-game (list I1 I2) (list M1 M2) T1))

;;  === Functions ===


;; Game -> Game
;; start the world with (main g)
;;

(define (main ws)
  (big-bang ws                      ; Game
    (on-tick   update-game-state)   ; Game -> Game
    (to-draw   render-game)         ; Game -> Image
    (on-key    key-handler)         ; Game KeyEvent -> WS
    (stop-when lose?)))             ; Game -> Boolean


;; ListOfInvaders -> Boolean
;; produces true if at least one invader has a y-position >= (HEIGHT - (height of INVADER))
;; examples/tests
(check-expect (invaded? empty) false)
(check-expect (invaded? (list I1 I2 (make-invader 20 HEIGHT -2))) true)

#;
(define (invaded? loi) "...") ;stub

(define (invaded? loi)
  (cond [(empty? loi) false]
        [(> (invader-y (first loi)) (- HEIGHT (image-height INVADER)) ) true]
        [else (invaded? (rest loi))]))

;; Game -> Boolean
;; produces true an invader reaches the bottom of the screen
;; examples/tests
(check-expect (lose? G0) false)
(check-expect (lose? (make-game (list I1 I2 (make-invader 20 HEIGHT -2)) (list M1 M2) T0)) true)
      
#;      
(define (lose? g) "....")  ;stub

(define (lose? g)
  (invaded? (game-invaders g)))


;; Natural Natural Image -> Boolean
;; produce true if the sum of the given natural is greater than (WIDTH - (width of image))
;;         or sum is less than the width of the image, false if otherswise
;; examples/tests
(check-expect (out-of-screen? 30 2 INVADER) false)
(check-expect (out-of-screen? (- WIDTH 10) 5 INVADER) true)
(check-expect (out-of-screen? (/ WIDTH 2) 5 TANK) false)
(check-expect (out-of-screen? (- WIDTH 10) 5 TANK) true)


(define (out-of-screen? n1 n2 img)
  (or (> (+ n1 n2) (- WIDTH (image-width img))) 
      (< (+ n1 n2) (image-width img))))


;; List -> List
;; takes in a list of invaders and update its positions accordingly
;;     - increase invader-x by invader-dx
;;     - increase invader-y by invader-dx
;; examples/tests

(check-expect (update-invaders (cons (make-invader (/ WIDTH 2) 100 0) empty))
              (cons (make-invader (/ WIDTH 2) 100 0) empty))
;; going right scenarios
(check-expect (update-invaders (cons (make-invader (/ WIDTH 2) 100 12) empty))
              (cons (make-invader (+ (/ WIDTH 2) 12) (+ 100 12) 12) empty))        ;middle

(check-expect (update-invaders (cons (make-invader (+ WIDTH 2) 100 8) empty))      ;end of screen
              (cons (make-invader (- WIDTH (image-width INVADER)) (+ 100 8) -8) empty))     
   
;; going left scenarios
(check-expect (update-invaders (cons (make-invader (/ WIDTH 2) 100 -12) empty))
              (cons (make-invader (+ (/ WIDTH 2) -12) (+ 100 12) -12) empty))      ;middle


(check-expect (update-invaders (cons (make-invader 2 100 -18) empty))               ;end of screen
              (cons (make-invader (image-width INVADER) (+ 100 18) 18) empty))     


#;(define (update-invaders loi) "false") ;stub

(define (update-invaders loi)
  (cond [(empty? loi) empty]
        [(out-of-screen? (invader-x (first loi)) (invader-dx (first loi)) INVADER)
         (cond [(>= (+ (invader-x (first loi)) (invader-dx (first loi))) (- WIDTH (image-width INVADER)))
                (cons (make-invader (- WIDTH (image-width INVADER))
                                    (+ (invader-y (first loi)) (abs (invader-dx (first loi))))
                                    (- (invader-dx (first loi))))
                      (update-invaders (rest loi)))]
               [(<= (+ (invader-x (first loi)) (invader-dx (first loi))) (image-width INVADER))
                (cons (make-invader (image-width INVADER)
                                    (+ (invader-y (first loi)) (abs (invader-dx (first loi))))
                                    (abs (invader-dx (first loi))))
                      (update-invaders (rest loi)))])]                     
        [else (cons (make-invader (+ (invader-x (first loi)) (invader-dx (first loi)))
                                  (+ (invader-y (first loi)) (abs (invader-dx (first loi))))
                                  (invader-dx (first loi)))
                    (update-invaders (rest loi)))]))


;; List -> List
;; takes in a list of missiles and update its positions accordingly
;;     - decrease missile-y by MISSILE-SPEED
;;     - retain missile-x
;; examples/tests
(check-expect (update-missiles empty) empty)
(check-expect (update-missiles (list M1))
              (cons (make-missile 150 (- 300 MISSILE-SPEED))
                    empty))
(check-expect (update-missiles (list M2 M1))
              (cons (make-missile (invader-x I1) (- (+ (invader-y I1) 10) MISSILE-SPEED))
                    (cons (make-missile 150 (- 300 MISSILE-SPEED))
                          empty)))

#;(define (update-invaders loi) "false") ;stub

(define (update-missiles lom)
  (cond [(empty? lom) empty]                  
        [else (cons (make-missile (missile-x (first lom))
                                  (- (missile-y (first lom)) MISSILE-SPEED))
                    (update-missiles (rest lom)))]))


;; Tank -> Tank
;; update the x position of the tank according to its dir
;; examples/tests
(check-expect (update-tank (make-tank (/ WIDTH 2) -1)) 
              (make-tank (+ (/ WIDTH 2) (* -1 TANK-SPEED)) -1))   ;center going left
(check-expect (update-tank (make-tank (/ WIDTH 2) 1)) 
              (make-tank (+ (/ WIDTH 2) (* 1 TANK-SPEED)) 1))     ;center going right             
(check-expect (update-tank (make-tank (- WIDTH (image-width TANK)) 1)) 
              (make-tank (- WIDTH (image-width TANK)) -1))        ;collides to right
(check-expect (update-tank (make-tank (image-width TANK) -1)) 
              (make-tank (image-width TANK) 1))                   ;collides to left

#;
(define (update-tank t) "false") ;stub

(define (update-tank t)
  (cond [(out-of-screen? (tank-x t) (tank-dir t) TANK)
         (if (< (tank-dir t) 0)
             (make-tank (image-width TANK) (abs (tank-dir t)))
             (make-tank (- WIDTH (image-width TANK)) (- (tank-dir t))))]
        [else (make-tank (+ (tank-x t) (* (tank-dir t) TANK-SPEED)) (tank-dir t))]))

;; ListOfMissiles Image -> ListOfMissiles
;; filters the given list of missiles by deleting missiles that has y-position <= image height
;; examples/tests
(check-expect (filter-missiles empty MISSILE) empty)
(check-expect (filter-missiles (list (make-missile 150 300) (make-missile 150 300)) MISSILE)
              (list (make-missile 150 300) (make-missile 150 300)))
(check-expect (filter-missiles (list (make-missile 150 -60) (make-missile 150 300)) MISSILE)
              (list (make-missile 150 300)))

#;
(define (filter-missiles lom img) "false") ;stub

(define (filter-missiles lom img)
  (cond [(empty? lom) empty]
        [(< (missile-y (first lom)) (image-height img))
          (rest lom)]
        [else (cons (first lom) (rest lom))]))


;; ListOfInvaders -> ListOfInvaders
;; add an invader with location at (x, 0), x will be choosen at random
;; examples/tests

(check-random (spawn-invader empty) 
              (cond [(> INVADE-RATE (random 5000)) 
                     (cons (make-invader (random WIDTH) 10 INVADER-X-SPEED) empty)]
                    [else empty]))
(check-random (spawn-invader (cons (make-invader 10 10 INVADER-X-SPEED) empty)) 
              (cond [(> INVADE-RATE (random 5000)) 
                     (cons (make-invader (random WIDTH) 10 INVADER-X-SPEED) 
                           (cons (make-invader 10 10 INVADER-X-SPEED) empty))]
                    [else (cons (make-invader 10 10 INVADER-X-SPEED) empty)]))


(define (spawn-invader loi)
  (cond [(> INVADE-RATE (random 5000)) (cons (make-invader (random WIDTH) 10 INVADER-X-SPEED) loi)]
        [else loi]))


;; Natural Natural Natural -> Boolean
;; produces true if the first natural is between the s(maller) and l(arger) natural
;; examples/tests
(check-expect (between 10 9 11) true)
(check-expect (between 12 15 18) false)

(define (between n s l)
  (cond [(and (>= n s) (<= n l)) true]
        [(or (= n s) (= n l)) true]
        [else false]))


;; Invader ListOfMissiles -> Boolean
;; produces true if invader occupies the same space with at least one missile
;; examples/tests
(check-expect (invader-hit? (make-invader 10 10 -1) (list (make-missile 10 10) M2)) true)
(check-expect (invader-hit? (make-invader 10 10 -1) (list (make-missile 0 10) (make-missile 10 10) M2)) true)
(check-expect (invader-hit? I1 (list M1 M2)) true)


#;
(define (invader-hit? i lom) "false")  ;stub

(define (invader-hit? i lom)
    (cond [(empty? lom) false]
          [(and (between (- (missile-y (first lom)) (/ (image-height MISSILE) 2)) 
                    (- (invader-y i) (/ (image-height INVADER) 2))
                    (+ (invader-y i) (/ (image-height INVADER) 2)))
                (between (missile-x (first lom))
                    (- (invader-x i) (/ (image-width INVADER) 2))
                    (+ (invader-x i) (/ (image-width INVADER) 2))))
            true]
          [else (invader-hit? i (rest lom))]))
                                

;; ListOfInvaders ListOfMissiles -> ListOfInvaders
;; remove the invaders that have the same position with at least 1 missile
;; examples/tests
(check-expect (destroy-invaders empty empty) empty)
#;
(check-expect (destroy-invaders (list I1 I2) (list M1 M2)) 
              (list I2))

#;
(define (destroy-invaders loi lom) "false")  ;stub

(define (destroy-invaders loi lom)
    (cond [(empty? loi) loi]
          [(invader-hit? (first loi) lom) (destroy-invaders (rest loi) lom)]
          [else (cons (first loi) (destroy-invaders (rest loi) lom))]))


;; Game -> Game
;; update the missiles and invaders position
;; examples/tests
#;
(check-expect (update-game-state G0) (make-game empty empty (update-tank T0)))

(check-expect (update-game-state G2)
              (make-game (update-invaders (spawn-invader (destroy-invaders (list I1) (list M1)))) 
                         (update-missiles (filter-missiles (list M1) MISSILE))
                         (update-tank (game-tank G2))))

#;
(define (update-game-state g) "false") ;stub

(define (update-game-state g)
  (make-game (update-invaders (spawn-invader (destroy-invaders (game-invaders g) (game-missiles g))))
             (update-missiles (filter-missiles (game-missiles g) MISSILE))
             (update-tank (game-tank g))))


;; List Image -> Image
;; takes in a list of missiles and render all of the elements in their appropriate x and y positions
;; examples/tests
(check-expect (render-missiles empty BACKGROUND) BACKGROUND)
(check-expect (render-missiles (list M1 M2) BACKGROUND)
              (place-image MISSILE
                           (missile-x M2)
                           (missile-y M2)
                           (place-image MISSILE (missile-x M1) (missile-y M1) BACKGROUND)))

#;
(define (render-missiles lom img) "false") ;stub


(define (render-missiles lom img)
  (cond [(empty? lom) img]                  
        [else (place-image MISSILE
                           (missile-x (first lom))
                           (missile-y (first lom))
                           (render-missiles (rest lom) img))]))


;; List Image -> Image
;; takes in a list of invaders and render all of the elements in their appropriate x and y positions
;; examples/tests
(check-expect (render-invaders empty BACKGROUND) BACKGROUND)
(check-expect (render-invaders (list I1 I2) BACKGROUND)
              (place-image INVADER
                           (invader-x I2)
                           (invader-y I2)
                           (place-image INVADER (invader-x I1) (invader-y I1) BACKGROUND)))

#;
(define (render-invaders loi img) "false") ;stub


(define (render-invaders loi img)
  (cond [(empty? loi) img]                  
        [else (place-image INVADER
                           (invader-x (first loi))
                           (invader-y (first loi))
                           (render-invaders (rest loi) img))]))


;; Tank -> Image
;; render a tank onto the BACKGROUND in its appropriate position
;; examples/tests
(check-expect (render-tank T0) (place-image TANK (tank-x T0) TANK-Y BACKGROUND))
(check-expect (render-tank T1) (place-image TANK (tank-x T1) TANK-Y BACKGROUND))
(check-expect (render-tank T2) (place-image TANK (tank-x T2) TANK-Y BACKGROUND))

#;
(define (render-tank t) "false") ;stub

(define (render-tank t)
  (place-image TANK (tank-x t) TANK-Y BACKGROUND))

;; Game -> Image
;; produces an image rendering of the game-state
;; examplest/tests

(check-expect (render-game G0)
              (render-invaders (game-invaders G0)
                               (render-missiles (game-missiles G0)
                                                (render-tank (game-tank G0)))))

(check-expect (render-game G2)
              (render-invaders (game-invaders G2)
                               (render-missiles (game-missiles G2)
                                                (render-tank (game-tank G2)))))
#;
(define (render-game g) "false")  ;stub

(define (render-game g)
  (render-invaders (game-invaders g)
                   (render-missiles (game-missiles g)
                                    (render-tank (game-tank g)))))



;; Tank String -> Tank
;; changes the tank dir to negative if given string is "left",
;;         if "right" changes tank dir to positive
;; 
;; examples/test
(check-expect (change-dir (make-tank 70 1)  "left")  (make-tank 70 -1))    ;right->left
(check-expect (change-dir (make-tank 20 1)  "right") (make-tank 20 1))     ;right->right
(check-expect (change-dir (make-tank 45 -1) "right") (make-tank 45 1))     ;left->right
(check-expect (change-dir (make-tank 60 -1) "left" ) (make-tank 60 -1))    ;left->left

#;
(define (change-dir tnk dir) "") ;stub

(define (change-dir tnk dir)
  (if (string=? dir "left")
      (make-tank (tank-x tnk) (- (abs (tank-dir tnk))))
      (make-tank (tank-x tnk) (abs (tank-dir tnk)))))



;; Game KeyEvent -> Game
;; changes the direction of the tank motion when the left or right arrow is pressed
;;
;; examplest/tests
(check-expect (key-handler G0 "down") G0)
(check-expect (key-handler G0 "left")
              (make-game empty empty (change-dir (game-tank G0) "left")))
(check-expect (key-handler G0 "right")
              (make-game empty empty (change-dir (game-tank G0) "right")))

#;
(define (key-handler ws ke) "") ;stub

(define (key-handler ws ke)
  (cond [(key=? ke "left")
         (make-game (game-invaders ws)
                    (game-missiles ws)
                    (change-dir (game-tank ws) "left"))]
        [(key=? ke "right")
         (make-game (game-invaders ws)
                    (game-missiles ws)
                    (change-dir (game-tank ws) "right"))]
        [(and (key=? ke " ") (< (length (game-missiles ws)) MAX-MISSILES))
         (make-game (game-invaders ws)
                    (cons (make-missile (tank-x (game-tank ws)) 
                                        (- HEIGHT (image-height TANK))) (game-missiles ws))
                    (game-tank ws))]
        [else ws]))

