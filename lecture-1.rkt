;; this is to declare that we are using the racket language
#lang racket 
;; make functions/bindings in this file externally available
(provide (all-defined-out))

;; declarations
(define s "hello")
(define x 3) 
(define y (+ x 2))

;; function bindings
(define cube1 (lambda (x) (* x (* x x))))
(define cube2 (lambda (x) (* x x x)))
(define (cube3 x) (* x x x))
(define (pow1 x y) ; x to the yth power (y must be nonnegative)
    (if (= y 0)
        1
        (* x (pow1 x (- y 1)))))
(define pow2
    (lambda (x)
        (lambda (y)
            (pow1 x y))))


;; list processing

;; (Listof Int) -> Int
;; sum all numbers in a list
(define (sum_all xs)
    (if (null? xs)
        0
        (+ (car xs) (sum_all (cdr xs)))))

(define (appends xs ys)
    (if (null? xs)
        ys
        (cons (car xs) (appends (cdr xs) ys))))

(define (maps f xs)
    (if (null? xs)
        null
        (cons (f (car xs)) (maps f (cdr xs)))))

