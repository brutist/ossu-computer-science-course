use "challenge-prob.sml";

val test1 = careful_player([(Clubs, Num 2),(Clubs, Num 9),(Clubs, Ace)], 20) = [Draw, Draw, Discard(Clubs, Num 2), Draw]