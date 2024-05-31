(require 2htdp/image)
(require racket/list) 

;  PROBLEM 1:

;  In the lecture videos we designed a function to make a Sierpinski triangle fractal. 
;  Here is another geometric fractal that is made of circles rather than triangles:

;           == sample available at ./geometric-fractal.png  ==

;  Design a function to create this circle fractal of size n and colour c.
  


(define CUT-OFF 2)
(define STEP 0.5)
(define MODE "outline")

;; Natural String -> Image
;; produce a circle fractal of size n and colour c
;; examples/tests
(check-expect (circle-fractal CUT-OFF "black")  (circle CUT-OFF MODE "black"))
(check-expect (circle-fractal (/ CUT-OFF STEP) "black") 
              (overlay (circle (/ CUT-OFF STEP) MODE "black")
                       (beside (circle CUT-OFF MODE "black") (circle CUT-OFF MODE "black"))))


(define (circle-fractal n c)
  (cond [(<= n CUT-OFF) (circle n MODE c)]
        [else 
              (overlay (circle n MODE c) 
                       (beside (circle-fractal (* n STEP) c) (circle-fractal (* n STEP) c)))]))



;  PROBLEM 2:

;  Below you will find some data definitions for a tic-tac-toe solver. 

;  In this problem we want you to design a function that produces all 
;  possible filled boards that are reachable from the current board. 

;  In actual tic-tac-toe, O and X alternate playing. For this problem
;  you can disregard that. You can also assume that the players keep 
;  placing Xs and Os after someone has won. This means that boards that 
;  are completely filled with X, for example, are valid.

;  Note: As we are looking for all possible boards, rather than a winning 
;  board, your function will look slightly different than the solve function 
;  you saw for Sudoku in the videos, or the one for tic-tac-toe in the 
;  lecture questions. 
 


;; Value is one of:
;; - false
;; - "X"
;; - "O"
;; interp. a square is either empty (represented by false) or has and "X" or an "O"

(define TTT-VALS (list "X" "O"))

(define (fn-for-value v)
  (cond [(false? v) (...)]
        [(string=? v "X") (...)]
        [(string=? v "O") (...)]))

;; Board is (listof Value)
;; a board is a list of 9 Values
(define B0 (list false false false
                 false false false
                 false false false))

(define B1 (list false "X"   "O"   ; a partly finished board
                 "O"   "X"   "O"
                 false false "X")) 

(define B2 (list "X"  "X"  "O"     ; a board where X will win
                 "O"  "X"  "O"
                 "X" false "X"))

(define B3 (list "X" "O" "X"       ; a board where Y will win
                 "O" "O" false
                 "X" "X" false))

(define B4f (list "X"  "X"  "O"     ; a completely filled board, invalid board
                  "O"  "X"  "O"
                  "X"  "X"  "X"))

(define (fn-for-board b)
  (cond [(empty? b) (...)]
        [else 
         (... (fn-for-value (first b))
              (fn-for-board (rest b)))]))


;; Board -> (listof Board)
;; produces a completely filled board reachable from the given board
;; Assumes - X and O continue playing even tic-tac-toe winning condition has been reached
;;         - X and O doesn't have to play alternately
;;  -produce next-move until full
;; examples/tests
(check-expect (possible-full-bd B3) (list (list "X" "O" "X"      
                                                   "O" "O" "X"
                                                   "X" "X" "X")
                                             (list "X" "O" "X"      
                                                   "O" "O" "X"
                                                   "X" "X" "O")
                                             (list "X" "O" "X"      
                                                   "O" "O" "O"
                                                   "X" "X" "X")
                                             (list "X" "O" "X"     
                                                   "O" "O" "O"
                                                   "X" "X" "O")))

(check-expect (possible-full-bd B2) (list (list "X" "X" "O"   
                                                   "O" "X" "O"
                                                   "X" "X" "X")
                                             (list "X" "X" "O"   
                                                   "O" "X" "O"
                                                   "X" "O" "X")))

(check-expect (possible-full-bd B4f) (list B4f)) 

(check-expect (length (possible-full-bd B0)) 512) ;2^9 possible completely filled states

(define (possible-full-bd b)
  (local [(define (full? b) (andmap (lambda (v) (not (false? v))) b))
          (define (solve-boards b)           
            (if (full? b)
                (list b)
                (fn-for-lobd (next-boards b)))) 
    
          (define (fn-for-lobd lobd)
            (cond [(empty? lobd) empty]
                  [else (local [(define try (solve-boards (first lobd)))
                                (define remaining-solution (fn-for-lobd (rest lobd)))]
                        (if (not (empty? try))
                            (append try remaining-solution)  
                            remaining-solution))]))]
  (solve-boards b)))
  

;; Board -> (listof Board)
;; produces a list of possible next moves from the given board
;; Assume: X and O doesn't have to play alternately
;;       : there is at least one empty spot
;;  pick the first empty spot on the board, then place X and O
;; examples/tests
(check-expect (next-boards B3) (list (list "X" "O" "X"      
                                           "O" "O" "X"
                                           "X" "X" false)
                                     (list "X" "O" "X"      
                                           "O" "O" "O"
                                           "X" "X" false)))
(check-expect (next-boards B0) (list (list "X"   false false
                                            false false false
                                            false false false)
                                     (list "O"   false false
                                            false false false
                                            false false false)))


(define (next-boards b)
  (local [(define p (index-of b false))
          (define (insert-octoth p) 
                  (map (lambda (v) (insert-val b p v)) TTT-VALS))]
    (insert-octoth p)))


;; Board Natural Value -> Board
;; replace the element in index p with v in the board
;; examples/tests
(check-expect (insert-val B0 0 "X") (list "X"   false false
                                          false false false
                                          false false false))
(check-expect (insert-val B1 6 "X") (list false "X"   "O"   ; a partly finished board
                                          "O"   "X"   "O"
                                          "X"   false "X"))
(check-expect (insert-val B0 4 "O") (list false false false
                                          false "O"   false
                                          false false false))



(define (insert-val b p v) (append (take b p) (list v) (drop b (add1 p))))


;  PROBLEM 3:
  
;  Now adapt your solution to filter out the boards that are impossible if 
;  X and O are alternating turns. You can continue to assume that they keep 
;  filling the board after someone has won though. 
  
;  You can assume X plays first, so all valid boards will have 5 Xs and 4 Os.
;  NOTE: make sure you keep a copy of your solution from problem 2 to answer 
;  the questions on edX.
  

;; Board -> (listof Board)
;; produces a list of completed boards that satisfy alternating moves between
;;      X and O
;; examples/tests
(check-expect (valid-full-bd B4f)  empty)
(check-expect (valid-full-bd B3) (list (list "X" "O" "X"      
                                             "O" "O" "X"
                                             "X" "X" "O")
                                       (list "X" "O" "X"      
                                             "O" "O" "O"
                                             "X" "X" "X")))


(define (valid-full-bd b) (local [(define final-boards (possible-full-bd b))
                                  (define (pred b) (valid-board? b))]
                            (filter pred final-boards)))


;; Board -> Boolean
;; produces true if the board is a valid board
;;  - valid means a possible board if X and O alternately moves
;; Assume: board given is full
;; examples/tests
(check-expect (valid-board? (list "X" "O" "X"      
                                  "O" "O" "X"
                                  "X" "X" "X")) false)

(check-expect (valid-board? (list "X" "O" "X"      
                                  "O" "O" "X"
                                  "X" "X" "O")) true)

(check-expect (valid-board? (list "X" "X" "X"      
                                  "O" "X" "X"
                                  "X" "X" "X")) false)

(check-expect (valid-board? (list "O" "O" "X"      
                                  "O" "O" "X"
                                  "O" "O" "X")) false)


(define (valid-board? b)
  (local [(define (count lov v) (cond [(empty? lov) 0]
                                      [else (if (string=? (first lov) v)
                                            (+ 1 (count (rest lov) v))
                                            (count (rest lov) v)) ]))]
  (cond [(= (count b "X") 5) true]
        [else false])))