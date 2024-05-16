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





fun cheat(c,held_card) =
  let
    fun aux(held_card,other_cards) =
      case held_card of
          [] => NONE
        | x::xs' => if score(c::xs'@other_cards,11) = 0
                    then SOME x
                    else aux(xs',x::other_cards)
  in
    aux(held_card,[])
  end

  