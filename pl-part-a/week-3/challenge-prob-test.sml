use "challenge-prob.sml";

val test1 = careful_player([(Clubs, Num 2),(Clubs, Num 9),(Clubs, Ace)], 20) = [Draw, Draw, Discard(Clubs, Num 2)]

val test3b = 
   let
      val cards1 = [(Spades, Num 7), (Hearts, King), (Clubs, Ace), (Diamonds, Num 2)]
      val cards2 = [(Spades, Num 8), (Hearts, King), (Clubs, Ace), (Diamonds, Num 2)]
      val cards3 = [(Spades, Ace), (Hearts, Queen), (Spades, Num 7)]
      val cards4 = [(Spades, Num 7), (Hearts, King), (Clubs, Ace), (Diamonds, Num 2)]
      val cards5 = [(Clubs, Ace), (Spades, Ace), (Diamonds, Ace), (Hearts, Ace), (Clubs, Num 10), (Spades, Num 10)]
      val cards6 = [(Spades, Num 7)]
   in
      [
       careful_player(cards1, 18),
       careful_player(cards1, 8),
       careful_player(cards2, 8),
       careful_player(cards2, 18),
       careful_player(cards2, 20),
       careful_player(cards2, 21),
       careful_player(cards3, 21),
       careful_player(cards4, 18),
       careful_player(cards4, 17),
       careful_player(cards5, 42),
       careful_player(cards6, 8)
      ]
   end