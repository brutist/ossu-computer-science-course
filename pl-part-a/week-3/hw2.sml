(* Dan Grossman, Coursera PL, HW2 Provided Code *)

(* if you use this function to compare two strings (returns true if the same
   string), then you avoid several of the functions in problem 1 having
   polymorphic types that may be confusing *)
fun same_string(s1 : string, s2 : string) =
    s1 = s2

(* put your solutions for problem 1 here *)

(* string, string list  -> list option *)
(* produces SOME list of strings except the given string, produces NONE
    if the string is not in the given list *)
fun all_except_option (word, lst) = 
  let fun aux (lst,rsf,count) =
          case (lst,rsf,count) of
              ([],_,0) => NONE
            | ([],SOME i,_) => SOME i
            | (x::xs', NONE,n) => if same_string(x, word)
                                  then aux(xs', SOME [],n+1)
                                  else aux(xs', SOME [x],n)
            | (x::xs', SOME i,n) => if same_string(x, word)
                                    then aux(xs', SOME i,n+1)
                                    else aux(xs', SOME (i@[x]),n)
  in
    aux(lst,NONE,0)
  end
  

(* string list list, string -> string list *)
(* produces a list of all the strings that are in some list in substitutions 
    that also has name but name should not be in the result *)
fun get_substitutions1 (substitutions, name) =
  case substitutions of
      [] => []
    | x::xs' => let val subs = all_except_option(name,x)
                in case subs of
                        NONE => get_substitutions1(xs',name)
                      | SOME i => i @ get_substitutions1(xs',name)
                end
               
(* string list list, string -> string list *)
(* tail-recursive version of get_substitutions1 *)
fun get_substitutions2 (substitutions, name) =
  let
    fun aux (substitutions, rsf) = 
      case substitutions of
          [] => rsf
        | x::xs' => let val sub = all_except_option(name,x)
                    in case sub of
                            NONE => aux(xs', rsf)
                          | SOME i => aux(xs', rsf @ i)
                    end
  in
    aux (substitutions, [])
  end


(* string list list, full_name -> full_name list*)
(* full_name of type {first:string,middle:string,last:string} *)
(* produces a list of full_names full names you can produce by 
    substituting for the first name using get_subsitutionsx *)
fun similar_names (substitutions,{first=f,middle=m,last=l}) =
  let
    val original_name = {first=f,middle=m,last=l}
    val aliases = get_substitutions2 (substitutions,f)
    fun create_aliases akas =
      case akas of
          [] => []
        | x::xs' => {first=x,middle=m,last=l}::create_aliases (xs')
  in
    original_name::create_aliases (aliases)
  end






(* you may assume that Num is always used with values 2, 3, ..., 10
   though it will not really come up *)
datatype suit = Clubs | Diamonds | Hearts | Spades
datatype rank = Jack | Queen | King | Ace | Num of int 
type card = suit * rank

datatype color = Red | Black
datatype move = Discard of card | Draw 

exception IllegalMove

(* put your solutions for problem 2 here *)

