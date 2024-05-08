;Consider the following data definitions for BinaryTree and Path

(define-struct node (k v l r))
;; BinaryTree is one of:
;; - false
;; - (make-node Natural String BinaryTree BinaryTree)
;; interp. a binary tree, each node has key, value, and l/r children
(define BT0 false)
(define BT1 (make-node 1 "a" false false))
(define BT4 (make-node 4 "d"
                       (make-node 2 "b"
                                  (make-node 1 "a" false false)
                                  (make-node 3 "c" false false))
                       (make-node 5 "e" false false)))
;; Path is one of:
;; - empty
;; (cons "L" Path)
;; (cons "R" Path)
;; interp. a sequence of left and right 'turns' down though a BinaryTree
;;         (list "L" "R" "R" means take the left child of the root, then
;;         the right child of that node, and the right child again.
;;         empty means you have arrived at the destination. 
(define P1 empty)
(define P2 (list "L"))
(define P3 (list "R"))
(define P4 (list "L" "R"))
(define P5 (list "R" "R" "R" "R"))
(define P6 (list "R" "R" "R" "L" "L"))
; Design the function has-path? that consumes a BinaryTree and a Path.
; The function should produce true if following the path through the tree leads to a node.
; If the path leads to false or runs into false before reaching the end of the path,
; the function should produce false.


;; Function

;; BinaryTree Path -> Boolean
;; produce true if following the path through the tree leads to a node; 
;;   if the path leads to false before the end of path, produce false
;; examples/tests
(check-expect (has-path? BT0 P1) true)
(check-expect (has-path? BT1 P1) true)
(check-expect (has-path? BT0 P2) false)
(check-expect (has-path? BT0 P3) false)
(check-expect (has-path? BT0 P4) false)
(check-expect (has-path? BT1 P3) false)
(check-expect (has-path? BT1 P2) false)
(check-expect (has-path? BT1 P4) false)
(check-expect (has-path? BT4 P4) true)
(check-expect (has-path? BT4 P5) false)
(check-expect (has-path? BT4 P6) false)

#;
(define (has-path? btre p) "false") ;stub

(define (has-path? btre p)
   (cond [(empty? p) true]
         [(false? btre) false]
         [(string=? "L" (first p)) (and (not (false? (node-l btre))) (has-path? (node-l btre) (rest p)))]
         [(string=? "R" (first p)) (and (not (false? (node-r btre))) (has-path? (node-r btre) (rest p)))]))
