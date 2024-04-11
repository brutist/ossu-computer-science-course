;; bump-up-starter.rkt

; 
; PROBLEM:
; 
; Using the LetterGrade data definition below design a function that
; consumes a letter grade and produces the next highest letter grade. 
; Call your function bump-up.
; 



;; Data definitions:

;; LetterGrade is one of: 
;;  - "A"
;;  - "B"
;;  - "C"
;; interp. the letter grade in a course
;; <examples are redundant for enumerations>
#;
(define (fn-for-letter-grade lg)
  (cond [(string=? lg "A") (...)]
        [(string=? lg "B") (...)]
        [(string=? lg "C") (...)]))

;; Template rules used:
;;  one-of: 3 cases
;;  atomic distinct: "A"
;;  atomic distinct: "B"
;;  atomic distinct: "C"


;; Functions:

;; LetterGrade -> LetterGrade
;; produce the next highest letter grade from the given letter grade (no change for A)

;; examples/tests
(check-expect (bump-up "A") "A")
(check-expect (bump-up "B") "A")
(check-expect (bump-up "C") "B")

;; stub
#;
(define (bump-up lg) "")

;; template from LetterGrade
#;
(define (fn-for-letter-grade lg)
  (cond [(string=? l "A") (...)]
        [(string=? lg "B") (...)]
        [(string=? lg "C") (...)]))

(define (bump-up lg)
  (cond [(string=? lg "C") "B"]
        [(string=? lg "B") "A"]
        [(string=? lg "A") "A"]))

