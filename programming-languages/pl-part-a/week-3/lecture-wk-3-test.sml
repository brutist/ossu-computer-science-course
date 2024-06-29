use "lecture-wk-3.sml";


val test1a = max_constant (Constant 1) = 1
val test1b = max_constant (Multiply ((Add (Constant 20, Constant 10)), Constant 20)) = 20
val test1c = max_constant (Add ((Constant 30), Multiply ((Negate (Constant 20)), Constant 10))) = 30

fun add_2 (num) =  num + 2
val test2a = map (add_2, [1,2,3,4,5]) = [3,4,5,6,7]

val test3a = append ([1,2,3], [4,5]) = [1,2,3,4,5]
val test3b = append (["a","b","c"], ["d","e"]) = ["a","b","c","d","e"]

val test4a = sum_tree(Leaf 10) = 10
val test4b = sum_tree(Node (10, Leaf 5, Leaf 2)) = 17
val test4c = sum_tree(Node (4, Leaf 10, Node (4, Leaf 10, Leaf 2))) = 30

val test5a = sum_leaves(Leaf 10) = 10
val test5b = sum_leaves(Node (10, Leaf 5, Leaf 2)) = 7
val test5c = sum_leaves(Node (4, Leaf 10, Node (4, Leaf 10, Leaf 2))) = 22

val test6a = num_leaves(Leaf 10) = 1
val test6b = num_leaves(Node (10, Leaf 5, Leaf 2)) = 2
val test6c = num_leaves(Node (4, Leaf 10, Node (4, Leaf 10, Leaf 2))) = 3

val test7a = zip([1,2,3],[4,5,6],[7,8,9]) = [(1,4,7),(2,5,8),(3,6,9)]

val test8a = unzip [(1,4,7),(2,5,8),(3,6,9)] = ([1,2,3],[4,5,6],[7,8,9])

val test9a = nondecreasing[1,2,3,4,5] = true
val test9b = nondecreasing[2,1,2,3,4] = false

val test10a = sum [1,2,3,4,5] = 15
val test10b = sum [1,2,3,4,5,6,7] = 28

val test11a = rev [1,2,3,4,5,6,7] = [7,6,5,4,3,2,1]
val test11b = rev [1,2,3,4,5] = [5,4,3,2,1]
val test11c = rev [] = []