# Problem Set 4B
# Name: Jonathan M. Mauring Jr.
# Collaborators: None
# Time Spent: started: May 5; ended: May 6 - around 11 pm

import string
import random

### HELPER CODE ###1
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

def get_story_string():
    """
    Returns: a story in encrypted text.
    """
    f = open("story.txt", "r")
    story = str(f.read())
    f.close()
    return story

### END HELPER CODE ###

WORDLIST_FILENAME = 'words.txt'

class Message(object):
    def __init__(self, text):
        '''
        Initializes a Message object
                
        text (string): the message's text

        a Message object has two attributes:
            self.message_text (string, determined by input text)
            self.valid_words (list, determined using helper function load_words)
        '''
        words = text.split(" ")
        valid_words = []

        for word in words:
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

    def build_shift_dict(self, shift):
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
        assert shift >= 0 and shift < 26, 'input shift; must be 0 - 25 only'

        # create a list of letters of the alphabet (small and capital letters) and instantiate the shift dictionary
        small_letters = list(string.ascii_lowercase)
        capital_letters = list(string.ascii_uppercase)
        all_letters = small_letters + capital_letters
        shifted_letters = dict()

        # create a dictionary (key = letter - input shift) with (value = letter)
        # it should be 'letter - input shift' to avoid going beyond the all_letters list
        # going negative means you go to the last item in the list which solves out of bounds problem
        for letter in all_letters:
            if letter.islower():
                shifted = small_letters[small_letters.index(letter) - shift]
                shifted_letters[shifted] = letter
            else:
                shifted = capital_letters[capital_letters.index(letter) - shift]
                shifted_letters[shifted] = letter

        return shifted_letters

    def apply_shift(self, shift):
        '''
        Applies the Caesar Cipher to self.message_text with the input shift.
        Creates a new string that is self.message_text shifted down the
        alphabet by some number of characters determined by the input shift        
        
        shift (integer): the shift with which to encrypt the message.
        0 <= shift < 26

        Returns: the message text (string) in which every character is shifted
             down the alphabet by the input shift
        '''
        # ASSERT that the input shift is within range
        assert shift >= 0 and shift < 26, 'input shift; must be 0 - 25 only'

        # Create a dictionary of with keys = letter and values = shifted letters according to input shift
        shifted_letters = self.build_shift_dict(shift)
        
        # create a list to contain temperary shifted message text
        shifted_message = list()

        # append to the shifted_message list each shifted letters according to the input shift
        for letter in self.message_text:
            if letter.isalpha():
                shifted_message.append(shifted_letters[letter])
            else:
                shifted_message.append(letter)

        return ''.join(shifted_message)

class PlaintextMessage(Message):
    def __init__(self, text, shift):
        '''
        Initializes a PlaintextMessage object        
        
        text (string): the message's text
        shift (integer): the shift associated with this message

        A PlaintextMessage object inherits from Message and has five attributes:
            self.message_text (string, determined by input text)
            self.valid_words (list, determined using helper function load_words)
            self.shift (integer, determined by input shift)
            self.encryption_dict (dictionary, built using shift)
            self.message_text_encrypted (string, created using shift)

        '''
        Message.__init__(self, text)
        self.shift = shift
        self.encryption_dict = self.build_shift_dict(shift)
        self.message_text_encrypted = self.apply_shift(shift)

    def get_shift(self):
        '''
        Used to safely access self.shift outside of the class
        
        Returns: self.shift
        '''
        return self.shift

    def get_encryption_dict(self):
        '''
        Used to safely access a copy self.encryption_dict outside of the class
        
        Returns: a COPY of self.encryption_dict
        '''
        return self.encryption_dict.copy()

    def get_message_text_encrypted(self):
        '''
        Used to safely access self.message_text_encrypted outside of the class
        
        Returns: self.message_text_encrypted
        '''
        return self.message_text_encrypted

    def change_shift(self, shift):
        '''
        Changes self.shift of the PlaintextMessage and updates other 
        attributes determined by shift.        
        
        shift (integer): the new shift that should be associated with this message.
        0 <= shift < 26

        Returns: nothing
        '''
        self.shift = shift
        self.encryption_dict = self.build_shift_dict(self, shift)
        self.message_text_encrypted = self.apply_shift(self, shift)


class CiphertextMessage(Message):
    def __init__(self, text):
        '''
        Initializes a CiphertextMessage object
                
        text (string): the message's text

        a CiphertextMessage object has two attributes:
            self.message_text (string, determined by input text)
            self.valid_words (list, determined using helper function load_words)
        '''
        # all Ciphertext attibutes are also Message attributes
        Message.__init__(self, text)
       
    def decrypt_message(self):
        '''
        Decrypt self.message_text by trying every possible shift value
        and find the "best" one. We will define "best" as the shift that
        creates the maximum number of real words when we use apply_shift(shift)
        on the message text. If s is the original shift value used to encrypt
        the message, then we would expect 26 - s to be the best shift value 
        for decrypting it.

        Note: if multiple shifts are equally good such that they all create 
        the maximum number of valid words, you may choose any of those shifts 
        (and their corresponding decrypted messages) to return

        Returns: a tuple of the best shift value used to decrypt the message
        and the decrypted message text using that shift value
        '''
        
        # PSEUDOCODE
        # create a dictionary (keys = shift value) with (values = no. of valid words)
        shift_values = dict()
        KEYS = 25

        # I know this is a shit code; Let me at least figure out the time complexity for consolation
        # 1st for loop - O(26)
        # 2nd for loop - O(n)
        # if (actually a loop) - O(55000) words
        # linear complexity? but with shitty long execution time
        for i in range(KEYS):
            j = KEYS - i
            decrypted_text = self.apply_shift(j)
            decrypted_text = decrypted_text.split()

            for word in decrypted_text:
                if is_word(word_list, word):
                    shift_values[j] = shift_values.get(j, 0) + 1

        # choose the best shift value and transform the message_text to a decrypted text
        max_valid_word = max(shift_values.values())
        best_shift = [k for k in shift_values if shift_values[k] == max_valid_word]
        best_shift = int(best_shift[0])
        decrypted_text = self.apply_shift(best_shift)

        return best_shift, decrypted_text

if __name__ == '__main__':

#    #Example test case (PlaintextMessage)
#    plaintext = PlaintextMessage('hello', 2)
#    print('Expected Output: jgnnq')
#    print('Actual Output:', plaintext.get_message_text_encrypted())
#
#    #Example test case (CiphertextMessage)
#    ciphertext = CiphertextMessage('jgnnq')
#    print('Expected Output:', (24, 'hello'))
#    print('Actual Output:', ciphertext.decrypt_message())
    
    word_list = load_words(WORDLIST_FILENAME)

    #TODO: WRITE YOUR TEST CASES HERE (DONE)

    # Test case for PlaintextMessage
    test_message = 'Expected Output: {expected} \nActual Output: {actual}'
    result = 'PASSED'

    plaintext = PlaintextMessage('hello', 2)
    expected_output = 'jgnnq'
    actual_output = plaintext.get_message_text_encrypted()   
    
    print(test_message.format(expected = expected_output, actual = actual_output))
    if expected_output != actual_output:
        result = 'FAILED'
    print(result, end = '\n\n')

    plaintext = PlaintextMessage('Hello World by Me', 6)
    expected_output = 'Nkrru Cuxrj he Sk'
    actual_output = plaintext.get_message_text_encrypted()
    
    print(test_message.format(expected = expected_output, actual = actual_output))
    if expected_output != actual_output:
        result = 'FAILED'
    print(result, end = '\n\n')
    
    plaintext = PlaintextMessage('!Every Good Boy Does Fine! @^@&(!', 10)
    expected_output = '!Ofobi Qyyn Lyi Nyoc Psxo! @^@&(!'
    actual_output = plaintext.get_message_text_encrypted()

    print(test_message.format(expected = expected_output, actual = actual_output))
    if expected_output != actual_output:
        result = 'FAILED'
    print(result, end = '\n\n')

    plaintext = PlaintextMessage('The quick Brown Fox Jump over the lazy Dog$%^&', 24)
    expected_output = 'Rfc osgai Zpmul Dmv Hskn mtcp rfc jyxw Bme$%^&'
    actual_output = plaintext.get_message_text_encrypted()

    print(test_message.format(expected = expected_output, actual = actual_output))
    if expected_output != actual_output:
        result = 'FAILED'
    print(result, end = '\n\n')


    # Test cases for CiphertextMessage
    test_message = 'Expected Output: {expected} \nActual Output: {actual}'
    result = 'PASSED'

    ciphertext = CiphertextMessage('jgnnq')
    expected_output = (24, 'hello')
    actual_output = ciphertext.decrypt_message() 
    
    print(test_message.format(expected = expected_output, actual = actual_output))
    if expected_output != actual_output:
        result = 'FAILED'
    print(result, end = '\n\n')

    ciphertext = CiphertextMessage('Dovv drkd sdc cmevzdyb govv dryco zkccsyxc bokn. Grsmr iod cebfsfo,')
    expected_output = (16, 'Tell that its sculptor well those passions read. Which yet survive,')
    actual_output = ciphertext.decrypt_message() 
    
    print(test_message.format(expected = expected_output, actual = actual_output))
    if expected_output != actual_output:
        result = 'FAILED'
    print(result, end = '\n\n')

    ciphertext = CiphertextMessage('Uvd P ht iljvtl Klhao, aol klzayvfly vm dvyskz.')
    expected_output = (19, 'Now I am become Death, the destroyer of worlds.')
    actual_output = ciphertext.decrypt_message() 
    
    print(test_message.format(expected = expected_output, actual = actual_output))
    if expected_output != actual_output:
        result = 'FAILED'
    print(result, end = '\n\n')

    
    #TODO: best shift value and unencrypted story 
    story = get_story_string()

    # Decode story.txt
    decoded_story = CiphertextMessage(story).decrypt_message()
    shift_value = decoded_story[0]
    decoded_message = decoded_story[1]
    
    # print decoded shift value and message
    print('Shift value:', shift_value)
    print('Decoded message:', decoded_message)