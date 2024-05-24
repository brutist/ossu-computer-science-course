;; this is to declare that we are using the racket language
#lang racket 
;; make functions/bindings in this file externally available
(provide (all-defined-out))


;; Int Int Natural -> (listof Int)
;; produces a list of int starting from low, suceeding elements
;;   will then be the incremented with stride until high
(define (sequence low high stride)
    (local [(define (aux n rsf)
                (if (> n high)
                    rsf
                    (aux (+ n stride) (append rsf (list n)))))]
        (aux low null)))


;; (listof String) String -> (listof String)
;; produce a new list with each element appended s
(define (string-append-map xs s)
    (map (lambda (x) (string-append x s)) xs))


;; (listof xs) Int -> x
;; produce the ith element (0-based index) of the list, i is the remainder produced
;;  when dividing n by the list’s length
(define (list-nth-mod xs n)
    (local [(define i (remainder n (length xs)))]
    (cond [(null? xs) (error "list-nth-mod: empty list")]
          [(< n 0) (error "list-nth-mod: negative number")]
          [else (car (list-tail xs i))])))


;; stream is represented as (cons x . next-stream)
;; Stream Int -> (listof x)
;; produces the first nth elements in the stream
(define (stream-for-n-steps stream n)
    (local [(define (aux stream p rsf) 
            (local [(define val (car (stream)))
                    (define next-stream (cdr (stream)))]
                (cond [(>= p n) rsf]
                      [else (aux next-stream (+ p 1) (append rsf (list val)))])))]
        (aux stream 0 null)))


;; a stream of natural numbers starting from 1 but all numbers divisible by 5
;;  are negated
(define funny-number-stream
    (local [(define (f x) (cons (if (= (modulo x 5) 0) (- x) x) (lambda () (f (+ (abs x) 1)))))]
        (lambda () (f 1))))


;; a stream of alternating strings of "dan.jpg" and "dog.jpg"
