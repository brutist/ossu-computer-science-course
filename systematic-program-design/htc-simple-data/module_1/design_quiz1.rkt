(require 2htdp/image)
;PROBLEM:

;Design a function that consumes two images and produces true if the first is larger than the second.

;Complete your design using DrRacket. When you are done, you must submit something
;in this box in order to unlock the assessment rubric, but when you are doing your assessment,
;grade your submission in DrRacket where indentation and formatting will be preserved.

;Be sure to watch the evaluation video before completing your assessment.


; 
; The HtDF recipe consists of the following steps:
; 1. Signature, purpose and stub.
; 2. Define examples, wrap each in check-expect.
; 3. Template and inventory.
; 4. Code the function body.
; 5. Test and debug until correct.

;; test images
(define test-img1 (rectangle 10 20 "solid" "blue"))
(define test-img2 (circle 20 "solid" "blue"))
(define test-img3 (square 20 "solid" "blue"))


;; signature -- Images (2) -> Boolean
;; purpose   -- identify which is larger from the two images
;; examples/tests
(check-expect (max_img test-img1 test-img2) #false)
(check-expect (max_img test-img1 test-img3) #false)
(check-expect (max_img test-img2 test-img3) #true)
(check-expect (max_img test-img3 test-img1) #true)

;; stub
;(define (max_img img1 img2) #false)


;; template
;(define (max_img img1 img2)
;  (... bool))

(define (max_img img1 img2)
  (if (> (* (image-height img1) (image-width img1)) (* (image-height img2) (image-width img2)))
      #true
      #false))
