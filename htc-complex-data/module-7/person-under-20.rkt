; We would like to design a function that consumes a person and produces a list of the names of
; all the people in the tree under 20 ("in the tree" includes the original person).

;; Data Definition

(define-struct person (name age subs))
;; Person is (make-person String Number ListOfPerson)

;; ListOfPerson is one of:
;;   - empty
;;   - (cons Person ListOfPerson)

#;
(define (fn-for-person p)
  (... (person-name p)
       (person-age p)
       (fn-for-lop (person-subs))))
#;
(define (fn-for-lop lop)
  (cond [(empty? lop) (...)]
        [else 
         (... (fn-for-person (first lop))
              (fn-for-lop (rest lop)))]))


;; Constants

(define KEVIN   (make-person "Kevin"   10 empty))
(define HANNAH  (make-person "Hannah"   4 empty))
(define JOHN    (make-person "John"     3 empty))
(define SHANE   (make-person "Shane"   15 empty))
(define CHERRY  (make-person "Cherry"  10 empty))
(define KENNETH (make-person "Kenneth"  2 empty))
(define CHIN    (make-person "Chin"    14 empty))

(define SIMON   (make-person "Simon"   25 (list KEVIN HANNAH)))
(define JOHAN   (make-person "Johan"   45 (list JOHN SHANE)))
(define BARBARA (make-person "Barbara" 19 (list CHERRY KENNETH)))

(define JAY     (make-person "Jay"     66 (list SIMON JOHAN BARBARA CHIN)))
(define RAYN    (make-person "Ray"      7 (list JAY)))

(define MIN-AGE 20)

;; Functions

;; Person       -> List
;; ListOfPerson -> List
;; produce a list of names of all the people under age 20
;; examples/tests
(check-expect (under-20--lop empty) empty)
(check-expect (under-20--person KEVIN) (list (person-name KEVIN)))  ;already there
(check-expect (under-20--person SIMON) (list (person-name KEVIN)    ;found 1 node deep
                                             (person-name HANNAH)))
(check-expect (under-20--person JAY)   (list (person-name KEVIN)    ;found 2 node deep
                                             (person-name HANNAH)
                                             (person-name JOHN)
                                             (person-name SHANE)
                                             (person-name BARBARA)
                                             (person-name CHERRY)
                                             (person-name KENNETH)
                                             (person-name CHIN)))
(check-expect (under-20--person RAYN)  (list (person-name RAYN)     ;found at 1 then 
                                             (person-name KEVIN)      ;continue until end
                                             (person-name HANNAH)
                                             (person-name JOHN)
                                             (person-name SHANE)
                                             (person-name BARBARA)
                                             (person-name CHERRY)
                                             (person-name KENNETH)
                                             (person-name CHIN)))

;(define (under-20--lop lop) "false")   ;stub
;(define (under-20--person p) "false")  ;stub


(define (under-20--person p)
  (if (> MIN-AGE (person-age p))
      (cons (person-name p) (under-20--lop (person-subs p)))
      (under-20--lop (person-subs p))))

(define (under-20--lop lop)
  (cond [(empty? lop) empty]
        [else (if (> MIN-AGE (person-age (first lop))) 
                  (append (under-20--person (first lop)) (under-20--lop (rest lop)))
                  (append (under-20--lop (person-subs (first lop))) (under-20--lop (rest lop))))]))