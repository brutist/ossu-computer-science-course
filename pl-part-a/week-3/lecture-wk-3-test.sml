use "lecture-wk-3.sml";


val test1a = max_constant (Constant 1) = 1
val test1b = max_constant (Multiply ((Add (Constant 20, Constant 10)), Constant 20)) = 20
val test1c = max_constant (Add ((Constant 30), Multiply ((Negate (Constant 20)), Constant 10))) = 30