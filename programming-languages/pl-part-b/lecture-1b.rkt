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

(define promise (delay (lambda () (fib-slow 39))))