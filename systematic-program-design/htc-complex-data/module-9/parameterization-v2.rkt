;; parameterization-starter.rkt

(define (area r)
      (* pi (sqr r)))

(area 4)    ;(* pi (sqr 4)) ;area of circle radius 4
(area 6)    ;(* pi (sqr 6)) ;area of circle radius 6


;; ====================

;; (listof String) String -> Boolean
;; produce true if los includes team-nm
(check-expect (contains-team-nm? empty "UBC") false)
(check-expect (contains-team-nm? (cons "UBC" empty) "UBC") true)
(check-expect (contains-team-nm? (cons "UBC" empty) "McGill") false)
(check-expect (contains-team-nm? (cons "McGill" empty) "McGill") true)
(check-expect (contains-team-nm? (cons "McGill" (cons "UBC" empty)) "UBC") true)
(check-expect (contains-team-nm? (cons "UBC" (cons "McGill" empty)) "Toronto") false)

(define (contains-team-nm? los team-nm)
  (cond [(empty? los) false]
        [else
         (if (string=? (first los) team-nm)
             true
             (contains-team-nm? (rest los) team-nm))]))


;; ====================

;; (X -> Y) (listof X) -> (listof Y)
;; given fn and (list n0 n1 ...) produce (list (fn n0) (fn n1) ...)
;; examples/tests
(check-expect (map2 sqr empty) empty)
(check-expect (map2 sqrt (list 9 16)) (list 3 4))
(check-expect (map2 sqr (list 3 4)) (list 9 16))
(check-expect (map2 string-length (list "" "hey")) (list 0 3))

(define (map2 fn lon)
  (cond [(empty? lon) empty]
        [else
         (cons (fn (first lon))
               (map2 fn (rest lon)))]))


;; ====================

;; (X -> Boolean) (listof X) -> (listof X)
;; given expr and (list n0 n1 ...) produce a list with elements that satisfy the expr
(check-expect (filter2 negative? empty) empty)
(check-expect (filter2 positive? (list 1 -2 3 -4)) (list 1 3))
(check-expect (filter2 negative? (list 1 -2 3 -4)) (list -2 -4))


(define (filter2 expr lon)
  (cond [(empty? lon) empty]
        [else
         (if (expr (first lon))
             (cons (first lon)
                   (filter2 expr (rest lon)))
             (filter2 expr (rest lon)))]))