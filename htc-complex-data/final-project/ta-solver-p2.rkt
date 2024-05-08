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


(define CHA (make-ta "Charity" 2 (list 1 2 4)))
(define HON (make-ta "Honesty" 1 (list 1 7)))
(define HUM (make-ta "Humility" 3 (list 5 6 9)))
(define INT (make-ta "Integrity" 2 (list 1 2 2)))
(define PIE (make-ta "Piety" 1 (list 3 2 8)))
(define PUR (make-ta "Purity" 4 (list 4 6 8 7)))

(define SECTION-TAs (list CHA HON HUM INT PIE PUR))


(define (same-ta? ta1 ta2)
  (and (string=? (ta-name ta1) (ta-name ta2))
       (= (ta-max ta1) (ta-max ta2))
       (equal? (ta-avail ta1) (ta-avail ta2))))



(define-struct assignment (ta slot))
;; Assignment is (make-assignment TA Slot)
;; interp. the TA is assigned to work the slot

;; Assignment -> Boolean
;; produce true if the assigned slot is in ta-avail
(define (valid-assignment? a)
  (member (assignment-slot a) (ta-avail (assignment-ta a))))



;; Schedule is (listof Assignment)

;; Schedule Natural -> Boolean
;; produces true if the given slot is already assigned in the schedule
(define (slot-in-sched? sched slot)
  (cond [(empty? sched) false]
        [(= (assignment-slot (first sched)) slot) true]
        [else (slot-in-sched? (rest sched) slot)]))

;; Schedule Assignment -> Schedule
;; appends the given assignment to the schedule
(define (append-schedule sched assignment)
  (append sched (list assignment)))

;; ============================= FUNCTIONS


;; Schedule (listof TA) (listof Slot) -> (listof Schedule)
;; produces valid next (listof Schedule) from the given Schedule (listof TA) (listof Slot)
;; finds first empty assignment, fills it with all of the TAs in the list and keeps
;; only the valid assignment to be appended into the schedule
;; examples/tests
(check-expect (next-schedule empty (list SOBA) (list 2)) empty)
(check-expect (next-schedule empty (list SOBA) (list 1)) (list (list (make-assignment SOBA 1))))
(check-expect (next-schedule empty NOODLE-TAs (list 1 3)) 
              (list (list (make-assignment SOBA 3))
                    (list (make-assignment UDON 3))))
(check-expect (next-schedule empty NOODLE-TAs (list 5 6 7)) 
              empty)


(define (next-schedule sched0 tas slots)
  (local [;; Tsp is (make-tsp TA Natural)
          ;; interp. TA with n assigned shifts
          (define-struct tsp (ta shift))

          (define (add-shift-tsp tsp)
            (make-tsp (tsp-ta tsp) (add1 (tsp-shift tsp))))

          (define (overworked-tsp? t)
            (< (ta-max (tsp-ta t)) (tsp-shift t)))

          ;; Assignment (listof Tsp) -> (listof Tsp)
          ;; increases the assignment-ta shift in (listof Tsp), if assignment-ta is not a ta 
          ;; in the (listof Tsp) it inserts assignment-ta and instantiate its shift with 1
          (define (add-tsp a lotsp0)
            (local 
              [(define (add-tsp lotsp prevtsp)
                 (cond [(empty? lotsp) (append prevtsp (list (make-tsp (assignment-ta a) 1)))]
                       [(same-ta? (assignment-ta a) (tsp-ta (first lotsp)))
                        (append prevtsp (list (add-shift-tsp (first lotsp))) (rest lotsp))]
                       [else (add-tsp (rest lotsp) (append prevtsp (list (first lotsp))))]))]

              (add-tsp lotsp0 empty)))

          ;; (listof Tsp) -> Boolean
          ;; produce true if no ta in the (listof Tsp) is assigned more than their maximum shifts
          (define (overworked-lotsp? lotsp)
            (cond [(empty? lotsp) true]
                  [(overworked-tsp? (first lotsp)) false]
                  [else (overworked-lotsp? (rest lotsp))]))


          ;; Schedule -> Boolean
          ;; produces true if the schedule is valid
          ;; Schedule is valid if no ta in the sched is working more than their maximum shifts
          (define (no-overworked? sched0)
            (local
              ;; rfs is (listof Tsp); result so far accumulator         
              [(define (no-overworked? sched rfs)
                 (cond [(empty? sched) (overworked-lotsp? rfs)]
                       [else (no-overworked? (rest sched) (add-tsp (first sched) rfs))]))]
            
              (no-overworked? sched0 empty)))

          ;; Schedule -> Boolean
          ;; produces true if all assignments in the schedule are valid
          ;; Assignments are valid if the the ta is available in the given shift
          (define (valid-sched? sched0)
            (local
              ;; rfs is (listof Tsp); result so far accumulator         
              [(define (valid-sched? sched rfs)
                 (cond [(empty? sched) rfs]
                       [(valid-assignment? (first sched)) (valid-sched? (rest sched) rfs)]
                       [else false]))]
            
              (valid-sched? sched0 true)))


          ;; (listof Schedule) -> (listof Schedule)
          ;; filters the given schedules by keeping only the valid shedules
          ;; Schedule is valid if no ta in the sched is working more than their maximum shifts
          (define (keep-valid losched)
            (filter (lambda (s) (and (valid-sched? s) (no-overworked? s))) losched))

          ;; (listof TA) Natural -> (listof Schedule)
          ;; produces a list of schedule in which every element of the list is the sched0
          ;;     appended with a ta in tas assigned to slot
          (define (fill-with-tas tas slot)
            ;; rfs is (listof Schedule)
            (local [(define (fill-with-tas tas rfs)
                      (cond [(empty? tas) rfs]
                            [else (fill-with-tas (rest tas) 
                                                 (append rfs (list (append-schedule sched0 (make-assignment (first tas) slot)))))]))]

              (fill-with-tas tas empty)))


          ;; Schedule -> Natural
          ;; produces the first slot in slots that is not in the sched
          ;; Assume: given schedule is not full          
          (define (find-empty sched slots0)
            (local [(define slots (reverse (sort slots0 <)))]
              (cond [(empty? slots) "Error in find-empty"]     ;will not happen normally
                    [(slot-in-sched? sched (first slots)) (find-empty sched (rest slots))]
                    [else (first slots)])))   


          (define (next-schedule sched)
            (keep-valid (fill-with-tas tas (find-empty sched slots))))]
    
    (next-schedule sched0)))




;; (listof TA) (listof Slot) -> Schedule or false
;; produce valid schedule given TAs and Slots; false if impossible
;; examples/tests
(check-expect (schedule-tas empty empty) empty)
(check-expect (schedule-tas empty (list 1 2)) false)
(check-expect (schedule-tas (list SOBA) empty) empty)

(check-expect (schedule-tas (list SOBA) (list 1)) (list (make-assignment SOBA 1)))
(check-expect (schedule-tas (list SOBA) (list 2)) false)
(check-expect (schedule-tas (list SOBA) (list 1 3)) (list (make-assignment SOBA 3)
                                                          (make-assignment SOBA 1)))

(check-expect (schedule-tas NOODLE-TAs (list 1 2 3 4 5)) false)
(check-expect (schedule-tas NOODLE-TAs (list 1 2 3 4)) 
              (list
               (make-assignment UDON 4)
               (make-assignment SOBA 3)
               (make-assignment RAMEN 2)
               (make-assignment SOBA 1)))

(check-expect (schedule-tas SECTION-TAs (list 1 2 3 4 5 6)) 
              (list
               (make-assignment (make-ta "Humility" 3 (list 5 6 9)) 6)
               (make-assignment (make-ta "Humility" 3 (list 5 6 9)) 5)
               (make-assignment (make-ta "Charity" 2 (list 1 2 4)) 4)
               (make-assignment (make-ta "Piety" 1 (list 3 2 8)) 3)
               (make-assignment (make-ta "Charity" 2 (list 1 2 4)) 2)
               (make-assignment (make-ta "Honesty" 1 (list 1 7)) 1)))

(check-expect (schedule-tas SECTION-TAs (list 1 3 4 6 8 11)) false)

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
                  [else (fn-for-schedule (first losched))]))]

    (fn-for-schedule empty)))

;; It seems that no backtracking is happening. I basically generate all possible solutions
;;    and just pick the right one. This is acceptable for this problem but if this is for
;;    a sudoku or problems that involve tremendous possibilities, this solution would be too
;;    slow.