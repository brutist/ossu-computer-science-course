
;; quidditch-starter.rkt

; 
; PROBLEM:
; 
; Imagine that you are designing a program that will keep track of
; your favorite Quidditch teams. (http://iqaquidditch.org/).
; 
; Design a data definition to represent a list of Quidditch teams. 
;    


;; ListofString is one of:
;;    - empty
;;    - (cons String ListofString)
;; interp. name of Quidditch teams
;; examples
(define LOS1 (cons "Hellsa" (cons "New Town" empty)))
(define LOS2 (cons "UBC" empty))
(define LOS3 empty)


(define (fn-for-los los)
  (cond [(empty? los) (...)]                    ;base case
        [else (... (first los)                  ;String
                   (fn-for-los (rest los)))]))  ;natural recursion

;; template rules used:
;;  - one of: 2 cases
;;  - atomic distinct: empty
;;  - compound: (cons String ListOfString)
;;  - self-reference: (rest los) is ListOfString

; 
; PROBLEM:
; 
; We want to know whether your list of favorite Quidditch teams includes
; UBC! Design a function that consumes ListOfString and produces true if 
; the list includes "UBC".
; 



;; ListOfString -> Boolean
;; produce true if the given ListOfString contains "UBC"
;; examples/tests
(check-expect (has-UBC? (cons "UBC" empty)) true)
(check-expect (has-UBC? empty) #false)
(check-expect (has-UBC? (cons "Hellsa" (cons "Mnoria" empty))) false)
(check-expect (has-UBC? (cons "Hellsa" (cons "UBC" empty))) true)

;; stub
#;
(define (has-UBC? los) #false)


;; template
(define (has-UBC? los)
  (cond [(empty? los) false]
        [else
         (if (string=? "UBC" (first los))
             true
             (has-UBC? (rest los)))]))

