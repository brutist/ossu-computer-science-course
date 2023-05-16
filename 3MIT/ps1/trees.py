'''
This file is created to replicate the program in 6.0002 - '2. Optimization Problems'
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


# list of names, values, and calories to initialize the menu
names = ['donut', 'pork bbq', 'juice', 'fried chicken', 'spaghetti', 'cake', 'rice']
values = [10, 77, 29, 77, 88, 38, 47]
calories = [195, 150, 40, 120, 200, 120, 95]


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


def testGreedy(items, constraint, keyFunction):
    print('Testing greedy with highest value:', greedy(items, constraint , Food.getValue))