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

val test5 = card_color (Clubs, Num 2) = Black
val test5a = card_color (Spades, Num 3) = Black
val test5b = card_color (Diamonds, Num 2) = Red

val test6 = card_value (Clubs, Num 2) = 2
val test6a = card_value (Clubs, Ace) = 11
val test6b = card_value (Diamonds, King) = 10
val test6c = card_value (Spades, Queen) = 10
val test6d = card_value (Spades, Jack) = 10

val test7 = remove_card ([(Hearts, Ace)], (Hearts, Ace), IllegalMove) = []
val test7a = remove_card ([(Hearts, Ace), (Hearts, Ace)], (Hearts, Ace), IllegalMove) = [(Hearts, Ace)]
val test7b = remove_card ([(Hearts, Ace), (Spades, Ace), (Diamonds, King)], (Hearts, Ace), IllegalMove) = [(Spades, Ace), (Diamonds, King)]
val test7c = remove_card ([(Hearts, Ace), (Spades, Ace), (Diamonds, King)], (Spades, Ace), IllegalMove) = [(Hearts, Ace), (Diamonds, King)]
val test7d = ((remove_card ([(Hearts, Ace), (Hearts, Ace)], (Hearts, Num 1), IllegalMove); false) handle IllegalMove => true)

val test8 = all_same_color [(Hearts, Ace), (Hearts, Ace)] = true
val test8a = all_same_color [(Hearts, Ace), (Hearts, Ace), (Spades, Ace)] = false
val test8b = all_same_color [(Hearts, Ace), (Hearts, Ace), (Diamonds, Ace), (Hearts, Ace), (Diamonds, Ace), (Diamonds, King)] = true
val test8c = all_same_color [(Hearts, Ace), (Hearts, Ace), (Spades, Ace), (Hearts, Ace), (Diamonds, Ace), (Diamonds, King)] = false
val test8d = all_same_color [(Spades, Num 2), (Clubs, Ace), (Spades, Ace), (Spades, Num 4), (Clubs, Num 8), (Clubs, King)] = true

val test9 = sum_cards [(Clubs, Num 2),(Clubs, Num 2)] = 4
val test9a = sum_cards [(Clubs, Ace),(Clubs, Num 10),(Diamonds, Queen)] = 31
val test9b = sum_cards [(Clubs, Ace),(Diamonds, Ace),(Spades, Ace)] = 33

val test10 = score ([(Hearts, Num 2),(Clubs, Num 4)],10) = 4
val test10a = score ([(Hearts, Ace),(Clubs, Num 4)],10) = 15
val test10b = score ([(Hearts, Ace),(Diamonds, Num 4)],10) = 7
val test10c = score ([(Hearts, Num 2),(Diamonds, Num 4)],10) = 2

val test11 = officiate ([(Hearts, Num 2),(Clubs, Num 4)],[Draw], 15) = 6
val test11a = officiate ([(Hearts, Num 2),(Clubs, Num 4),(Clubs, Num 2),(Clubs, Ace)],[Draw,Discard (Hearts, Num 2),Draw,Draw], 15) = 4
val test11b = officiate ([(Hearts, Num 2),(Clubs, Num 4),(Diamonds, Num 2),(Clubs, Ace)],[Draw,Discard (Hearts, Num 2),Draw,Draw], 15) = 9
val test11c = officiate ([(Hearts, Num 2),(Clubs, Num 4)],[], 15) = 7
val test11d = ((officiate ([(Hearts, Num 2),(Clubs, Num 4)],[Discard (Hearts, Num 2)], 15); false) handle IllegalMove => true)
val test11e = officiate ([(Hearts, Ace),(Clubs, Num 4),(Diamonds, Num 2),(Clubs, Ace)],[Draw,Draw,Draw,Draw], 15) = 6
val test11f = officiate ([],[Draw,Draw,Draw,Draw], 1) = 0
val test11g = officiate ([(Clubs,Ace),(Spades,Ace),(Clubs,Ace),(Spades,Ace)], [Draw,Draw,Draw,Draw,Draw], 42)  = 3
val test11h = ((officiate([(Clubs,Jack),(Spades,Num(8))], [Draw,Discard(Hearts,Jack)], 42); false) handle IllegalMove => true)
val test11i = officiate ([(Hearts, Ace),(Clubs, Num 4)],[Draw], 1) = 15

val test12a = score_challenge ([(Hearts, Num 2),(Clubs, Num 4)],10) = 4
val test12b = score_challenge ([(Hearts, Ace),(Clubs, Num 4)],10) = 5
val test12c = score_challenge ([(Hearts, Ace),(Diamonds, Num 4)],10) = 2
val test12d = score_challenge ([(Hearts, Num 2),(Diamonds, Num 4)],10) = 2

val test13a = officiate_challenge ([(Hearts, Num 2),(Clubs, Num 4)],[Draw], 15) = 6
val test13b = officiate_challenge ([(Hearts, Num 2),(Clubs, Num 4),(Clubs, Num 2),(Clubs, Ace)],
                                   [Draw,Discard (Hearts, Num 2),Draw,Draw], 
                                   15) 
                                   = 4
val test13c = officiate_challenge ([(Hearts, Num 2),(Clubs, Num 4),(Diamonds, Num 2),(Clubs, Ace)],
                                   [Draw,Discard (Hearts, Num 2),Draw,Draw], 
                                   15) 
                                   = 9
val test13d = officiate_challenge ([(Hearts, Ace),(Clubs, Num 4)],[Draw], 1) = 0
val test13e = ((officiate_challenge ([(Hearts, Num 2),(Clubs, Num 4)],
                                     [Discard (Hearts, Num 2)], 15); false) 
                                     handle IllegalMove => true)

val test14b = careful_player ([(Hearts, Num 2),(Clubs, Num 4),(Clubs, Ace)], 16) = 
                [Draw,Draw,Discard(Hearts, Num 2),Draw]
val test14c = careful_player([(Clubs, Num 2),(Clubs, Num 9),(Clubs, Ace)], 20) = 
                [Draw, Draw, Discard(Clubs, Num 2),Draw]
