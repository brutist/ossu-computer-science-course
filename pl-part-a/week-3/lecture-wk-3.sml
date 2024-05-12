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


exception ListLengthMismatch


fun zip list_triple = 
  case list_triple of
      ([],[],[]) => []
    | (x::xs',y::ys',z::zs') => (x,y,z)::zip(xs',ys',zs')
    | _ => raise ListLengthMismatch

fun unzip lst =
  case lst of
      [] => ([],[],[])
    | (a,b,c)::tl => let val (l1,l2,l3) = unzip tl
                     in
                         (a::l1,b::l2,c::l3)
                     end

fun nondecreasing lst = 
  case lst of
      [] => true
    | _::[] => true
    | hd::(nck::tl) => if hd > nck
                       then false
                       else nondecreasing(nck::tl)

fun factorial n =
  let
    fun aux (n, rsf) =
      if n = 0
      then rsf
      else aux (n-1, n*rsf)
  in
    aux (n,1)
  end


fun fib n =
  let
    fun aux (n,n1,n2) =
      if n = 0
      then n1
      else if n = 1
      then n2
      else aux (n-1, n2, n1 + n2)
  in
    aux (n,0,1)
  end
  
(* sum all ints int in int list *)
fun sum lst = 
  let
    fun aux (lst, rsf) =
      case lst of
          [] => rsf
        | x::xs' => aux (xs', x+rsf)
  in
    aux (lst,0)
  end

(* reverses a list *)
fun rev lst = 
  let
    fun aux (lst, rsf) =
      case lst of 
          [] => rsf
        | x::xs' => aux (xs', x::rsf)
  in
    aux (lst, [])
  end
