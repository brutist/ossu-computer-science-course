(* you may assume that Num is always used with values 2, 3, ..., 10
   though it will not really come up *)
datatype suit = Clubs | Diamonds | Hearts | Spades
datatype rank = Jack | Queen | King | Ace | Num of int 
type card = suit * rank

datatype color = Red | Black
datatype move = Discard of card | Draw 

exception IllegalMove

(* put your solutions for problem 2 here *)

(* Card -> Color *)
(* produces the color of the given card *)
fun card_color card =
  case card of
      (Spades,_) => Black
    | (Clubs,_) => Black
    | (Diamonds,_) => Red
    | (Hearts,_) => Red


(* Card -> Int *)
(* produce the value of the card *)
fun card_value card =
  case card of
      (_,Jack) => 10
    | (_,Queen) => 10
    | (_,King) => 10
    | (_,Ace) => 11
    | (_,Num n) => n


(* Card list, Card, exn -> Card list *)
(* produces a list with the given card removed*)
fun remove_card (cs, c, e) =
  case cs of
      [] => raise e
    | x::xs' => if x = c
                then xs'
                else x::remove_card(xs',c,e)


(* Card list -> Boolean *)
(* produce true if all of the cards in the list
    have the same color *)
fun all_same_color (cs) =
  case cs of 
      [] => true
    | card::[] => true
    | card::(card1::xs') => card_color(card) = card_color(card1) andalso
                            all_same_color(card1::xs')


(* Card list -> Int *)
(* produces the sum of the values of the cards in the cards list *)
fun sum_cards (cs) =
let
  fun aux (cs, rsf) = 
    case cs of 
        [] => rsf
      | x::xs' => aux(xs',rsf + card_value(x))
in
  aux(cs,0)
end                                       


(* Card list, Int -> Int *)
(* calculates the score of the held_cards according to the rules below,

    Scoring works as follows: Let sum be the sum of the values of the 
    held-cards. If sum is greater than goal, the preliminary score is 
    three times (sum−goal), else the preliminary score is (goal − sum). 
    The score is the preliminary score unless all the held-cards are 
    the same color, in which case the score is the preliminary score 
    divided by 2 (and rounded down as usual with integer division; 
    use ML’s div operator).    
*)
fun score (cs, goal) =
let
  val sum = sum_cards(cs)
  val preliminary_score = if sum > goal
                          then 3 * (sum - goal)
                          else (goal - sum)
  val final_score = if all_same_color(cs)
                    then preliminary_score div 2
                    else preliminary_score
in
  final_score
end


(* Card list, Move list, Int -> Int *)
(* calculates the final score of the players held cards if the given 
    Move list is implemented *)
fun officiate (cs, mv_list, goal) =
let
  fun player_moves (cs, mv_list, held_cards) = 
    case (cs, mv_list, held_cards) of
        ([],_,_) => score (held_cards,goal)
      | (_,[],_) => score (held_cards,goal)
      | (c::cs',m::mvs',held_cards) => case m of
                                            Draw => if sum_cards(c::held_cards) > goal 
                                                    then score (c::held_cards,goal)
                                                    else player_moves(cs', mvs', c::held_cards)
                                          | Discard c1 => player_moves(c::cs', mvs', remove_card (held_cards, c1, IllegalMove))
in
  player_moves(cs,mv_list,[])
end



(* Card list, Int -> Move list *)
(* returns a move-list such that calling officiate with the card-list, the goal, and 
    the move-list has this behavior:

    • The value of the held cards never exceeds the goal.
    • A card is drawn whenever the goal is more than 10 greater than  the value of the 
        held cards. As a detail, you should (attempt to) draw, even if no cards remain 
        in the card-list.
    • If a score of 0 is reached, there must be no more moves.
    • If it is possible to reach a score of 0 by discarding a card followed by drawing 
        a card, then this must be done. Note careful_player will have to look ahead 
        to the next card, which in many card games is considered “cheating.” Also note 
        that the previous requirement takes precedence: There must be no more moves 
        after a score of 0 is reached even if there is another way to get back to 0.
*)
fun careful_player (cs, goal) =
let
  fun cheat(c,held_card) =
  let
    fun aux(held_card,other_cards) =
      case held_card of
          [] => NONE
        | x::xs' => if score(c::xs'@other_cards,goal) = 0
                    then SOME x
                    else aux(xs',x::other_cards)
  in
    aux(held_card,[])
  end
    
  fun aux(cs,held_card) =
    case (cs,held_card) of 
        ([],_) => Draw::[]
      | (c::cs',[]) => if goal > 10 
                       then Draw::aux(cs',[c])
                       else []
      | (c::cs',held_card) => if (goal - sum_cards(held_card)) > 10
                              then Draw::aux(cs',c::held_card)
                              else case (cheat(c,held_card)) of
                                       NONE => []
                                     | SOME i => aux(cs',remove_card(held_card,i,IllegalMove))
in
  aux(cs,[])
end
