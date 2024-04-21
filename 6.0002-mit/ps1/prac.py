from trees import *
import sys

def fastFib(n, memo={}):
    if n == 0 or n == 1:
        return 1
    try:
        return memo[n]
    except KeyError:
        result = fastFib(n-1, memo) + fastFib(n-2, memo)
        memo[n] = result
        return result
    
for i in range(121):
    print('Fibonacci of', str(i), "=", fastFib(i))


def fastMaxVal2(toConsider, avail, memo={}):
    
    # check if the answer is in memo
    if (len(toConsider), avail) in memo:
        return memo[(len(toConsider), avail)]

    # check for base case
    elif toConsider == [] or avail == 0:
        return (0, ())

    # if the cost of an item exceeds the available don't include it
    # explore right branch
    elif toConsider[0].getCost() > avail:
        result = fastMaxVal2(toConsider[1:], avail, memo)
    
    # explore left branch
    else:
        withItem = toConsider[0]
        withVal, withToTake = fastMaxVal2(toConsider[1:], avail - withItem.getCost(), memo)
        withVal += withItem.getValue()

        withoutVal, withoutToTake = fastMaxVal2(toConsider[1:], avail, memo)

        if withVal > withoutVal:
            result = (withVal, withToTake + (withItem,))
        else:
            result = (withoutVal, withoutToTake)

    memo[(len(toConsider), avail)] = result
    return result


def testFastMaxVal2(items, maxUnits, printItems = True):
    print('\nUsing search trees with memo to allocate', maxUnits, 'calories')
    val, taken = fastMaxVal2(items, maxUnits, memo={})
    print('Total value of items taken:', val)
    
    if printItems:
        for item in taken:
            print('   ', item)

    return val, taken


if __name__ == '__main__':

    sys. setrecursionlimit(4000)

    for n in [50, 305, 400, 1000, 2440]:
            items = buildLargeMenu(n, 90, 250)
            print('Testing with', n, 'items', end=' ')

            testFastMaxVal2(items, 750, True)
            print('\n')
               