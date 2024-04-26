(require 2htdp/image)

;; decreasing-image-starter.rkt

;  PROBLEM:
;  
;  Design a function called decreasing-image that consumes a Natural n and produces an image of all the numbers 
;  from n to 0 side by side. 
;  
;  So (decreasing-image 3) should produce .



;; Natural -> Image
;; produces an image of all numbers from n to 0 laid side by side
;; examples/tests
(check-expect (int->img 4) (beside (text "4 " 16 "black")
                                   (beside (text "3 " 16 "black")
                                           (beside (text "2 " 16 "black")
                                                   (beside (text "1 " 16 "black")
                                                           (text "0" 16 "black"))))))

(check-expect (int->img 1) (beside (text "1 " 16 "black")
                                   (text "0" 16 "black")))

(check-expect (int->img 0) (text         "0" 16 "black"))

;; stub
#;
(define (int->img n) (square 0 "solid" "white"))

(define (int->img n)
  (cond [(zero? n) (text "0" 16 "black")]
        [else (beside (text (string-append (number->string n) " ") 16 "black")
                      (int->img (sub1 n)))]))

