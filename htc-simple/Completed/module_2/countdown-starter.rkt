;; countdown-starter.rkt

; 
; PROBLEM:
; 
; Consider designing the system for controlling a New Year's Eve
; display. Design a data definition to represent the current state 
; of the countdown, which falls into one of three categories: 
; 
;  - not yet started
;  - from 10 to 1 seconds before midnight
;  - complete (Happy New Year!)
; 


;; countdown is one of:
;;    - false
;;    - [1,10] - Natural
;;    - true

;; interp.
;;     - false    means countdown has not started
;;     - [1, 10]  means countdown is running and how many seconds left
;;     - "done"   means countdown has ended

(define CN1 #false)
(define CN3 5)      ;midway
(define CN2 1)      ;almost over
(define CN4 "done")

;;
(define (fn-for-countdown cn)
  (cond [(false? cn) (...)]
        [(and (number? cn) ( <= 1 cn) (<= cn 10)) (... cn)]
        [else (...)]))

;; Template rules used:
;;    - one of: 3 cases
;;    - atomic distinct: false
;;    - atomic non-distinct (interval): [1,10]
;;    - atomic distinct: "done"