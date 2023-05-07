import string

def build_shift_dict(shift):
    '''
    Creates a dictionary that can be used to apply a cipher to a letter.
    The dictionary maps every uppercase and lowercase letter to a
    character shifted down the alphabet by the input shift. The dictionary
    should have 52 keys of all the uppercase letters and all the lowercase
    letters only.
    
    shift (integer): the amount by which to shift every letter of the 
    alphabet. 0 <= shift < 26

    Returns: a dictionary mapping a letter (string) to 
                another letter (string). 
    '''
    # PSEUDOCODE
    # ASSERT that the shift value be only within 0 to 26 only
    assert shift >= 0 and shift < 26, 'input shift; out of range'

    # create a list of letters of the alphabet (small and capital letters) and instantiate the shift dictionary
    small_letters = list(string.ascii_lowercase)
    capital_letters = list(string.ascii_uppercase)
    all_letters = small_letters + capital_letters
    shifted_letters = dict()

    # create a dictionary (key = letter) with (value = letter - input shift)
    for key in all_letters:
        if key.islower():
            shifted_letters[key] = small_letters[small_letters.index(key) - shift]
        else:
            shifted_letters[key] = capital_letters[capital_letters.index(key) - shift]
    
    return shifted_letters

print(build_shift_dict(7))

str = 'Everygoodboydoes'

for l in str:
    print(l)