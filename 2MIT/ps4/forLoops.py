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

sequence = 'bc'
permutations = ['c']

new_permutations = [[insert_letter(word, sequence[0], i) for i in range(len(word) + 1)] for word in permutations]
permutations = [val for sublist in new_permutations for val in sublist]

print(permutations)