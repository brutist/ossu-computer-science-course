;; demolish-starter.rkt

;; =================
;; Data definitions:

; 
; PROBLEM A:
; 
; You are assigned to develop a system that will classify 
; buildings in downtown Vancouver based on how old they are. 
; According to city guidelines, there are three different classification levels:
; new, old, and heritage.
; 
; Design a data definition to represent these classification levels. 
; Call it BuildingStatus.
; 


;; BuildingStatus is String
;; interp. the classification of a building is one of:
;;   - new       
;;   - old        
;;   - heritage

;; examples are redundant for enumerations
#;
(define (fn-for-building-status s)
  (cond [(string=? s "new") (...)]
        [(string=? s "old") (...)]
        [(string=? s "heritage") (...)]))

;; template rules used:
;;   - one of: 3 cases
;;   - atomic distinct: "new"
;;   - atomic distinct: "old"
;;   - atomic distinct: "heritage"


;; =================
;; Functions:


; 
; PROBLEM B:
; 
; The city wants to demolish all buildings classified as "old". 
; You are hired to design a function called demolish? 
; that determines whether a building should be torn down or not.
; 


;; BuildingStatus -> Boolean
;; produce true if the given BuildingStatus is "old", otherwise false
;; examplples/tests
(check-expect (demolish? "old") #true)
(check-expect (demolish? "new") #false)
(check-expect (demolish? "heritage") #false)


;; stub
#;
(define (demolish? s) #false )

;; template from BuildingStatus
(define (demolish? s)
  (cond [(or (string=? s "heritage") (string=? s "new")) #false]
        [(string=? s "old") #true]))


