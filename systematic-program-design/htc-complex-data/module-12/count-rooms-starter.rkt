
;; count-rooms-starter.rkt

 
; PROBLEM:
 
; Using the following data definition, design a function that consumes a room and produces 
; the total number of rooms reachable from the given room. Include the starting room itself. 
; Your function should be tail recursive, but you should not use the primitive length function.
 


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

(define H0 (make-room "Q" empty))

(define H4F
   (shared ((-A- (make-room "A" (list -B- -D-)))
            (-B- (make-room "B" (list -C- -E-)))
            (-C- (make-room "C" (list -B-)))
            (-D- (make-room "D" (list -E-)))
            (-E- (make-room "E" (list -F- -A-)))
            (-F- (make-room "F" (list))))

   -F-))

;; template: structural recursion, encapsulate w/ local, tail-recursive w/ worklist, 
;;           context-preserving accumulator what rooms have we already visited
#;
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


;; Room -> Natural
;; produces the total number of rooms reachable from r0, includes the starting room
;; examples/tests
(check-expect (count-rooms H0) 1)
(check-expect (count-rooms H1) 2)
(check-expect (count-rooms H3) 3)
(check-expect (count-rooms H4) 6)
(check-expect (count-rooms H4F) 1)


(define (count-rooms r0)
  ;; todo is (listof Room); a list of to be visited rooms
  ;; visited is (listof String); context preserving accumulator, names of rooms already visited
  (local [(define (los-length los) (foldr (lambda (s x) (+ 1 x)) 0 los))
          (define (fn-for-room r todo visited) 
            (if (member (room-name r) visited)
                (fn-for-lor todo visited)
                (fn-for-lor (append (room-exits r) todo)
                            (cons (room-name r) visited))))
          (define (fn-for-lor todo visited)
            (cond [(empty? todo) (los-length visited)]
                  [else
                   (fn-for-room (first todo) 
                                (rest todo)
                                visited)]))]
    (fn-for-room r0 empty empty)))
