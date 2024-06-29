;; same-house-as-parent-v1.rkt


; PROBLEM:
 
; In the Harry Potter movies, it is very important which of the four houses a
; wizard is placed in when they are at Hogwarts. This is so important that in 
; most families multiple generations of wizards are all placed in the same family. 
 
; Design a representation of wizard family trees that includes, for each wizard,
; their name, the house they were placed in at Hogwarts and their kids. We
; encourage you to get real information for wizard families from: 
;    http://harrypotter.wikia.com/wiki/Main_Page
 
; The reason we do this is that designing programs often involves collection
; domain information from a variety of sources and representing it in the program
; as constants of some form. So this problem illustrates a fairly common scenario.

; That said, for reasons having to do entirely with making things fit on the
; screen in later videos, we are going to use the following wizard family tree,
; in which wizards and houses both have 1 letter names. (Sigh)


;; Data Definition

(define-struct wizard (name house kids))
;; Wizard is (make-wizard String String (listof Wizard))
;; interp. a wizard with
;;             - name     (name of the wizard)
;;             - house    (house in Hogwarts where they belong)
;;                  - house is one of:
;;                  - "G" means Gryffindor
;;                  - "H" means Hufflepuff
;;                  - "R" means Ravenclaw
;;                  - "S" means Slytherin
;;             - kids (a list of their kids)

;; (listof Wizard) is one of:
;;    - empty
;;    - (cons Wizard (listof Wizard))


(define (fn-for-wizard w)
   (...  (wizard-name w)
         (wizard-house w)
         (wizard-kids w)))
         
;; examples
(define Wag (make-wizard "A" "G" empty))
(define Wbh (make-wizard "B" "H" empty))
(define Wcr (make-wizard "C" "R" empty))
(define Wds (make-wizard "D" "S" empty))
(define Weg1 (make-wizard "E" "G" (list Wag)))
(define Wfh1 (make-wizard "F" "H" (list Wbh)))
(define Wgr1 (make-wizard "G" "R" (list Wds)))
(define Whs1 (make-wizard "H" "S" (list Wcr)))
(define Wig3 (make-wizard "I" "G" (list Weg1 Wfh1 Wgr1)))
(define Wjh3 (make-wizard "J" "H" (list Wig3 Whs1 Wds)))



(define (fn-for-wiz w)
   (local [(define (fn-for-wizard w)
               (... (wizard-name w)
                    (wizard-house w)
                    (fn-for-low (wizard-kids w))))

            (define (fn-for-low low)
               (cond [(empty? low) (...)]
                     [else (... (fn-for-wizard (first low))
                                (fn-for-low (rest low)))]))]

    (fn-for-wizard w)))


; PROBLEM A:
 
; Design a function that consumes a wizard and produces the names of every 
; wizard in the tree that was placed in the same house as their immediate
; parent. 

;; Wizard -> (listof String)
;; produces a list of wizard names that belongs to the same house as their immediate parent
;; examples/tests
(check-expect (same-house-with-parent Wag) empty)
(check-expect (same-house-with-parent Weg1) (list "A"))
(check-expect (same-house-with-parent Wgr1) empty)
(check-expect (same-house-with-parent Wjh3) (list  "E" "A" "B"))


(define (same-house-with-parent w)
   ;; acc: String - name of the house the previous parent belongs
   ;; (fn-for-wizard (make-wizard "E" "G" (list Wag)) "none")
   ;; (fn-for-wizard (make-wizard "E" "G" (list Wag)) "G")
   ;; (fn-for-wizard (make-wizard "A" "G" empty)      "G")
 
   (local [(define (fn-for-wizard w acc)  ;->list
               (if  (string=? acc (wizard-house w))
                    (cons (wizard-name w) (fn-for-low (wizard-kids w) (wizard-house w)))
                    (fn-for-low (wizard-kids w) (wizard-house w))))

            (define (fn-for-low low acc)  ;->list
               (cond [(empty? low) empty]
                     [else (append (fn-for-wizard (first low) acc)
                                   (fn-for-low (rest low) acc))]))]

    (fn-for-wizard w "none")))



; PROBLEM B:
 
; Design a function that consumes a wizard and produces the number of wizards 
; in that tree (including the root). Your function should be tail recursive.
 
;; Wizard -> Natural
;; produce the number of wizards in the tree, includes the root wizard
;; examples/tests
(check-expect (count-wizards Wag) 1)
(check-expect (count-wizards Weg1) 2)
(check-expect (count-wizards Wig3) 7)
(check-expect (count-wizards Wjh3) 11)
(check-expect (count-wizards Wgr1) 2)
(check-expect (count-wizards Wbh) 1)

(define (count-wizards w)
  ;; acc: Natural; number of previously seen wizards
  ;;      (listof Wizards); list of wizards to be counted
  ;; (fn-for-wizard Wig3) empty 0)

  ;; (fn-for-wizard Wig3) 1 (list Weg1 Wfh1 Wgr1))
  ;; (fn-for-wizard Weg1) 2 (list Wag Wfh1 Wgr1))
  ;; (fn-for-wizard Wag)  3 (list Wfh1 Wgr1))
  ;; (fn-for-wizard Wfh1) 4 (list Wbh Wgr1))
  ;; (fn-for-wizard Wbh)  5 (list Wgr1))
  ;; (fn-for-wizard Wgr1) 6 (list Wds))
  ;; (fn-for-wizard Wds)  7 empty)
  (local [(define (fn-for-wizard w todo rsf)
            (fn-for-low (append (wizard-kids w) todo) (add1 rsf)))

          (define (fn-for-low todo rsf)
            (cond [(empty? todo) rsf]
                  [else (fn-for-wizard (first todo) (rest todo) rsf)]))]

    (fn-for-wizard w empty 0)))


 
; PROBLEM C:
 
; Design a new function definition for same-house-as-parent that is tail 
; recursive. You will need a worklist accumulator. 

(check-expect (same-house-with-parent-tr Wag) empty)
(check-expect (same-house-with-parent-tr Weg1) (list "A"))
(check-expect (same-house-with-parent-tr Wgr1) empty)
(check-expect (same-house-with-parent-tr Wjh3) (list  "E" "A" "B"))
(check-expect (same-house-with-parent-tr Wig3) (list  "E" "A" "B"))
(check-expect (same-house-with-parent-tr Wfh1) (list "B"))


(define (same-house-with-parent-tr w)
  ;; acc: String - name of the house the intermediate parent belongs
  ;;      (listof Pairs)  - list of Pairs to be checked
  ;;      (listof String) - list of all the names of wizards that has the same house
  ;;                               with their intermediate parent
  ;;       String         - wizard parent's house
  ;; (same-house-with-parent-tr Whs1)

  ;; (fn-for-wizard Whs1 "" (list Wig3 Whs1 Wds) empty)

  (local [(define-struct pair (wiz h))
          ;; Pair is (make-pair Wizard String)
          ;; interp. a wizard with parent's house h
          (define (kids-pairs low h)
            (map (lambda (w) (make-pair w h)) low))

          (define (fn-for-wizard w todo rsf house)
            (if (string=? house (wizard-house w))
                (fn-for-low (append (kids-pairs (wizard-kids w) (wizard-house w)) todo)
                            (append rsf (list (wizard-name w))) 
                            house)
                (fn-for-low (append (kids-pairs (wizard-kids w) (wizard-house w)) todo)
                            rsf 
                            house)))

          (define (fn-for-low todo rsf house)
            (cond [(empty? todo) rsf]
                  [else (fn-for-wizard (pair-wiz (first todo)) 
                                       (rest todo) 
                                       rsf 
                                       (pair-h (first todo)))]))]

    (fn-for-wizard w empty empty "?")))

