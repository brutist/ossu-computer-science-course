# Problem Set 4C
# Name: Jonathan Mauring Jr
# Collaborators: None
# Time Spent: started - May 7 finished - May 7

import string
from ps4a import get_permutations

### HELPER CODE ###
def load_words(file_name):
    '''
    file_name (string): the name of the file containing 
    the list of words to load    
    
    Returns: a list of valid words. Words are strings of lowercase letters.
    
    Depending on the size of the word list, this function may
    take a while to finish.
    '''
    
    print("Loading word list from file...")
    # inFile: file
    inFile = open(file_name, 'r')
    # wordlist: list of strings
    wordlist = []
    for line in inFile:
        wordlist.extend([word.lower() for word in line.split(' ')])
    print("  ", len(wordlist), "words loaded.")
    return wordlist

def is_word(word_list, word):
    '''
    Determines if word is a valid word, ignoring
    capitalization and punctuation

    word_list (list): list of words in the dictionary.
    word (string): a possible word.
    
    Returns: True if word is in word_list, False otherwise

    Example:
    >>> is_word(word_list, 'bat') returns
    True
    >>> is_word(word_list, 'asdf') returns
    False
    '''
    word = word.lower()
    word = word.strip(" !@#$%^&*()-_+={}[]|\:;'<>?,./\"")
    return word in word_list


### END HELPER CODE ###

WORDLIST_FILENAME = 'words.txt'

# you may find these constants helpful
VOWELS_LOWER = 'aeiou'
VOWELS_UPPER = 'AEIOU'
CONSONANTS_LOWER = 'bcdfghjklmnpqrstvwxyz'
CONSONANTS_UPPER = 'BCDFGHJKLMNPQRSTVWXYZ'

class SubMessage(object):
    def __init__(self, text):
        '''
        Initializes a SubMessage object
                
        text (string): the message's text

        A SubMessage object has two attributes:
            self.message_text (string, determined by input text)
            self.valid_words (list, determined using helper function load_words)
        '''
        text_list = text.split()
        valid_words = []

        for word in text_list:
            if is_word(word_list, word):
                valid_words.append(word)

        self.message_text = text
        self.valid_words = valid_words
    
    def get_message_text(self):
        '''
        Used to safely access self.message_text outside of the class
        
        Returns: self.message_text
        '''
        return self.message_text

    def get_valid_words(self):
        '''
        Used to safely access a copy of self.valid_words outside of the class.
        This helps you avoid accidentally mutating class attributes.
        
        Returns: a COPY of self.valid_words
        '''
        return self.valid_words.copy()
                
    def build_transpose_dict(self, vowels_permutation):
        '''
        vowels_permutation (string): a string containing a permutation of vowels (a, e, i, o, u)
        
        Creates a dictionary that can be used to apply a cipher to a letter.
        The dictionary maps every uppercase and lowercase letter to an
        uppercase and lowercase letter, respectively. Vowels are shuffled 
        according to vowels_permutation. The first letter in vowels_permutation 
        corresponds to a, the second to e, and so on in the order a, e, i, o, u.
        The consonants remain the same. The dictionary should have 52 
        keys of all the uppercase letters and all the lowercase letters.

        Example: When input "eaiuo":
        Mapping is a->e, e->a, i->i, o->u, u->o
        and "Hello World!" maps to "Hallu Wurld!"

        Returns: a dictionary mapping a letter (string) to 
                 another letter (string). 
        '''
        # a list of all 52 uppercase and lowercase letters
        ALL_CONSONANTS = CONSONANTS_LOWER + CONSONANTS_UPPER
        ALL_VOWELS = VOWELS_LOWER + VOWELS_UPPER

        vowels_permutation = list(vowels_permutation)
        shifted_letter = {}

        # create a dictionary with consonants mapped to itself
        for consonant in ALL_CONSONANTS:
            shifted_letter[consonant] = consonant

        # add the vowels to the shifted_letter dictionary; vowels should be mapped with vowels_permutation
        for vowel in ALL_VOWELS:
            if vowel.islower():
                shifted_letter[vowel] = vowels_permutation[VOWELS_LOWER.index(vowel)]
            else:
                shifted_letter[vowel] = vowels_permutation[VOWELS_UPPER.index(vowel)].upper()

        return shifted_letter
    
    def apply_transpose(self, transpose_dict):
        '''
        transpose_dict (dict): a transpose dictionary
        
        Returns: an encrypted version of the message text, based 
        on the dictionary
        '''
        encrypted_message = ''

        for letter in self.message_text:
            if letter in transpose_dict.keys():
                encrypted_message += transpose_dict[letter]
            else:
                encrypted_message += letter

        return encrypted_message

class EncryptedSubMessage(SubMessage):
    def __init__(self, text):
        '''
        Initializes an EncryptedSubMessage object

        text (string): the encrypted message text

        An EncryptedSubMessage object inherits from SubMessage and has two attributes:
            self.message_text (string, determined by input text)
            self.valid_words (list, determined using helper function load_words)
        '''
        SubMessage.__init__(self, text)

    def decrypt_message(self):
        '''
        Attempt to decrypt the encrypted message 
        
        Idea is to go through each permutation of the vowels and test it
        on the encrypted message. For each permutation, check how many
        words in the decrypted text are valid English words, and return
        the decrypted message with the most English words.
        
        If no good permutations are found (i.e. no permutations result in 
        at least 1 valid word), return the original string. If there are
        multiple permutations that yield the maximum number of words, return any
        one of them.

        Returns: the best decrypted message    
        
        Hint: use your function from Part 4A
        '''
        permutations = get_permutations('aeiou')
        decrypted_messages = []
        permutation_to_valid_words= {}

        # There is probably a better and shorter way to implement this but I was just really trying to avoid
        # nested loops as much as I can

        # create a list of decrypted messages in each tried encryption permutations
        for permutation in permutations:
            enc_dict = self.build_transpose_dict(permutation)
            decrypted_message = self.apply_transpose(enc_dict)
            decrypted_messages.append(decrypted_message)

        # find how many valid words in each decrypted_messages and map it to the permutation used
        # Example {1 valid word : 'aeiuo'} , {2 valid words : 'eiaou}
        i = 0
        for message in decrypted_messages:
            message = SubMessage(message)
            no_of_valid_words = len(message.get_valid_words())
            permutation_to_valid_words[no_of_valid_words] = permutations[i]
            i += 1
        
        # find the permutation that maximized the number of valid words
        max_word = max(permutation_to_valid_words.keys())
        max_permutation = permutation_to_valid_words[max_word]
        
        enc_dict = self.build_transpose_dict(max_permutation)
        decoded_message = self.apply_transpose(enc_dict)

        return decoded_message


if __name__ == '__main__':

    word_list = load_words(WORDLIST_FILENAME)
    result = 'PASSED'

    # Example test case
    message = SubMessage("Hello World!")
    original_message = message.get_message_text()
    permutation = "eaiuo"
    answer = 'Hallu Wurld!'
    enc_dict = message.build_transpose_dict(permutation)
    ciphertext = message.apply_transpose(enc_dict)
    message = EncryptedSubMessage(ciphertext)
    converted_text = message.decrypt_message()
    
    print('Original message:', original_message , 'Permutation:', permutation)
    print('Expected encryption:', answer)
    print('Actual encryption:', ciphertext)
    print('Decrypted message:', converted_text)
    if answer != ciphertext or original_message != converted_text:
        result = 'FAILED'
    print('Test result:', result, end = '\n\n')
    
    #TODO: WRITE YOUR TEST CASES HERE
    
    message = SubMessage("The last shall be first and the first last")
    original_message = message.get_message_text()
    permutation = "eaiuo"
    answer = 'Tha lest shell ba first end tha first lest'
    enc_dict = message.build_transpose_dict(permutation)
    ciphertext = message.apply_transpose(enc_dict)
    message = EncryptedSubMessage(ciphertext)
    converted_text = message.decrypt_message()
    
    print('Original message:', original_message , 'Permutation:', permutation)
    print('Expected encryption:', answer)
    print('Actual encryption:', ciphertext)
    print('Decrypted message:', converted_text)
    if answer != ciphertext or original_message != converted_text:
        result = 'FAILED'
    print('Test result:', result, end = '\n\n')


    message = SubMessage("If you wish to make an apple pie from scratch, you must first invent the universe ^&*!(@123124214")
    original_message = message.get_message_text()
    permutation = "eaiuo"
    answer = 'If yuo wish tu meka en eppla pia frum scretch, yuo most first invant tha onivarsa ^&*!(@123124214'
    enc_dict = message.build_transpose_dict(permutation)
    ciphertext = message.apply_transpose(enc_dict)
    message = EncryptedSubMessage(ciphertext)
    converted_text = message.decrypt_message()
    
    print('Original message:', original_message , 'Permutation:', permutation)
    print('Expected encryption:', answer)
    print('Actual encryption:', ciphertext)
    print('Decrypted message:', converted_text)
    if answer != ciphertext or original_message != converted_text:
        result = 'FAILED'
    print('Test result:', result, end = '\n\n')
    

    message = SubMessage("In every universe, I will always love you.")
    original_message = message.get_message_text()
    permutation = "eaiuo"
    answer = 'In avary onivarsa, I will elweys luva yuo.'
    enc_dict = message.build_transpose_dict(permutation)
    ciphertext = message.apply_transpose(enc_dict)
    message = EncryptedSubMessage(ciphertext)
    converted_text = message.decrypt_message()
    
    print('Original message:', original_message , 'Permutation:', permutation)
    print('Expected encryption:', answer)
    print('Actual encryption:', ciphertext)
    print('Decrypted message:', converted_text)
    if answer != ciphertext or original_message != converted_text:
        result = 'FAILED'
    print('Test result:', result, end = '\n\n')
    

# Well it seems all tests have been passed!
