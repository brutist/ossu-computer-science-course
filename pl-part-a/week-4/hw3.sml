(* Coursera Programming Languages, Homework 3, Provided Code *)

exception NoAnswer

datatype pattern = Wildcard
		 | Variable of string
		 | UnitP
		 | ConstP of int
		 | TupleP of pattern list
		 | ConstructorP of string * pattern

datatype valu = Const of int
	      | Unit
	      | Tuple of valu list
	      | Constructor of string * valu

fun g f1 f2 p =
    let 
	val r = g f1 f2 
    in
	case p of
	    Wildcard          => f1 ()
	  | Variable x        => f2 x
	  | TupleP ps         => List.foldl (fn (p,i) => (r p) + i) 0 ps
	  | ConstructorP(_,p) => r p
	  | _                 => 0
    end

(**** for the challenge problem only ****)

datatype typ = Anything
	     | UnitT
	     | IntT
	     | TupleT of typ list
	     | Datatype of string

(**** you can put all your code here ****)

fun only_capitals words =
    List.filter (fn word => Char.isUpper (String.sub(word,0))) words


fun longest_string1 words =
    List.foldl (fn (word,rsf) => if size(word) > size(rsf) then word else rsf) "" words


fun longest_string2 words =
    List.foldl (fn (word,rsf) => if size(word) >= size(rsf) then word else rsf) "" words


(*  (int * int -> bool) -> string list -> string  *)
fun longest_string_helper f words word =
    List.foldl (fn (word,rsf) => if f(size(word),size(rsf)) then word else rsf) "" words


(* this function exist for partial applications below *)
fun find_longest f words = longest_string_helper f words ""
val longest_string3 = find_longest (fn(i,j) => i>j)
val longest_string4 = find_longest (fn(i,j) => i>=j)


val longest_capitalized = longest_string1 o only_capitals


val rev_string = String.implode o List.rev o String.explode