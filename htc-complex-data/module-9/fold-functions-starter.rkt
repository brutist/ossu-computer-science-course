(require 2htdp/image)

;; fold-functions-starter.rkt

;; At this point in the course, the type (listof X) means:

;; ListOfX is one of:
;; - empty
;; - (cons X ListOfX)
;; interp. a list of X

;; and the template for (listof X) is:

(define (fn-for-lox lox)
  (cond [(empty? lox) (...)]
        [else
         (... (first lox)
              (fn-for-lox (rest lox)))]))

 
; PROBLEM:
; Design an abstract fold function for (listof X). 

;; (X Y -> Y) Y (listof X) -> Y
;; an abstract fold function for (listof X)
(check-expect (fold + 0 (list 0 1 2)) 3)
(check-expect (fold * 1 (list 1 2 3)) 6)
(check-expect (fold + 5 (list 1 2 3)) 11)

(define (fold fn b lox)
  (cond [(empty? lox) b]
        [else
         (fn (first lox)
             (fold fn b (rest lox)))]))


; PROBLEM:
; Complete the function definition for sum using fold. 
 
;; (listof Number) -> Number
;; add up all numbers in list
(check-expect (sum empty) 0)
(check-expect (sum (list 2 3 4)) 9)

(define (sum lon) (fold + 0 lon))



; PROBLEM:
; Complete the function definition for juxtapose using foldr. 

(define BLANK (square 0 "solid" "white"))

;; (listof Image) -> Image
;; juxtapose all images beside each other
(check-expect (juxtapose empty) (square 0 "solid" "white"))
(check-expect (juxtapose (list (triangle 6 "solid" "yellow")
                               (square 10 "solid" "blue")))
              (beside (triangle 6 "solid" "yellow")
                      (square 10 "solid" "blue")
                      (square 0 "solid" "white")))

(define (juxtapose loi) (fold beside BLANK loi))



; PROBLEM:
; Complete the function definition for copy-list using foldr. 

;; (listof X) -> (listof X)
;; produce copy of list
(check-expect (copy-list empty) empty)
(check-expect (copy-list (list 1 2 3)) (list 1 2 3))

(define (copy-list lox) (foldr cons empty lox))



;; ======================

; PROBLEM:
; Design an abstract fold function for Element (and (listof Element)). 


(define-struct elt (name data subs))
;; Element is (make-elt String Integer ListOfElement)
;; interp. An element in the file system, with name, and EITHER data or subs.
;;         If data is 0, then subs is considered to be list of sub elements.
;;         If data is not 0, then subs is ignored.

;; ListOfElement is one of:
;;  - empty
;;  - (cons Element ListOfElement)
;; interp. A list of file system Elements

; .

(define F1 (make-elt "F1" 1 empty))
(define F2 (make-elt "F2" 2 empty))
(define F3 (make-elt "F3" 3 empty))
(define D4 (make-elt "D4" 0 (list F1 F2)))
(define D5 (make-elt "D5" 0 (list F3)))
(define D6 (make-elt "D6" 0 (list D4 D5)))
#;
(define (fn-for-element e)
  (local [(define (fn-for-element e)
            (... (elt-name e)    ;String
                 (elt-data e)    ;Integer
                 (fn-for-loe (elt-subs e))))

          (define (fn-for-loe loe)
            (cond [(empty? loe) (...)]
                  [else
                   (... (fn-for-element (first loe))
                        (fn-for-loe (rest loe)))]))]
    (fn-for-element e)))


;; (String Integer Y -> X) (X Y -> Y) Y Element -> X
;; the abstract fold function for Element
(check-expect (local [(define (c1 n d los) (cons n los))]
                  (fold-element c1 append empty D6))
                  (list "D6" "D4" "F1" "F2" "D5" "F3"))
(check-expect (local [(define (c1 n d los) (cons (string-append n ":" (number->string d)) los))]
                  (fold-element c1 append empty D6))
                  (list "D6:0" "D4:0" "F1:1" "F2:2" "D5:0" "F3:3"))

(define (fold-element c1 c2 b e)
  (local [(define (fn-for-element e)  ;->X
            (c1 (elt-name e)    ;String
                (elt-data e)    ;Integer
                (fn-for-loe (elt-subs e))))

          (define (fn-for-loe loe)    ;->Y
            (cond [(empty? loe) b]
                  [else
                   (c2 (fn-for-element (first loe))
                       (fn-for-loe (rest loe)))]))]
    (fn-for-element e)))

 
; PROBLEM
; Complete the design of sum-data that consumes Element and produces
; the sum of all the data in the element and its subs

;; Element -> Integer
;; produce the sum of all the data in element (and its subs)
(check-expect (sum-data F1) 1)
(check-expect (sum-data D5) 3)
(check-expect (sum-data D4) (+ 1 2))
(check-expect (sum-data D6) (+ 1 2 3))

(define (sum-data e) (local [(define (c1 n d los) (+ d los))]
                              (fold-element c1 + 0 e))) 


; PROBLEM
; Complete the design of all-names that consumes Element and produces a list of the
; names of all the elements in the tree. 

;; Element       -> ListOfString
;; produce list of the names of all the elements in the tree
(check-expect (all-names F1) (list "F1"))
(check-expect (all-names D5) (list "D5" "F3"))
(check-expect (all-names D4) (list "D4" "F1" "F2"))
(check-expect (all-names D6) (list "D6" "D4" "F1" "F2" "D5" "F3"))
               
(define (all-names e) (local [(define (c1 n d los) (cons n los))]
                             (fold-element c1 append empty e)))


; PROBLEM
; If the tree is very large, then fold-element is not a good way to implement the find 
; function from last week.  Why? If you aren't sure then discover the answer by implementing
; find using fold-element and then step the two versions with different arguments.

(define-struct person (name age kids))

(define P1 (make-person "N1" 5 empty))
(define P2 (make-person "N2" 25 (list P1)))
(define P3 (make-person "N3" 15 empty))
(define P4 (make-person "N4" 45 (list P3 P2)))

(check-expect (search-person-age P1 "N1") 5)
(check-expect (search-person-age P1 "N2") false)
(check-expect (search-person-age P2 "N1") 5)
(check-expect (search-person-age P2 "N2") 25)
(check-expect (search-person-age P2 "N3") false)
(check-expect (search-person-age P4 "N3") 15)
(check-expect (search-person-age P4 "N1") false)


;; search-person-age using fold-element
;(String Integer Y -> X) (X Y -> Y) Y Element -> X
(define (search-person-age person name) "false")  ;stub

#;
(define (search-person-age p name)
  (local [(define (fn-for-person p name)
            (if (string=? name (person-name p))
                (person-age p) 
                (fn-for-lop (person-kids p) name)))
          ;; ListOfPerson -> Natural or False
          ;; produce the age of a the person's name if found in the list, 
          ;;         produce false if not
          (define (fn-for-lop lop name) 
            (cond
              [(empty? lop) false]
              [else (if (string=? name (person-name (first lop)))
                        (person-age (first lop))
                        (fn-for-lop (rest lop) name))]))]
    (fn-for-person p name)))
