;; Data definitions:

(define-struct person (name age children))
;; Person is (make-elt String Integer ListOfElement)
;; interp. A person with name age and a list of person

#;(define (fn-for--person p)
    (... (person-name p) (person-age p) (fn-for--lop (person-children p))))
#;(define (fn-for-lop lop)
    (cond
      [(empty? lop) (...)]
      [else (... (names-under-20--person (first lop)) (names-under-20--lop (rest lop)))]))

(define P1 (make-person "N1" 5 empty))
(define P2 (make-person "N2" 25 (list P1)))
(define P3 (make-person "N3" 15 empty))
(define P4 (make-person "N4" 45 (list P2 P3)))

;; Person -> ListOfString
;; ListOfPerson -> ListOfString
;; produce a list of the names of the persons under 20

(check-expect (names-under-20 P1) (list "N1"))
(check-expect (names-under-20 P2) (list "N1"))
(check-expect (names-under-20 P4) (list "N1" "N3"))

(define (names-under-20 p)
  (local [(define (names-under-20--person p)
            (if (< (person-age p) 20)
                (cons (person-name p) (names-under-20--lop (person-children p)))
                (names-under-20--lop (person-children p))))
          (define (names-under-20--lop lop)
            (cond
              [(empty? lop) empty]
              [else (append (names-under-20--person (first lop)) (names-under-20--lop (rest lop)))]))]
         (names-under-20--person p)))