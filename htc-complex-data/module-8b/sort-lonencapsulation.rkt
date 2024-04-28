;; ListOfNumber -> ListOfNumber
;; sort the numbers in lon in increasing order
(check-expect (sort-list empty) empty)
(check-expect (sort-list (list 1)) (list 1))
(check-expect (sort-list (list 1 2 3)) (list 1 2 3))
(check-expect (sort-list (list 2 1 3)) (list 1 2 3))
(check-expect (sort-list (list 3 2 1)) (list 1 2 3))

(define (sort-list lon)
  (local [(define (sort-lon lon)
            (cond
              [(empty? lon) empty]
              [else (insert (first lon) (sort-lon (rest lon)))]))
          (define (insert n lon)
            (cond
              [(empty? lon) (cons n empty)]
              [else (if (> (first lon) n) (cons n lon) (cons (first lon) (insert n (rest lon))))]))]
         (sort-lon lon)))

; Encapsulation doesn't just work with mutually recursive functions.
; As a guideline, it is valuable whenever one function is useful at top-level,
; and it has one or more helpers that are not useful at top level. With that in mind,
; let's look at using encapsulation with a sorting example similar to what you saw in
; the arrange-images problem from the Function Composition lecture.
;
; Go ahead and encapsulate sort-lon and insert using local and compare your solution with
; ours in the next section.
