
;; render-bst-w-lines-starter.rkt

(require 2htdp/image)

; PROBLEM:
; 
; Given the following data definition for a binary search tree,
; design a function that consumes a bst and produces a SIMPLE 
; rendering of that bst including lines between nodes and their 
; subnodes.
; 
; To help you get started, we've added some sketches below of 
; one way you could include lines to a bst.


;; Constants

(define TEXT-SIZE  14)
(define TEXT-COLOR "BLACK")

(define KEY-VAL-SEPARATOR ":")

(define MTTREE (rectangle 20 1 "solid" "white"))

(define HSPACE (rectangle 20 1 "outline" "white"))


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
(define BST7 (make-node 7 "ruf" false false)) 
(define BST4 (make-node 4 "dcj" false (make-node 7 "ruf" false false)))
(define BST3 (make-node 3 "ilk" BST1 BST4))
(define BST42 
  (make-node 42 "ily"
             (make-node 27 "wit" (make-node 14 "olp" false false) false)
             (make-node 50 "dug" false false)))
(define BST10
  (make-node 10 "why" BST3 BST42))
(define BST100 
  (make-node 100 "large" BST10 false))
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

; 
; Here is a sketch of one way the lines could work. What 
; this sketch does is allows us to see the structure of
; the functions pretty clearly. We'll have one helper for
; the key value image, and one helper to draw the lines.
; Each of those produces a rectangular image of course.
; 
; .
; 
; And here is a sketch of the helper that draws the lines:
; .  
; where lw means width of left subtree image and
;       rw means width of right subtree image



;; === Functions ===


.

;; Integer String -> Image
;; produces an image in the format of <int>:<string>
;; examplest/tets
(check-expect (render-key-val 1 "asd")
              (text (string-append "1" KEY-VAL-SEPARATOR "asd") TEXT-SIZE TEXT-COLOR))

(define (render-key-val k v)
  (text (string-append (number->string k) KEY-VAL-SEPARATOR v)
        TEXT-SIZE
        TEXT-COLOR))


;; Number Number -> Image
;; takes in two numbers corresponding to the widths of a rectangle and produce
;       an image that would add a line starting at the middle of the rectangle
;;      to the center of the left width and right width.
;;
;;            '  --------/-\--------
;;            '  '      / ' \      '    ;representation of the given rectangle widths
;;    (lw+rw)/4  '     /  '  \     '    ;nevermind the overlapped lines for the triangle
;;            '  '----/---'---\----'
;;               ----- lw+rw -------
;;

;; examples/tests
(check-expect (lines 20 30) (add-line (add-line (rectangle
                                                 (+ 20 30) (/ (+ 20 30) 4) "outline" "white")
                                                (/ (+ 20 30) 2)
                                                0
                                                (/ 20 2)
                                                (/ (+ 20 30) 4)
                                                "lightred")
                                      (/ (+ 20 30) 2)
                                      0
                                      (+ 20 (/ 30 2))
                                      (/ (+ 20 30) 4)
                                      "lightred")) 

#;
(define (lines lw rw) false)  ;stub

(define (lines lw rw)
  (add-line (add-line (rectangle (+ lw rw) (/ (+ lw rw) 4) "outline" "white")
                      (/ (+ lw rw) 2) 0 (/ lw 2) (/ (+ lw rw) 4) "lightred")
            (/ (+ lw rw) 2) 0 (+ lw (/ rw 2)) (/ (+ lw rw) 4) "lightred")) 





;; BST -> Image
;; produces an image rendering of the bst with lines connecting the parent and child nodes
;; examples/tests


(check-expect (render-bst false) MTTREE)

(check-expect (render-bst BST1)
              (above (text (string-append (number->string (node-key BST1))
                                          KEY-VAL-SEPARATOR
                                          (node-val BST1))
                           TEXT-SIZE
                           TEXT-COLOR)
                     (lines (image-width (render-bst false))
                            (image-width (render-bst false)))
                     (render-bst false)))

                      
#;
(define (render-bst t) MTTREE) ;stub


(define (render-bst t)
  (cond [(false? t) MTTREE]
        [else
         (above (render-key-val (node-key t) (node-val t))   
                (lines (image-width (render-bst (node-l t)))
                       (image-width (render-bst (node-r t))))
                (beside/align "top"
                              (render-bst (node-l t))
                              (render-bst (node-r t))))]))

