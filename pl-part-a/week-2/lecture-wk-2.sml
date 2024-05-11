(* int ->  int list *)
fun count_from_1 (x : int) =
    let
      fun count_up (from : int) =
        if from = x
        then from :: []
        else from :: count_up (from + 1)
    in
      count_up 1
    end


fun bad_max (xs : int list) =
  if null xs
  then 0
  else if hd xs > bad_max (tl xs)
  then hd xs
  else bad_max (tl xs)


fun max (xs : int list) =
  if null xs
  then 0
  else if null (tl xs)
  then hd xs
  else
    let val try = max (tl xs)
    in 
        if hd xs > try
        then hd xs
        else try
    end

(* let expressions can be used to avoid repeated computation.
    However, using a tail recursion and an accumulator can 
    simplify and beautify the code *)
(* This is a tail recursive max_tr 
   The previous max function is a good and working code but this
   tail recursive max_tr, I believe, is a good working code AND
   has a good style *)
  
(* There was also an application of OPTIONS in this function.
   Options is basically a way to get around the one type ouput
   constraint that SML have *)

(* int list -> int option *)
fun max_tr (xs : int list) =
  let
    fun max_0 (xs : int list, rfs : int option) =
      if null xs
      then rfs
      else if rfs = NONE orelse valOf rfs < hd xs
      then max_0 ((tl xs), SOME (hd xs))
      else max_0 ((tl xs), rfs)
  in
    max_0 (xs, NONE)
  end

