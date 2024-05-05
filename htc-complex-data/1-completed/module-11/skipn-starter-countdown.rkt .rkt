;; skipn-starter.rkt

; 
; PROBLEM:
; 
; Design a function that consumes a list of elements lox and a natural number
; n and produces the list formed by including the first element of lox, then 
; skipping the next n elements, including an element, skipping the next n 
; and so on.

;  (skipn (list "a" "b" "c" "d" "e" "f") 2) should produce (list "a" "d")

;; (listof lox) Natural -> (listof lox)
;; takes in (l1 l2 l3 ...) produces (l1 l1+(n+1) l1+(n+1)+(n+1) ...)
;; examples/test
(check-expect (skip-n empty 1) empty)
(check-expect (skip-n (list "a" "b" "c" "d" "e" "f") 2) (list "a" "d"))
(check-expect (skip-n (list "a" "b" "c") 3) (list "a"))
(check-expect (skip-n (list "a" "b" "c" "d" "e" "f"
                            "g" "h" "i" "j" "k" "l") 2) (list "a" "d" "g" "j"))
#;
(define (skip-n lox0 n)
    ;; acc: Natural - 1-based index of (first lox) in lox0
    ;;      Natural - 1-based index of the next item to be included in the list
    ;; (skip-n (list "a" "b" "c" "d" "e" "f") 1 1)
    ;; (skip-n (list     "b" "c" "d" "e" "f") 2 4)     Assume n = 2
    ;; (skip-n (list         "c" "d" "e" "f") 3 4)
    ;; (skip-n (list             "d" "e" "f") 4 4)
    ;; (skip-n (list                 "e" "f") 5 7)
    (local [(define (skip-n lox cn nn)
                (cond [(empty? lox) empty]
                      [else (if (= cn nn)
                                 (cons (first lox) (skip-n (rest lox) (add1 cn) (+ nn (+ n 1))))
                                 (skip-n (rest lox) (add1 cn) nn))]))]
    
    (skip-n lox0 1 1)))


;; Alternative solution from the lecture video
;; countdown version

(define (skip-n lox0 n)
    ;; acc: Natural - countdown from n to 0, zero means element must be included

    ;; (skip-n (list "a" "b" "c" "d" "e" "f") 0)
    ;; (skip-n (list     "b" "c" "d" "e" "f") 2)     Assume n = 2
    ;; (skip-n (list         "c" "d" "e" "f") 1)
    ;; (skip-n (list             "d" "e" "f") 0)
    ;; (skip-n (list                 "e" "f") 2)
    (local [(define (skip-n lox acc)
                (cond [(empty? lox) empty]
                      [else (if (zero? acc)
                                 (cons (first lox) (skip-n (rest lox) n))
                                 (skip-n (rest lox) (sub1 acc)))]))]
    
    (skip-n lox0 0)))

;; countoff version
#;
(define (skip-n lox0 n)
    ;; acc: Natural - countoff from 0 to n, zero means element must be included
   
    ;; (skip-n (list "a" "b" "c" "d" "e" "f") 0)
    ;; (skip-n (list     "b" "c" "d" "e" "f") 1)     Assume n = 2
    ;; (skip-n (list         "c" "d" "e" "f") 2)
    ;; (skip-n (list             "d" "e" "f") 0)
    ;; (skip-n (list                 "e" "f") 1)
    (local [(define (skip-n lox acc)
                (cond [(empty? lox) empty]
                      [(= acc 0) (cons (first lox) (skip-n (rest lox) (add1 acc)))]
                      [else (if (= acc n)
                                (skip-n (rest lox) 0)
                                (skip-n (rest lox) (add1 acc)))]))]
    
    (skip-n lox0 0)))