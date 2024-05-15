(* Dan Grossman, Coursera PL, HW2 Provided Code *)

(* if you use this function to compare two strings (returns true if the same
   string), then you avoid several of the functions in problem 1 having
   polymorphic types that may be confusing *)
fun same_string(s1 : string, s2 : string) =
    s1 = s2

(* put your solutions for problem 1 here *)

(* string, string list  -> list option *)
(* produces SOME list of strings except the given string, produces NONE
    if the string is not in the given list *)
fun all_except_option (word, lst) = 
  let fun aux (lst,rsf,count) =
          case (lst,rsf,count) of
              ([],_,0) => NONE
            | ([],SOME i,_) => SOME i
            | (x::xs', NONE,n) => if same_string(x, word)
                                  then aux(xs', SOME [],n+1)
                                  else aux(xs', SOME [x],n)
            | (x::xs', SOME i,n) => if same_string(x, word)
                                    then aux(xs', SOME i,n+1)
                                    else aux(xs', SOME (i@[x]),n)
  in
    aux(lst,NONE,0)
  end
  

(* string list list, string -> string list *)
(* produces a list of all the strings that are in some list in substitutions 
    that also has name but name should not be in the result *)
fun get_substitutions1 (substitutions, name) =
  case substitutions of
      [] => []
    | x::xs' => case all_except_option(name,x) of
                    NONE => get_substitutions1(xs',name)
                  | SOME i => i @ get_substitutions1(xs',name)
                
               

(* string list list, string -> string list *)
(* tail-recursive version of get_substitutions1 *)
fun get_substitutions2 (substitutions, name) =
  let
    fun aux (substitutions, rsf) = 
      case substitutions of
          [] => rsf
        | x::xs' => case all_except_option(name,x) of
                        NONE => aux(xs', rsf)
                      | SOME i => aux(xs', rsf @ i)
  in
    aux (substitutions, [])
  end


(* string list list, full_name -> full_name list*)
(* full_name of type {first:string,middle:string,last:string} *)
(* produces a list of full_names full names you can produce by 
    substituting for the first name using get_subsitutionsx *)
fun similar_names (substitutions,{first=f,middle=m,last=l}) =
  let
    val original_name = {first=f,middle=m,last=l}
    val aliases = get_substitutions2 (substitutions,f)
    fun create_aliases akas =
      case akas of
          [] => []
        | x::xs' => {first=x,middle=m,last=l}::create_aliases (xs')
  in
    original_name::create_aliases (aliases)
  end



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
  val preliminary_score = if sum >= goal
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
      | (c::cs',m::mvs',held_cards) => 
          case m of
            Draw => if sum_cards(c::held_cards) > goal 
                    then score (c::held_cards,goal)
                    else player_moves(cs', mvs', c::held_cards)
          | Discard c1 => player_moves(c::cs', mvs', remove_card (held_cards, c1, IllegalMove))
in
  player_moves(cs,mv_list,[])
end


(* Card list, Int -> Int *)
(* calculates the score of the held_cards according to the rules below,

    Scoring works as follows: Let sum be the sum of the values of the 
    held-cards. If sum is greater than goal, the preliminary score is 
    three times (sum−goal), else the preliminary score is (goal − sum). 
    The score is the preliminary score unless all the held-cards are 
    the same color, in which case the score is the preliminary score 
    divided by 2. Except each ace can have a value of 1 or 11 and 
    score_challenge should always return the least (i.e., best)
    possible score. (Note the game-ends-if-sum-exceeds-goal rule should 
    apply only if there is no sum that is less than or equal to the goal.)   
*)
fun score_challenge (cs, goal) =
let
  fun calculate_score sum = 
    if sum >= goal
    then (3 * (sum - goal)) div (if all_same_color(cs) then 2 else 1)
    else (goal - sum) div (if all_same_color(cs) then 2 else 1)

  fun aces_1_sum cs =
    case cs of
      [] => 0
    | (_,Ace)::cs' => 1 + aces_1_sum(cs')
    | c::cs' => card_value(c) + aces_1_sum(cs')
 
  val special_ace_score = calculate_score (aces_1_sum(cs))
  val base_score = calculate_score (sum_cards cs)
in
  if special_ace_score < base_score 
  then special_ace_score
  else base_score
end


(* Card list, Move list, Int -> Int *)
(* calculates the final score of the players held cards if the given 
    Move list is implemented
    Special rules - Aces could have value of 1 or 11 to return a score
                    that is always the least possible score*)
fun officiate_challenge (cs, mv_list, goal) =
let
  fun player_moves (cs, mv_list, held_cards) = 
    case (cs, mv_list, held_cards) of
        ([],_,_) => score_challenge (held_cards,goal)
      | (_,[],_) => score_challenge (held_cards,goal)
      | (c::cs',m::mvs',held_cards) => 
          case m of
            Draw => if sum_cards(c::held_cards) > goal 
                    then score_challenge (c::held_cards,goal)
                    else player_moves(cs', mvs', c::held_cards)
          | Discard c1 => player_moves(c::cs', mvs', remove_card (held_cards, c1, IllegalMove))
in
  player_moves(cs,mv_list,[])
end


(* Card list, Int -> Move list *)
(* produces a move list that calling officiate with card list will have the
      following behaviour:
      
      • The value of the held cards never exceeds the goal.
      • A card is drawn whenever the goal is more than 10 greater than the 
        value of the held cards. As a detail, you should (attempt to) draw, 
        even if no cards remain in the card-list.
      • If a score of 0 is reached, there must be no more moves.
      • If it is possible to reach a score of 0 by discarding a card followed 
        by drawing a card, then this  must be done. Note careful_player will 
        have to look ahead to the next card, which in many card games is 
        considered “cheating.” Also note that the previous requirement 
        takes precedence: There must be no more moves after a score of 0 
        is reached even if there is another way to get back to 0. 
*)
fun careful_player (cs, goal) =
let
  fun aux (cs, held_cards) =
  let
    val 
  in
    
  end
  

in

end