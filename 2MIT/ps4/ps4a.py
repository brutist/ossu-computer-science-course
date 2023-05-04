# Problem Set 4A
# Name: Jonathan Mauring 
# Collaborators: None
# Time Spent: started: May 3 
#             finished; May 4 11:00 pm

def insert_letter(word, letter, i):
    '''
    Insert a given letter into a word at a specified index

    word (string): an arbitrary word

    letter(string): assume that there is only one character to insert.

    i (integer): the index in which the letter must be inserted into

    Returns: string with inserted letter

    Example:
    >>> insert_letter('recursion', 'i', 1)
    riecursion
    '''
    word = list(word)
    word.insert(i, letter)
    word = ''.join(word)

    return word

def remove_duplicates(items):
    '''
    Takes in a list and remove all the duplicates

    items (list of integers or strings) - assumes not null

    Returns: items (list) - list of items with duplicates removed
    '''

    item_count = dict()

    for item in items:
      item_count[item] = item_count.get(item, 0) + 1
    
    items = [k for k in item_count.keys()]

    return items


def get_permutations(sequence):
    '''
    Enumerate all permutations of a given string

    sequence (string): an arbitrary string to permute. Assume that it is a
    non-empty string.  

    You MUST use recursion for this part. Non-recursive solutions will not be
    accepted.

    Returns: a list of all permutations of sequence

    Example:
    >>> get_permutations('abc')
    ['abc', 'acb', 'bac', 'bca', 'cab', 'cba']

    Note: depending on your implementation, you may return the permutations in
    a different order than what is listed here.
    '''
  
    n = 0     # starting index

    # if the sequence is a single character; return that character
    if len(sequence) == 1:
      return sequence
    
    # if there are several characters; find the permutations of the characters[1 : len(sequence)]
    # and the new permutations will be the different ways to insert the first character into each permutations
    else:
      # find all of the permutations of the sequence except the first letter
      permutations = get_permutations(sequence[n + 1 : len(sequence)])
      # create a new list in which the first letter is inserted in every index
      new_permutations = [[insert_letter(word, sequence[0], i) for i in range(len(word) + 1)] for word in permutations]
      # I am quite sure that it is possible to create a one-dimensional list on the get-go
      # but in my limited knowledge; I got to flatten the resulting list of all permutations
      permutations = [val for sublist in new_permutations for val in sublist]

    permutations = remove_duplicates(permutations)

    return permutations
    


if __name__ == '__main__':
#    #EXAMPLE
#    example_input = 'abc'
#    print('Input:', example_input)
#    print('Expected Output:', ['abc', 'acb', 'bac', 'bca', 'cab', 'cba'])
#    print('Actual Output:', get_permutations(example_input))
    
#    # Put three example test cases here (for your sanity, limit your inputs
#    to be three characters or fewer as you will have n! permutations for a 
#    sequence of length n)
    
    result = 'PASSED'

    print('#################################################################')
    print('Test Case No. 1')
    example_input = 'abc'
    expected_output = sorted(['abc', 'acb', 'bac', 'bca', 'cab', 'cba'])
    actual_output = sorted(get_permutations(example_input))
    

    if expected_output != actual_output:
      result = 'FAILED'

    print('Input:', example_input)
    print('Expected Output:', expected_output)
    print('Actual Output:', actual_output)
    print('Test result:', result)
    print('#################################################################')


    print('#################################################################')
    print('Test Case No. 2')
    example_input = 'ABSG'
    expected_output = sorted(['ABGS', 'ABSG', 'AGBS', 'AGSB', 'ASBG', 'ASGB', 'BAGS',
    'BASG', 'BGAS', 'BGSA', 'BSAG', 'BSGA', 'GABS', 'GASB', 'GBAS', 'GBSA', 'GSAB', 
    'GSBA', 'SABG', 'SAGB', 'SBAG', 'SBGA', 'SGAB', 'SGBA'])
    actual_output = sorted(get_permutations(example_input))
    

    if expected_output != actual_output:
      result = 'FAILED'

    print('Input:', example_input)
    print('Expected Output:', expected_output)
    print('Actual Output:', actual_output)
    print('Test result:', result)
    print('#################################################################')
    

    print('#################################################################')
    print('Test Case No. 3')
    example_input = 'xyz'
    expected_output = sorted(['xyz', 'xzy', 'yxz', 'yzx', 'zxy', 'zyx'])
    actual_output = sorted(get_permutations(example_input))
    

    if expected_output != actual_output:
      result = 'FAILED'

    print('Input:', example_input)
    print('Expected Output:', expected_output)
    print('Actual Output:', actual_output)
    print('Test result:', result)
    print('#################################################################')