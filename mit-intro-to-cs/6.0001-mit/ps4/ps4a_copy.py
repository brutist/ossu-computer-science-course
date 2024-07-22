# I am experimenting with how to convert this permutation program to a 
# power set program

# Edit
# ->> My understanding of power set was initially mistaken.
# ->> I thought that power set is all of the permutations of the possible sublist
#     Example: given set {a, b} -->>>>> 'power set' = {{}, {c}, {bc}, {cb}, {ac}, {ca}}

# Main Idea
# 1. Before returning to the function call, append the current local permutations to the 
#    power set list

# Note: I know that the code could be a lot cleaner and the some helper functions can be removed.
#       but I was just trying to convert my previous solution to the permutation problem to this 
#       supposed to be 'power set' problem. If I was really going to work on the power set problem
#       it would be better to write the code from scratch

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


def get_power_set(sequence):
    '''
    Enumerate all possible subset of a sequence

    sequence (string): an arbitrary string to get the power set. Assume that it is a
    non-empty string.  

    You MUST use recursion for this part. Non-recursive solutions will not be
    accepted.

    Returns: a list of the power set of sequence

    Example:
    >>> get_permutations('abc')
    ['abc', 'acb', 'bac', 'bca', 'cab', 'cba']

    Note: depending on your implementation, you may return the permutations in
    a different order than what is listed here.
    '''
    perm_power_set = list()
    n = 0     # starting index

    # if the sequence is a single character; return that character
    if len(sequence) == 1:
      return sequence
    
    else:
      # find the permutation of all characters except the first one
      perm = get_power_set(sequence[1:])
      print('Perm = ', perm, end = " ")

      for item in perm:
        perm_power_set.append(item)

      # the permutations is just the different ways in which you can insert the first
      # character to the permutation of all charaters without the first one
      permutations = [insert_letter(i, sequence[0], j) for i in perm for j in range(len(i) + 1)]
      print('Permutations = ', permutations)
      
      # append everything to the permutation of the power set
      for item in permutations:
        perm_power_set.append(item)

    return perm_power_set
    


if __name__ == '__main__':

  print(get_power_set('abc'))