;; student-starter.rkt

;; =================
;; Data definitions:

; 
; PROBLEM A:
; 
; Design a data definition to help a teacher organize their next field trip. 
; On the trip, lunch must be provided for all students. For each student, track 
; their name, their grade (from 1 to 12), and whether or not they have allergies.
; 


(define-struct student (nm grd allg?))
;; Student is (make-student String Natural Boolean)
;; interp. details of a particular student
;;         nm as name
;;         grd as grade level
;;         allg? is whether or not the student have allergies

;; examples
(define ST1 (make-student "John Snow" 4 #false))
(define ST2 (make-student "Kat Wood" 6 #true))
(define ST3 (make-student "Henry Wood" 2 #true))
(define ST4 (make-student "Jenn Spirit" 12 #true))

(define (fn-for-student s)
  (... (student-nm s)       ;String
       (student-grd s)      ;Natural
       (student-allg? s)))  ;Boolean

;; template rules used:
;;   - compound: 3 fields


;; =================
;; Functions:

; 
; PROBLEM B:
; 
; To plan for the field trip, if students are in grade 6 or below, the teacher 
; is responsible for keeping track of their allergies. If a student has allergies, 
; and is in a qualifying grade, their name should be added to a special list. 
; Design a function to produce true if a student name should be added to this list.
; 



;; === CONSTANTS ===
(define MAX-GRD 6)

;; Student->Boolean
;; produce true if the given student has allergies, otherwise false
;; examples/tests
(check-expect (toList? ST1) #false)
(check-expect (toList? ST2) #true)
(check-expect (toList? ST3) #true)
(check-expect (toList? ST4) #false)

;; stub
#;
(define (toList? ST1) #true)

;; <template from Student>
(define (toList? s)
  (and (student-allg? s) (<= (student-grd s) MAX-GRD )))