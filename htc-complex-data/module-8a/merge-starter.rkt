;; merge-starter.rkt

; Problem:
;
; Design the function merge. It consumes two lists of numbers, which it assumes are
; each sorted in ascending order. It produces a single list of all the numbers,
; also sorted in ascending order.
;
; Your solution should explicitly show the cross product of type comments table,
; filled in with the values in each case. Your final function should have a cond
; with 3 cases. You can do this simplification using the cross product table by
; recognizing that there are subtly equal answers.
;
; Hint: Think carefully about the values of both lists. You might see a way to
; change a cell content so that 2 cells have the same value.
;

;; Function

;; ListOfNumbers ListOfNumbers -> ListOfNumbers
;; combine two sorted lists of number into one list sorted in ascending order
;; examples/tests
(check-expect (merge-lon empty empty) empty)
(check-expect (merge-lon empty (list 1)) (list 1))
(check-expect (merge-lon (list 2) empty) (list 2))
(check-expect (merge-lon (list 1) (list 2)) (list 1 2))
(check-expect (merge-lon (list 1 2 4) (list 6 9)) (list 1 2 4 6 9))
(check-expect (merge-lon (list 1 2 4) (list 2 4 5)) (list 1 2 2 4 4 5))
(check-expect (merge-lon (list 1 6 8) (list 2 4 9)) (list 1 2 4 6 8 9))

;(define (merge-lon lon1 lon2) "false") ;stub

(define (merge-lon lon1 lon2)
  (cond
    [(empty? lon1) lon2]
    [(empty? lon2) lon1]
    [else (sort (append (list (first lon1) (first lon2)) 
                        (merge-lon (rest lon1) (rest lon2))) <)]))
