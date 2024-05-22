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


;; parenthesis matter

(define (fact n)
    (if (= n 0)
    1
    (* n (fact (- n 1)))))

;; apparently, using () on a number (1) works now??
(define (fact1 n) 
    (if (= n 0) 
        (1) 
        (* n (fact (- n 1)))))

;; be cautious of missing and misplaced parenthesis as it
;;  will give you hard to understand error messages

(define (fib n)
    (if (or (= n 1) (= n 2))
        1
        (+ (fib (- n 1)) (fib (- n 2)))))

(define (fibtr n)
    (local [(define (fibtr_0 n n1 n2)
                (cond [(= n 0) 0]
                      [(= n 1) (+ n1 n2)]  
                      [else (fibtr_0 (- n 1) (+ n1 n2) n1)]))]
        (fibtr_0 n 0 1)))

;; suppose you want to sum a list of (numbers and listof Numbers)
(define xs (list 4 5))
(define ys (list (list 4 5 (list 2 2)) 4 5 (list 8 8 10) 10 20 (list 1 2 (list 4 4 0 10))))
(define zs (list (list 4 5 "hello" (list 2 2)) 4 5 (list 8 8 10) 10 20 (list 1 2 (list 4 4 0 10))))

(define (sum1 xs)
    (if (null? xs)
        0
        (if (number? (car xs))
            (+ (car xs) (sum1 (cdr xs)))
            (+ (sum1 (car xs)) (sum1 (cdr xs))))))

(define (sum2 xs)
    (cond [(null? xs) 0]
          [(number? (car xs)) (+ (car xs) (sum2 (cdr xs)))]
          [(list? (car xs)) (+ (sum2 (car xs)) (sum2 (cdr xs)))]
          [else (sum2 (cdr xs))]))