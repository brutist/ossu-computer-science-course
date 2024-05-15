(* This is a temp lecture code and will be combined once the main 
    lecture code file is pushed to main *)

fun n_times (f,n,x) =
    if n=0
    then x
    else f (n_times(f,n-1,x))


(* This one is a little better because it defined the helper
    locally. However, it using let expression is overkill for this
    really simple helper *)
fun triple_n_times_ (n,x) =
    let
      fun triple n = 3 * n
    in
      n_times(triple,n,x)
    end

(* In order to turn triple_n_times into a more concise and 
    straightforward code, we can use lambda expression *)

fun triple_n_times (n,x) =
    n_times((fn a => 3*a),n,x)



(* It's interesting that you can actually use val bindings
    to define a function. However, this method won't work
    for recursive functions *)
val triple = fn y => 3*y



fun nth_tail (n,xs) =
    n_times(tl,n,xs)


fun map(f,xs) =
    case xs of
        [] => []
      | x::xs' => f x::map(f,xs')

val x1 = map((fn x => x+1),[1,2,3,4])
val x2 = map(hd,[[1,2,3],[4,5],[2,2,5]])


fun filter(f,xs) =
    case xs of 
        [] => []
      | x::xs' => if f x
                  then x::filter(f,xs')
                  else filter(f,xs')

fun all_even xs =
    filter((fn x => x mod 2 = 0),xs)
 