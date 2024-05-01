(require 2htdp/image)

;; fold-dir-starter.rkt

; In this exercise you will be need to remember the following DDs
; for an image organizer.

;; =================
;; Data definitions:

(define-struct dir (name sub-dirs images))
;; Dir is (make-dir String ListOfDir ListOfImage)
;; interp. An directory in the organizer, with a name, a list
;;         of sub-dirs and a list of images.

;; ListOfDir is one of:
;;  - empty
;;  - (cons Dir ListOfDir)
;; interp. A list of directories, this represents the sub-directories of
;;         a directory.

;; ListOfImage is one of:
;;  - empty
;;  - (cons Image ListOfImage)
;; interp. a list of images, this represents the sub-images of a directory.
;; NOTE: Image is a primitive type, but ListOfImage is not.

#;#;#;
(define (fn-for-dir dir)
  (... (dir-name) (fn-for-lod (dir-sub-dirs)) (fn-for-loi (dir-images))))

(define (fn-for-lod)
  (cond
    [(empty? lod) (...)]
    [else (... (fn-for-dir (first lod)) (fn-for-lod (rest lod)))]))

(define (fn-for-loi)
  (cond
    [(empty? loi) (...)]
    [else (... (first loi) (fn-for-loi (rest loi)))]))

(define I1 (square 10 "solid" "red"))
(define I2 (square 12 "solid" "green"))
(define I3 (rectangle 13 14 "solid" "blue"))
(define D4 (make-dir "D4" empty (list I1 I2)))
(define D5 (make-dir "D5" empty (list I3)))
(define D6 (make-dir "D6" (list D4 D5) empty))
(define D7 (make-dir "D7" empty empty))
(define D8 (make-dir "D8" (list D4 D5) (list I3)))
(define BLANK (square 0 "solid" "white"))

;; =================
;; Functions:

; PROBLEM A:
; Design an abstract fold function for Dir called fold-dir.

;; examples/tests
(check-expect (local [(define (list-names n ld li) (cons n ld))]
                     (fold-dir list-names append above empty BLANK D6))
              (list "D6" "D4" "D5"))
(check-expect (local [(define (c1 n ld li) (beside ld li))
                      (define (all-images i li) (beside i li))]
                     (fold-dir c1 all-images all-images BLANK BLANK D6))
              (beside BLANK I1 I2 I3))

;;         c1            c2             c3      b1   b2       
;; (String Y Z -> X) (X Y -> Y) (Image Z -> Z)  Y    Z    Dir -> X
;; abstract fold function for Directory

(define (fold-dir c1 c2 c3 b1 b2 d)
  (local [(define (fn-for-dir d)      ;-> X
            (c1 (dir-name d) (fn-for-lod (dir-sub-dirs d)) (fn-for-loi (dir-images d))))

          (define (fn-for-lod lod)    ;-> Y
            (cond
              [(empty? lod) b1]
              [else (c2 (fn-for-dir (first lod)) (fn-for-lod (rest lod)))]))

          (define (fn-for-loi loi)    ;-> Z
            (cond
              [(empty? loi) b2]
              [else (c3 (first loi) (fn-for-loi (rest loi)))]))]
    (fn-for-dir d)))


; PROBLEM B:
; Design a function that consumes a Dir and produces the number of
; images in the directory and its sub-directories.
; Use the fold-dir abstract function.

;; Dir -> Natural
;; produces the total number of images in the directory and its subs
;; examples/tests
(check-expect (count-images D6) 3)
(check-expect (count-images D4) 2)
(check-expect (count-images D5) 1)
(check-expect (count-images D7) 0)
(check-expect (count-images D8) 4)

(define (count-images dir) (local [(define (c1 n ld li) (+ ld li))
                                   (define (c3 i li) (+ 1 li))]
                                  (fold-dir c1 + c3 0 0 dir)))

; PROBLEM C:
; Design a function that consumes a Dir and a String. The function looks in
; dir and all its sub-directories for a directory with the given name. If it
; finds such a directory it should produce true, if not it should produce false.
; Use the fold-dir abstract function.

;; Dir String -> Boolean
;; produce true if the given s is a name of a dir
;; examples/tests
(check-expect (search-name D6 "D6") true)
(check-expect (search-name D7 "D5") false)
(check-expect (search-name D6 "D4") true)
(check-expect (search-name D8 "D6") false)

(define (search-name dir s) (local [(define (c1 n ld li) (or (string=? n s) ld))
                                    (define (c2 d ld)    (or d ld))
                                    (define (c3 i li)    i)]
                                    (fold-dir c1 c2 c3 false false dir)))


; PROBLEM D:
; Is fold-dir really the best way to code the function from part C? Why or
; why not?

;; Judging from the complexity of the fold-dir based search and the existence of andmap, i think 
;; that fold-dir may not be that efficient. This could be because of the c3's useless existence or 
;; the complexity may produce more recursion than necessary.