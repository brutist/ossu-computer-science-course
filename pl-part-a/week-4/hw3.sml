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
fun longest_string_helper f words =
    List.foldl (fn (word,rsf) => if f(size(word),size(rsf)) then word else rsf) "" words


(* this function exist for partial applications below *)
val longest_string3 = longest_string_helper (fn(i,j) => i>j)
val longest_string4 = longest_string_helper (fn(i,j) => i>=j)


val longest_capitalized = longest_string1 o only_capitals


val rev_string = String.implode o List.rev o String.explode


(* (’a -> ’b option) -> ’a list -> ’b *)
fun first_answer f alist = 
	let val only_somes = (List.filter (fn a => isSome(a))) o (List.map f)
	in case only_somes alist of
			[] => raise NoAnswer
		  | SOME x::xs => x
	end

(* (’a -> ’b list option) -> ’a list -> ’b list option *)
fun all_answers f alist = 
	let val answers = List.map f alist
		fun collate_somes alist rsf =
			case alist of
				[] => rsf
			| NONE::xs => NONE
			| SOME i::xs => collate_somes xs (SOME ((valOf rsf)@i))
	in
	  collate_somes answers (SOME [])
	end


val count_wildcards = g (fn () => 1) (fn x => 0)
val count_wild_and_variable_lengths = g (fn () => 1) (fn x => size(x)) 
fun count_some_var (word,pattern) = g (fn () => 0) (fn x => if word = x then 1 else 0) pattern


fun check_pat pattern = 
	let
	  fun variable_names pat = 
	  	case pat of
			Wildcard			=> []
		  | Variable w			=> [w]
		  | UnitP			 	=> []
		  | ConstP _			=> []
		  | TupleP ps			=> List.foldl (fn (p,i) => variable_names p@i) [] ps
		  | ConstructorP (_,p)  => variable_names p

	  fun no_repeats names =
	  	case names of 
			[] => true
		  | x::[] => true
		  | v::xs => List.exists (fn x => x<>v) xs andalso no_repeats xs
	in
	  (no_repeats o variable_names) pattern
	end


fun match (v,pattern) =
	case (v,pattern) of
		(v,Wildcard)						   => []
	  | (v,Variable s) 					       => [(s,v)]
	  | (Unit,UnitP) 						   => []
	  | (Const i, ConsP i) 					   => []
	  | (Tuple vals, TupleP ps) 			   => ...
	  | (Constructor(s1,v),ConstructorP(s2,p)) => if s1 = s2
	  											  then match (v,p)
												  else NONE
	  | _ 								       => NONE
