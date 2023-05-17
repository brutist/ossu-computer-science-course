from random import randint, shuffle
import unittest

# TODO : Implement BOGO sort
# randomly shuffle the elements in the list until it is sorted
def bogoSort(values):
    '''
    Takes in a list and sort it with bogo sort method.
    '''
    values = values.copy()

    while values != sorted(values):
        shuffle(values)

    return values



# TODO : Implement BUBBLE sort
# compare the element L[j] with L[j-1] and swap if L[j] is larger
# do this swaps until the list is sorted
def bubbleSort(values):
    '''
    Takes in a list and sort it wth bubble sort method.
    Assumes the list is non-empty. Does not have any side-effects to the passed value
    '''
    values = values.copy()

    isSorted = False
    while not isSorted:
        isSorted = True
        for i in range(1, len(values)):
            if values[i] < values[i - 1]:
                temp = values[i]
                values[i] = values[i - 1]
                values[i - 1] = temp
                isSorted = False

    return values


# TODO : Implement SELECTION sort
# find the smallest element in the list and put it in index [i]. Continue doing so 
# with the remaining part of the list until sorted.

def selectionSort(values):
    '''
    Takes in a list and sort it using selection sort. Assumes a non-empty list.
    Does not have any side-effects.
    '''

    values = values.copy()

    # find the smallest element and put it in the current unsorted index.
    for i in range(len(values)):
    
        for j in range(i, len(values) - 1):
            if values[j+1] < values[i]:
                temp = values[j+1]
                values[j+1] = values[i]
                values[i] = temp
    
    return values


# TODO : Implement MERGE sort
# divide the list in until you reach base case (no. of element: 0 or 1)
# merge all of those divided list







    # main merge sort function
    if len(values) == 1:
        return values
    else:
        middle = len(values) // 2
        left = mergeSort(values[middle:])
        right = mergeSort(values[:middle])
        return merge(left, right)



ITEMS = 1929
data = []
for i in range(ITEMS):
    data.append(randint(-100000, 100000))

special = [1,4,6]
if bogoSort(special) == sorted(special):
    print('Bogo sort Successful')
else:
    print('Bogo sort Test FAILED')


if bubbleSort(data.copy()) == sorted(data):
    
    print('Bubble sort Successful')
else:
    print('Bubble sort Test FAILED')


if selectionSort(data.copy()) == sorted(data):        
    print('Selection sort Successful')

else:    
    print('Selection sort Test FAILED')


if mergeSort(data.copy()) == sorted(data):        
    print('Merge sort Successful')

else:    
    print('Merge sort Test FAILED')