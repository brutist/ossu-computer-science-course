; You are asked to design a function that numbers a list of strings by inserting a number
; and a colon before each element of the list based on its position. So for example:
; 
; (number-list (list "first" "second" "third")) would produce (list "1: first" "2: second" "3: third")


;; ListOfString Number -> ListOfString
;; append each string's position in the list to the front of the string to number the list
(check-expect (number-list empty) empty)
(check-expect (number-list (list "first" "second" "third"))
              (list "1:first" "2:second" "3:third")) 
              
;(define (number-list los) los)  ;stub


(define (number-list los0)
   ;; acc: Natural; 1-based position of (first los) in los0
   ;; (skip1 (list "a" "b" "c") 1)
   ;; (skip1 (list     "b" "c") 2)
   ;; (skip1 (list         "c") 3)
   (local [(define (number-list los p)
            (cond [(empty? los) empty]
                  [else (cons (string-append (number->string p) ":" (first los))
                              (number-list (rest los) (add1 p)))]))]

      (number-list los0 1)))