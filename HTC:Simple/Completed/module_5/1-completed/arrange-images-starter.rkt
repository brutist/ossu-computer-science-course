(require 2htdp/image)

;; arrange-images-starter.rkt (problem statement)

; 
; PROBLEM:
; 
; In this problem imagine you have a bunch of pictures that you would like to 
; store as data and present in different ways. We'll do a simple version of that 
; here, and set the stage for a more elaborate version later.
; 
; (A) Design a data definition to represent an arbitrary number of images.
; 
; (B) Design a function called arrange-images that consumes an arbitrary number
;     of images and lays them out left-to-right in increasing order of size.
;     



;; === Constants ===

;; blank list
(define BLANK-IMG (square 0 "solid" "white"))
(define BLNK-LOI empty)
(define REC-1 (rectangle 10 60 "solid" "red"))
(define ELL-1 (ellipse 10 80 "outline" "blue"))
(define CIRC-1 (circle 10 "solid" "red"))
(define SQR-1 (square 50 "solid" "maroon"))




;; == Data Definitions ===

;; ListOfImage is one of:
;;   - empty
;;   - (cons Image ListOfImage)
;; interp. a list of images

(define LOI-1 empty)
(define LOI-2 (cons CIRC-1 empty))
(define LOI-3 (cons REC-1 (cons CIRC-1 (cons ELL-1 (cons SQR-1 empty)))))

(define (fn-for-loi loi)
  (cond [(empty? loi) (...)]                    ;base case
        [else (... (first loi)                  ;Image
                   (fn-for-loi (rest loi)))]))  ;natural recursion



;; === Functions ===


;; ListOfImage -> Image
;; lay out images left->right in an increasing size
;; examples/tests
(check-expect (arrange-images empty) BLANK-IMG)
(check-expect (arrange-images LOI-3)
              (beside CIRC-1
                      (beside REC-1
                              (beside ELL-1 SQR-1))))

(check-expect (arrange-images (cons (rectangle 1 6 "solid" "red")
                                    (cons (ellipse 1 8 "outline" "blue")
                                          (cons (circle 10 "solid" "red")
                                                (cons (square 50 "solid" "maroon")
                                                      empty)))))
              
              (beside (rectangle 1 6 "solid" "red")
                      (beside (ellipse 1 8 "outline" "blue")
                              (beside (circle 10 "solid" "red")
                                      (square 50 "solid" "maroon")))))


(check-expect (arrange-images (cons (rectangle 1 500 "solid" "red")
                                    (cons (ellipse 1 8 "outline" "blue")
                                          (cons (circle 10 "solid" "red")
                                                (cons (square 50 "solid" "maroon")
                                                      empty)))))
              
             
              (beside (ellipse 1 8 "outline" "blue")
                      (beside (circle 10 "solid" "red")
                              (beside (rectangle 1 500 "solid" "red")
                                      (square 50 "solid" "maroon")))))

;; stub
#;
(define (arrange-images loi) (circle 1 "solid" "white"))


(define (arrange-images loi)
  (loi->img (sort-images loi)))



;; Image ListOfImage -> ListOfImage
;; insert a given image to fit the ascending order of the list
;; examples/tests
(check-expect (insert (circle 10 "solid" "red") empty)
              (cons (circle 10 "solid" "red") empty))
(check-expect (insert (circle 40 "solid" "red")
                      (cons (circle 10 "solid" "red")
                            (cons (rectangle 10 60 "solid" "red") 
                                  empty)))

              (cons (circle 10 "solid" "red")
                    (cons (rectangle 10 60 "solid" "red")
                          (cons (circle 40 "solid" "red")
                                empty))))

(check-expect (insert (circle 5 "solid" "blue")
                      (cons (circle 10 "solid" "red")
                            (cons (rectangle 10 60 "solid" "green") 
                                  empty)))

              (cons (circle 5 "solid" "blue")
                    (cons (circle 10 "solid" "red")
                          (cons (rectangle 10 60 "solid" "green")
                                empty))))



(check-expect (insert (circle 10 "solid" "blue")
                      (cons (circle 5 "solid" "red")
                            (cons (rectangle 10 60 "solid" "green") 
                                  empty)))

              (cons (circle 5 "solid" "red")
                    (cons (circle 10 "solid" "blue")
                          (cons (rectangle 10 60 "solid" "green")
                                empty))))
#;
(define (insert i loi) loi) ;stub

(define (insert i loi)
  (cond [(empty? loi) (cons i empty)]
        [(> (area i) (area (first loi)))
         (cons (first loi) (insert i (rest loi)))]
        [else (cons i loi)]))



;; Image -> Number
;; calculates the area of the image
;; examples/tests
(check-expect (area ELL-1) (* (image-width ELL-1) (image-height ELL-1)))
(check-expect (area CIRC-1) (* (image-width CIRC-1) (image-height CIRC-1)))

;; (define (area i) 0) ;stub

(define (area i) (* (image-width i) (image-height i)))
            
          

;; ListOfImage -> Image
;; lays out all of the image in the list from left->right
;; examples/tests
(check-expect (loi->img empty) BLANK-IMG)
(check-expect (loi->img LOI-3)
              (beside REC-1
                      (beside CIRC-1
                              (beside ELL-1 SQR-1))))

;; (define (loi->img i) BLANK-IMG) ;stub

(define (loi->img loi)
  (cond [(empty? loi) BLANK-IMG]                  
        [else (beside (first loi)                  
                      (loi->img (rest loi)))]))  



;; ListOfImage -> ListOfImage
;; sorts the given LOI with increasing size
;; examples/tests
(check-expect (sort-images empty) empty)
(check-expect (sort-images (cons (circle 10 "solid" "red")
                                 (cons (rectangle 10 60 "solid" "red") empty))) 
              (cons (circle 10 "solid" "red")
                    (cons (rectangle 10 60 "solid" "red") empty)))

(check-expect (sort-images (cons (rectangle 10 60 "solid" "red")
                                 (cons (circle 10 "solid" "red") empty))) 
              (cons (circle 10 "solid" "red")
                    (cons (rectangle 10 60 "solid" "red") empty)))
              

#;
(define (sort-images loi) loi) ;stub


(define (sort-images loi)
  (cond [(empty? loi) empty]
        [else (insert (first loi)                  
                      (sort-images (rest loi)))])) 


