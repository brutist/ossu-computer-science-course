; PROBLEM: design a function that consumes two lists of strings and produces true
; if the first list is a prefix of the second. Prefix means that the elements of
; the first list match the elements of the second list 1 for 1, and the second list
; is at least as long as the first.
;
; For reference, the ListOfString data definition is provided below.

;; =================
;; Data Definitions:

;; ListOfString is one of:
;; - empty
;; - (cons String ListOfString)
;; interp. a list of strings

(define LS0 empty)
(define LS1 (cons "a" empty))
(define LS2 (cons "a" (cons "b" empty)))
(define LS3 (cons "c" (cons "b" (cons "a" empty))))

#;(define (fn-for-los los)
    (cond
      [(empty? los) (...)]
      [else (... (first los) (fn-for-los (rest los)))]))

;; Functions

;; ListOfString ListOfString -> Boolean
;; produces true if lsta is a prefix of lstb, otherwise false
;; examples/tests
(check-expect (prefix=? empty empty) true)
(check-expect (prefix=? empty (list "a")) true)
(check-expect (prefix=? (list "a") empty) false)
(check-expect (prefix=? (list "a") (list "a")) true)
(check-expect (prefix=? (list "a" "b") (list "a" "b" "c")) true)
(check-expect (prefix=? (list "a" "c") (list "a" "b" "c")) false)
(check-expect (prefix=? (list "a" "b" "c" "d") (list "a" "b" "c")) false)
(check-expect (prefix=? (list "a" "b" "d" "d") (list "a" "b" "c")) false)

(define (prefix=? ls1 ls2)
  (cond
    [(empty? ls1) true]
    [(empty? ls2) false]
    [else (and (string=? (first ls1) (first ls2)) (prefix=? (rest ls1) (rest ls2)))]))
