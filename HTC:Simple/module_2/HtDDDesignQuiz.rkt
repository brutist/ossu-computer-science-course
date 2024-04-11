;; HtDD Design Quiz

;; Age is Natural
;; interp. the age of a person in years
(define A0 18)
(define A1 25)

#;
(define (fn-for-age a)
  (... a))

;; Template rules used:
;; - atomic non-distinct: Natural


; Problem 1:
; 
; Consider the above data definition for the age of a person.
; 
; Design a function called teenager? that determines whether a person
; of a particular age is a teenager (i.e., between the ages of 13 and 19).


;; Age -> Boolean
;; produce true if given Age is a teenager [13, 19] years old
;; examples/tests
(check-expect (teenager? 13) #true)
(check-expect (teenager? 19) #true)
(check-expect (teenager? 10) #false)
(check-expect (teenager? 20) #false)

;; stub
#;
(define (teenager? a) #true)

;; template used from Age
(define (teenager? a)
  (and (>= a 13) (<= a 19)))


; Problem 2:
; 
; Design a data definition called MonthAge to represent a person's age
; in months.


;; MonthAge is Natural (Age is Natural)
;; interp. represents the age in months

;; examples
(define MA1 10)
(define MA2 90)

(define (fn-for-month-age a)
  (... a))

;; template used:
;;   - atomic non-distinct - Natural


; Problem 3:
; 
; Design a function called months-old that takes a person's age in years 
; and yields that person's age in months.
; 


;; Age -> Natural (Since Age is a Natural, months-old will also be Natural)
;; converts given age in years to months
;; examples/tests
(check-expect (months-old 5) 60)
(check-expect (months-old 12) 144)

;; stub
#;
(define (months-old a) 0)

;; template used from Age
(define MONTHS-IN-YEAR 12)
(define (months-old a)
  (* a MONTHS-IN-YEAR))

  
; Problem 4:
; 
; Consider a video game where you need to represent the health of your
; character. The only thing that matters about their health is:
; 
;   - if they are dead (which is shockingly poor health)
;   - if they are alive then they can have 0 or more extra lives
; 
; Design a data definition called Health to represent the health of your
; character.
; 
; Design a function called increase-health that allows you to increase the
; lives of a character.  The function should only increase the lives
; of the character if the character is not dead, otherwise the character
; remains dead.



;; ==== Data Definition ===

;; Health is one of:
;;   - false    (player is dead)
;;   - [0, 10]  (player is alive, extra lives are capped to TEN)

;; interp. number of extra lives of a player

;; examples
(define H1 #false)
(define H2 10)      ;full extra lives
(define H3 0)       ;low extra lives

#;
(define (fn-for-health h)
  (cond [(false? h) (...)]
        [else (...)]))

;; template rules used:
;;   - one of: 2 cases
;;   - atomic distinct: false
;;   - atomic non-distinct: Interval [0, 10]


;; === Functions ===

;; Health -> Health
;; increases the health of player if alive (not false), otherwise no effect

;; examples/tests
(check-expect (increase-health 4) (+ 4 1))
(check-expect (increase-health 0) (+ 0 1))
(check-expect (increase-health #false) #false)

;; stub
#;
(define (increase-health h) 0)

;; template from Health
(define INCREMENT 1)
(define (increase-health h)
  (cond [(false? h) #false]
        [else (+ h INCREMENT)]))







