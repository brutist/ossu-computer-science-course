;; letter-grade-starter.rkt

; 
; PROBLEM:
; 
; As part of designing a system to keep track of student grades, you
; are asked to design a data definition to represent the letter grade 
; in a course, which is one of A, B or C.
;   


;; type comment -- letterGrade is String and one of ("A", "B", "C")            
;; interp. letter grade of students

;; <examples are redundant in enumerations>

(define (fn-for-letterGrade g)
  (cond [(string=? g "A") (...)]
        [(string=? g "B") (...)]
        [(string=? g "C") (...)]))

;; template rules used:
;; - one of:3 cases
;; - atomic distinct: "A"
;; - atomic distinct: "B"
;; - atomic distinct: "C"
