###########################
# 6.0002 Problem Set 1b: Space Change
# Name: Jonathan Mauring 
# Collaborators: none
# Time: start - May 19, 2023
# Author: charz, cdenise

#================================
# Part B: Golden Eggs
#================================

from random import randint
import sys
# Problem 1
def dp_make_weight(egg_weights, target_weight, memo = {}):
    """
    Find number of eggs to bring back, using the smallest number of eggs. Assumes there is
    an infinite supply of eggs of each weight, and there is always a egg of value 1.
    
    Parameters:
    egg_weights - tuple of integers, available egg weights sorted from smallest to largest value (1 = d1 < d2 < ... < dk)
    target_weight - int, amount of weight we want to find eggs to fit
    memo - dictionary, OPTIONAL parameter for memoization (you may not need to use this parameter depending on your implementation)
    
    Returns: int, smallest number of eggs needed to make target weight
    """
    # break the problems into subproblems, solve the subproblems then store the solution
    # Imagine the eggs are items you are packing. What is the objective function? 
    # What is the weight limit in this case? What are the values of each item? 
    # What is the weight of each item?
    
    # recursive dynamic version (top-down version)
    
    # check if answer in memo
    if target_weight in memo:
        return memo[target_weight]
    
    # base case
    elif target_weight == 0:
        memo[target_weight] = 0
        return memo[target_weight]

    else:
        memo[target_weight] = target_weight + 1
        # choose from the egg weights
        for j in range(len(egg_weights)):
            # skip weight if it is larger than the available weight
            if egg_weights[j] > target_weight:
                continue
            
            if egg_weights[j] <= target_weight:
                temp = 1 + dp_make_weight(egg_weights, target_weight - egg_weights[j], memo)

            # save new smallest to the memo
            if temp < memo[target_weight]:
                memo[target_weight] = temp

    return memo[target_weight]


def dp_make_weight_greedy(egg_weights, target_weight):
    # this is greedy algorithm which might be a good enough solution for this problem.
    total_weight = 0
    egg_taken = []
    j = len(egg_weights) - 1
    while total_weight < target_weight and j >= 0:
        if total_weight + egg_weights[j] <= target_weight:
            egg_taken.append(egg_weights[j])
            total_weight += egg_weights[j]
        else:
            j -= 1
    
    if sum(egg_taken) != target_weight:
        return -1
    
    return len(egg_taken)


def dp_make_weight_iterative(egg_weights, target_weight, memo = {}):
    # iterative dynamic version. This ones fucking lovely!
    
    # populate the memo with an imposible no. of coins
    for j in range(target_weight + 1):
        memo[j] = target_weight + 1

    # iterate through 0 - target_weight-1
    for target in range(target_weight + 1):
        if not target:
            memo[target] = 0
            continue

        # try all of the possible weights
        for weight in egg_weights:
            if weight > target: 
                continue

            if weight <= target:
                temp = 1 + memo[target-weight]

            if temp < memo[target]:
                memo[target] = temp
    
    if memo[target_weight] >= target_weight + 1:
        return -1
    
    return memo[target_weight]
   

def test_dp(egg_weights, total):

    greedy = dp_make_weight_greedy(egg_weights, total)
    iterative = dp_make_weight_iterative(egg_weights, total, memo={})
    recursive = dp_make_weight(egg_weights, total, memo={})
    base_line = minCoins(egg_weights,len(egg_weights) ,total)
   
    print('GREEDY OUTPUT:', greedy)
    print('ITERATIVE OUTPUT:', iterative)
    print('RECURSIVE OUTPUT:', recursive)
    print('BASELINE OUTPUT:', base_line)
    result = 'PASSED'
    if iterative != recursive:
        print('     ITERATIVE AND RECURSIVE DOES NOT COINCIDE: TEST FAILED')
        print('     TEST PARAMETERS', egg_weights, 'TOTAL:', total)
        result = 'FAILED'
    if (greedy < iterative or greedy < recursive) and greedy != -1:
        print('     GREEDY FOUND A BETTER SOLUTION: TEST FAILED')
        print('     TEST PARAMETERS', egg_weights, 'TOTAL:', total)
        print('     ITERATIVE RESULT:', iterative)
        result = 'FAILED'
    if base_line != iterative:
        print('     ITERATIVE NOT EQUAL TO BASELINE')
        print('     TEST PARAMETERS', egg_weights, 'TOTAL:', total)
        result = 'FAILED'
    if iterative <= greedy and recursive <= greedy and iterative == recursive:
        print('     TEST PASSED')
    print()
    
    return result

def tests_dp(no_of_weights, total, no_of_test):
    for i in range(no_of_test):

        egg_weights = []
        for i in range(no_of_weights):
            egg_weights.append(randint(1,total//4))
    
        egg_weights = tuple(sorted(egg_weights))
        result = test_dp(egg_weights, total)

        if result == 'FAILED':
            print('RESULT FAILED')
            return
        
# source https://www.geeksforgeeks.org/find-minimum-number-of-coins-that-make-a-change/
# testing purposes
# m is size of coins array (number of different coins)
def minCoins(coins, m, V):
     
    # table[i] will be storing the minimum
    # number of coins required for i value.
    # So table[V] will have result
    table = [0 for i in range(V + 1)]
 
    # Base case (If given value V is 0)
    table[0] = 0
 
    # Initialize all table values as Infinite
    for i in range(1, V + 1):
        table[i] = sys.maxsize
 
    # Compute minimum coins required
    # for all values from 1 to V
    for i in range(1, V + 1):
         
        # Go through all coins smaller than i
        for j in range(m):
            if (coins[j] <= i):
                sub_res = table[i - coins[j]]
                if (sub_res != sys.maxsize and
                    sub_res + 1 < table[i]):
                    table[i] = sub_res + 1
     
    if table[V] == sys.maxsize:
        return -1
       
    return table[V]
 
    
# EXAMPLE TESTING CODE, feel free to add more if you'd like
if __name__ == '__main__':

    no_of_weights = 3
    total = 100
    no_of_test = 100000
    tests_dp(no_of_weights, total, no_of_test)