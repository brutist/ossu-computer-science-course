;; ta-solver-starter.rkt



;  PROBLEM 1:
 
;  Consider a social network similar to Twitter called Chirper. Each user has a name, a note about
;  whether or not they are a verified user, and follows some number of people. 
 
;  Design a data definition for Chirper, including a template that is tail recursive and avoids 
;  cycles. 
 
;  Then design a function called most-followers which determines which user in a Chirper Network is 
;  followed by the most people.


;; Data Definition

(define-struct chirper (name note follows))
;; Chirper is (make-chirper String Note (listof Chirper))
;; interp. a user in the chirper network with name, 
;;         a verified note and the list of chirpers they follow

;; Note is one of:
;;  - "ver" means user is verified
;;  - "notver" means user is not verified
;; interp. a note that describes the verification status of a chirper
;; examples

(define CH1 (make-chirper "Josh" "ver" empty))
(define CH2 (shared ((-K- (make-chirper "Kenneth" "notver" (list -D-)))
                     (-D- (make-chirper "Danny" "ver" (list -A- -D-)))
                     (-A- (make-chirper "Axel" "ver" (list -K-))))
            -D-))

(define CH3P (shared ((-A- (make-chirper "Anna" "notver" (list -P- -Q-)))
                      (-P- (make-chirper "Panny" "ver" (list -A- -B-)))
                      (-Q- (make-chirper "Queen" "notver" (list -A- -P-)))
                      (-B- (make-chirper "Bobby" "ver" (list -A- -B- -C-)))
                      (-C- (make-chirper "Cathy" "ver" empty)))
            -P-))

(define CH3A (shared ((-A- (make-chirper "Anna" "notver" (list -P- -Q-)))
                      (-P- (make-chirper "Panny" "ver" (list -A- -B-)))
                      (-Q- (make-chirper "Queen" "notver" (list -A- -P-)))
                      (-B- (make-chirper "Bobby" "ver" (list -A- -B- -C-)))
                      (-C- (make-chirper "Cathy" "ver" empty)))
             -A-))


(define CH4 (shared ((-A- (make-chirper "Anna" "notver" (list -P- -Q-)))
                     (-P- (make-chirper "Panny" "ver" (list -A- -B-)))
                     (-Q- (make-chirper "Queen" "notver" (list -A- -P-)))
                     (-B- (make-chirper "Bobby" "ver" (list -A- -B- -C-)))
                     (-C- (make-chirper "Cathy" "ver" (list CH2 -A-))))
             -C-))

(define CH4A (shared ((-A- (make-chirper "Anna" "notver" (list -P- -Q-)))
                      (-P- (make-chirper "Panny" "ver" (list -A- -B-)))
                      (-Q- (make-chirper "Queen" "notver" (list -A- -P-)))
                      (-B- (make-chirper "Bobby" "ver" (list -A- -B- -C-)))
                      (-C- (make-chirper "Cathy" "ver" (list CH2 -A-))))
             -A-))  

#;
(define (fn-for-chirper ch)
    ;; todo is (listof Chirper); worklist accumulator
    ;; rfs is X??; result so far accumulator
    (local [(define (fn-for-chirper ch visited todo rfs)
                        (if (member (chirper-name ch) visited)
                            (fn-for-loch visited todo rfs)
                            (fn-for-loch (append visited (chirper-name ch)) 
                                         (append (chirper-follows ch) todo) 
                                         (... rfs))))

            (define (fn-for-loch visited todo rfs)
                        (cond [(empty? todo) rfs]
                              [else (fn-for-chirper (first todo) visited (rest todo) rfs)]))]
        
    
    (fn-for-chirper ch empty empty empty)))



;; === Functions ===

;; Chirper -> Chirper
;; produces the chiper that is followed by most people; 
;;      pick any of the tie in case of tie;
;;      produce (list) if the given chirper don't have follows
;; Assume: chirper-name is unique
;; examples/tests
(check-expect (most-followers CH1) (list))
(check-expect (most-followers CH2) CH2)
(check-expect (most-followers CH3P) CH3A)
(check-expect (most-followers CH3A) CH3A)
(check-expect (most-followers CH4) CH4A)

(define (most-followers ch)
    ;; todo is (listof Cfp); worklist accumulator
    ;; visited is (listof String); list of chirper names that has already been visited
    ;; rfs is (list of Cfp); result so far accumulator
    (local [(define-struct cfp (chirper n-followers))          ;; chirper-n-followers pair
            ;; Cfp is (make-cfp Chirper Natural)
            ;; interp. a Chirper with n number of followers
    
            ;; (listof Chirper) (listof Cfp) -> (listof Cfp)
            ;; insert the (listof Chirper) to the (listof Cfp), if the chirper-name is already in the
            ;;      locfp then it increases the n-followers of that chirper by 1
            (define (add-rfs loch0 rfs0)
                (local [(define (fn-for-loch loch rfs)
                            (cond [(empty? loch) rfs]
                                  [else (add-rfs (rest loch) (insert-ch (first loch) rfs))]))]
                
                (fn-for-loch loch0 rfs0)))
                   
            ;; Chirper (listof Cfp) -> (listof Cfp)
            ;; insert chirper to the (listof Cfp) if the chirper with same name is not in list,
            ;;      otherwise it increases the given chirper's name chirper-n-followers in the list
            (define (insert-ch ch0 locfp0)
                (local [(define (insert-ch ch locfp prevcfp)
                            (cond [(empty? locfp) (append prevcfp (list (make-cfp ch 1)))]
                                  [else (if (string=? (chirper-name ch) 
                                                      (chirper-name (cfp-chirper (first locfp))))
                                            (append prevcfp  
                                                    (list (make-cfp (cfp-chirper (first locfp)) 
                                                                    (add1 (cfp-n-followers (first locfp))))) 
                                                    (rest locfp))
                                            (insert-ch ch (rest locfp) 
                                                          (append prevcfp (list (first locfp)))))]))]
                
                (insert-ch ch0 locfp0 empty)))
            
            ;; (listof Cfp) -> Chirper
            ;; produces the chirper of the cfp in the list that has the biggest n-followers
            ;; Assume: the given locfp has at least one element
            (define (max-followers locfp0)
                (local [(define (max-followers locfp max-n-followers rfs)
                            (cond [(empty? locfp) rfs]
                                  [(> (cfp-n-followers (first locfp)) max-n-followers) 
                                    (max-followers (rest locfp) (cfp-n-followers (first locfp)) (cfp-chirper (first locfp)))]
                                  [else (max-followers (rest locfp) max-n-followers rfs)]))]
                
                (max-followers locfp0 0 empty)))

            (define (fn-for-chirper ch visited todo rfs)
                        (if (member (chirper-name ch) visited)
                            (fn-for-loch visited todo rfs)
                            (fn-for-loch (append visited (list (chirper-name ch))) 
                                         (append (chirper-follows ch) todo) 
                                         (add-rfs (chirper-follows ch) rfs))))

            (define (fn-for-loch visited todo rfs)
                        (cond [(empty? todo) (max-followers rfs)]
                              [else (fn-for-chirper (first todo) visited (rest todo) rfs)]))]
        
    
    (fn-for-chirper ch empty empty empty)))


