#lang racket


(require "lecture-2.rkt")
(require rackunit)

(define tests
  (test-suite
   "Sample tests for Lecture 2"

   (check-equal? (eval-exp (add (const 1) (const 10))) (const 11) "eval-exp test a")
   (check-equal? (eval-exp (negate (const 10))) (const -10) "eval-exp test b")
   (check-equal? (eval-exp (const 1)) (const 1) "eval-exp test c")
   (check-equal? (eval-exp (multiply (const 2) (const 5))) (const 10) "eval-exp test c")
  
 
   ))

(require rackunit/text-ui)
;; runs the test
(run-tests tests)
