;; graphs-v2.rkt

 
; PROBLEM: 
 
; Imagine you are suddenly transported into a mysterious house, in which all
; you can see is the name of the room you are in, and any doors that lead OUT
; of the room.  One of the things that makes the house so mysterious is that
; the doors only go in one direction. You can't see the doors that lead into
; the room.
; 
; Here are some examples of such a house:

;  == ./graph-example.png  ==

; In computer science, we refer to such an information structure as a directed
; graph. Like trees, in directed graphs the arrows have direction. But in a
; graph it is  possible to go in circles, as in the second example above. It
; is also possible for two arrows to lead into a single node, as in the fourth
; example.

   
; Design a data definition to represent such houses. Also provide example data
; for the four houses above.

(define-struct room (name exits))
;; Room is (make-room String (listof Room))
;; interp. the room's name, and the list of rooms that exits leads to

;; examples starting from left in ./graph-example.png
(define H1 (make-room "A" (list (make-room "B" empty))))
(define H2 
   (shared ((-0- (make-room "A" (list (make-room "B" (list -0-))))))
   -0-))

(define H3 
   (shared ((-A- (make-room "A" (list -B-)))
            (-B- (make-room "B" (list -C-)))
            (-C- (make-room "C" (list -A-))))
   -A-))

(define H4
   (shared ((-A- (make-room "A" (list -B- -D-)))
            (-B- (make-room "B" (list -C- -E-)))
            (-C- (make-room "C" (list -B-)))
            (-D- (make-room "D" (list -E-)))
            (-E- (make-room "E" (list -F- -A-)))
            (-F- (make-room "F" (list))))

   -A-))

(define H4F
   (shared ((-A- (make-room "A" (list -B- -D-)))
            (-B- (make-room "B" (list -C- -E-)))
            (-C- (make-room "C" (list -B-)))
            (-D- (make-room "D" (list -E-)))
            (-E- (make-room "E" (list -F- -A-)))
            (-F- (make-room "F" (list))))

   -F-))

#;
(define (fn-for-house r)
   ;; todo is (listof Room); a worklist accumulator
   ;; visited is (listof String); a list of room-names that is already visited
  (local [(define (fn-for-room r visited todo)
            (if (member (room-name r) visited)
                (fn-for-lor visited todo)
                (fn-for-lor (cons (room-name r) visited)  (append (room-exits r) todo)))) 

          (define (fn-for-lor visited todo)
            (cond [(empty? todo) (...)]
                  [else (fn-for-room (first todo) visited (rest todo))]))] ;(... (first todo))
      
    (fn-for-room r empty empty)))


   
; PROBLEM:

; Design a function that consumes a Room and a room name, and produces true
; if it is possible to reach a room with the given name starting at the given
; room. For example:

;   (reachable? H1 "A") produces true
;   (reachable? H1 "B") produces true
;   (reachable? H1 "C") produces false
;   (reachable? H4 "F") produces true
  
; But note that if you defined H4F to be the room named F in the H4 house then 
; (reachable? H4F "A") would produce false because it is not possible to get
; to A from F in that house.


;; Room String -> Boolean
;; produce true if string name is reachable from the given room
;; examples/tests
(check-expect (reachable? H1 "A") true)
(check-expect (reachable? H1 "B") true)
(check-expect (reachable? H1 "C") false)
(check-expect (reachable? H4 "F") true)
(check-expect (reachable? H4F "A") false)
(check-expect (reachable? (first (room-exits H1)) "A") false)


(define (reachable? r0 name)
   ;; todo is (listof Room); list of to be visited rooms
   ;; visited is (listof String); a list of room-names that is already visited
  (local [(define (fn-for-room r visited todo)
            (cond [(string=? (room-name r) name) true]
                  [(member (room-name r) visited) (fn-for-lor visited todo)]
                  [else (fn-for-lor (cons (room-name r) visited) (append (room-exits r) todo))]))

          (define (fn-for-lor visited todo)
            (cond [(empty? todo) false]
                  [else (fn-for-room (first todo) visited (rest todo))]))] 
      
    (fn-for-room r0 empty empty)))


