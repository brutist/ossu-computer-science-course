;; bt-contains-tr-starter.rkt

; Problem:

; Starting with the following data definition for a binary tree (not a binary search tree) 
; design a tail-recursive function called contains? that consumes a key and a binary tree 
; and produces true if the tree contains the key.



(define-struct node (k v l r))
;; BT is one of:
;;  - false
;;  - (make-node Integer String BT BT)
;; Interp. A binary tree, each node has a key, value and 2 children
(define BT1 false)
(define BT2 (make-node 1 "a"
                       (make-node 6 "f"
                                  (make-node 4 "d" false false)
                                  false)
                       (make-node 7 "g" false false)))

(define BT10 (make-node 10 "q" BT2 false))
(define BT11 (make-node 11 "k" false BT10))
(define BT12 (make-node 12 "x" BT10 false))
(define BT13 (make-node 13 "y" false BT12))

;; Binary Tree String -> Boolean
;; produces true if s is a key in the given tree
;; examples/tests
(check-expect (contains? BT1 1) false)
(check-expect (contains? BT2 6) true)
(check-expect (contains? BT2 8) false)
(check-expect (contains? BT11 1) true)
(check-expect (contains? BT11 5) false)
(check-expect (contains? BT12 1) true)
(check-expect (contains? BT13 1) true)


(define (contains? t0 key)
  ;; acc: todo - (listof Nodes); nodes that will be visited 
  ;;      rst - Boolean; true if the key is found in visited nodes, false otherwise
  ;;      
  ;; (contains? BT2 4)
  ;; (fn-for-node (node 1a) 4 (list (node 6f)) false)
  ;; (fn-for-node (node 6f) 4 (list (node 4d)) false)
  ;; (fn-for-node (node 4d) 4 (list (node 7g)) true)
  ;; (fn-for-node (node 7g) 4 empty            true)
  (local [(define (fn-for-node t todo rst)  ;->Boolean
            (cond [(false? t) (fn-for-lond todo rst)]
                  [else (if (= key (node-k t))
                            (fn-for-lond empty true)
                            (fn-for-lond (cons (node-l t) (cons (node-r t) todo)) rst))]))
            
          (define (fn-for-lond todo rst)    ;->Boolean
            (cond [(empty? todo) rst]
                  [else (fn-for-node (first todo) (rest todo) rst)]))]

    (fn-for-node t0 empty false)))




