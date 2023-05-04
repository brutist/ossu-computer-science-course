# Problem Set 4A
# Name: Jonathan Mauring 
# Collaborators: None
# Time Spent: started: May 3

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
    
    # instantiate the return value
    n = 0                       # starting index

    # if the sequence is a single character; return that character
    if len(sequence) == 1:
      return sequence
    
    # if there are several characters; find the permutations of the characters[1 : len(sequence)]
    # and the new permutations will be the different ways to inser the first character into each permutations
    else:
      permutations = get_permutations(sequence[n + 1 : len(sequence)])

      # I have found the solution by myself but don't understand it enough. 
      # I'll take my time how it works
      new_permutations = [[insert_letter(word, sequence[0], i) for i in range(len(word) + 1)] for word in permutations]
      permutations = [val for sublist in new_permutations for val in sublist]
      
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
    

