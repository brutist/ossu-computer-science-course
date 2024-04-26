(require 2htdp/image)

;; fs-starter.rkt (type comments and examples)
;; fs-v1.rkt (complete data-definition plus function problems)

;; Data definitions:

(define-struct elt (name data subs))
;; Element is (make-elt String Integer ListOfElement)
;; interp. An element in the file system, with name, and EITHER data or subs.
;;         If data is 0, then subs is considered to be list of sub elements.
;;         If data is not 0, then subs is ignored.

;; ListOfElement is one of:
;;  - empty
;;  - (cons Element ListOfElement)
;; interp. A list of file system Elements
; .

(define F1 (make-elt "F1" 1 empty))
(define F2 (make-elt "F2" 2 empty))
(define F3 (make-elt "F3" 3 empty))
(define D4 (make-elt "D4" 0 (list F1 F2)))
(define D5 (make-elt "D5" 0 (list F3)))
(define D6 (make-elt "D6" 0 (list D4 D5)))
#;
(define (fn-for-element e)
  (... (elt-name e)    ;String
       (elt-data e)    ;Integer
       (fn-for-loe (elt-subs e))))
#;
(define (fn-for-loe loe)
  (cond [(empty? loe) (...)]
        [else
         (... (fn-for-element (first loe))
              (fn-for-loe (rest loe)))])) 


;; Functions:

; 
; PROBLEM
; 
; Design a function that consumes Element and produces the sum of all the file data in 
; the tree.
; 


;; name ending with -- have unique name for both function
;; Element -> Integer
;; ListOfElement -> Integer
;; produce the sum of all the file data in the tree.
(check-expect (sum-data--element F1) 1)
(check-expect (sum-data--element D5) 3)
(check-expect (sum-data--element D4) (+ 1 2))
(check-expect (sum-data--element D6) (+ 1 2 3))
(check-expect (sum-data--loe empty) 0)

;(define (sum-data--element e) 0)  ;stub
;(define (sum-data--loe loe)   0)  ;stub


(define (sum-data--element e)
  (if (zero? (elt-data e))
      (sum-data--loe (elt-subs e))
      (elt-data e)))

(define (sum-data--loe loe)
  (cond [(empty? loe) 0]
        [else
         (+ (sum-data--element (first loe))
            (sum-data--loe (rest loe)))])) 


; 
; PROBLEM
; 
; Design a function that consumes Element and produces a list of the names of all the elements in 
; the tree. 
; 


;; Element -> ListOfString
;; ListOfElement -> ListOfString
;; produce a list of names of all the elements in the tree
;; examples/tests
(check-expect (list-names--element D6) (list "D6" "D4" "F1" "F2" "D5" "F3"))
(check-expect (list-names--element D4) (list "D4" "F1" "F2"))
(check-expect (list-names--element F1) (cons "F1" empty))
(check-expect (list-names--loe empty) empty)

;(define (sum-data--element e) 0)  ;stub
;(define (sum-data--loe loe)   0)  ;stub

(define (list-names--element e)
  (cons (elt-name e)       
        (list-names--loe (elt-subs e))))

(define (list-names--loe loe)
  (cond [(empty? loe) empty]
        [else
         (append (list-names--element (first loe))
                 (list-names--loe (rest loe)))])) 


; 
; PROBLEM
; 
; Design a function that consumes String and Element and looks for a data element with the given 
; name. If it finds that element it produces the data, otherwise it produces false.
; 


;; String Element -> String or False
;; String ListOfElement -> String or False
;; looks for the data element with given name; if it finds that element produce data otherwise produce false
;; examples/tests
(check-expect (find-data--element "D6" D6) 0)
(check-expect (find-data--element "F3" D6) 3)
(check-expect (find-data--element "D7" D6) false)
(check-expect (find-data--element "D4" D6) 0)
(check-expect (find-data--element "F1" F1) 1)
(check-expect (find-data--loe "D4" empty) false)  
(check-expect (find-data--loe "D6" empty) false)
(check-expect (find-data--element "D4" F1) false)
(check-expect (find-data--element "F1" F1) 1)
(check-expect (find-data--element "D4" D4) 0)
(check-expect (find-data--loe "F2" (cons F1 (cons F2 empty))) 2)
(check-expect (find-data--loe "F3" (cons F1 (cons F2 empty))) false)
(check-expect (find-data--element "F2" D6) 2)
(check-expect (find-data--element "D4" D6) 0)
(check-expect (find-data--element "F6" D6) false)

;(define (find-data--element name e) "")  ;stub
;(define (find-data--loe name loe)   "")  ;stub


(define (find-data--element name e)
  (if (string=? name (elt-name e))    
      (elt-data e)   
      (find-data--loe name (elt-subs e))))

(define (find-data--loe name loe)
  (cond [(empty? loe) false]
        [else
         (if (false? (find-data--element name (first loe)))
             (find-data--loe name (rest loe))
             (find-data--element name (first loe)))])) 

; 
; PROBLEM
; 
; Design a function that consumes Element and produces a rendering of the tree. For example: 
; 
; (render-tree D6) should produce something like the following.
; 
; .
; 
; HINTS:
;   - This function is not very different than the first two functions above.
;   - Keep it simple! Start with a not very fancy rendering like the one above.
;     Once that works you can make it more elaborate if you want to.
;   - And... be sure to USE the recipe. Not just follow it, but let it help you.
;     For example, work out a number of examples BEFORE you try to code the function. 
;     


;; CONSTANTS
(define BLANK (square 0 "solid" "white"))
(define FONT-SIZE 16)
(define FONT-COLOR "black")
(define HSPACE (rectangle 10 2 "solid" "white"))
(define VSPACE (rectangle 2 10 "solid" "white"))

;; Element -> Image
;; ListOfElements -> Image
;; ListOfElements -> Image
;; produces a simple rendering of an element tree.
;; examples/tests
(check-expect (render-tree--loe empty) BLANK)
(check-expect (render-tree--element F2) (above (text (elt-name F2) FONT-SIZE FONT-COLOR)
                                               BLANK))
(check-expect (render-tree--element D4)
              (above (text (elt-name D4) FONT-SIZE FONT-COLOR)
                     VSPACE
                     (beside (text (elt-name F1) FONT-SIZE FONT-COLOR)
                             HSPACE
                             (text (elt-name F2) FONT-SIZE FONT-COLOR))))
#;
(check-expect (render-tree--element D4)
              (above (text (elt-name D4) FONT-SIZE FONT-COLOR)
                     (lines (list (text (elt-name F1) FONT-SIZE FONT-COLOR)
                                  (text (elt-name F2) FONT-SIZE FONT-COLOR)
                                  (image-width (beside (text (elt-name F1) FONT-SIZE FONT-COLOR)
                                                       (rectangle (* 2 (image-width (text (elt-name F1) FONT-SIZE FONT-COLOR)))
                                                                  5 "outline" "white")
                                                       (text (elt-name F2) FONT-SIZE FONT-COLOR)))))
                     (beside (text (elt-name F1) FONT-SIZE FONT-COLOR)
                             (rectangle (* 2 (image-width (text (elt-name F1) FONT-SIZE FONT-COLOR)))
                                        5 "outline" "white")
                             (text (elt-name F2) FONT-SIZE FONT-COLOR))))


;(define (render-tree--loe loe) "false") ;stub             
;(define (render-tree--element elt) "false") ;stub


(define (render-tree--element e)
  (if (empty? (elt-subs e))
      (text (elt-name e) FONT-SIZE FONT-COLOR)
      (above (text (elt-name e) FONT-SIZE FONT-COLOR)   
             VSPACE
             (render-tree--loe (elt-subs e)))))

(define (render-tree--loe loe)
  (cond [(empty? loe) BLANK]
        [(empty? (rest loe))
         (render-tree--element (first loe))]
        [else
         (beside (render-tree--element (first loe))
                 HSPACE
                 (render-tree--loe (rest loe)))])) 

