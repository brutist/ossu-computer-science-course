;; bst-dd-starter.rkt

; 
; PROBLEM:
; 
; Design a data definition to represent binary search trees. As a reminder,
; here is one example BST:
; 
; .



(define-struct node (key val l r))
;; BST (binary search tree) is one of:
;;   - false
;;   - (make-node Natural String BST BST)
;; interp. false means empty BST
;;         key is the node key
;;         val is the node val
;;         l and r are left and right subtrees
;; INVARIANT: for a given node:
;;     key is > all keys in its l(eft) child
;;     key is < all keys in its r(ight) child
;;     any given key is not repeated in the tree

(define BST0 false)
(define BST1 (make-node 1 "abc" false false))
(define BST7 (make-node 7 "ruf" false false))
(define BST4 (make-node 4 "dcj" false BST7))
(define BST3 (make-node 3 "ilk" BST1 BST4))

(define BST14 (make-node 14 "olp" false false))
(define BST27 (make-node 27 "wit" BST14 false))
(define BST50 (make-node 50 "dug" false false))
(define BST42 (make-node 42 "ily" BST27 BST50))

(define BST10 (make-node 10 "why" BST3 BST42))

(define (fn-for-bst t)
  (cond [(false? t) (...)]
        [else
         (... (node-key t)                ;Natural
              (node-val t)                ;String
              (fn-for-bst(node-l t))      ;natural recursion
              (fn-for-bst(node-l t)))]))  ;natural recursion

;; template rules used:
;;  - one of: 2 cases
;;  - atomic distinct: false
;;  - compound: (make-node Natural String BST BST)'
;;  - self-reference: (node-l t) is a BST
;;  - self-reference: (node-r t) is a BST

