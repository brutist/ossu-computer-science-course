datatype exp = Constant of int
             | Negate   of exp
             | Add      of exp * exp
             | Multiply of exp * exp

(* Exp -> int *)
(* produces the max int in the expression *)
fun max_constant (e) =
    case e of
        Constant i => i
      | Negate e1  => max_constant e1
      | Add (e1, e2) => Int.max(max_constant e1,max_constant e2)
      | Multiply (e1, e2) => Int.max(max_constant e1,max_constant e2)

