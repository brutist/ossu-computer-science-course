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


; PROBLEM:
 
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



; PROBLEM:
 
; Design a function that consumes a wizard and produces the number of wizards 
; in that tree (including the root). Your function should be tail recursive.
 



 
; PROBLEM:
 
; Design a new function definition for same-house-as-parent that is tail 
; recursive. You will need a worklist accumulator. 

