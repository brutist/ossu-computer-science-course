
;; hp-family-tree-starter.rkt

; In this problem set you will represent information about descendant family 
; trees from Harry Potter and design functions that operate on those trees.
; 
; To make your task much easier we suggest two things:
;   - you only need a DESCENDANT family tree
;   - read through this entire problem set carefully to see what information 
;     the functions below are going to need. Design your data definitions to
;     only represent that information.
;   - you can find all the information you need by looking at the individual 
;     character pages like the one we point you to for Arthur Weasley.
; 


; PROBLEM 1:
; 
; Design a data definition that represents a family tree from the Harry Potter 
; wiki, which contains all necessary information for the other problems.  You 
; will use this data definition throughout the rest of the homework.
; 


; PROBLEM 2: 
; 
; Define a constant named ARTHUR that represents the descendant family tree for 
; Arthur Weasley. You can find all the infomation you need by starting 
; at: http://harrypotter.wikia.com/wiki/Arthur_Weasley.
; 
; You must include all of Arthur's children and these grandchildren: Lily, 
; Victoire, Albus, James.
; 
; 
; Note that on the Potter wiki you will find a lot of information. But for some 
; people some of the information may be missing. Enter that information with a 
; special value of "" (the empty string) meaning it is not present. Don't forget
; this special value when writing your interp.
; 


; PROBLEM 3:
; 
; Design a function that produces a pair list (i.e. list of two-element lists)
; of every person in the tree and his or her patronus. For example, assuming 
; that HARRY is a tree representing Harry Potter and that he has no children
; (even though we know he does) the result would be: (list (list "Harry" "Stag")).
; 
; You must use ARTHUR as one of your examples.
; 


; PROBLEM 4:
; 
; Design a function that produces the names of every person in a given tree 
; whose wands are made of a given material. 
; 
; You must use ARTHUR as one of your examples.
; 


;; DATA DEFINITIONS


;; necessary data
;;  - first name
;;  - last name
;;  - patronus
;;  - wand material


(define-struct person (first last patronus wand children))
;; Person is (make-person String String ListOfString ListOfPerson)
;; interp. A person in the Harry Potter Universe with a
;;           first    - given name
;;           last     - lastname
;;           patronus - ghostly magical guardian
;;           wand     - list of material their wands are made of
;;           children - is a ListOfPerson containing their children, no children is false
;;
;;           "" means the information is not available
  
;; ListOfPerson is one of:
;;   - empty
;;   - (cons Person ListOfPerson)
;; interp. a list of person in the Harry Potter universe

#;
(define (fn-for-person p)
  (... (person-first p)        ;String
       (person-last p)         ;String
       (person-patronus p)     ;Sting
       (person-wand p)         ;ListOfString
       (fn-for-lop (person-children p))))

#;
(define (fn-for-lop lop)
  (cond [(empty? lop) (...)]
        [else (... (fn-for-person (first lop))
                   (fn-for-lop (rest lop)))]))

;; CONSTANTS
(define LILY     (make-person "Lily Luna" "Potter" "" (list "unknown") empty))
(define JAMES    (make-person "James Sirius" "Potter" "" (list "unknown") empty))
(define ALBUS    (make-person "Albus Severus" "Potter" "" (list "unknown") empty))
(define VICTOIRE (make-person "Victoire" "Weasley" "" (list "unknown") empty))

(define BILL     (make-person "William Arthur" "Weasly" "non-corporeal" (list "unknown") (list VICTOIRE)))
(define CHARLIE  (make-person "Charles" "Weasly" "non-corporeal" (list "unknown") empty))
(define PERCY    (make-person "Percy Ignatius" "Weasly" "non-corporeal" (list "unknown") empty))
(define FRED     (make-person "Fred" "Weasly I" "magpie" (list "unknown") empty))
(define GEORGE   (make-person "George" "Weasly" "magpie" (list "unknown") empty))
(define RON      (make-person "Ronald Bilius" "Weasly" "jack russell terrier"
                              (list "unicorn tail hair" "dragon heartstring" "ash" "willow") empty))
(define GINNY    (make-person "Ginevra Molly" "Weasly" "horse" (list "yew") (list ALBUS JAMES LILY)))

(define ARTHUR   (make-person "Arthur" "Weasly" "weasel" (list "unknown")
                              (list BILL CHARLIE PERCY FRED GEORGE RON GINNY))) 


;; Person       -> ListOfList
;; ListOfPerson -> List
;; produces a list of two-element lists [name and patronus] of each person in the tree.
;; examples/tests
(check-expect (list-patronus--lop empty) empty)
(check-expect (list-patronus--person LILY)    (list (list (person-first LILY)     (person-patronus LILY))))
(check-expect (list-patronus--person LILY)    (list (list (person-first LILY)     (person-patronus LILY))))
(check-expect (list-patronus--person CHARLIE) (list (list (person-first CHARLIE)  (person-patronus CHARLIE))))
(check-expect (list-patronus--person BILL)    (list (list (person-first BILL)     (person-patronus BILL)) 
                                                    (list (person-first VICTOIRE) (person-patronus VICTOIRE))))
(check-expect (list-patronus--person GINNY)   (list (list (person-first GINNY)    (person-patronus GINNY))
                                                    (list (person-first ALBUS)    (person-patronus ALBUS))
                                                    (list (person-first JAMES)    (person-patronus JAMES)) 
                                                    (list (person-first LILY)     (person-patronus LILY))))
(check-expect (list-patronus--person ARTHUR)  (list (list (person-first ARTHUR)   (person-patronus ARTHUR))
                                                    (list (person-first BILL)     (person-patronus BILL))
                                                    (list (person-first VICTOIRE) (person-patronus VICTOIRE))
                                                    (list (person-first CHARLIE)  (person-patronus CHARLIE))
                                                    (list (person-first PERCY)    (person-patronus PERCY))
                                                    (list (person-first FRED)     (person-patronus FRED))
                                                    (list (person-first GEORGE)   (person-patronus GEORGE))
                                                    (list (person-first RON)      (person-patronus RON))
                                                    (list (person-first GINNY)    (person-patronus GINNY))
                                                    (list (person-first ALBUS)    (person-patronus ALBUS))
                                                    (list (person-first JAMES)    (person-patronus JAMES))
                                                    (list (person-first LILY)     (person-patronus LILY))))

;(define (list-patronus--person p) "false")
;(define (list-patronus--lop lop) "false")


(define (list-patronus--person p)
  (cons (list (person-first p) (person-patronus p))  ;2-element List
        (list-patronus--lop (person-children p))))   ;empty or another List


(define (list-patronus--lop lop)
  (cond [(empty? lop) empty]                                   ;no children                            
        [else (append (list-patronus--person (first lop))
                      (list-patronus--lop (rest lop)))]))


; PROBLEM 4:
; 
; Design a function that produces the names of every person in a given tree 
; whose wands are made of a given material. 
; 
; You must use ARTHUR as one of your examples.


;; String Person -> Boolean
;; produce true if the given string is in the list
;; examples/test
(check-expect (have-wand "yew" (person-wand LILY)) (member "yew" (person-wand LILY)))
(check-expect (have-wand "unknown" (person-wand LILY)) (member "unknown" (person-wand LILY)))
(check-expect (have-wand "unicorn tail hair" (person-wand RON)) (member "unicorn tail hair" (person-wand RON)))

(define (have-wand m w)
   (member m w))


;; Person       String -> List
;; ListOfPerson String -> List
;; produces a list of names of every person whose wands are made of the given string
;; examples/tests
(check-expect (same-wand--lop empty "yew") empty)
(check-expect (same-wand--person LILY "unknown") (list (person-first LILY)))
(check-expect (same-wand--person RON "ash") (list (person-first RON)))
(check-expect (same-wand--person RON "unicorn tail hair") (list (person-first RON)))
(check-expect (same-wand--person ARTHUR "ash") (list (person-first RON)))
(check-expect (same-wand--person ARTHUR "unknown") (list (person-first ARTHUR)
                                                         (person-first BILL)
                                                         (person-first VICTOIRE)
                                                         (person-first CHARLIE)
                                                         (person-first PERCY)
                                                         (person-first FRED)
                                                         (person-first GEORGE)
                                                         (person-first ALBUS)
                                                         (person-first JAMES)
                                                         (person-first LILY)))


;(define (same-wand--person person material) "false")  ;stub
;(define (same-wand--lop person material) "false")     ;stub

(define (same-wand--person person material)
  (if (have-wand material (person-wand person))
      (cons (person-first person) (same-wand--lop (person-children person) material))
      (same-wand--lop (person-children person) material)))
  

(define (same-wand--lop lop material)            
  (cond [(empty? lop) empty]
        [(have-wand material (person-wand (first lop)))
         (append (same-wand--person (first lop) material) (same-wand--lop (rest lop) material))]
        [else (append (same-wand--lop (person-children (first lop)) material) 
                      (same-wand--lop (rest lop) material))]))










