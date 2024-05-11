use "hw1.sml";
(* All the tests should evaluate to true. For example, the REPL should say: val test1 = true : bool *)


val test1 = is_older ((1,2,3),(2,3,4)) = true
val test1a = is_older ((2020,2,3),(2020,2,3)) = false
val test1b = is_older ((2020,1,3),(2020,2,3)) = true
val test1c = is_older ((2021,2,3),(2020,2,3)) = false
val test1d = is_older ((2000,2,3),(2020,2,3)) = true

val test2 = number_in_month ([(2012,2,28),(2013,12,1)],2) = 1
val test2a = number_in_month ([(2012,4,28),(2013,12,1)],2) = 0
val test2b = number_in_month ([(2012,5,28),(2013,12,1), (2010,5,1)],5) = 2

val test3 = number_in_months ([(2012,2,28),(2013,12,1),(2011,3,31),(2011,4,28)],[2,3,4]) = 3
val test3a = number_in_months ([(2012,2,28),(2013,12,1),(2011,3,31),(2011,4,28)],[]) = 0 
val test3b = number_in_months ([(2050,1,10),(2025,12,12),(2000,3,31),(2011,3,28)],[2,5,8]) = 0
val test3c = number_in_months ([(2050,1,10),(2025,12,12),(2000,3,31),(2011,3,28)],[1,3,12]) = 4
val test3d = number_in_months ([(2050,1,10),(2025,12,12),(2000,3,31),(2011,3,28)],[1,12]) = 2

val test4 = dates_in_month ([(2012,2,28),(2013,12,1)],2) = [(2012,2,28)]
val test4a = dates_in_month ([(2012,2,28),(2013,12,1)],5) = []
val test4b = dates_in_month ([(2012,2,28),(2013,12,1),(2010,2,9)],2) = [(2012,2,28),(2010,2,9)]

val test5 = dates_in_months ([(2012,2,28),(2013,12,1),(2011,3,31),(2011,4,28)],[2,3,4]) = [(2012,2,28),(2011,3,31),(2011,4,28)]
val test5a = dates_in_months ([(2012,2,28),(2013,12,1),(2011,3,31),(2011,4,28)],[]) = []
val test5b = dates_in_months ([(2012,12,28),(2013,12,1),(2011,3,31),(2011,4,28)],[1]) = []
val test5c = dates_in_months ([(2012,12,28),(2013,12,1),(2011,3,31),(2011,4,28)],[3,4,12]) = [(2011,3,31),(2011,4,28),(2012,12,28),(2013,12,1)]

val test6 = get_nth (["hi", "there", "how", "are", "you"], 2) = "there"
val test6a = get_nth (["hi", "there", "how", "are", "you"], 3) = "how"
val test6b = get_nth (["hi", "there", "how", "are", "you"], 1) = "hi"

val test7 = date_to_string (2013, 6, 1) = "June 1, 2013"
val test7a = date_to_string (2000, 1, 10) = "January 10, 2000"
val test7b = date_to_string (2020, 10, 20) = "October 20, 2020"

val test8 = number_before_reaching_sum (10, [1,2,3,4,5]) = 3
val test8a = number_before_reaching_sum (20, [1,2,3,4,5]) = 5
val test8b = number_before_reaching_sum (3, [5,8,3,4,5]) = 0
val test8c = number_before_reaching_sum (15, [5,8,3,4,5]) = 2

val test9 = what_month 70 = 3
val test9a = what_month 20 = 1
val test9b = what_month 360 = 12
val test9c = what_month 300 = 10
val test9d = what_month 31 = 1
val test9e = what_month 20 = 1


val test10 = month_range (31,34) = [1,2,2,2]
val test10a = month_range (100,104) = [4,4,4,4,4]
val test10b = month_range (58,60) = [2,2,3]
val test10c = month_range (90,91) = [3,4]