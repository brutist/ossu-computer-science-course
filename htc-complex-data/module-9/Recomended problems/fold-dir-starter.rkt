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
(define BLANK (square 0 "solid" "white"))

;; =================
;; Functions:

; PROBLEM A:
; Design an abstract fold function for Dir called fold-dir.

;; examples/tests
(check-expect (local [(define (list-names n ld li) (append (list n) ld))]
                     (fold-dir list-names append beside empty BLANK D6))
              (list "D6" "D4" "D5"))


;; (String X Y -> X, Y or Z) (Dir (listof Dir) -> X) (Image (listof Image) -> Y) X Y Dir -> X, Y or Z
;; abstract fold function for Directory
(define (fold-dir fn c1 c2 b1 b2 dir)
  (fn (dir-name dir)
      (local [(define (fn-for-lod lod)
                (cond
                  [(empty? lod) b1]
                  [else (c1 (fold-dir fn c1 c2 b1 b2 (first lod)) (fn-for-lod (rest lod)))]))]
             (fn-for-lod (dir-sub-dirs dir))) ;-> X
      (local [(define (fn-for-loi loi)
                (cond
                  [(empty? loi) b2]
                  [else (c2 (first loi) (fn-for-loi (rest loi)))]))]
             (fn-for-loi (dir-images dir))))) ;-> Y

;
; PROBLEM B:

; Design a function that consumes a Dir and produces the number of
; images in the directory and its sub-directories.
; Use the fold-dir abstract function.


;
; PROBLEM C:
;
; Design a function that consumes a Dir and a String. The function looks in
; dir and all its sub-directories for a directory with the given name. If it
; finds such a directory it should produce true, if not it should produce false.
; Use the fold-dir abstract function.
;

;
; PROBLEM D:
;
; Is fold-dir really the best way to code the function from part C? Why or
; why not?
;
