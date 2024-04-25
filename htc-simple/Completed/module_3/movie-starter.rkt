;; movie-starter.rkt

;; =================
;; Data definitions:

; 
; PROBLEM A:
; 
; Design a data definition to represent a movie, including  
; title, budget, and year released.
; 
; To help you to create some examples, find some interesting movie facts below: 
; "Titanic" - budget: 200000000 released: 1997
; "Avatar" - budget: 237000000 released: 2009
; "The Avengers" - budget: 220000000 released: 2012
; 
; However, feel free to resarch more on your own!
; 


(define-struct movie (ttl bdgt yr))
;; Movie is (make-movie String Number Natural)
;; interp. details of a specific movie with
;;        ttl as title,
;;        bdgt as production budget and
;;        yr as the year released

;; examples
(define M1 (make-movie "Titanic" 200000000 1997))
(define M2 (make-movie "Avatar" 237000000 2009))
(define M3 (make-movie "The Avengers" 220000000 2012))

(define (fn-for-movie m)
  (... (movie-ttl m)      ;String 
       (movie-bdgt m)     ;Number
       (movie-yr m)))     ;Natural


;; template rules used:
;;    - compound: 3 fields


;; =================
;; Functions:

; 
; PROBLEM B:
; 
; You have a list of movies you want to watch, but you like to watch your 
; rentals in chronological order. Design a function that consumes two movies 
; and produces the title of the most recently released movie.
; 
; Note that the rule for templating a function that consumes two compound data 
; parameters is for the template to include all the selectors for both 
; parameters.
; 


;; Movie, Movie -> String
;; produce the tiltle of the more recent movie. if the given movies are made
;;      in the same year, produces the first movie title

;; examples/tests
(check-expect (latest-mv M1 M2) "Avatar")
(check-expect (latest-mv M2 M3) "The Avengers")
(check-expect (latest-mv M1 M3) "The Avengers")
(check-expect (latest-mv M3 M1) "The Avengers")

;; stub
#;
(define (latest-mv m1 m2) "")
  
;; <template from Movie>

(define (latest-mv m1 m2)
  (if (< (movie-yr m1) (movie-yr m2))
      (movie-ttl m2)
      (movie-ttl m1)))     
