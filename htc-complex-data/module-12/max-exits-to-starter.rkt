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
;;   if given room don't have exit, produces that room
;; Assume: room-names are unique
;; examples/tests
(check-expect (most-reachable H0) H0)
(check-expect (most-reachable H4) H4)
(check-expect (most-reachable H4E) H4E)
(check-expect (most-reachable H3) H3B)

(define (most-reachable r0)
  ;; todo is (listof Rop); to be visited room-origin pairs
  ;; visited is (listof Rop); list of visited room-origin pairs
  ;; rfs is (listof Rrp); the current reachability (no. of exits to it) of each rooms found so far
  (local [(define-struct rop (room orig))                 ;;room-origin pair
            ;; Rop is (make-rop Room String)
            ;; interp. a pair of Room and its origin room name

          (define-struct rrp (room n))                    ;;room-reachability pair
            ;; Rrp is (make-rrp Room Natural)
            ;; interp. the room and the number of other rooms that has exits to it
          
          ;; Room (listof Rrp) -> (listof Rrp)
          ;; produces a list increases that room's room-n by 1; if room is not in lorrp, it appends it
          (define (add-rrp r0 lorrp0)
            (local [(define (add-rrp r lorrp prevrrp)
                      (cond [(empty? lorrp) (cons (make-rrp r 1) empty)]
                            [else (if (string=? (room-name (rrp-room (first lorrp))) (room-name r))
                                      (append prevrrp (list (make-rrp r (add1 (rrp-n (first lorrp))))) (rest lorrp))
                                      (add-rrp r (rest lorrp) (append (list (first lorrp)) prevrrp) ))]))]

            (add-rrp r0 lorrp0 empty)))
          
          ;; (listof Rrp) -> Room
          ;; produces the room that has the largest room-n (reachability)
          ;; Assume: lorrp has at least one element
          (define (max-reachability lorrp0)
            (local [(define (max-reachability lorrp max-reach rsf)
                      (cond [(empty? lorrp) rsf]
                            [else (if (> (rrp-n (first lorrp)) max-reach)
                                  (max-reachability (rest lorrp) (rrp-n (first lorrp)) (rrp-room (first lorrp)))
                                  (max-reachability (rest lorrp) max-reach rsf))]))]

            (max-reachability lorrp0 0 empty)))

          (define (rops lor str)
            (map (lambda (r) (make-rop r str)) lor))

          (define (fn-for-room r origin todo visited rfs) 
            (if (member (make-rop r origin) visited)
                (fn-for-lor origin todo visited rfs)
                (fn-for-lor origin (append (rops (room-exits r) (room-name r)) todo) 
                                   (cons (make-rop r origin) visited) 
                                   (add-rrp r rfs)))) 

          (define (fn-for-lor origin todo visited rfs)
            (cond [(empty? todo) (max-reachability rfs)]
                  [else (fn-for-room (rop-room (first todo)) 
                                     (rop-orig (first todo)) 
                                     (rest todo) 
                                     visited 
                                     rfs)]))]

    (fn-for-room r0 "" empty empty empty))) 
