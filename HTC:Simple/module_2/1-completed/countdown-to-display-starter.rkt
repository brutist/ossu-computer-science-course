(require 2htdp/image)
;; countdown-to-display-starter.rkt

; 
; PROBLEM:
; 
; You are asked to contribute to the design for a very simple New Year's
; Eve countdown display. You already have the data definition given below. 
; You need to design a function that consumes Countdown and produces an
; image showing the current status of the countdown. 
; 


;; Data definitions:

;; Countdown is one of:
;;  - false
;;  - Natural[1, 10]
;;  - "complete"
;; interp.
;;    false           means countdown has not yet started
;;    Natural[1, 10]  means countdown is running and how many seconds left
;;    "complete"      means countdown is over
(define CD1 false)
(define CD2 10)          ;just started running
(define CD3  1)          ;almost over
(define CD4 "complete")
#;
(define (fn-for-countdown c)
  (cond [(false? c) (...)]
        [(and (number? c) (<= 1 c) (<= c 10)) (... c)]
        [else (...)]))

;; Template rules used:
;;  - one of: 3 cases
;;  - atomic distinct: false
;;  - atomic non-distinct: Natural[1, 10]
;;  - atomic distinct: "complete"

;; Functions:


;; Countdown -> Image
;; produce an image of the current state of the countdown
;; examples/test
(check-expect (display-cd 1) (text (number->string 1) 10 "black"))
(check-expect (display-cd 5) (text (number->string 5) 10 "black"))
(check-expect (display-cd 10) (text (number->string 10) 10 "black"))
(check-expect (display-cd #false) (text "Countdown has not started yet" 10 "black"))
(check-expect (display-cd "complete") (text "Happy New Year" 10 "red"))


; stub
#;(define (display-cd c) (square 0 "outline" "black"))

;; template from Countdown

(define (display-cd c)
  (cond [(false? c) (text "Countdown has not started yet" 10 "black")]
        [(number? c) (text (number->string c) 10 "black")]
        [else (text "Happy New Year" 10 "red")]))

