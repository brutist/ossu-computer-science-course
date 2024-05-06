;; max-exits-to-starter.rkt


; PROBLEM:

; Using the following data definition, design a function that produces the room to which the greatest 
; number of other rooms have exits (in the case of a tie you can produce any of the rooms in the tie).


;; Data Definitions: 

(define-struct room (name exits))
;; Room is (make-room String (listof Room))
;; interp. the room's name, and list of rooms that the exits lead to


(define H1 (make-room "A" (list (make-room "B" empty))))

(define H2 
  (shared ((-0- (make-room "A" (list (make-room "B" (list -0-))))))
    -0-)) 


(define H3
  (shared ((-A- (make-room "A" (list -B-)))
           (-B- (make-room "B" (list -C-)))
           (-C- (make-room "C" (list -A- -B-))))
    -A-))

(define H3B
  (shared ((-A- (make-room "A" (list -B-)))
           (-B- (make-room "B" (list -C-)))
           (-C- (make-room "C" (list -A- -B-))))
    -B-))
           
(define H4
  (shared ((-A- (make-room "A" (list -B- -D-)))
           (-B- (make-room "B" (list -C- -E-)))
           (-C- (make-room "C" (list -B-)))
           (-D- (make-room "D" (list -E-)))
           (-E- (make-room "E" (list -F- -A-)))
           (-F- (make-room "F" (list))))
    -A-))

(define H0 (make-room "Q" empty))

(define H4F
   (shared ((-A- (make-room "A" (list -B- -D-)))
            (-B- (make-room "B" (list -C- -E-)))
            (-C- (make-room "C" (list -B-)))
            (-D- (make-room "D" (list -E-)))
            (-E- (make-room "E" (list -F- -A-)))
            (-F- (make-room "F" (list))))

   -F-))

(define H4C
  (shared ((-A- (make-room "A" (list -B- -D-)))
           (-B- (make-room "B" (list -C- -E-)))
           (-C- (make-room "C" (list -B-)))
           (-D- (make-room "D" (list -E-)))
           (-E- (make-room "E" (list -F- -A-)))
           (-F- (make-room "F" (list))))
    -C-))


(define H4E
  (shared ((-A- (make-room "A" (list -B- -D-)))
           (-B- (make-room "B" (list -C- -E-)))
           (-C- (make-room "C" (list -B-)))
           (-D- (make-room "D" (list -E-)))
           (-E- (make-room "E" (list -F- -A-)))
           (-F- (make-room "F" (list))))
    -E-))
    
(define H4B
  (shared ((-A- (make-room "A" (list -B- -D-)))
           (-B- (make-room "B" (list -C- -E-)))
           (-C- (make-room "C" (list -B-)))
           (-D- (make-room "D" (list -E-)))
           (-E- (make-room "E" (list -F- -A-)))
           (-F- (make-room "F" (list))))
    -B-))

;; template: structural recursion, encapsulate w/ local, tail-recursive w/ worklist, 
;;           context-preserving accumulator what rooms have we already visited

(define (fn-for-house r0)
  ;; todo is (listof Room); a worklist accumulator
  ;; visited is (listof String); context preserving accumulator, names of rooms already visited
  (local [(define (fn-for-room r todo visited) 
            (if (member (room-name r) visited)
                (fn-for-lor todo visited)
                (fn-for-lor (append (room-exits r) todo)
                            (cons (room-name r) visited)))) ; (... (room-name r))
          (define (fn-for-lor todo visited)
            (cond [(empty? todo) (...)]
                  [else
                   (fn-for-room (first todo) 
                                (rest todo)
                                visited)]))]
    (fn-for-room r0 empty empty))) 




;; Room -> Room
;; produces the room to which the greatest number of other rooms have exits (i.e most reachable);
;;   in a tie, any of the room in the tie will be produced;
;;   if given room don't have exit, produces empty list
;; Assume: room-names are unique
;; examples/tests
(check-expect (most-reachable H0) (list))
(check-expect (most-reachable H4) H4B)
(check-expect (most-reachable H4E) H4B)
(check-expect (most-reachable H3) H3B)
(check-expect (most-reachable H4F) (list))


(check-expect (most-reachable H1) (first (room-exits H1)))
(check-expect (most-reachable H2) (shared ((-A- (make-room "A" (list -B-)))
                                         (-B- (make-room "B" (list -A-))))
                                  -B-))

(check-expect (most-reachable H4) H4B)



(define (most-reachable r0)
  ;; todo is (listof Room); list of to be visited rooms
  ;; visited is (listof String); list of visited room names
  ;; rfs is (listof Rrp); the current reachability (no. of exits to it) of each rooms found so far

  (local [(define-struct rrp (room n))             ;;room-reachability pair
            ;; Rrp is (make-rrp Room Natural)
            ;; interp. the room and the number of other rooms that has exits to it
          
          ;; (listof Room) (listof Rrp) -> (listof Rrp)
          ;; converts the (listof Room) into (listof Rrp) with (rrp-n 1) each and inserts it 
          ;;    into the list; if the rrp-room is already present in the list, it increases 
          ;;    the rrp-n of that rrp.
          (define (add-rrps lor0 rsf0)
            (local [(define (add-rrps todo-room rsf)
                      (cond [(empty? todo-room) rsf]
                            [else (add-rrps (rest todo-room) 
                                            (add-rrp (first todo-room) rsf))]))] 
            (add-rrps lor0 rsf0)))


          ;; Room (listof Rrp) -> (listof Rrp)
          ;; increases the room's room-n by 1; if room is not in lorrp, it appends it
          (define (add-rrp r0 lorrp0)
            (local [(define (fn-for-rrp r todo prevrrp)
                      (cond [(empty? todo) (append prevrrp (list (make-rrp r 1)))]
                            [(string=? (room-name r) (room-name (rrp-room (first todo))))
                              (append prevrrp (list (make-rrp r (add1 (rrp-n (first todo))))) (rest todo))]
                            [else (fn-for-rrp r (rest todo) (append prevrrp (list (first todo))))]))]
            (fn-for-rrp r0 lorrp0 empty)))

          ;; (listof Rrp) -> Room
          ;; produces the room that has the largest no of exits leading to it (reachability)
          ;; Assume: lorrp has at least one element
          (define (max-reachability lorrp0) 
            (local [(define (max-reachability lorrp max-reach rsf)
                      (cond [(empty? lorrp) rsf]
                            [else (if (> (rrp-n (first lorrp)) max-reach)
                                  (max-reachability (rest lorrp) (rrp-n (first lorrp)) (rrp-room (first lorrp)))
                                  (max-reachability (rest lorrp) max-reach rsf))]))]
            (max-reachability lorrp0 0 empty)))

          (define (fn-for-room r todo visited rfs) 
            (if (member (room-name r) visited)
                (fn-for-lor todo visited rfs)
                (fn-for-lor (append (room-exits r) todo) 
                                   (cons (room-name r) visited) 
                                   (add-rrps (room-exits r) rfs)))) 

          (define (fn-for-lor todo visited rfs)
            (cond [(empty? todo) (max-reachability rfs)]
                  [else (fn-for-room (first todo) (rest todo) visited rfs)]))]

    (fn-for-room r0 empty empty empty))) 
