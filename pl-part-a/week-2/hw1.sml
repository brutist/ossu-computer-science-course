(* int -> int *)
(* produce the number of days in a given month m *)
fun normal_month_days (m : int) =
    if m = 1 orelse m = 3 orelse m = 5 orelse 
       m = 7 orelse m = 8 orelse m = 10 orelse 
       m = 12
    then 31
    else if m = 4 orelse m = 6 orelse
            m = 9 orelse m = 11
    then 30
    else 28


(*  (int * int * int) list, (int * int * int) list -> bool  *)
(*  d1 and d2 are dates in format (year, month, day)
    produce true if d1 is older than d2 *)
fun is_older (d1 : int * int * int, d2 : int * int * int) =
    let        
      val total_d1 = (#1 d1 * 365) + (#2 d1 * normal_month_days (#2 d1)) + #3 d1
      val total_d2 = (#1 d2 * 365) + (#2 d2 * normal_month_days (#2 d2)) + #3 d2
    in
      total_d1 < total_d2
    end


(*  (int * int * int) list, int -> int  *)
(* produce the number of dates that contains the given month *)
fun number_in_month (dates : (int * int * int) list, month : int) =
    if dates = []
    then 0
    else if #2 (hd dates) = month
    then 1 + number_in_month (tl dates, month)
    else number_in_month (tl dates, month)


(*  (int * int * int) list, int list -> int  *)
(* produce the number of dates that contains the given months *)
(* Assume no month is repeated in months *)
fun  number_in_months (dates: (int * int * int) list, months: int list) =
    if months = []
    then 0
    else number_in_month (dates, hd months) +  number_in_months (dates, tl months)


(*  (int * int * int) list, int -> int list  *)
(* produce a list of date that contains the given month *)
fun dates_in_month (dates : (int * int * int) list, month : int) =
    if dates = []
    then []
    else if #2 (hd dates) = month
    then hd dates :: dates_in_month (tl dates, month)
    else dates_in_month (tl dates, month)


(*  (int * int * int) list, int list -> int list  *)
(* produce a list of date that contains the given months *)
fun dates_in_months (dates : (int * int * int) list, months : int list) =
    if months = []
    then []
    else dates_in_month (dates, hd months) @ dates_in_months (dates, tl months)


(* string list, int -> string  *)
(* produce the string at p *)
fun get_nth (words : string list, p : int) =
    let
        fun get_nth_0 (words : string list, n : int) =
            if words = [] 
            then "error"
            else if p = n
            then hd words
            else get_nth_0 (tl words, n+1)
    in 
        get_nth_0 (words, 1)
    end


(* int -> string *)
(* produce the name of the given month *)
fun month_name (month : int) = 
    let 
      val NAMES = ["January", "February", "March", "April", "May", 
                    "June", "July", "August", "September", "October",
                    "November", "December"]
    in 
      get_nth (NAMES, month)
    end


(* (int * int * int) -> string *)
(* produces a string version of the date following format MonthName xx, xxxx *)
fun date_to_string (date : (int * int * int)) =
    month_name (#2 date) ^ " " ^ Int.toString (#3 date) ^ ", " ^ Int.toString  (#1 date)


(* int , int list -> int *)
(* produces the nth 1-based index such that en + en-1 ... e0 < sum and 
    en+1 + en ... e0 > sum  *)
fun number_before_reaching_sum (sum : int, numbers : int list) = 
    let
      fun number_before_reaching_sum (current_sum : int, numbers : int list, n : int) =
        if numbers = []
        then n
        else if sum > current_sum + hd numbers
        then number_before_reaching_sum (current_sum + hd numbers, tl numbers, n + 1)
        else n
    in
      number_before_reaching_sum (0, numbers, 0)
    end


(* int -> int *)
(* produces the month the the given day is in *)
fun what_month (day : int) =
    let
        val CONVERTER = 1 (* align the input to the definition of number_before_reaching_sum *)
        val month_lengths = [31,28,31,30,31,30,31,31,30,31,30,31]
    in 
        number_before_reaching_sum (day, month_lengths) + CONVERTER
    end


(* int , int -> int list *)
(* produce a list of months each days between day1 and day2 belongs *)
fun month_range (day1 : int , day2 : int) =
    if day1 > day2
    then []
    else what_month (day1) :: month_range (day1 + 1, day2)


(* (int * int * int) list -> (int * int * int) option  *)
(* produces the oldest SOME date in given list, NONE if the list has no dates *)
fun oldest (dates : (int * int * int) list) =
    let
      fun oldest (dates : (int * int * int) list, rsf : (int * int * int) option) =
        if dates = []
        then rsf
        else if rsf = NONE orelse is_older (hd dates, valOf rsf)
        then oldest (tl dates, SOME (hd dates))
        else oldest (tl dates, rsf)
    in
      oldest (dates, NONE)
    end

(*  int list ->  int list *)
(* remove the duplicates in a list of months *)
fun remove_duplicates (months) =
    let
      fun not_in_list (month, months) =
        if months = []
        then true
        else if month = hd months
        then false
        else not_in_list (month, tl months)

      fun remove_duplicates (months, rsf) =
        if months = []
        then rsf
        else if not_in_list (hd months, rsf)
        then remove_duplicates (tl months, rsf @ [hd months])
        else remove_duplicates (tl months, rsf)
  
    in
      remove_duplicates (months, [])
    end


(*  (int * int * int) list, int list -> int  *)
(* produce the number of dates that contains the given months *)
fun  number_in_months_challenge (dates: (int * int * int) list, months: int list) =
    let
      val months = remove_duplicates months
    in
      if months = []
      then 0
      else number_in_month (dates, hd months) +  number_in_months_challenge (dates, tl months)
    end
    

fun reasonable_date (date : (int * int * int)) =
    let
      val year = #1 date
      val month = #2 date
      val day = #3 date

      fun is_leap_year (year) =
        year mod 400 = 0 orelse (year mod 4 = 0 andalso not (year mod 100 = 0))

      fun month_days (year, month) =
        if is_leap_year (year) andalso month = 2
        then 29
        else normal_month_days (month)

    in
      year > 0 andalso
      month > 0 andalso month <= 12 andalso
      day <= month_days (year, month) andalso day > 0
    end


