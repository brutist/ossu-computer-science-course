fun double x = 2 * x
fun incr x = x + 1

val a_tuple = (double, incr, double (incr 7))
val eighteen = (#1 a_tuple) 9

(* Function closure: Functions can use bindings from
    outside the function definition (in scopre where
    function is defined) *)

(* First-class functions are functions that take in
    other functions as arguments or returns other 
    functions) *)

fun n_times(f,n,x) =
    if n=0
    then x
    else f(n_times(f,n-1,x))

fun increment x = x+1
fun double x = x+x

val x1 = n_times(double,4,7)
val x2 = n_times(increment,4,7)
val x3 = n_times(tl,2,[4,5,6,7])

fun addition(n,x) = n_times(increment,n,x)
fun double_n_times(n,x) = n_times(double,n,x)
fun nth_tail(n,x) = n_times(tl,n,x)

fun map(f,xs) =
    case xs of 
        [] => []
      | x::xs' => f(x)::map(f,xs')


fun increment_all(xs,n) =
    map((fn x => x+n), xs)

fun decrement_all(xs,n) =
    map((fn x => x-n), xs)