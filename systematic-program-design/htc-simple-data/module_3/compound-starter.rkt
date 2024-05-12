;; compound-starter.rkt

; 
; PROBLEM:
; 
; Design a data definition to represent hockey players, including both 
; their first and last names.
; 


(define-struct player (fn ln))
;; Player is (make-player String String)
;; interp. (make-player fn ln) is a hockey player with
;;          fn as firstname and;
;;          ln as lastname

;; examples
(define P1 (make-player "John" "Snow"))
(define P2 (make-player "Elton" "John"))


(define (fn-for-player p)
  (... (player-ln p)            ;String
       (player-fn p)))          ;String

;; template rules used:
;;    - compound: 2 fields