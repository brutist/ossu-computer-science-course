;; best-starter.rkt

; 
; PROBLEM:
; 
; Using the CityName data definition below design a function
; that produces true if the given city is the best in the world. 
; (You are free to decide for yourself which is the best city 
; in the world.)
; 


;; Data definitions:


;; CityName is String
;; interp. the name of a city
(define CN1 "Boston")
(define CN2 "Vancouver")
#;
(define (fn-for-city-name cn)
  (... cn))

;; Template rules used:              For the first part of the course
;;   atomic non-distinct: String     we want you to list the template
;;                                   rules used after each template.
;;

;; Functions:

;; CityName -> Boolean
;; produce true if the given city is the best ("Mandaue)
;; examples/tests
(check-expect (best? "Vancouver") #false)
(check-expect (best? "Mandaue") #true)

;; stub
#;
(define (best? "Jacob") #false)

;; template from CityName

(define (best? cn)
  (string=? cn "Mandaue"))
