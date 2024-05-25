#lang racket
;; Programming Languages Homework4 Simple Test
;; Save this file to the same directory as your homework file
;; These are basic tests. Passing these tests does not guarantee that your code will pass the actual 
;;  homework grader

;; Be sure to put your homework file in the same folder as this test file.
;; Uncomment the line below and change HOMEWORK_FILE to the name of your homework file.
;;(require "HOMEWORK_FILE")

(require "hw4.rkt")
(require rackunit)

;; Helper functions
(define ones (lambda () (cons 1 ones)))
(define powers-of-two
  (local [(define (f x) (cons x (lambda () (f (* x 2)))))]
    (lambda () (f 2))))
(define a 2)

(define tests
  (test-suite
   "Sample tests for Assignment 4"
   
   ; sequence test
   (check-equal? (sequence 0 5 1) (list 0 1 2 3 4 5) "Sequence test a")
   (check-equal? (sequence 3 11 2) (list 3 5 7 9 11) "Sequence test b")
   (check-equal? (sequence 3 8 3) (list 3 6) "Sequence test c")
   (check-equal? (sequence 3 2 1) (list) "Sequence test d")

   ; string-append-map test
   (check-equal? (string-append-map 
                  (list "dan" "dog" "curry" "dog2") 
                  ".jpg") '("dan.jpg" "dog.jpg" "curry.jpg" "dog2.jpg") "string-append-map test a")
   (check-equal? (string-append-map 
                  (list "dan" "dog" "curry" "dog2") 
                  ".rkt") '("dan.rkt" "dog.rkt" "curry.rkt" "dog2.rkt") "string-append-map test b")

   ; list-nth-mod test
   (check-equal? (list-nth-mod (list 0 1 2 3 4) 2) 2 "list-nth-mod test a")
   (check-equal? (list-nth-mod (list 0 1 2 8 4 6 8) 3) 8 "list-nth-mod test b")
   (check-exn exn:fail? (lambda () (list-nth-mod (list) 3)) "list-nth-mod: empty list")
   (check-exn exn:fail? (lambda () (list-nth-mod (list 1 2 2) -3)) "list-nth-mod: negative number")

   ; stream-for-n-steps test
   (check-equal? (stream-for-n-steps ones 2) (list 1 1) "stream-for-n-steps test a")
   (check-equal? (stream-for-n-steps powers-of-two 3) (list 2 4 8) "stream-for-n-steps test b")
   (check-equal? (stream-for-n-steps powers-of-two 6) (list 2 4 8 16 32 64) 
                 "stream-for-n-steps test b")

   ; funny-number-stream test
   (check-equal? (stream-for-n-steps funny-number-stream 16) 
                 (list 1 2 3 4 -5 6 7 8 9 -10 11 12 13 14 -15 16) "funny-number-stream test a")
   (check-equal? (stream-for-n-steps funny-number-stream 20)
                 (list 1 2 3 4 -5 6 7 8 9 -10 11 12 13 14 -15 16 17 18 19 -20)  
                 "funny-number-stream test a")

   ; dan-then-dog test
   (check-equal? (stream-for-n-steps dan-then-dog 1) (list "dan.jpg") "dan-then-dog test a")
   (check-equal? (stream-for-n-steps dan-then-dog 5) 
                 (list "dan.jpg" "dog.jpg" "dan.jpg" "dog.jpg" "dan.jpg") "dan-then-dog test b")

   ; stream-add-zero test
   (check-equal? (stream-for-n-steps (stream-add-zero ones) 3) 
                 (list (cons 0 1) (cons 0 1) (cons 0 1)) "stream-add-zero test a")
   (check-equal? (stream-for-n-steps (stream-add-zero powers-of-two) 3) 
                 (list (cons 0 2) (cons 0 4) (cons 0 8)) "stream-add-zero test a")
   (check-equal? (stream-for-n-steps (stream-add-zero dan-then-dog) 2) 
                 (list (cons 0 "dan.jpg") (cons 0 "dog.jpg")) "dan-then-dog test c")

   ; cycle-lists test
   (check-equal? (stream-for-n-steps (cycle-lists (list 1 2 3) (list "a" "b")) 3) 
                 (list (cons 1 "a") (cons 2 "b") (cons 3 "a")) 
                 "cycle-lists test a")
   (check-equal? (stream-for-n-steps (cycle-lists (list "a") (list "a" "b")) 3) 
                 (list (cons "a" "a") (cons "a" "b") (cons "a" "a")) 
                 "cycle-lists test b")

   ; vector-assoc test
   (check-equal? (vector-assoc 4 (vector "June" (cons 4 1) (cons 5 1))) (cons 4 1) "vector-assoc test a")
   (check-equal? (vector-assoc 4 (vector (list 2 3) (cons 4 1) (cons 5 1))) (cons 4 1) "vector-assoc test b")
   (check-equal? (vector-assoc 4 (vector (cons 2 1) (cons 3 1) (cons 2 1) (cons 5 1))) #f "vector-assoc test c")
   (check-equal? (vector-assoc 4 (vector 4 (cons 3 1) (cons 2 1) (cons 5 1))) #f "vector-assoc test d")

   
   ; cached-assoc tests
   (check-equal? ((cached-assoc (list (cons 1 2) (cons 3 4)) 3) 3) (cons 3 4) "cached-assoc test a")
   (check-equal? ((cached-assoc (list (cons 1 2) (cons 3 4)) 3) 1) (cons 1 2) "cached-assoc test b")
   (check-equal? ((cached-assoc (list (cons 1 2) (cons 3 4)) 3) 0) #f "cached-assoc test c")
   (check-equal? (cached-test 1) (cons 1 2))
   (check-equal? (cached-test 1) (cons 1 2))

   ))

(require rackunit/text-ui)
;; runs the test
(run-tests tests)
