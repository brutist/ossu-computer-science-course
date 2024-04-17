(require 2htdp/image)
;; tuition-graph-starter.rkt  (just the problem statements)

; 
; PROBLEM:
; 
; Eva is trying to decide where to go to university. One important factor for her is 
; tuition costs. Eva is a visual thinker, and has taken Systematic Program Design, 
; so she decides to design a program that will help her visualize the costs at 
; different schools. She decides to start simply, knowing she can revise her design
; later.
; 
; The information she has so far is the names of some schools as well as their 
; international student tuition costs. She would like to be able to represent that
; information in bar charts like this one:
; 
; 
;         .
;         
; (A) Design data definitions to represent the information Eva has.
; (B) Design a function that consumes information about schools and their
;     tuition and produces a bar chart.
; (C) Design a function that consumes information about schools and produces
;     the school with the lowest international student tuition.
; 


;; === Constants ===

;; bar graph
(define BAR-COLOR "lightblue")
(define BAR-WIDTH 30)
(define Y-SCALE 1/30)

;; fonts
(define FONT-COLOR "black")
(define FONT-SIZE 16)


;; === Data Definitions ===

(define-struct school (nm ttn))
;; School is (make-school String Number)
;; interp. a school with <nm> and <ttn> tuition in USD

(define SCHOOL-1 (make-school "Cebu Normal University" 8000))
(define SCHOOL-2 (make-school "Saint Louis College" 15000))
(define SCHOOL-3 (make-school "San Carlos University" 10000))

(define (fn-for-school sc)
  (... (school-nm sc)      ;String
       (school-ttn sc)))   ;Number

;; template rules used:
;;   - compound: (make-school String Number)




;; ListOfSchool is one of:
;;   - empty
;;   - (cons School ListOfSchool)
;; interp. a list of schools

(define LOS-1 empty)
(define LOS-2 (cons SCHOOL-1 empty))
(define LOS-3 (cons SCHOOL-2 (cons SCHOOL-1 empty)))
(define LOS-4 ( cons SCHOOL-3 (cons SCHOOL-2 (cons SCHOOL-1 empty))))


(define (fn-for-los los)
  (cond [(empty los) (...)]                      ;base case
        [else (... (fn-for-school (first los))   ;natural helper
                   (fn-for-los (rest los)))]))   ;natural recursion

;; template rules used:
;;  - one of: 2 cases
;;  - atomic distinct: empty
;;  - compound: (cons School ListOfSchool)
;;  - reference: (first los) is School
;;  - self-reference: (res los) is ListOfSchool




;; === Functions ===

;; School -> Image
;; produces an image with a black outline and the name of the school with a BAR-COLOR fill
;; examples/tests
(check-expect (bar SCHOOL-1)
              (overlay/align "middle" "bottom"
                             (rotate 90 (text (school-nm SCHOOL-1) FONT-SIZE FONT-COLOR))
                             (rectangle BAR-WIDTH (* Y-SCALE (school-ttn SCHOOL-1)) "outline" "black" )
                             (rectangle BAR-WIDTH (* Y-SCALE (school-ttn SCHOOL-1)) "solid" BAR-COLOR )))


;; stub
#;
(define (bar sc) (square 0 "solid" "white"))

(define (bar sc)
  (overlay/align "middle" "bottom"
                  (rotate 90 (text (school-nm sc) FONT-SIZE FONT-COLOR))
                  (rectangle BAR-WIDTH (* Y-SCALE (school-ttn sc)) "outline" "black" )
                  (rectangle BAR-WIDTH (* Y-SCALE (school-ttn sc)) "solid" BAR-COLOR )))   


;; ListOfSchool -> Image
;; produce a bar graph with the schools in the x-axis and the (Y-SCALE * tuition) in y-axis,
;;           produces an empty image if the list is empty
;; examples/tests
(check-expect (bar-graph LOS-1)
              (square 0 "solid" "white"))
(check-expect (bar-graph LOS-2)
              (beside/align "bottom"
                            (overlay/align "middle" "bottom"
                                           (rotate 90 (text (school-nm SCHOOL-1) FONT-SIZE FONT-COLOR))
                                           (rectangle BAR-WIDTH (* Y-SCALE (school-ttn SCHOOL-1)) "outline" "black" )
                                           (rectangle BAR-WIDTH (* Y-SCALE (school-ttn SCHOOL-1)) "solid" BAR-COLOR ))
                            (square 0 "solid" "white")))


(check-expect (bar-graph LOS-4)
              (beside/align "bottom"
                            (overlay/align "middle" "bottom"
                                           (rotate 90 (text (school-nm SCHOOL-3) FONT-SIZE FONT-COLOR))
                                           (rectangle BAR-WIDTH (* Y-SCALE (school-ttn SCHOOL-3)) "outline" "black" )
                                           (rectangle BAR-WIDTH (* Y-SCALE (school-ttn SCHOOL-3)) "solid" BAR-COLOR ))

                            (overlay/align "middle" "bottom"
                                           (rotate 90 (text (school-nm SCHOOL-2) FONT-SIZE FONT-COLOR))
                                           (rectangle BAR-WIDTH (* Y-SCALE (school-ttn SCHOOL-2)) "outline" "black" )
                                           (rectangle BAR-WIDTH (* Y-SCALE (school-ttn SCHOOL-2)) "solid" BAR-COLOR ))

                            (overlay/align "middle" "bottom"
                                           (rotate 90 (text (school-nm SCHOOL-1) FONT-SIZE FONT-COLOR))
                                           (rectangle BAR-WIDTH (* Y-SCALE (school-ttn SCHOOL-1)) "outline" "black" )
                                           (rectangle BAR-WIDTH (* Y-SCALE (school-ttn SCHOOL-1)) "solid" BAR-COLOR ))
                            (square 0 "solid" "white")))


;; stub
#;
(define (bar-graph los) (square 0 "solid" "white"))


(define (bar-graph los)
  (cond [(empty? los) (square 0 "solid" "white")]                    
        [else (beside/align "bottom"
                            (bar (first los))  
                            (bar-graph (rest los)))]))   

