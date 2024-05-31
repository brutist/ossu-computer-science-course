(require 2htdp/image)
(require 2htdp/universe)

;; countdown-animation starter.rkt

; 
; PROBLEM:
; 
; Design an animation of a simple countdown. 
; 
; Your program should display a simple countdown, that starts at ten, and
; decreases by one each clock tick until it reaches zero, and stays there.
; 
; To make your countdown progress at a reasonable speed, you can use the 
; rate option to on-tick. If you say, for example, 
; (on-tick advance-countdown 1) then big-bang will wait 1 second between 
; calls to advance-countdown.
; 
; Remember to follow the HtDW recipe! Be sure to do a proper domain 
; analysis before starting to work on the code file.
; 
; Once you are finished the simple version of the program, you can improve
; it by reseting the countdown to ten when you press the spacebar.
; 


;; === Constants ===

;; UI dimensions
(define WIDTH 100)
(define HEIGHT 100)

;; x,y center of UI
(define CTR-X (/ WIDTH 2))
(define CTR-Y (/ HEIGHT 2))

;; empty scene
(define MTS (rectangle WIDTH HEIGHT "outline" "white"))


;; === Data Definition ===

;; Countdown is Natural
;; interp. a simple countdown from 10 to 0

(define FONT-SIZE 24)
(define COLOR "black")

;; example
(define CD1 (text (number->string 10) FONT-SIZE COLOR))   ;start of countdown
(define CD2 (text (number->string 5) FONT-SIZE COLOR))    ;around midway
(define CD3 (text (number->string 0) FONT-SIZE COLOR))    ;end of countdown


#;
(define (fn-for-countdown s)
  (... s))

;; template rules used:
;;   - atomic non-distinct: Natural


;; === Functions ===

;; Countdown -> Countdown
;; start the world with (main 10)
;; 
(define (main s)
  (big-bang s                                ; Countdown
            (on-tick  countdown-move 1)      ; Countdown -> Countdown
            (to-draw  countdown-render)))    ; Countdown -> Image
           

;; Countdown->Countdown
;; produce a natural that is less than 1 of the given. if the given is zero, produces zero
;; examples/tests
(check-expect (countdown-move 10) 9)
(check-expect (countdown-move 0) 0)

;; stub
#;
(define (countdown-move s) 0)

;; <template from Countdown>
(define END 0)
(define (countdown-move s)
  (if (> s END)
      (- s 1)
      END))


;; test image of number 10
(define test-img (text (number->string 10) FONT-SIZE COLOR))

;; Countdown->Image
;; produces an image of the given Countdown
;; examples/tests
(check-expect (countdown-render 10) (place-image (text (number->string 10) FONT-SIZE COLOR) CTR-X CTR-Y MTS))

;; stub
#;
(define (countdown-img s) (text (number->string 10) FONT-SIZE COLOR))

;; <template from Countdown>
(define (countdown-render s)
  (place-image (text (number->string s) FONT-SIZE COLOR) CTR-X CTR-Y MTS))


