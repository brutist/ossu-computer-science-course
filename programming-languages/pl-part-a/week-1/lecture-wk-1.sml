(* This is a comment. This is an example of a program *)

val x = 34;

val y = 19;

val z = 0 - ((x + y) + (y + 5));

val abs_of_z = if z < 0 then 0 - z else z;

(* The idea is that bindings are just variable assignments, 
    The static environment takes care of conformity to the 
    types of variables (type checking) while the dynamic 
    environment keeps track of the bindings as the program 
    runs *)
    
(* The if statements results can only be of the same type??, 
    that would be an interesting contraints moving forward *)


(* Syntax, type-checking rules, and evaluation rules for 
    less-than comparisons 
    
    Syntax:
    e1 < e2
    where < is a keyword and
    e1 and e2 are subexpressions
    
    Type-checking:
    e1 and e2 must have the same type and could only be one of:
            int, string, char or real
    
    Evaluation rules:
    first evaluates e1 and e2 to a value v1 and v2
    if v1 is less than v2 then the result will be true,
    otherwise it would be false*)


(* Creating functions syntax *)

fun pow (a:int, b:int) = 
    if b = 0
    then 1
    else a * pow (a, b-1)


fun cube (c: int) =
    pow(c,3)


fun fib (n: int) =
    if (n = 1)
    then 1
    else n + fib (n-1)


(* Creating and accessing tuples *)
fun swap (pr: int*bool) = 
    (#2 pr, #1 pr)

fun div_mod (x : int, y : int) =
    (x div y, x mod y)


fun sum_list (xs : int list) =
    if null xs
    then 0
    else hd xs + sum_list (tl xs)


(* lecture countdown *)
fun countdown (x : int) =
    if x = 0
    then []
    else x :: countdown (x-1)


fun append (xs : int list, ys : int list) =
    if null xs
    then ys
    else (hd xs) :: append (tl xs, ys)


fun sum_pair_list (xs : (int * int) list) =
    if null xs
    then 0
    else #1 (hd xs) + #2 (hd xs) + sum_pair_list (tl xs)

fun firsts (xs : (int * int) list) =
    if null xs
    then []
    else #1 (hd xs) :: firsts(tl xs)


