###########################
# 6.0002 Problem Set 1b: Space Change
# Name: Jonathan Mauring 
# Collaborators: none
# Time: start - May 19, 2023
# Author: charz, cdenise

#================================
# Part B: Golden Eggs
#================================


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
    
    # populate dict with impossible values
    for j in range(target_weight + 1):
        memo[j] = target_weight + 1

    # check if answer in memo
    if target_weight in memo and not memo[target_weight] > target_weight:
        return memo[target_weight]
    
    # base case
    elif target_weight == 0:
        return 0

    else:
        for j in reversed(range(len(egg_weights))):
            if egg_weights[j] > target_weight:
                continue

            if egg_weights[j] <= target_weight:
                temp = 1 + dp_make_weight(egg_weights, target_weight - egg_weights[j], memo)

            if temp < memo[target_weight]:
                memo[target_weight] = temp
    print(memo)
    return memo[target_weight]



def dp_make_weight_greedy(egg_weights, target_weight, memo = {}):
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
   

# EXAMPLE TESTING CODE, feel free to add more if you'd like
if __name__ == '__main__':
    egg_weights = (1, 5, 10, 25)
    n = 99
    print("Egg weights = (1, 5, 10, 25)")
    print("n = 99")
    print("Expected ouput: 9 (3 * 25 + 2 * 10 + 4 * 1 = 99)")
    print("Actual output GREEDY :", dp_make_weight(egg_weights, n))
    print("Actual output ITERATIVE:", dp_make_weight_iterative(egg_weights, n))
    print("Actual output GREEDY :", dp_make_weight_greedy(egg_weights, n))
    print()