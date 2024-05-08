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
#;(define (fn-for-element e)
    (... (elt-name e) ;String
         (elt-data e) ;Integer
         (fn-for-loe (elt-subs e))))
#;(define (fn-for-loe loe)
    (cond
      [(empty? loe) (...)]
      [else (... (fn-for-element (first loe)) (fn-for-loe (rest loe)))]))

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
(check-expect (render-tree F2) (above (text (elt-name F2) FONT-SIZE FONT-COLOR) BLANK))
(check-expect (render-tree D4)
              (above (text (elt-name D4) FONT-SIZE FONT-COLOR)
                     VSPACE
                     (beside (text (elt-name F1) FONT-SIZE FONT-COLOR)
                             HSPACE
                             (text (elt-name F2) FONT-SIZE FONT-COLOR))))
#;
(check-expect
 (render-tree--element D4)
 (above (text (elt-name D4) FONT-SIZE FONT-COLOR)
        (lines (list (text (elt-name F1) FONT-SIZE FONT-COLOR)
                     (text (elt-name F2) FONT-SIZE FONT-COLOR)
                     (image-width
                      (beside (text (elt-name F1) FONT-SIZE FONT-COLOR)
                              (rectangle (* 2 (image-width (text (elt-name F1) FONT-SIZE FONT-COLOR)))
                                         5
                                         "outline"
                                         "white")
                              (text (elt-name F2) FONT-SIZE FONT-COLOR)))))
        (beside
         (text (elt-name F1) FONT-SIZE FONT-COLOR)
         (rectangle (* 2 (image-width (text (elt-name F1) FONT-SIZE FONT-COLOR))) 5 "outline" "white")
         (text (elt-name F2) FONT-SIZE FONT-COLOR))))

;(define (render-tree--loe loe) "false") ;stub
;(define (render-tree--element elt) "false") ;stub

(define (render-tree e)
  (local [(define (render-tree--element e)
      (if (empty? (elt-subs e))
          (text (elt-name e) FONT-SIZE FONT-COLOR)
          (above (text (elt-name e) FONT-SIZE FONT-COLOR) 
                 VSPACE 
                 (render-tree--loe (elt-subs e)))))
    (define (render-tree--loe loe)
      (local [(define render-first (render-tree--element (first loe)))]
      (cond
        [(empty? loe) BLANK]
        [(empty? (rest loe)) render-first]
        [else (beside render-first 
                      HSPACE 
                      (render-tree--loe (rest loe)))])))]
   (render-tree--element e)))
