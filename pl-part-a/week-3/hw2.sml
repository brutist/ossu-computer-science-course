(* Dan Grossman, Coursera PL, HW2 Provided Code *)

(* if you use this function to compare two strings (returns true if the same
   string), then you avoid several of the functions in problem 1 having
   polymorphic types that may be confusing *)
fun same_string(s1 : string, s2 : string) =
    s1 = s2

(* put your solutions for problem 1 here *)

(* string, string list  -> list option *)
fun all_except_option (word, lst) = 
  let fun aux lst_rfs = 
          case lst_rfs of 
              ([], NONE) => SOME []
            | ([], SOME i) => SOME i
            | (x::xs', NONE) => if same_string(x, word)
                                then aux (xs', NONE) 
                                else aux (xs', SOME [x])
            | (x::xs', SOME i) => if same_string(x, word)
                                  then aux (xs', SOME i) 
                                  else aux (xs', SOME (i @ [x]))
  in
    aux (lst, NONE)
  end


(* string list list, string -> string list *)
fun get_substitutions1 (nicknames, name) =
  let fun aux nicknames_rsf =
          case nicknames_rsf of
              ([], rsf) => rsf
            | (x::xs', rsf) => if
                               then
                               else aux (xs', rsf @ valOf (all_except_option (name,x)))
  in  
    aux (nicknames, [])
  end

(* TODO *)


(* you may assume that Num is always used with values 2, 3, ..., 10
   though it will not really come up *)
datatype suit = Clubs | Diamonds | Hearts | Spades
datatype rank = Jack | Queen | King | Ace | Num of int 
type card = suit * rank

datatype color = Red | Black
datatype move = Discard of card | Draw 

exception IllegalMove

(* put your solutions for problem 2 here *)

