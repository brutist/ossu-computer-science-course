;; dropn-starter.rkt


; PROBLEM:

; Design a function that consumes a list of elements lox and a natural number
; n and produces the list formed by dropping every nth element from lox.

; (dropn (list 1 2 3 4 5 6 7) 2) should produce (list 1 2 4 5 7)



;; (listof X) Natural -> (listof X)
;; produces a list formed dropping every nth element of the given lox
;; examples/tests
(check-expect (dropn (list 1 2 3 4 5 6 7) 2) (list 1 2 4 5 7))
(check-expect (dropn (list "a" "b" "c" "d" "e" "f" "g") 3) (list "a" "b" "c" "e" "f" "g"))
(check-expect (dropn (list "a" "b" "c" "d" "e" "f" "g") 1) (list "a" "c" "e" "g"))


(define (dropn lox0 n)
   ;; acc: Natural; countdown for dropping an element in the list
   ;;                zero means that element must not be included
   ;; (dropn (list 1 2 3 4 5) 2)

   ;; (dropn (list 1 2 3 4 5) 2 2) 
   ;; (dropn (list   2 3 4 5) 2 1)
   ;; (dropn (list     3 4 5) 2 0)
   ;; (dropn (list       4 5) 2 2)
   ;; (dropn (list         5) 2 1)
   ;; (dropn (list          ) 2 0)


   (local [(define (dropn lox n count)
      (cond [(empty? lox) empty]
            [else (if (zero? count)
                      (dropn (rest lox) n n)
                      (cons (first lox) (dropn (rest lox) n (sub1 count))))]))]

   (dropn lox0 n n)))