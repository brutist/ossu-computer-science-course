;; max-exits-from-starter.rkt

 
; PROBLEM:

; Using the following data definition, design a function that produces the room with the most exits 
; (in the case of a tie you can produce any of the rooms in the tie).
 


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


;; Room -> Room
;; produce the room that has the most exits
;; examples/tests
(check-expect (most-exits H1) H1)
(check-expect (most-exits H0) H0)
(check-expect (most-exits H4) H4)
(check-expect (most-exits H4F) (make-room "F" (list)))
(check-expect (most-exits H4C) H4B)


(define (most-exits r0)
   ;; todo is (listof Room); list of to be visited rooms
   ;; rsf is Room; the room that has the most exits so far
   ;; visited is (listof String); list of room names that has previously been visited
  (local [(define (more-exit r rsf) 
            ;; Room Room -> Room
            ;; outputs the room that has more exits
            (if (> (length (room-exits r)) (length (room-exits rsf)))
               r
               rsf))

          (define (fn-for-room r todo rsf visited)
            (if (member (room-name r) visited)
                (fn-for-lor todo rsf visited)
                (fn-for-lor (append (room-exits r) todo) (more-exit r rsf) (cons (room-name r) visited))))

          (define (fn-for-lor todo rsf visited)
            (cond [(empty? todo) rsf]
                  [else (fn-for-room (first todo) (rest todo) rsf visited)]))]
    
    (fn-for-room r0 empty r0 empty)))

