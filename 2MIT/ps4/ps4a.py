# Problem Set 4A
# Name: Jonathan Mauring 
# Collaborators: None
# Time Spent: started: May 3


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
    r = len(sequence)
    i = 0                         # starting index

    # This is going to be the list where all of the permutations are stored
    permutations = list()
    
    # append to the list the current permutations
    if len(sequence) == 1:
      permutations.append(sequence)
      return sequence
    
    for j in range(i, len(sequence)):
   
      word = [letter for letter in sequence]
      print(word)
      # swap the first letter to all of the letters in word
      word [j], word[r] = word[r], word[j]
      
      sequence = sequence[i + 1 : r]
      get_permutations(word)
      i += 1

    
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
  print(get_permutations('abc'))
    

