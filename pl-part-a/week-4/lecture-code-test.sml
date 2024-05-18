use "lecture-codes-4.sml";

val test1 = increment_all([8,2,5,6],2) = [10,4,7,8]

val test2 = decrement_all([8,2,5,6],2) = [6,0,3,4]

val test3a = sorted3 3 5 8 = true
val test3b = sorted3 4 3 8 = false

val test4a = sorted3_nicer 3 5 8 = true
val test4b = sorted3_nicer 4 3 8 = false

val test5 = increment_all_c [8,2,5,6] 2 = [10,4,7,8]

val test6 = decrement_all_c [8,2,5,6] 2 = [6,0,3,4]

val test7 = sum_all [8,2,5,6] = 21