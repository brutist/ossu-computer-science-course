;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-abbr-reader.ss" "lang")((modname |space-invaders-starter (copy)|) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
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

(define M1 (make-missile 150 300))                       ;not hit U1
(define M2 (make-missile (invader-x I1) (+ (invader-y I1) 10)))  ;exactly hit U1
(define M3 (make-missile (invader-x I1) (+ (invader-y I1)  5)))  ;> hit U1

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
#;
(define (main ws)
  (big-bang ws                              ; Game
    (on-tick   next-game-state)     ; Game -> Game
    (to-draw   render-game)         ; Game -> Image
    (on-key    key-handler)))       ; Game KeyEvent -> WS


;; List -> List
;; takes in a list of invaders and update its positions accordingly
;;     - increase invader-x by INVADER-X-SPEED
;;     - increase invader-y by INVADER-Y-SPEED
;; examples/tests
(check-expect (update-invaders empty) empty)
(check-expect (update-invaders (list I1))
              (cons (make-invader (+ 150 INVADER-X-SPEED) (+ 100 INVADER-Y-SPEED) 12)
                    empty))
(check-expect (update-invaders (list I1 I2))
              (cons (make-invader (+ 150 INVADER-X-SPEED) (+ 100 INVADER-Y-SPEED) 12)
                    (cons (make-invader (+ 150 INVADER-X-SPEED) (+ HEIGHT INVADER-Y-SPEED) -10)
                          empty)))

#;(define (update-invaders loi) "false") ;stub

(define (update-invaders loi)
  (cond [(empty? loi) empty]                  
        [else (cons (make-invader (+ (invader-x (first loi)) INVADER-X-SPEED)
                                  (+ (invader-y (first loi)) INVADER-Y-SPEED)
                                  (invader-dx (first loi)))
                                  (update-invaders (rest loi)))]))

;; relevant info
;; (define-struct game ((list-of-invaders) (list-of-missiles) tank))
;; (define-struct invader (x y dx))
;; (define-struct missile (x y))
;; (define-struct tank (x dir))



;; Game -> Game
;; update the missiles and invaders position
;; examples/tests
#;
(check-expect (move-miss-inv G0) (make-game empty empty T0))
#;
(check-expect (move-miss-inv G2)
              (make-game (update-invaders (list I1))    (update-missiles (list M1)) T1))
#;
(check-expect (move-miss-inv G3)
              (make-game (update-invaders (list I1 I2)) (update-missiles (list M1 M2)) T1))

#;
(define (move-miss-inv g) "false") ;stub








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
  (cond [(empty? lom) BACKGROUND]                  
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
  (cond [(empty? loi) BACKGROUND]                  
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

(check-expect (render-game G0) (render-invaders (game-invaders G0)
                                                (render-missiles (game-missiles G0)
                                                                 (place-image TANK (tank-x (game-tank G0)) TANK-Y BACKGROUND))))

(check-expect (render-game G2) (render-invaders (game-invaders G2)
                                                (render-missiles (game-missiles G2)
                                                                 (place-image TANK (tank-x (game-tank G2)) TANK-Y BACKGROUND))))
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
        [else ws]))









































