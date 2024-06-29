(require 2htdp/image)
;  PROBLEM 1:
;
;  Design an abstract function called arrange-all to simplify the
;  above-all and beside-all functions defined below. Rewrite above-all and
;  beside-all using your abstract function.

;; abstract function for arranging (listof Image)
(check-expect (arrange-all overlay
                           empty-image
                           (list (regular-polygon 20 5 "solid" (make-color 50 50 255))
                                 (regular-polygon 26 5 "solid" (make-color 100 100 255))
                                 (regular-polygon 32 5 "solid" (make-color 150 150 255))
                                 (regular-polygon 38 5 "solid" (make-color 200 200 255))
                                 (regular-polygon 44 5 "solid" (make-color 250 250 255))))
              (overlay (regular-polygon 20 5 "solid" (make-color 50 50 255))
                       (regular-polygon 26 5 "solid" (make-color 100 100 255))
                       (regular-polygon 32 5 "solid" (make-color 150 150 255))
                       (regular-polygon 38 5 "solid" (make-color 200 200 255))
                       (regular-polygon 44 5 "solid" (make-color 250 250 255))))

(define (arrange-all fn b loi)
  (cond
    [(empty? loi) b]
    [else (fn (first loi) (arrange-all fn b (rest loi)))]))


;; (listof Image) -> Image
;; combines a list of images into a single image, each image above the next one
(check-expect (above-all empty) empty-image)
(check-expect (above-all (list (rectangle 20 40 "solid" "red") (star 30 "solid" "yellow")))
              (above (rectangle 20 40 "solid" "red") (star 30 "solid" "yellow")))
(check-expect
 (above-all
  (list (circle 30 "outline" "black") (circle 50 "outline" "black") (circle 70 "outline" "black")))
 (above (circle 30 "outline" "black") (circle 50 "outline" "black") (circle 70 "outline" "black")))

;(define (above-all loi) empty-image)  ;stub

(define (above-all loi) (arrange-all above empty-image loi))

;; (listof Image) -> Image
;; combines a list of images into a single image, each image beside the next one
(check-expect (beside-all empty) (rectangle 0 0 "solid" "white"))
(check-expect (beside-all (list (rectangle 50 40 "solid" "blue") (triangle 30 "solid" "pink")))
              (beside (rectangle 50 40 "solid" "blue") (triangle 30 "solid" "pink")))
(check-expect
 (beside-all
  (list (circle 10 "outline" "red") (circle 20 "outline" "blue") (circle 10 "outline" "yellow")))
 (beside (circle 10 "outline" "red") (circle 20 "outline" "blue") (circle 10 "outline" "yellow")))

;(define (beside-all loi) empty-image)  ;stub

(define (beside-all loi) (arrange-all beside empty-image loi))




;  PROBLEM 2:
;  Finish the design of the following functions, using built-in abstract functions.

;; Function 1
;; ==========

;; (listof String) -> (listof Natural)
;; produces a list of the lengths of each string in los
(check-expect (lengths empty) empty)
(check-expect (lengths (list "apple" "banana" "pear")) (list 5 6 4))

(define (lengths lst) (map string-length lst))

;; Function 2
;; ==========

;; (listof Natural) -> (listof Natural)
;; produces a list of just the odd elements of lon
(check-expect (odd-only empty) empty)
(check-expect (odd-only (list 1 2 3 4 5)) (list 1 3 5))

(define (odd-only lon) (filter odd? lon))

;; Function 3
;; ==========

;; (listof Natural -> Boolean
;; produce true if all elements of the list are odd
(check-expect (all-odd? empty) true)
(check-expect (all-odd? (list 1 2 3 4 5)) false)
(check-expect (all-odd? (list 5 5 79 13)) true)

(define (all-odd? lon) (andmap odd? lon))

;; Function 4
;; ==========

;; (listof Natural) -> (listof Natural)
;; subtracts n from each element of the list
(check-expect (minus-n empty 5) empty)
(check-expect (minus-n (list 4 5 6) 1) (list 3 4 5))
(check-expect (minus-n (list 10 5 7) 4) (list 6 1 3))

(define (minus-n lon n) (local [(define (decr-n val) (- val n))]
                                (map decr-n lon)))

;  PROBLEM 3
;
;  Consider the data definition below for Region. Design an abstract fold function for region,
;  and then use it do design a function that produces a list of all the names of all the
;  regions in that region.
;
;  For consistency when answering the multiple choice questions, please order the arguments in your
;  fold function with combination functions first, then bases, then region. Please number the bases
;  and combination functions in order of where they appear in the function.
;
;  So (all-regions CANADA) would produce
;  (list "Canada" "British Columbia" "Vancouver" "Victoria" "Alberta" "Calgary" "Edmonton")

(define-struct region (name type subregions))
;; Region is (make-region String Type (listof Region))
;; interp. a geographical region

;; Type is one of:
;; - "Continent"
;; - "Country"
;; - "Province"
;; - "State"
;; - "City"
;; interp. categories of geographical regions

(define VANCOUVER (make-region "Vancouver" "City" empty))
(define VICTORIA (make-region "Victoria" "City" empty))
(define BC (make-region "British Columbia" "Province" (list VANCOUVER VICTORIA)))
(define CALGARY (make-region "Calgary" "City" empty))
(define EDMONTON (make-region "Edmonton" "City" empty))
(define ALBERTA (make-region "Alberta" "Province" (list CALGARY EDMONTON)))
(define CANADA (make-region "Canada" "Country" (list BC ALBERTA)))

#;(define (fn-for-region r)
    (local [(define (fn-for-region r)
              (... (region-name r) (fn-for-type (region-type r)) (fn-for-lor (region-subregions r))))
            (define (fn-for-type t)
              (cond
                [(string=? t "Continent") (...)]
                [(string=? t "Country") (...)]
                [(string=? t "Province") (...)]
                [(string=? t "State") (...)]
                [(string=? t "City") (...)]))
            (define (fn-for-lor lor)
              (cond
                [(empty? lor) (...)]
                [else (... (fn-for-region (first lor)) (fn-for-lor (rest lor)))]))]
           (fn-for-region r)))

;;         c1             c2     b1   b2   b3   b4   b5   b6
;; (String Y Z -> X) (X Z -> Z)  Y    Y    Y    Y    Y    Z  -> X
;; abstract fold function for Region
;; examples/tests
(check-expect (local [(define (all-names n t lr) (cons n lr))
                      (define b1 "Continent")
                      (define b2 "Country")
                      (define b3 "Province")
                      (define b4 "State")
                      (define b5 "City")
                      (define b6 empty)]
                     (fold-region all-names append b1 b2 b3 b4 b5 b6 CANADA))
              (list "Canada" "British Columbia" "Vancouver" "Victoria" "Alberta" "Calgary" "Edmonton"))

(check-expect (local [(define (all-types n t lr) (cons t lr))
                      (define b1 "Continent")
                      (define b2 "Country")
                      (define b3 "Province")
                      (define b4 "State")
                      (define b5 "City")
                      (define b6 empty)]
                     (fold-region all-types append b1 b2 b3 b4 b5 b6 CANADA))
              (list "Country" "Province" "City" "City" "Province" "City" "City"))

(define (fold-region c1 c2 b1 b2 b3 b4 b5 b6 r)
  (local [(define (fn-for-region r)         ;-> X
            (c1 (region-name r) (fn-for-type (region-type r)) (fn-for-lor (region-subregions r))))
          (define (fn-for-type t)           ;-> Y
            (cond
              [(string=? t "Continent") b1]
              [(string=? t "Country") b2]
              [(string=? t "Province") b3]
              [(string=? t "State") b4]
              [(string=? t "City") b5]))
          (define (fn-for-lor lor)          ;-> Z
            (cond
              [(empty? lor) b6]
              [else (c2 (fn-for-region (first lor)) (fn-for-lor (rest lor)))]))]
    (fn-for-region r)))



;; Region -> (listOf String)
;; produces a list of region's names in the given region
;; exmaples/tests
(check-expect (all-names CANADA) 
              (list "Canada" "British Columbia" "Vancouver" "Victoria" "Alberta" "Calgary" "Edmonton"))
(check-expect (all-names VANCOUVER) 
              (list "Vancouver"))
(check-expect (all-names ALBERTA) 
              (list "Alberta" "Calgary" "Edmonton"))

(check-expect (all-names BC) 
              (list "British Columbia" "Vancouver" "Victoria"))


(define (all-names r) (local [(define (all-names n t lr) (cons n lr))
                      (define b1 "Continent")
                      (define b2 "Country")
                      (define b3 "Province")
                      (define b4 "State")
                      (define b5 "City")
                      (define b6 empty)]
                     (fold-region all-names append b1 b2 b3 b4 b5 b6 r)))
             