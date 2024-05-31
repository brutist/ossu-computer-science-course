;; parameterization-starter.rkt

(define (area r)
      (* pi (sqr r)))

(area 4)    ;(* pi (sqr 4)) ;area of circle radius 4
(area 6)    ;(* pi (sqr 6)) ;area of circle radius 6


;; ====================

;; ListOfString -> Boolean
;; produce true if los includes "UBC"
(check-expect (contains-team-nm? empty "UBC") false)
(check-expect (contains-team-nm? (cons "McGill" empty) "UBC") false)
(check-expect (contains-team-nm? (cons "UBC" empty) "UBC") true)
(check-expect (contains-team-nm? (cons "McGill" (cons "UBC" empty)) "UBC") true)

;(define (contains-ubc? los) false) ;stub

;<template from ListOfString>

(define (contains-ubc? los)
  (cond [(empty? los) false]
        [else
         (if (string=? (first los) "UBC")
             true
             (contains-ubc? (rest los)))]))

;; ListOfString -> Boolean
;; produce true if los includes "McGill"
(check-expect (contains-team-nm? empty "McGill") false)
(check-expect (contains-team-nm? (cons "UBC" empty) "McGill") false)
(check-expect (contains-team-nm? (cons "McGill" empty) "McGill") true)
(check-expect (contains-team-nm? (cons "UBC" (cons "McGill" empty)) "McGill") true)

;(define (contains-mcgill? los) false) ;stub

;<template from ListOfString>

(define (contains-mcgill? los)
  (cond [(empty? los) false]
        [else
         (if (string=? (first los) "McGill")
             true
             (contains-mcgill? (rest los)))]))


(define (contains-team-nm? los team-nm)
  (cond [(empty? los) false]
        [else
         (if (string=? (first los) team-nm)
             true
             (contains-team-nm? (rest los) team-nm))]))


;; ====================

;; ListOfNumber -> ListOfNumber
;; produce list of sqr of every number in lon
(check-expect (squares empty) empty)
(check-expect (squares (list 3 4)) (list 9 16))

;(define (squares lon) empty) ;stub

;<template from ListOfNumber>

(define (squares lon) (map2 sqr lon))

;; ListOfNumber -> ListOfNumber
;; produce list of sqrt of every number in lon
(check-expect (square-roots empty) empty)
(check-expect (square-roots (list 9 16)) (list 3 4))

;(define (square-roots lon) empty) ;stub

;<template from ListOfNumber>

(define (square-roots lon) (map2 sqrt lon))

(define (map2 fn lon)
  (cond [(empty? lon) empty]
        [else
         (cons (fn (first lon))
               (map2 fn (rest lon)))]))


;; ====================

;; ListOfNumber -> ListOfNumber
;; produce list with only positive? elements of lon
(check-expect (positive-only empty) empty)
(check-expect (positive-only (list 1 -2 3 -4)) (list 1 3))

;(define (positive-only lon) empty) ;stub

;<template from ListOfNumber>

(define (positive-only lon) (filter2 positive? lon))


;; ListOfNumber -> ListOfNumber
;; produce list with only negative? elements of lon
(check-expect (negative-only empty) empty)
(check-expect (negative-only (list 1 -2 3 -4)) (list -2 -4))

;(define (negative-only lon) empty) ;stub

;<template from ListOfNumber>

(define (negative-only lon) (filter2 negative? lon))

(define (filter2 expr lon)
  (cond [(empty? lon) empty]
        [else
         (if (expr (first lon))
             (cons (first lon)
                   (filter2 expr (rest lon)))
             (filter2 expr (rest lon)))]))
