(* Homework2 Simple Test *)
use "hw2.sml";
(* All the tests should evaluate to true. For example, the REPL should say: val test1 = true : bool *)

val test1 = all_except_option ("string", ["string"]) = SOME []
val test1a = all_except_option ("string", ["string","my_string","hey"]) = SOME ["my_string","hey"]
val test1b = all_except_option ("hey" , ["string","my_string","hey"]) = SOME ["string","my_string"]
val test1c = all_except_option ("keys" , ["string","my_string","hey"]) = NONE

val test2 = get_substitutions1 ([["foo"],["there"]], "foo") = []
val test2a = get_substitutions1 ([["Fred","Fredrick"],["Elizabeth","Betty"],["Freddie","Fred","F"]], "Fred") = ["Fredrick","Freddie","F"]
val test2b = get_substitutions1 ([["Fred","Fredrick"],["Elizabeth","Betty"],["Freddie","Fred","F"]], "Betty") = ["Elizabeth"]

val test3 = get_substitutions2 ([["foo"],["there"]], "foo") = []
val test3a = get_substitutions2 ([["foo"],["there"]], "foo") = []
val test3b = get_substitutions2 ([["Fred","Fredrick"],["Elizabeth","Betty"],["Freddie","Fred","F"]], "Fred") = ["Fredrick","Freddie","F"]
val test3c = get_substitutions2 ([["Fred","Fredrick"],["Elizabeth","Betty"],["Freddie","Fred","F"]], "Betty") = ["Elizabeth"]

val test4 = similar_names ([["Fred","Fredrick"],["Elizabeth","Betty"],["Freddie","Fred","F"]], {first="Fred", middle="W", last="Smith"}) =
            [{first="Fred", last="Smith", middle="W"}, {first="Fredrick", last="Smith", middle="W"},
             {first="Freddie", last="Smith", middle="W"}, {first="F", last="Smith", middle="W"}]
val test4a = similar_names ([["Fred","Fredrick"],["Elizabeth","Betty"],["Freddie","Fred","F"]], {first="Elizabeth", middle="W", last="Smith"}) =
            [{first="Elizabeth", last="Smith", middle="W"}, {first="Betty", last="Smith", middle="W"}]
val test4b = similar_names ([["Fred","Fredrick"],["Freddie","Fred","F"]], {first="Elizabeth", middle="W", last="Smith"}) =
            [{first="Elizabeth", last="Smith", middle="W"}]