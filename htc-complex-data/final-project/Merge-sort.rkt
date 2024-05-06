#;
(define (fn-for-lon lon)
  (cond [(trivial? lon) (trivial lon)]
        [else
         (...lon
          (fn-for-lon (next-problem lon)))]))

; Merge Sort:
; Design a function thath implement merge sort this is a generative recursion sort algorith.
; 
; the way it works:
; - break the list into parts
; - sort the parts
; - merge the parts back together (merge the result of the recursive)
; How we now this a generative recursion ?
; because when we split the list in two we know the first half of the list is a new list
; 
; 1. take the first half of the list
; 2. drop the second half of the list
  

;; (listofnumber) -> (listofnumber)
;; given a listof numbers produce a listof number sorted in ascending order using merge sort.
(check-expect (merge-sort empty) empty)
(check-expect (merge-sort (list 1)) (list 1))
(check-expect (merge-sort (list 1 2)) (list 1 2))
(check-expect (merge-sort (list 7 4)) (list 4 7))
(check-expect (merge-sort (list 6 5 8 2 1 4 9)) (list 1 2 4 5 6 8 9))

;(define (merge-sort lon) empty)  ;stub

(define (merge-sort lon)
  (cond [(empty? lon) empty]
        [(empty? (rest lon)) lon]
        [else
         (merge (merge-sort (take lon (quotient (length lon) 2)))         ;1
                (merge-sort (drop lon (quotient (length lon) 2))))]))     ;2


;; (listof Number) (listof Number) -> (listof Number)
;; produces a sorted list of number
;; Assumes that the given lists are sorted
;; examples/tests
(check-expect (merge (list 1 2) (list 1 2)) (list 1 1 2 2))
(check-expect (merge (list 2 8 10) (list 5 5 9)) (list 2 5 5 8 9 10))
(check-expect (merge (list 1 3 4 5) (list 3 4 8 19)) (list 1 3 3 4 4 5 8 19))


(define (merge lon01 lon02)
   (local [(define (merge lon1 lon2 rsf)
            (cond [(empty? lon2) (append rsf lon1)]
                  [(empty? lon1) (append rsf lon2)]
                  [else (if (< (first lon2) (first lon1))
                            (merge (rest lon2) lon1 (append rsf (list (first lon2))))
                            (merge lon2 (rest lon1) (append rsf (list (first lon1)))))]))]

   (merge lon01 lon02 empty)))


;; (listof X) Natural -> (listof X)
;; produce a list that starts from l0 to lnth, exclusive
;; Assume the given list is of at least 2 elements long and n < length of list
;; examples/tests
(check-expect (take (list 1 2) 1) (list 1))
(check-expect (take (list 1 2 3) 2) (list 1 2))
(check-expect (take (list 1 2 3 8 9 2) 3) (list 1 2 3))

(define (take lox0 p0)
   (local [(define (take lox p acc rsf)
            (cond [(= acc p) rsf]
                  [else (take (rest lox) p (add1 acc) (append rsf (list (first lox))))]))]
   (take lox0 p0 0 empty)))


;; (listof X) Natural -> (listof X)
;; produce a list that starts from ln+1th up until to the end of the list
;; Assume the given list is of at least 2 elements long
;; examples/tests
(check-expect (drop (list 1 2) 1) (list 2))
(check-expect (drop (list 1 2 3 4) 2) (list 3 4))
(check-expect (drop (list 1 2 3 8 9 2) 3) (list 8 9 2))

(define (drop lox0 p0)
   (local [(define (drop lox p acc rsf)
            (cond [(= acc p) rsf]
                  [else (drop (rest lox) p (add1 acc) (rest lox))]))]
   (drop lox0 p0 0 empty)))


