;; local-design-quiz.rkt


; Problem 1:
; 
; Suppose you have rosters for players on two opposing tennis team, and each
; roster is ordered by team rank, with the best player listed first. When both 
; teams play, the best players of each team play one another,
; and the second-best players play one another, and so on down the line. When
; one team has more players than the other, the lowest ranking players on
; the larger team do not play.
; 
; Design a function that consumes two rosters, and produces true if all players 
; on both teams will play if the teams play each other. 
; No marks will be given to solution that do not use a cross product table. 
; 


;; Player is String
;; interp.  the name of a tennis player.
(define P0 "Maria")
(define P2 "Serena")

#;
(define (fn-for-player p)
  (... p))



;; Roster is one of:
;; - empty
;; - (cons Player Roster)
;; interp.  a team roster, ordered from best player to worst.
(define R0 empty)
(define R1 (list "Eugenie" "Gabriela" "Sharon" "Aleksandra"))
(define R2 (list "Maria" "Nadia" "Elena" "Anastasia" "Svetlana"))

#;
(define (fn-for-roster r)
  (cond [(empty? r) (...)]
        [else 
         (... (fn-for-player (first r))
              (fn-for-roster (rest r)))]))



(define-struct match (p1 p2))
;; Match is (make-match Player Player)
;; interp.  a match between player p1 and player p2, with same team rank
(define M0 (make-match "Eugenie" "Maria"))
(define M1 (make-match "Gabriela" "Nadia"))

#;
(define (fn-for-match m)
  (... (match-p1 m) (match-p2 m)))



;; ListOfMatch is one of:
;; - empty
;; - (cons Match ListOfMatch)
;; interp. a list of matches between one team and another.
(define LOM0 empty)
(define LOM1 (list (make-match "Eugenie" "Maria")
                   (make-match "Gabriela" "Nadia")))

#;
(define (fn-for-lom lom)
  (cond [(empty? lom) (...)]
        [else
         (... (fn-for-match (first lom))
              (fn-for-lom (rest lom)))]))


;; Roster Roster -> Boolean
;; produces true if all players on both teams will play if the teams play each other,
;;          produce true if both rosters are empty

;   ┌───────────────────────────────────────────────────┐
;   │                                                   │
;   │    ros1           empty              cons         │
;   │                                                   │
;   │ ros2                                              │
;   │                   (1)                 (2)         │
;   │               ┌──────────┐       ┌──────────┐     │
;   │ empty         │   true   │       │   false  │     │
;   │               │          │       │          │     │
;   │               │          │       │          │     │
;   │ cons          │   false  │       │   true   │     │
;   └───────────────┴──────────┴───────┴──────────┴─────┘

;; examples/tests
(check-expect (roster-coincide? empty empty) true)
(check-expect (roster-coincide? empty (list "June Kim")) false)
(check-expect (roster-coincide? (list "June Kim") empty) false)
(check-expect (roster-coincide? (list "Maria" "Jim") (list "June")) false)
(check-expect (roster-coincide? (list "June") (list "Maria" "Jim")) false)
(check-expect (roster-coincide? (list "Maria" "Jim") (list "June" "Kim")) true)


(define (roster-coincide? ros1 ros2)
  (cond [(empty? ros1) (empty? ros2)]  ;(1)
        [else (and (not (empty? ros2)) ;(2)
                   (roster-coincide? (rest ros1) (rest ros2)))]))


; Problem 2:
; 
; Now write a function that, given two teams, produces the list of tennis matches
; that will be played. 
; 
; Assume that this function will only be called if the function you designed above
; produces true. In other words, you can assume the two teams have the same number
; of players. 
; 

;; Roster Roster -> ListOfMatch
;; produce a list of tennis matches that will be played; r1 nth vs r2 nth element
;; Assumes the two rosters have equal number of players

;   ┌─────────────────────────────────────────┐
;   │   ros1                                  │
;   │              empty           cons       │
;   │ ros2                                    │
;   │                (1)                      │
;   │            ┌────────┐                   │
;   │ empty      │ empty  │         xx        │
;   │            └────────┘   ┌─────────────┐ │
;   │                         │ make-match; │ │
;   │ cons          xx        │  recursion  │ │
;   │                         ├────┐(2)┌────┤ │
;   └─────────────────────────┴────┴───┴────┴─┘

;; examples/tests
(check-expect (create-game empty empty) empty)
(check-expect (create-game (list "Maria") (list "June")) 
              (list (make-match "Maria" "June")))
(check-expect (create-game (list "Maria" "Jim") (list "June" "Kim")) 
              (list (make-match "Maria" "June") (make-match "Jim" "Kim")))

(define (create-game ros1 ros2)
  (cond [(empty? ros1) empty]
        [else 
         (append (list (make-match (first ros1) (first ros2)))
                 (create-game (rest ros1) (rest ros2)))]))