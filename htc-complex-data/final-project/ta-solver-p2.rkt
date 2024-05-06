;  PROBLEM 2:
 
;  In UBC's version of How to Code, there are often more than 800 students taking 
;  the course in any given semester, meaning there are often over 40 Teaching Assistants. 

;  Designing a schedule for them by hand is hard work - luckily we've learned enough now to write 
;  a program to do it for us! 
  
;  Below are some data definitions for a simplified version of a TA schedule. There are some 
;  number of slots that must be filled, each represented by a natural number. Each TA is 
;  available for some of these slots, and has a maximum number of shifts they can work. 
  
;  Design a search program that consumes a list of TAs and a list of Slots, and produces one
;  valid schedule where each Slot is assigned to a TA, and no TA is working more than their 
;  maximum shifts. If no such schedules exist, produce false. 
 
;  You should supplement the given check-expects and remember to follow the recipe!


;; Slot is Natural
;; interp. each TA slot has a number, is the same length, and none overlap

(define-struct ta (name max avail))
;; TA is (make-ta String Natural (listof Slot))
;; interp. the TA's name, number of slots they can work, and slots they're available for

(define SOBA (make-ta "Soba" 2 (list 1 3)))
(define UDON (make-ta "Udon" 1 (list 3 4)))
(define RAMEN (make-ta "Ramen" 1 (list 2)))

(define NOODLE-TAs (list SOBA UDON RAMEN))



(define-struct assignment (ta slot))
;; Assignment is (make-assignment TA Slot)
;; interp. the TA is assigned to work the slot

;; Schedule is (listof Assignment)