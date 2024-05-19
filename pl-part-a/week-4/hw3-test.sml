(* Homework3 Simple Test*)
use "hw3.sml";

val test1 = only_capitals ["A","B","C"] = ["A","B","C"]
val test1a = only_capitals ["Apple","Bat","cat"] = ["Apple","Bat"]

val test2 = longest_string1 ["A","bc","C"] = "bc"
val test2a = longest_string1 ["Apple","bc","C"] = "Apple"
val test2b = longest_string1 ["Apple","because","C"] = "because"
val test2c = longest_string1 ["speedup","between","C"] = "speedup"

val test3 = longest_string2 ["A","bc","C"] = "bc"
val test3a = longest_string2 ["Apple","bc","C"] = "Apple"
val test3b = longest_string2 ["Apple","because","C"] = "because"
val test3c = longest_string2 ["speedup","between","C"] = "between"

val test4aa = longest_string3 ["A","bc","C"] = "bc"
val test4ab = longest_string3 ["speedup","between","C"] = "speedup"
val test4ba = longest_string4 ["A","B","C"] = "C"
val test4bb = longest_string4 ["speedup","between","C"] = "between"

val test5 = longest_capitalized ["A","bc","C"] = "A"
val test5a = longest_capitalized ["Apple","bc","Cat"] = "Apple"
val test5b = longest_capitalized ["Apple","bc","Catherine"] = "Catherine"
val test5c = longest_capitalized ["Apple","beautifully","Catherine"] = "Catherine"
val test5d = longest_capitalized ["Architect","beautifully","Catherine"] = "Architect"

val test6 = rev_string "abc" = "cba"
val test6a = rev_string "Catherine" = "enirehtaC"

val test7 = first_answer (fn x => if x > 3 then SOME x else NONE) [1,2,3,4,5] = 4
val test7a = first_answer (fn word => if size(word) > 3 then SOME word else NONE) ["hey","Johan","cat","bird"] = "Johan"

val test8 = all_answers (fn x => if x = 1 then SOME [x] else NONE) [2,3,4,5,6,7] = NONE
val test8a = all_answers (fn x => if x > 1 then SOME [x] else NONE) [2,3,4,5,6,7] = SOME [2,3,4,5,6,7]
val test8b = all_answers (fn x => if x > 1 then SOME [x] else NONE) [2,3,1,5,6,7] = NONE
