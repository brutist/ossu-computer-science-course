(require 2htdp/image)

;; render-bst-starter.rkt


;; === Constants ===

;; font related constants
(define SIZE 14)
(define COLOR "black")
(define SEPARATOR ":")

;; spacing of the BST elements in pixels
(define VSPACE (rectangle 1 10 "outline" "white"))
(define HSPACE (rectangle 10 1 "outline" "white"))

;; images constants
(define BLANK (square 0 "solid" "white"))
(define FILLER (rectangle 35 15 "outline" "white"))

; 
; Consider the following data definition for a binary search tree: 
; 



;; Data definitions:

(define-struct node (key val l r))
;; A BST (Binary Search Tree) is one of:
;;  - false
;;  - (make-node Integer String BST BST)
;; interp. false means no BST, or empty BST
;;         key is the node key
;;         val is the node val
;;         l and r are left and right subtrees
;; INVARIANT: for a given node:
;;     key is > all keys in its l(eft)  child
;;     key is < all keys in its r(ight) child
;;     the same key never appears twice in the tree
; .

(define BST0 false)
(define BST1 (make-node 1 "abc" false false))
(define BST4 (make-node 4 "dcj" false (make-node 7 "ruf" false false)))
(define BST3 (make-node 3 "ilk" BST1 BST4))
(define BST42 
  (make-node 42 "ily"
             (make-node 27 "wit" (make-node 14 "olp" false false) false)
             (make-node 50 "dug" false false)))
(define BST10
  (make-node 10 "why" BST3 BST42))
#;
(define (fn-for-bst t)
  (cond [(false? t) (...)]
        [else
         (... (node-key t)    ;Integer
              (node-val t)    ;String
              (fn-for-bst (node-l t))
              (fn-for-bst (node-r t)))]))

;; Template rules used:
;;  - one of: 2 cases
;;  - atomic-distinct: false
;;  - compound: (make-node Integer String BST BST)
;;  - self reference: (node-l t) has type BST
;;  - self reference: (node-r t) has type BST

;; Functions:


; PROBLEM:
; 
; Design a function that consumes a bst and produces a SIMPLE 
; rendering of that bst. Emphasis on SIMPLE. You might want to 
; skip the lines for example.
; 


;; Node -> Image
;; produce an image of a node with the following format
;;    -   <key>:<value>
;; examples/tests
(check-expect (node->img false) BLANK)
(check-expect (node->img (make-node 1 "abc" false false)) (text "1:abc" SIZE COLOR))

#;
(define (node->img n) false) ;stub

(define (node->img n)
  (cond [(false? n) BLANK]
        [else (text (string-append (number->string (node-key n)) SEPARATOR (node-val n))
                    SIZE
                    COLOR)]))




;; BST -> Image
;; produce a simple rendering of a BST
;; examples/tests
(check-expect (render-bst false) FILLER)
(check-expect (render-bst BST1) (text "1:abc" SIZE COLOR))
(check-expect (render-bst BST3) (above (text "3:ilk" SIZE COLOR)
                                       VSPACE
                                       (beside (text "1:abc" SIZE COLOR)
                                               HSPACE
                                               (text "4:dcj" SIZE COLOR))
                                       VSPACE
                                       (beside FILLER
                                               HSPACE
                                               FILLER
                                               HSPACE
                                               (text "7:ruf" SIZE COLOR))))

(check-expect (render-bst BST10) false)
#;
(define (render-bst t) false) ;stub


(define (render-bst t)
  (cond [(false? t) FILLER]
        [(and (false? (node-l t)) (false? (node-r t))) BLANK]
        [else
         (above (node->img t)    
                VSPACE
                (cond 
                      [(false? (node-l t)) (beside FILLER
                                                   HSPACE
                                                   (render-bst (node-r t)))]
                      [(false? (node-r t)) (beside (render-bst (node-l t))
                                                   HSPACE
                                                   FILLER)]))]))


