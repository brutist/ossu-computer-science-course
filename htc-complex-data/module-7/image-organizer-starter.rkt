(require 2htdp/image)

;; image-organizer-starter.rkt

; 
; PROBLEM:
; 
; Complete the design of a hierarchical image organizer.  The information and data
; for this problem are similar to the file system example in the fs-starter.rkt file. 
; But there are some key differences:
;   - this data is designed to keep a hierchical collection of images
;   - in this data a directory keeps its sub-directories in a separate list from
;     the images it contains
;   - as a consequence data and images are two clearly separate types
;   
; Start by carefully reviewing the partial data definitions below.  
; 


;; =================
;; Constants:


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

(define I1 (square 10 "solid" "red"))
(define I2 (square 10 "solid" "green"))
(define I3 (rectangle 13 14 "solid" "blue"))
(define D1 (make-dir "D1" empty        empty))
(define D2 (make-dir "D2" empty        (list I1)))
(define D3 (make-dir "D3" (list D2)    empty))
(define D4 (make-dir "D4" (list D2)    (list I1 I2)))
(define D5 (make-dir "D5" empty        (list I3)))
(define D6 (make-dir "D6" (list D4 D5) empty))
; 
; PART A:
; 
; Annotate the type comments with reference arrows and label each one to say 
; whether it is a reference, self-reference or mutual-reference.
; 
;;                                                                Legends
;;                                                                 - MR  <mutual reference>
;;                                                                 - SR  <self-reference>
;; Dir is (make-dir String ListOfDir ListOfImage) -------\           
;;    \                      /               \            \ MR    
;;     \                    / MR              \            \   
;;      \ MR               /                   \            \ListOfImage is one of:
;;       \           ListOfDir is one of:       \                       \
;;        \                    \                 \                          \ SR
;;         \                      \  SR           \                - empty     \   
;;          \           - empty      \             \               - (cons Image ListOfImage)
;;           \          - (cons Dir ListOfDir)      \                                   /
;;            \__________________/                   \_________________________________/
;;                                                                  MR
;; 

; PART B:
; 
; Write out the templates for Dir, ListOfDir and ListOfImage. Identify for each 
; call to a template function which arrow from part A it corresponds to.

#;
(define (fn-for-dir d)
  (... (dir-name)            ;String
       (fn-for-lod (dir-sub-dirs))
       (fn-for-loi (dir-images))))
#;
(define (fn-for-lod lod)
  (cond [(empty? lod) (...)]
        [else 
         (... (fn-for-dir (first lod))
              (fn-for-lod (rest lod)))]))
#;
(define (fn-for-loi loi)
  (cond [(empty? loi) (...)]
        [else 
         (... (first loi)   ;Image
              (fn-for-loi (rest lod)))]))


;; =================
;; Functions:
  
; 
; PROBLEM B:
; 
; Design a function to calculate the total size (width* height) of all the images 
; in a directory and its sub-directories.
; 



;; Dir          -> Natural
;; ListOfDir    -> Natural
;; ListOfImages -> Natural
;; produce the total size of all the images in a directory and its sub-directories
;; examples/tests
(check-expect (total-img-area--lod empty) 0)
(check-expect (total-img-area--lod (list D4 D5)) 
              (+ (* (image-height I1) (image-width I1)) 
                 (* (image-height I1) (image-width I1)) 
                 (* (image-height I2) (image-width I2))
                 (* (image-height I3) (image-width I3))))
(check-expect (total-img-area--img empty) 0)
(check-expect (total-img-area--img (list I1 I2)) 
              (+ (* (image-height I1) (image-width I1)) 
                 (* (image-height I2) (image-width I2))))                                    
(check-expect (total-img-area--dir D1) 0)
(check-expect (total-img-area--dir D2) (* (image-height I1) (image-width I1)))
(check-expect (total-img-area--dir D3) (* (image-height I1) (image-width I1)))
(check-expect (total-img-area--dir D4) (+ (* (image-height I1) (image-width I1))
                                          (* (image-height I1) (image-width I1))
                                          (* (image-height I2) (image-width I2))))
(check-expect (total-img-area--dir D6) (+ (* (image-height I3) (image-width I3)) 
                                          (* (image-height I1) (image-width I1)) 
                                          (* (image-height I1) (image-width I1))
                                          (* (image-height I2) (image-width I2))))

;(define (total-img-area--dir d) "false")  ;stub
;(define (total-img-area--lod d) "false")  ;stub
;(define (total-img-area--img d) "false")  ;stub

(define (total-img-area--dir d)
  (+ (total-img-area--img (dir-images d))        
     (total-img-area--lod (dir-sub-dirs d))))

(define (total-img-area--lod lod)
  (cond [(empty? lod) 0]
        [else 
         (+ (total-img-area--dir (first lod))
            (total-img-area--lod (rest lod)))]))

(define (total-img-area--img loi)
  (cond [(empty? loi) 0]
        [else 
         (+ (* (image-width (first loi)) (image-height (first loi)))   ;Image
            (total-img-area--img (rest loi)))]))



; 
; PROBLEM C:
; 
; Design a function to produce rendering of a directory with its images. Keep it 
; simple and be sure to spend the first 10 minutes of your work with paper and 
; pencil!
; 


