#lang racket
(provide (all-defined-out))

;; lazy evaluation is deferring an expensive calculation,
;;   but when you do calculate it, you remember it so
;;   that you don't have to do it again
(define (fib n)
    (local [(define (fib_0 n n1 n2)
                (cond [(= n 0) 0]
                      [(= n 1) (+ n1 n2)]
                      [else (fib_0 (- n 1) (+ n1 n2) n1)]))]
        (fib_0 n 0 1)))

(define (fib-slow n)
    (cond [(= n 0) 0]
          [(= n 1) 1]
          [else (+ (fib-slow (- n 1)) (fib-slow (- n 2)))]))


(define (mults x y-thunk)
    (cond [(= x 0) 0]
          [(= x 1) (y-thunk)]
          [else (+ (y-thunk) (mults (- x 1) y-thunk))]))


;; The idea of force and delay is to use some mutable pair
;;  of bool and the thunk function to represent the value 
;;  of the expensive calculation. When it is really necessary
;;  to calculate the thunk (delayed function) then you can use
;;  force to set the bool in the mutable pair to #t (true) and 
;;  evaluate the thunk e by (e). If there is a suceeding need 
;;  for the thunk value, then you don't have to redo calculation
;;  and can just look up the value in the mutated pair

(define (my-delay func)
    (cons #f func))

(define (my-force mth)
    (if (mcar mth)
        (mcdr mth)
        (begin (set-mcar! mth #t)
               (set-mcdr! mth ((mcdr mth)))
               (mcdr mth))))

;; streams are "infinitely" sized sequence of values
;;  - cannot make a stream by evaluating all values (infinite loop)
;;  - use thunk to delay creating most of the sequence
;;  - only create the part of stream that you need

;; the idea is to represent a stream so you don't have to evaluate
;;  and infinitely long list. This can be done by using pairs and thunks

;; Let a stream be a thunk that when called returns a pair:
;;              (next-answer . next-thunk)

(define (number-until stream tester)
    (local [(define (aux s rsf) (local [(define pr (s))]
                (if (tester (car pr))
                    rsf
                    (aux (cdr pr) (+ rsf 1)))))]
        (aux stream 1)))


;; example of a stream
;; this one produce an infinite series of 1
(define ones (lambda () (cons 1 ones)))

;; produces a stream of natural numbers starting at x
(define nats
    (local [(define (f x) (cons x (lambda () (f (+ x 1)))))]
        (lambda () (f 1))))

;; produces a stream of every element ith is i^2
(define powers-of-two
    (local [(define (f x) (cons x (lambda () (f (* x 2)))))]
        (lambda () (f 2))))

;; abstract function for making simple streams of ints
(define (stream-maker fn arg)
    (local [(define (f x) (cons x (lambda () (f (fn x arg)))))]
        (lambda () (f arg))))

;; implement nats and powers-of-two using abstract function
(define nats2 (stream-maker + 1))
(define powers-of-two2 (stream-maker * 2)) 
