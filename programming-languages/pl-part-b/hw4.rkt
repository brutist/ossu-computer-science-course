;; this is to declare that we are using the racket language
#lang racket 
;; make functions/bindings in this file externally available
(provide (all-defined-out))


;; Int Int Natural -> (listof Int)
;; produces a list of int starting from low, suceeding elements
;;   will then be the incremented with stride until high
(define (sequence low high stride)
    (if (> low high)
        null
        (cons low (sequence (+ low stride) high stride))))


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


;; Data-type Stream
;; stream a thunk than when called will produce a pair with the value in car position
;; and the next stream in the cdr position (cons x . next-stream)


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
(define dan-then-dog
    (local [(define dan-stream (lambda () (cons "dan.jpg" dog-stream)))
            (define dog-stream (lambda () (cons "dog.jpg" dan-stream)))]
        dan-stream))


;; Stream -> Stream
;; produces a new stream with elements (0 . v) for every v in input stream's ith element
(define (stream-add-zero s)
    (local [(define pr (s))]
        (lambda () (cons (cons 0 (car pr)) (stream-add-zero (cdr pr))))))


;; (listof x) (listof y) -> Stream
;; produce a stream of (cons x y), cycling thru the list infinitely.
;; In case the two list has different lengths, return to the first position of the 
;; shorter list and continue making pairs
(define (cycle-lists xs ys)
    (local [(define (aux n)
                (cons (cons (list-nth-mod xs n) (list-nth-mod ys n)) (lambda () (aux (+ n 1)))))]
        (lambda () (aux 0))))


;; x Vector -> Pair or false
;; produces the first pair from the vector which car position matches the given x
(define (vector-assoc v vec)
    (local [(define (v-in-pair? v pr) (equal? v (car pr)))
            (define first-pr (if (> (vector-length vec) 0) (vector-ref vec 0) #f))]
    (cond [(= (vector-length vec) 0) #f]
          [(not (pair? (vector-ref vec 0))) (vector-assoc v (vector-drop vec 1))]
          [else (if (v-in-pair? v first-pr)
                    first-pr
                    (vector-assoc v (vector-drop vec 1)))])))


;; (listof x) Int -> (Y -> Pair or false)
;; takes a list xs and a number n and returns a function that takes
;;  one argument v and returns the same thing that (assoc v xs) would return
(define (cached-assoc xs n)
    (local [(define cache (make-vector n))
            (define i 0)
            (define (update-cache pr)
                (begin (vector-set! cache (modulo i n) pr)
                       (set! i (add1 i))))]

        (lambda (v) (let* ([lookup-cache (vector-assoc v cache)]
                           [lookup-list (if lookup-cache #f (assoc v xs))])
                    (cond [lookup-cache lookup-cache]
                          [lookup-list (begin (update-cache lookup-list)
                                              lookup-list)]
                          [else #f])))))

;; for testing
(define cached-test (cached-assoc (list (cons 1 2) (cons 3 4)) 3))