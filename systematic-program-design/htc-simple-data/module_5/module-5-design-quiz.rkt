(require 2htdp/image)
;; SPD2-Design-Quiz-1.rkt


;; ======================================================================
;; Constants (a placeholder to avoid issues with github and VScode
(define COOKIES (overlay (rectangle 20 40 "outline" "black")
                         (rectangle 20 40 "solid" "maroon")))

(define BLANK (square 0 "solid" "white"))

;; ======================================================================
;; Data Definitions

;; Natural is one of:
;;  - 0
;;  - (add1 Natural)
;; interp. a natural number
(define N0 0)         ;0
(define N1 (add1 N0)) ;1
(define N2 (add1 N1)) ;2

#;
(define (fn-for-natural n)
  (cond [(zero? n) (...)]
        [else
         (... n   ; n is added because it's often useful                   
              (fn-for-natural (sub1 n)))]))

;; Template rules used:
;;  - one-of: two cases
;;  - atomic distinct: 0
;;  - compound: 2 fields
;;  - self-reference: (sub1 n) is Natural




; PROBLEM 1:
; 
; Complete the design of a function called pyramid that takes a natural
; number n and an image, and constructs an n-tall, n-wide pyramid of
; copies of that image.
; 
; For instance, a 3-wide pyramid of cookies would look like this:
; 
; .



;; Natural Image -> Image
;; produce an n-wide and n-height pyramid of the given image
(check-expect (pyramid 0 COOKIES) empty-image)
(check-expect (pyramid 1 COOKIES) COOKIES)
(check-expect (pyramid 3 COOKIES)
              (above COOKIES
                     (beside COOKIES COOKIES)
                     (beside COOKIES COOKIES COOKIES)))

#;
(define (pyramid n i) empty-image) ; stub

(define (pyramid n i)
  (cond [(zero? n) BLANK]
        [else (above (pyramid (sub1 n) i)
                     (stack n i))]))



;; Natural Image -> Image
;; produce an n-wide array image of the given image
;; examples/tests
(check-expect (stack 1 COOKIES) COOKIES)
(check-expect (stack 2 COOKIES) (beside COOKIES
                                        COOKIES))
(check-expect (stack 3 COOKIES) (beside COOKIES
                                        COOKIES
                                        COOKIES))
#;
(define (stack n img) BLANK) ;stub

(define (stack n img)
  (cond [(zero? n) BLANK]
        [else
         (beside COOKIES (stack (sub1 n) img))]))
  


; Problem 2:
; Consider a test tube filled with solid blobs and bubbles.  Over time the
; solids sink to the bottom of the test tube, and as a consequence the bubbles
; percolate to the top.  Let's capture this idea in BSL.
; 
; Complete the design of a function that takes a list of blobs and sinks each
; solid blob by one. It's okay to assume that a solid blob sinks past any
; neighbor just below it.
; 
; To assist you, we supply the relevant data definitions.


;; Blob is one of:
;; - "solid"
;; - "bubble"
;; interp.  a gelatinous blob, either a solid or a bubble
;; Examples are redundant for enumerations
#;
(define (fn-for-blob b)
  (cond [(string=? b "solid") (...)]
        [(string=? b "bubble") (...)]))

;; Template rules used:
;; - one-of: 2 cases
;; - atomic distinct: "solid"
;; - atomic distinct: "bubble"


;; ListOfBlob is one of:
;; - empty
;; - (cons Blob ListOfBlob)
;; interp. a sequence of blobs in a test tube, listed from top to bottom.
(define LOB0 empty) ; empty test tube
(define LOB2 (cons "solid" (cons "bubble" empty))) ; solid blob above a bubble

#;
(define (fn-for-lob lob)
  (cond [(empty? lob) (...)]
        [else
         (... (fn-for-blob (first lob))
              (fn-for-lob (rest lob)))]))

;; Template rules used
;; - one-of: 2 cases
;; - atomic distinct: empty
;; - compound: 2 fields
;; - reference: (first lob) is Blob
;; - self-reference: (rest lob) is ListOfBlob



;; Blob ListOfBlob -> ListOfBlob
;; insert the given blob to the ListOfBlob
;;    - "bubble" blobs inserted as the first element
;;    - "solid" blobs displace a bubble by one 
;; examples/tests
(check-expect (insert-blob "solid" empty) (cons "solid" empty))
(check-expect (insert-blob "bubble" empty) (cons "bubble" empty))

(check-expect (insert-blob "bubble" (cons "bubble" (cons "solid" empty)))
              (cons "bubble" (cons "bubble" (cons "solid" empty)))) 
(check-expect (insert-blob "solid" (cons "bubble" (cons "solid" empty)))
              (cons "bubble" (cons "solid" (cons "solid" empty))))
(check-expect (insert-blob "solid" (cons "bubble" (cons "bubble" empty)))
              (cons "bubble" (cons "solid" (cons "bubble" empty))))


(define (insert-blob b lob)
  (cond [(empty? lob) (cons b lob)]
        [(string=? b "solid") (cons (first lob) (cons b (rest lob))) ]
        [(string=? b "bubble") (cons b lob)]))



;; ListOfBlob -> ListOfBlob
;; produce a list of blobs that sinks the given solid blobs by one

(check-expect (sink empty) empty)
(check-expect (sink (cons "bubble" (cons "solid" (cons "bubble" empty))))
              (cons "bubble" (cons "bubble" (cons "solid" empty))))
(check-expect (sink (cons "solid" (cons "solid" (cons "bubble" empty))))
              (cons "bubble" (cons "solid" (cons "solid" empty))))
(check-expect (sink (cons "solid" (cons "bubble" (cons "bubble" empty))))
              (cons "bubble" (cons "solid" (cons "bubble" empty))))
(check-expect (sink (cons "solid" (cons "bubble" (cons "solid" empty))))
              (cons "bubble" (cons "solid" (cons "solid" empty))))
(check-expect (sink (cons "bubble" (cons "solid" (cons "solid" empty))))
              (cons "bubble" (cons "solid" (cons "solid" empty))))
(check-expect (sink (cons "solid"
                          (cons "solid"
                                (cons "bubble" (cons "bubble" empty)))))
              (cons "bubble" (cons "solid" 
                                   (cons "solid" (cons "bubble" empty)))))
#;
(define (sink lob) empty) ; stub

(define (sink lob)
  (cond [(empty? lob) empty]
        [else
         (insert-blob (first lob) (sink (rest lob)))]))
  