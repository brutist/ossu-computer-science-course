(require 2htdp/image)

;; image-list-starter.rkt

;; =================
;; Data definitions:

; 
; PROBLEM A:
; 
; Design a data definition to represent a list of images. Call it ListOfImage. 
; 


;; ListOfImage is one of:
;;   - empty
;;   - (cons Image ListOfImage)
;; interp. list of images

(define LOI-1 empty)
(define LOI-2 (cons (circle 20 "outline" "red") empty))
(define LOI-3 (cons (ellipse 20 10 "outline" "lightblue") (cons (circle 20 "outline" "red") empty)))


(define (fn-for-loi loi)
  (cond [(empty? loi) (...) ]
        [else (... (first loi)
              (fn-for-loi (rest loi)))]))

;; template rules used:
;;   - one of: 2 cases
;;   - atomic distinct: empty
;;   - compound: (cons Image ListOfImage)
;;   - self-reference: (rest loi) is ListOfString


;; =================
;; Functions:

; 
; PROBLEM B:
; 
; Design a function that consumes a list of images and produces a number 
; that is the sum of the areas of each image. For area, just use the image's 
; width times its height.
; 


;; ListOfImage -> Number
;; produce the sum of the areas of the images in the list, produces 0 if the list is empty
;; examples/tests
(check-expect (sum-area LOI-1) 0)
(check-expect (sum-area LOI-2) (+ (* 40 40) 0))
(check-expect (sum-area LOI-3) (+ (* 20 10) (* 40 40)))

;; stub
#;
(define (sum-area loi) 0.0002)


(define (sum-area loi)
  (cond [(empty? loi) 0 ]
        [else (+ (* (image-width (first loi)) (image-height (first loi)))
              (sum-area (rest loi)))]))

