(* Homework2 Simple Test *)
use "hw2.sml";
(* All the tests should evaluate to true. For example, the REPL should say: val test1 = true : bool *)

val test1 = all_except_option ("string", ["string"]) = SOME []
val test1a = all_except_option ("string", ["string","my_string","hey"]) = SOME ["my_string","hey"]
val test1b = all_except_option ("hey" , ["string","my_string","hey"]) = SOME ["string","my_string"]

val test2 = get_substitutions1 ([["foo"],["there"]], "foo") = []
val test2a = get_substitutions1 ([["Fred","Fredrick"],["Elizabeth","Betty"],["Freddie","Fred","F"]], "Fred") = ["Fredrick","Freddie","F"]
val test2b = get_substitutions1 ([["Fred","Fredrick"],["Elizabeth","Betty"],["Freddie","Fred","F"]], "Betty") = ["Elizabeth"]


             