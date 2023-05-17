'''
This file is created to replicate the program in 6.0002 Introduction to Computational Thinking and Data Science.

All codes here were created from scratch after watching the video '2. Optimization Problems'
'''

import random

class Food(object):
    def __init__(self, n, v, c):
        self.name = n
        self.value = v
        self.calorie = c
        self.density = v/c

    def getName(self):
        return self.name
    
    def getValue(self):
        return self.value
    
    def getCost(self):
        return self.calorie
    
    def getDensity(self):
        return self.density

    def __str__(self):
        return self.name + ' <' + str(self.value) + ', ' + str(self.calorie) + ' calories'+' >'


# create a list of foods automatically
# 'n' is the number of food items in the list
def menuBuilder(names, values, calories):
    '''
    Takes in a list of names, values and calories. Assumes similar length for the three.

    returns a list of Food object whose attributes comes from the parameters.
    '''
    menu = []
    for i in range(len(values)):
        menu.append(Food(names[i], values[i], calories[i]))

    return menu


def greedy(items, maxCost, keyFunction):
    '''
    items (list of Food object) - assumes non-empty.
    maxCost (int) - the maximum cost that can fit in the knapsack.
    keyFunction (function) - this is the definition of best item (food). This function will be used
                            before sorting the items.

    returns a list of items that can be taken conforming to the limit
    '''
    itemsCopy = sorted(items, key=keyFunction, reverse=True)
    result = []
    totalValue, totalCost = 0.0, 0.0

    for i in range(len(itemsCopy)):
        # check if the item[i] can fit in the knapsack
        if (totalCost + itemsCopy[i].getCost()) <= maxCost:
            result.append(itemsCopy[i])
            totalValue += itemsCopy[i].getValue()
            totalCost += itemsCopy[i].getCost()

    return result, totalValue

def maxVal(toConsider, avail):
    '''
    toConsider (list of Food object) - Assumes non-empty list.
    avail (int) - the available 'space' to optimize picks from toConsider.

    returns a tuple of total value and the list of items of that solution.
    '''
    # if the list is empty; total value is 0 and no item were taken (base case)
    if toConsider == [] or avail == 0:
        return (0, ())
    
    # if taking the particular item would cost greater than what is available,
    # do not take the item. i.e. go to the left branch of the tree
    elif toConsider[0].getCost() >= avail:
        result = maxVal(toConsider[1:], avail)
    
    else:
        # this calculates the value of the right leafs
        withItem = toConsider[0]
        withVal, withToTake = maxVal(toConsider[1:], avail - withItem.getCost())
        withVal += withItem.getValue()
        
        # this calculates the value of the left leafs
        withoutVal, withoutToTake = maxVal(toConsider[1:], avail)

        # find the highest value from the left and right leafs
        if withVal > withoutVal:
            result = withVal, withToTake + (withItem,)
        else:
            result = withoutVal, withoutToTake


    return result


def testGreedy(items, constraint, keyFunction):
    taken, val = greedy(items, constraint, keyFunction)
    print('Total value of items taken:', val)
    for item in taken:
        print('   ', item)

def testGreedys(maxUnits):
    print('\nUsing greedy by value to allocate', maxUnits, 'calories')
    testGreedy(foods, maxUnits, Food.getValue)

    print('\nUsing greedy by calories to allocate', maxUnits, 'calories')
    testGreedy(foods, maxUnits, lambda x: 1/Food.getCost(x))

    print('\nUsing greedy by density to allocate', maxUnits, 'calories')
    testGreedy(foods, maxUnits, Food.getDensity)


def testMaxVal(maxUnits):
    print('\nUsing search trees to allocate', maxUnits, 'calories')
    val, taken = maxVal(foods, maxUnits)
    print('Total value of items taken:', val)
    for item in taken:
        print('   ', item)

# list of names, values, and calories to initialize the menu
names = ['donut', 'pork bbq', 'juice', 'fried chicken', 'spaghetti', 'cake', 'rice', 'burger', 'lechon']
values = [10, 77, 29, 77, 88, 38, 47, 57, 79]
calories = [195, 150, 40, 120, 200, 145, 95, 110, 210]

foods = menuBuilder(names, values, calories)
testGreedys(800)
testMaxVal(800)



# This is the list from the lecture. Copied and tested to see if my algorithm works the same way.
# Note: Yes. My recreation works the same way!
names = ['wine', 'beer', 'pizza', 'burger', 'fries', 'cola', 'apple', 'donut', 'cake']
values = [89,90,95,100,90,79,50,10]
calories = [123,154,258,354,365,150,95,195]
foods = menuBuilder(names, values, calories)
testGreedys(750)
testMaxVal(750)

