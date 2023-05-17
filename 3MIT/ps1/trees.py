'''
This file is created to replicate the program in 6.0002 Introduction to Computational Thinking and Data Science.

All codes here were created from scratch after watching the video '2. Optimization Problems'
'''

from random import randint

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

def buildLargeMenu(n, maxVal, maxCost):
    '''
    This function creates a list of Food objects with name from 0-n with values and cost(calories)
    ranging from '1 to maxval' and '1 to maxCost, respectively.

    returns a list of Food objects with attributes produced randomly.
    '''
    items = []
    for i in range(n):
        items.append(Food(str(i), randint(1, maxVal), randint(1, maxCost)))

    return items


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
    elif toConsider[0].getCost() > avail:
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


# TODO - Modify maxVal() to use a memo (dynamic programming)
# key of memo is a tuple (items left to be considered or len(toConsider), available weight))
# the body of the function will check if value is in memo, update the memo if it's not there

def fastMaxVal(toConsider, avail, memo={}):
    '''
    This is a dynamic implementation of maxVal().
    '''
    # check for base case
    if toConsider == [] or avail == 0:
        return (0, ())

    # if the element to consider cost more than what's available consider the next items only
    elif toConsider[0].getCost() > avail:
        result = fastMaxVal(toConsider[1:], avail)

    else:
      
        # this will get the value of the left branch
        withItem = toConsider[0]
        withVal, withToTake = maxVal(toConsider[1:], avail - withItem.getCost())
        withVal += withItem.getValue()

        # this takes the value of the right branch
        withoutVal, withoutToTake = maxVal(toConsider[1:], avail)

        # compare the values of the left and right branch
        # choose the better branch
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


def testMaxVal(items, maxUnits, printItems = True):
    print('\nUsing search trees to allocate', maxUnits, 'calories')
    val, taken = maxVal(items, maxUnits)
    print('Total value of items taken:', val)
    
    if printItems:
        for item in taken:
            print('   ', item)

    return val, taken


def testFastMaxVal(items, maxUnits, printItems = True):
    print('\nUsing search trees with memo to allocate', maxUnits, 'calories')
    val, taken = fastMaxVal(items, maxUnits)
    print('Total value of items taken:', val)
    
    if printItems:
        for item in taken:
            print('   ', item)

    return val, taken




if __name__ == '__main__':

    # list of names, values, and calories to initialize the menu
    # This is my own list for  testing. 
    names = ['donut', 'pork bbq', 'juice', 'fried chicken', 'spaghetti', 'cake', 'rice', 'burger', 'lechon']
    values = [10, 77, 29, 77, 88, 38, 47, 57, 79]
    calories = [195, 150, 40, 120, 200, 145, 95, 110, 210]

    foods = menuBuilder(names, values, calories)
    testGreedys(800)
    testMaxVal(foods, 800, True)


    # This is the list from the lecture. Copied and tested to see if my algorithm works the same way.
    # Note: Yes. My re-creation works the same way!
    names = ['wine', 'beer', 'pizza', 'burger', 'fries', 'cola', 'apple', 'donut', 'cake']
    values = [89,90,95,100,90,79,50,10]
    calories = [123,154,258,354,365,150,95,195]
    foods = menuBuilder(names, values, calories)
    testGreedys(750)
    testMaxVal(foods, 750, True)
    testFastMaxVal(foods, 750, True)

    for n in [5, 10, 15, 20, 25, 30, 35, 40, 45, 50]:
        items = buildLargeMenu(n, 90, 250)
        print('Testing with', n, 'items', end= ' ')
        if testFastMaxVal(items, 750, False) == testMaxVal(items, 750, False):
            print('     ','Test result: SUCESS\n')
        else:
            print('     ','Test result: FAILED\n')
