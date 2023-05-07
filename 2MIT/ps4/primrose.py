
# implement a bisection search

# must use recursion
# must not copy the list; look at the midpoint and compare only when len(list) == 1

def bisection_search(L, e):
    '''
    L (list) - this could be any list; assume it's not size 0
    e (string or int) - element to search in the list

    Returns True if the element is in L; False if otherwise
    '''
    
    half = len(L) // 2
    
    if len(L) == 1:
        if L[0] == e:
            return True
        else:
            return False

    else:
        # recursive function
        if L[half] > e:
            found = bisection_search(L[:half], e)
        else:
            found = bisection_search(L[half:], e)
    
    return found

def number_list(n):
    '''
    Create a list of integers from 0 to n.

    n (integer) - no of items in list. Assumes a non-zero and non-negative input

    Returns list of numbers (0 - n)
    '''
    numbers = []
    for i in range(n):
        numbers.append(i)

    return numbers

def odd_number_list(n):
    '''
    Create a list of odd integers from 0 to n.

    n (integer) - no of items in list. Assumes a non-zero and non-negative input

    Returns list of numbers (0 - n)
    '''
    numbers = []
    for i in range(n):
        if (i % 2) != 0:
            numbers.append(i)

    return numbers


def even_number_list(n):
    '''
    Create a list of odd integers from 0 to n.

    n (integer) - no of items in list. Assumes a non-zero and non-negative input

    Returns list of numbers (0 - n)
    '''
    numbers = []
    for i in range(n):
        if (i % 2) == 0:
            numbers.append(i)

    return numbers



# Testing...
numbers = number_list(100)
odd_numbers = odd_number_list(100)
even_numbers = even_number_list(100)

result = 'PASSED'

actual = bisection_search(numbers, 12)
expected = True
if actual != expected:
    result = 'FAILED'
else:
    result = 'PASSED'
print('Actual:', actual)
print('Expected:', expected)
print(result, end = '\n\n')

actual = bisection_search(odd_numbers, 12)
expected = False
if actual != expected:
    result = 'FAILED'
else:
    result = 'PASSED'

print('Actual:', actual)
print('Expected:', expected)
print(result, end = '\n\n')

actual = bisection_search(even_numbers, 82)
expected = True
if actual != expected:
    result = 'FAILED'
else:
    result = 'PASSED'

print('Actual:', actual)
print('Expected:', expected)
print(result, end = '\n\n')
