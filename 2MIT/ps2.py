# Problem Set 2, hangman.py
# Name: Jonathan Mauring
# Collaborators: None
# Time spent: start time - April 22, 2023

# Hangman Game
# -----------------------------------
# Helper code
# You don't need to understand this helper code,
# but you will have to know how to use the functions
# (so be sure to read the docstrings!)
import random
import string

WORDLIST_FILENAME = "words.txt"


def load_words():
    """
    Returns a list of valid words. Words are strings of lowercase letters.
    
    Depending on the size of the word list, this function may
    take a while to finish.
    """
    print("Loading word list from file...")
    # inFile: file
    inFile = open(WORDLIST_FILENAME, 'r')
    # line: string
    line = inFile.readline()
    # wordlist: list of strings
    wordlist = line.split()
    print("  ", len(wordlist), "words loaded.")
    return wordlist



def choose_word(wordlist):
    """
    wordlist (list): list of words (strings)
    
    Returns a word from wordlist at random
    """
    return random.choice(wordlist)

# end of helper code

# -----------------------------------

# Load the list of words into the variable wordlist
# so that it can be accessed from anywhere in the program
wordlist = load_words()

def is_word_guessed(secret_word, letters_guessed):
    '''
    secret_word: string, the word the user is guessing; assumes all letters are
      lowercase
    letters_guessed: list (of letters), which letters have been guessed so far;
      assumes that all letters are lowercase
    returns: boolean, True if all the letters of secret_word are in letters_guessed;
      False otherwise
    '''
    secret_word = list(secret_word)
    unguessed_letters = [letter for letter in secret_word if letter not in letters_guessed]
    unguessed_letters = len(unguessed_letters)
    NONE = 0
  
    if unguessed_letters is NONE:
      guessed = True
    else:
      guessed = False

    return guessed

def get_guessed_word(secret_word, letters_guessed):
    '''
    secret_word: string, the word the user is guessing
    letters_guessed: list (of letters), which letters have been guessed so far
    returns: string, comprised of letters, underscores (_), and spaces that represents
      which letters in secret_word have been guessed so far.
    '''
    secret_word = list(secret_word)
    space = '_ '
    common_letters = [letter if letter in letters_guessed else space for letter in secret_word]
    common_letters = ''.join(common_letters)
    
    return(common_letters)

def get_available_letters(letters_guessed):
    '''
    letters_guessed: list (of letters), which letters have been guessed so far
    returns: string (of letters), comprised of letters that represents which letters have not
      yet been guessed.
    '''
    all_letters = list(string.ascii_lowercase)
    available_letters = [letter for letter in all_letters if letter not in letters_guessed]
    available_letters = ''.join(available_letters)
  
    return available_letters
    
def is_letter_in(secret_word, letter):
  '''
  secret_word: string, the word the user is guessing.
  letter: string, the guess letter. Must be checked if this letter is present in secret_word
  returns: boolean, True if the letter is present, False if otherwise.
  '''
  is_found = None
  if letter in secret_word:
    is_found = True
  else: 
    is_found = False

  return is_found

def is_letter_valid(guess_letter, letters_guessed):
  '''
  gues_letter: string/char - the letter that the user input each round
  letters_guessed: list (of letters) that the user have already guessed
  return: Boolean: True if the letter is an alphabet and not in the letters already guessed
          False if otherwise
  '''
  if guess_letter.isalpha() and not guess_letter in letters_guessed:
    return True
  else:
    return False
  
def hangman(secret_word):
    '''
    secret_word: string, the secret word to guess.
    
    Starts up an interactive game of Hangman.
    
    * At the start of the game, let the user know how many 
      letters the secret_word contains and how many guesses s/he starts with.
      
    * The user should start with 6 guesses

    * Before each round, you should display to the user how many guesses
      s/he has left and the letters that the user has not yet guessed.
    
    * Ask the user to supply one guess per round. Remember to make
      sure that the user puts in a letter!
    
    * The user should receive feedback immediately after each guess 
      about whether their guess appears in the computer's word.

    * After each guess, you should display to the user the 
      partially guessed word so far.
    
    Follows the other limitations detailed in the problem write-up.
    '''
    word_length = len(secret_word)
    GUESS = 6
    EXHAUSTED = 0
    WARNINGS = 3
    letters_guessed = list()
    vowels = ['a','e','i','o','u']
  
    print("Welcome to the game Hangman!")
    print("I am thinking of a word that is", word_length, "letters long.")
    print("You have", WARNINGS, "warnings left.")
    print('------------')
    while True:
      if is_word_guessed(secret_word, letters_guessed):
        unique_letters_guessed = len(set(secret_word))
        total_score = GUESS * unique_letters_guessed
        print('Congratulations, you won!')
        print('Your total score for this game:', total_score)
        break
      if GUESS <= EXHAUSTED:
        print('Sorry. You ran out of guesses. The word was', secret_word)
        break
        
      print('You have', GUESS, 'guesses left.')
      print('Available letters:',get_available_letters(letters_guessed))
      guess_letter = (input('Please guess a letter: ')).lower()

      if not is_letter_valid(guess_letter, letters_guessed):
        if not guess_letter.isalpha():  
          print("Opps! That's not a valid letter.", end = "")

        if guess_letter in letters_guessed:
          print("Oops! You've already guessed that letter.", end = "")
        
        if WARNINGS <= EXHAUSTED:
          GUESS -= 1
          print("You have no warnings left so you lose one guess:", get_guessed_word(secret_word, letters_guessed))
        
        if WARNINGS > EXHAUSTED:
          WARNINGS -= 1
          print("You now have", WARNINGS,"warnings left:", get_guessed_word(secret_word, letters_guessed)) 
          
  
      elif is_letter_in(secret_word, guess_letter):
        letters_guessed.append(guess_letter)
        print('Good guess:', get_guessed_word(secret_word, letters_guessed))
      
      elif not is_letter_in(secret_word, guess_letter):
        print('Oops! That letter is not in my word:', get_guessed_word(secret_word, letters_guessed))
        letters_guessed.append(guess_letter)
        if guess_letter in vowels:
          GUESS -= 2
        else:
          GUESS -= 1
      
      # line separator for the every round/guess
      print('------------')
      
      
# When you've completed your hangman function, scroll down to the bottom
# of the file and uncomment the first two lines to test
#(hint: you might want to pick your own
# secret_word while you're doing your own testing)


# -----------------------------------



def match_with_gaps(my_word, other_word):
  '''
  my_word: string with _ characters, current guess of secret word
  other_word: string, regular English word
  returns: boolean, True if all the actual letters of my_word match the corresponding letters of other_word, or the letter is the special symbol
  _ , and my_word and other_word are of the same length;False otherwise: 
  '''
  other_word = other_word.strip()
  my_word = my_word.replace(" ","")
  UNKNOWN = '_'
  if not len(my_word) == len(other_word):
    return False
    
  for A, B in zip(my_word, other_word):
    
    if A == B:
      continue
    if A == UNKNOWN and not B in my_word:
      continue
    else:
      return False

  return True
  

def show_possible_matches(my_word):
    '''
    my_word: string with _ characters, current guess of secret word
    returns: nothing, but should print out every word in wordlist that matches my_word
             Keep in mind that in hangman when a letter is guessed, all the positions
             at which that letter occurs in the secret word are revealed.
             Therefore, the hidden letter(_ ) cannot be one of the letters in the word
             that has already been revealed.
    '''
    count = 0
    matches = list()
    for word in wordlist:
      if match_with_gaps(my_word, word):
        count += 1
        matches.append(word)
      else: 
        continue
    
    if count == 0:
      print("No matches found")
    else:
      print(*matches)

def hangman_with_hints(secret_word):
    '''
    secret_word: string, the secret word to guess.
    
    Starts up an interactive game of Hangman.
    
    * At the start of the game, let the user know how many 
      letters the secret_word contains and how many guesses s/he starts with.
      
    * The user should start with 6 guesses
    
    * Before each round, you should display to the user how many guesses
      s/he has left and the letters that the user has not yet guessed.
    
    * Ask the user to supply one guess per round. Make sure to check that the user guesses a letter
      
    * The user should receive feedback immediately after each guess 
      about whether their guess appears in the computer's word.

    * After each guess, you should display to the user the 
      partially guessed word so far.
      
    * If the guess is the symbol *, print out all words in wordlist that
      matches the current guessed word. 
    
    Follows the other limitations detailed in the problem write-up.
    '''
    word_length = len(secret_word)
    GUESS = 6
    EXHAUSTED = 0
    WARNINGS = 3
    letters_guessed = list()
    vowels = ['a','e','i','o','u']
  
    print("Welcome to the game Hangman!")
    print("I am thinking of a word that is", word_length, "letters long.")
    print("You have", WARNINGS, "warnings left.")
    print('------------')
    while True:
      my_word = get_guessed_word(secret_word, letters_guessed)
      if is_word_guessed(secret_word, letters_guessed):
        unique_letters_guessed = len(set(secret_word))
        total_score = GUESS * unique_letters_guessed
        print('Congratulations, you won!')
        print('Your total score for this game:', total_score)
        break
      if GUESS <= EXHAUSTED:
        print('Sorry. You ran out of guesses. The word was', secret_word)
        break
        
      print('You have', GUESS, 'guesses left.')
      print('Available letters:',get_available_letters(letters_guessed))
      guess_letter = (input('Please guess a letter: ')).lower()

      if not is_letter_valid(guess_letter, letters_guessed):
        if guess_letter == "*":
          print("Possible matches are:")
          show_possible_matches(my_word)
          print('------------')
          continue
          
        if not guess_letter.isalpha():  
          print("Opps! That's not a valid letter.", end = "")

        if guess_letter in letters_guessed:
          print("Oops! You've already guessed that letter.", end = "")
        
        if WARNINGS <= EXHAUSTED:
          GUESS -= 1
          print("You have no warnings left so you lose one guess:", get_guessed_word(secret_word, letters_guessed))
        
        if WARNINGS > EXHAUSTED:
          WARNINGS -= 1
          print("You now have", WARNINGS,"warnings left:", get_guessed_word(secret_word, letters_guessed)) 
          
  
      elif is_letter_in(secret_word, guess_letter):
        letters_guessed.append(guess_letter)
        print('Good guess:', get_guessed_word(secret_word, letters_guessed))
      
      elif not is_letter_in(secret_word, guess_letter):
        print('Oops! That letter is not in my word:', get_guessed_word(secret_word, letters_guessed))
        letters_guessed.append(guess_letter)
        if guess_letter in vowels:
          GUESS -= 2
        else:
          GUESS -= 1
      
      # line separator for the every round/guess
      print('------------')
      
      



# When you've completed your hangman_with_hint function, comment the two similar
# lines above that were used to run the hangman function, and then uncomment
# these two lines and run this file to test!
# Hint: You might want to pick your own secret_word while you're testing.


if __name__ == "__main__":
    # pass

    # To test part 2, comment out the pass line above and
    # uncomment the following two lines.
    
    secret_word = choose_word(wordlist)
    hangman_with_hints('apple')
    #show_possible_matches("t_ _ t")
    # hangman(secret_word)



    # Current Tests
    #print(is_word_guessed('apple', ['a', 'p', 'l', 'e', 'u', 'q']))
    #print('Common letters: ', get_guessed_word('apple', ['e', 'i', 'k', 'p', 'r', 's']))
    #print(get_available_letters(['e', 'i', 'k', 'p', 'r', 's']))
###############
    
    # To test part 3 re-comment out the above lines and 
    # uncomment the following two lines. 
    
    #secret_word = choose_word(wordlist)
    #hangman_with_hints(secret_word)
