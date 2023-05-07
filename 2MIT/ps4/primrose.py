
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
            return bisection_search(L[:half], e)
        else:
            return bisection_search(L[half:], e)
    

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
numbers = number_list(10000)
odd_numbers = odd_number_list(1000)
even_numbers = even_number_list(10000)

result = 'PASSED'

i = 0
for j in odd_numbers:
    if bisection_search(even_numbers, j):
        result = 'FAILED'
    i += 1

print('TESTING IF ODD IS IN NUMBERS - TRUE')
print('Number of test done:', i )
print('Test result:', result, end = '\n\n')

i = 0
for j in even_numbers:
    if not bisection_search(numbers, j):
        result = 'FAILED'
    i += 1
    
print('TESTING IF EVEN IS IN NUMBERS - TRUE')
print('Number of test done:', i )
print('Test result:', result, end = '\n\n')