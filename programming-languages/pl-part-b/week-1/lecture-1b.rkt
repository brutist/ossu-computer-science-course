#lang racket
(provide (all-defined-out))

;; lazy evaluation is deferring an expensive calculation,
;;   but when you do calculate it, you remember it so
;;   that you don't have to do it again

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


;; memoization is keeping a record of past results as a way
;;  to optimize the calculation. It is more powerful than 
;;  force and delay due to multiple results from different 
;;  arguments can be stored by a memo


;; let's take a look at a naive solution for nth fibonacci 

;; due to multiple recursions in the result of the function,
;;  the calls increases exponentially as n increases
(define (fib-slow n)
    (cond [(= n 0) 0]
          [(= n 1) 1]
          [else (+ (fib-slow (- n 1)) (fib-slow (- n 2)))]))

;; we can simplify and optimize nth fibonacci by keeping track
;;  of the elements right before and 2 places before the nth element,
;;  since the nth element is just (n-1) + (n-2), the resulting answer
;;  once we reach the end of the count will be (n-1) + (n-2)
(define (fib n)
    (local [(define (fib_0 n n1 n2)
                (cond [(= n 0) 0]
                      [(= n 1) (+ n1 n2)]
                      [else (fib_0 (- n 1) (+ n1 n2) n1)]))]
        (fib_0 n 0 1)))


;; however, there is an alternative solution that would use a memo
;;  to keep track of the answers for every calculated fib(x), so that 
;;  even if the recursion calls are still growing, there is no need
;;  to recalculate. We can just look at the memo and directly get the 
;;  answer.


;; this alternative solution is different than the lecture's. Instead of using
;;   a mutable cons pair, I implemented it using immutable cons pair To keep track,
;;   of the updated memo, we can pass it as a variable to the next function call.
(define (fib-memo n)
    (local [(define (val-in-memo k m) (cdr (assoc k m)))
            (define (add-memo-vals n1 n2 memo) (+ (val-in-memo n1 memo) (val-in-memo n2 memo)))
            (define (aux c memo)
                (cond [(> c n) (val-in-memo n memo)]
                      [(= c 0) (aux (+ c 1) (cons (cons c 0) memo))]
                      [(or (= c 1) (= c 2)) (aux (+ c 1) (cons (cons c 1) memo))]
                      [else (aux (+ c 1) (cons (cons c (add-memo-vals (- c 1) (- c 2) memo)) memo))]))]
    (aux 0 null)))