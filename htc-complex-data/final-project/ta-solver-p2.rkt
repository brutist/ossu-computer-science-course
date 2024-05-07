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



;; ============================= FUNCTIONS


;; Schedule (listof TA) (listof Slot) -> (listof Schedule)
;; produces valid next (listof Schedule) from the given Schedule (listof TA) (listof Slot)
;; finds first empty assignment, fills it with all of the TAs in the list and keeps
;; only the valid assignment to be appended into the schedule
;; examples/tests
(check-expect (next-schedule empty (list SOBA) (list 2)) (list empty))
(check-expect (next-schedule empty (list SOBA) (list 1)) (list (list (make-assignment SOBA 1))))
(check-expect (next-schedule empty NOODLE-TAs (list 1 3)) 
              (list (list (make-assignment SOBA 3))
                    (list (make-assignment UDON 3))))
(check-expect (next-schedule empty NOODLE-TAs (list 5 6 7)) 
              (list empty))


(define (next-schedule sched0 tas0 slots0)
    (local [;; Schedule -> Boolean
            ;; produces true if the schedule is valid
            ;; Schedule is valid if no ta in the sched is working more than their maximum shifts
            (define (valid-sched? sched)
                (...))

            ;; (listof Schedule) -> (listof Schedule)
            ;; filters the given schedules by keeping only the valid shedules
            ;; Schedule is valid if no ta in the sched is working more than their maximum shifts
            (define (keep-valid losched)
                (filter valid-sched? losched))

            ;; Natural -> (listof Schedule)
            ;; produces a list of schedule in which every element of the list is the sched0
            ;;     appended with a ta in tas0 assigned to slot0
            

            ;; Schedule -> Natural
            ;; produces the first slot in slots0 that is not in the sched


            (define (next-schedule sched)
                (keep-valid (fill-with-tas (find-empty sched))))]
    
    (next-schedule sched0)))






;; (listof TA) (listof Slot) -> Schedule or false
;; produce valid schedule given TAs and Slots; false if impossible

(check-expect (schedule-tas empty empty) empty)
(check-expect (schedule-tas empty (list 1 2)) false)
(check-expect (schedule-tas (list SOBA) empty) empty)

(check-expect (schedule-tas (list SOBA) (list 1)) (list (make-assignment SOBA 1)))
(check-expect (schedule-tas (list SOBA) (list 2)) false)
(check-expect (schedule-tas (list SOBA) (list 1 3)) (list (make-assignment SOBA 3)
                                                          (make-assignment SOBA 1)))

(check-expect (schedule-tas NOODLE-TAs (list 1 2 3 4)) 
              (list
               (make-assignment UDON 4)
               (make-assignment SOBA 3)
               (make-assignment RAMEN 2)
               (make-assignment SOBA 1)))

(check-expect (schedule-tas NOODLE-TAs (list 1 2 3 4 5)) false)


(define (schedule-tas tas slots)
  (local [;; Schedule (listof Slot) -> Boolean 
          ;; produce true if the given schedule has an assignment for each slots in the list
          ;; Assume: given schedule is valid, so it is full if (length schedule) = (length slots)
          (define (full? sched slots) (= (length sched) (length slots)))

          (define (fn-for-schedule sched)
            (if (full? sched slots)
                sched
                (fn-for-losched (next-schedule sched tas slots))))
            
          (define (fn-for-losched losched)
            (cond [(empty? losched) false]
                  [else 
                   (local [(define try (fn-for-schedule (first losched)))]
                     (if (not (false? try))
                         try
                         (fn-for-losched (rest losched))))]))]

    (fn-for-schedule empty)))

