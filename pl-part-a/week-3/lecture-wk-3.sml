datatype exp = Constant of int
             | Negate   of exp
             | Add      of exp * exp
             | Multiply of exp * exp

datatype ('a,'b) tree  = Node of 'a * ('a,'b) tree * ('a,'b) tree
                       | Leaf of 'b


(* Exp -> int *)
(* produces the max int in the expression *)
fun max_constant (e) =
    case e of
        Constant i => i
      | Negate e1  => max_constant e1
      | Add (e1, e2) => Int.max(max_constant e1,max_constant e2)
      | Multiply (e1, e2) => Int.max(max_constant e1,max_constant e2)

(* recreating map function course logo *)
fun map (f, xs) =
  case xs of 
      [] => []
    | x::xs' => (f x) :: map (f, xs')


fun append (xs, ys) =
  case xs of
      [] => ys
    | x::xs' => x::append(xs', ys)


fun sum_tree tr =
  case tr of 
      Leaf i => i
    | Node(i,lft,rgt) => i + sum_tree lft + sum_tree rgt


fun sum_leaves tr = 
  case tr of 
      Leaf i => i
    | Node(i,lft,rgt) => sum_leaves lft + sum_leaves rgt


fun num_leaves tr =
  case tr of 
      Leaf i => 1
    | Node(i,lft,rgt) => num_leaves lft + num_leaves rgt

(* all functions in sml takes exactly one argument - a tuple - *)
(* instead of using an argument name in functions, you can actually use a pattern *)
fun sum_triple (x,y,z) = 
  x + y + z

fun full_name {first=x, middle=y, last=z} =
  x ^ " " ^ y ^ " " ^ z

(* this make functions more concise and understandable *)