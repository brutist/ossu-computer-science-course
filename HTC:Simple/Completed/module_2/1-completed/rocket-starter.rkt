;; rocket-starter.rkt
(require 2htdp/image)
; 
; PROBLEM A:
; 
; You are designing a program to track a rocket's journey as it descends 
; 100 kilometers to Earth. You are only interested in the descent from 
; 100 kilometers to touchdown. Once the rocket has landed it is done.
; 
; Design a data definition to represent the rocket's remaining descent. 
; Call it RocketDescent.
; 

;; =================
;; Data definitions:

;; RocketDescent is Number
;; interp. remaining kilometers to the ground

;; examples
(define RD1 100)     ;descent started from 100 km
(define RD2 50.5)    ;around midway
(define RD3 0.5)     ;almost touchdown


(define (fn-for-rocket-descent d)
  (... d))

;; template rules used:
;;  -atomic non-distinct: Number



; 
; PROBLEM B:
; 
; Design a function that will output the rocket's remaining descent distance 
; in a short string that can be broadcast on Twitter. 
; When the descent is over, the message should be "The rocket has landed!".
; Call your function rocket-descent-to-msg.
; 


;; =================
;; Functions:

;; RocketDescent -> String
;; produce a string in the format of "<d> km left before touchdown" with d
;;      as remaining distance. When d = 0, the message will be "The rocket
;;      has landed!"

;; examples/tests
(check-expect (rocket-descent-to-msg 10) (string-append (number->string 10 ) " km left before touchdown"))
(check-expect (rocket-descent-to-msg 0) "The rocket has landed!")

;; stub
#;
(define (rocket-descent-to-msg d) "")

;; template from RocketDescent
(define (rocket-descent-to-msg d)
  (if (> d 0)
    (string-append (number->string d ) " km left before touchdown")
    "The rocket has landed!"))

