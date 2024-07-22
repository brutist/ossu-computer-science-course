# 6.0001 Problem Set 3
#
# The 6.0001 Word Game
# Created by: Kevin Luu <luuk> and Jenna Wiens <jwiens>
#
# Name          : Jonathan Mauring Jr
# Collaborators : None
# Time spent    : start date: April 24, 2023; finished date: April 29, 2023

import math
import random
import string

VOWELS = 'aeiou'
CONSONANTS = 'bcdfghjklmnpqrstvwxyz'
HAND_SIZE = 7

SCRABBLE_LETTER_VALUES = {
  'a': 1,
  'b': 3,
  'c': 3,
  'd': 2,
  'e': 1,
  'f': 4,
  'g': 2,
  'h': 4,
  'i': 1,
  'j': 8,
  'k': 5,
  'l': 1,
  'm': 3,
  'n': 1,
  'o': 1,
  'p': 3,
  'q': 10,
  'r': 1,
  's': 1,
  't': 1,
  'u': 1,
  'v': 4,
  'w': 4,
  'x': 8,
  'y': 4,
  'z': 10
}

# -----------------------------------
# Helper code
# (you don't need to understand this helper code)

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
  # wordlist: list of strings
  wordlist = []
  for line in inFile:
    wordlist.append(line.strip().lower())
  print("  ", len(wordlist), "words loaded.")
  return wordlist


def get_frequency_dict(sequence):
  """
    Returns a dictionary where the keys are elements of the sequence
    and the values are integer counts, for the number of times that
    an element is repeated in the sequence.

    sequence: string or list
    return: dictionary
    """

  # freqs: dictionary (element_type -> int)
  freq = {}
  for x in sequence:
    freq[x] = freq.get(x, 0) + 1
  return freq


# (end of helper code)
# -----------------------------------


#
# Problem #1: Scoring a word
#
def get_word_score(word, n):
  """
    Returns the score for a word. Assumes the word is a
    valid word.

    You may assume that the input word is always either a string of letters, 
    or the empty string "". You may not assume that the string will only contain 
    lowercase letters, so you will have to handle uppercase and mixed case strings 
    appropriately. 

	The score for a word is the product of two components:

	The first component is the sum of the points for letters in the word.
	The second component is the larger of:
            1, or
            7*wordlen - 3*(n-wordlen), where wordlen is the length of the word
            and n is the hand length when the word was played

	Letters are scored as in Scrabble; A is worth 1, B is
	worth 3, C is worth 3, D is worth 2, E is worth 1, and so on.

    word: string
    n: int >= 0
    returns: int >= 0
    """
  word = word.lower()
  # calculate points for first component (sum of letters' points)
  sum = 0
  special_score = ((7 * len(word)) - 3 * (n - len(word)))

  for letter in word:
    sum += SCRABBLE_LETTER_VALUES.get(letter, 0)

  # calculate points for second component (special calculation)
  if special_score < 1:
    special_score = 1

  total_points = sum * special_score

  return total_points


#
# Make sure you understand how this function works and what it does!
#
def display_hand(hand):
  """
    Displays the letters currently in the hand.

    For example:
       display_hand({'a':1, 'x':2, 'l':3, 'e':1})
    Should print out something like:
       a x x l l l e
    The order of the letters is unimportant.

    hand: dictionary (string -> int)
    """

  for letter in hand.keys():
    for j in range(hand[letter]):
      print(letter, end=' ')  # print all on the same line
  print()  # print an empty line


#
# Make sure you understand how this function works and what it does!
# You will need to modify this for Problem #4.
#
def deal_hand(n):
  """
    Returns a random hand containing n lowercase letters.
    ceil(n/3) letters in the hand should be VOWELS (note,
    ceil(n/3) means the smallest integer not less than n/3).

    Hands are represented as dictionaries. The keys are
    letters and the values are the number of times the
    particular letter is repeated in that hand.

    n: int >= 0
    returns: dictionary (string -> int)
    """

  hand = {}
  num_vowels = int(math.ceil(
    n / 3))  # why cast into int? math.ceil will return an int type anyway

  for i in range(num_vowels):
    x = random.choice(VOWELS)
    hand[x] = hand.get(x, 0) + 1

  for i in range(num_vowels, n):
    x = random.choice(CONSONANTS)
    hand[x] = hand.get(x, 0) + 1

  asterisk_choice = random.choice(list(hand.keys())[0])
  
  while not asterisk_choice in VOWELS:
    asterisk_choice = random.choice(list(hand.keys())[0])

  hand['*'] = 1
  if hand[asterisk_choice] > 1:
    hand[asterisk_choice] = hand[asterisk_choice] - 1
  else:
    hand.pop(asterisk_choice)

  return hand
#
# Problem #2: Update a hand by removing letters
#
def update_hand(hand, word):
  """
    Does NOT assume that hand contains every letter in word at least as
    many times as the letter appears in word. Letters in word that don't
    appear in hand should be ignored. Letters that appear in word more times
    than in hand should never result in a negative count; instead, set the
    count in the returned hand to 0 (or remove the letter from the
    dictionary, depending on how your code is structured). 

    Updates the hand: uses up the letters in the given word
    and returns the new hand, without those letters in it.

    Has no side effects: does not modify hand.

    word: string
    hand: dictionary (string -> int)    
    returns: dictionary (string -> int)
    """
  word_letters = get_frequency_dict(word.lower())
  hand_letters = hand.copy()

  # Compare the letters in the word to the hand
  for k, v in word_letters.items():
    if not k in hand.keys():  # if the letter in word is not in hand; ignore it. User input something that's not supposed to be included
      continue
    if k in hand.keys() and v >= hand_letters[k]:  # if user input letter more than or equal to the hand. Updated hand should no longer have those letters
      hand_letters.pop(k)
    else:  # if input is less than no. of times it occurs in hand. Updated hand should be "no. of 'x' letter in hand - no. of 'x' letter in input"
      hand_letters[k] = hand[k] - v

  return hand_letters


# Problem #3: Test word validity
#
def is_valid_word(word, hand, word_list):
  """
    Returns True if word is in the word_list and is entirely
    composed of letters in the hand. Otherwise, returns False.
    Does not mutate hand or word_list.
   
    word: string
    hand: dictionary (string -> int)
    word_list: list of lowercase strings
    returns: boolean
    """
  word = word.lower()
  word_copy = get_frequency_dict(word)
  word_in_hand = True
  
  # make sure that letters in word are in hand
  for k in word_copy.keys():
    if k in hand.keys() and word_copy[k] <= hand[k]:
      continue
    else:
      word_in_hand = False
  
  # return True if word in word list and is a valid word from hand
  word_in_word_list = False
  
  for vowel in VOWELS:
    if word.replace('*', vowel) in word_list:
      word_in_word_list = True 
    else:
      continue
  
  if word_in_word_list and word_in_hand:
    return True
  else:
    return False

#
# Problem #5: Playing a hand
#
def calculate_handlen(hand):
  """ 
    Returns the length (number of letters) in the current hand.
    
    hand: dictionary (string-> int)
    returns: integer
    """
  sum = 0
  if len(hand) == 0:
    return sum
    
  for v in hand.values():
    sum += v

  return sum


def play_hand(hand, word_list):
  """
    Allows the user to play the given hand, as follows:

    * The hand is displayed.
    
    * The user may input a word.

    * When any word is entered (valid or invalid), it uses up letters
      from the hand.

    * An invalid word is rejected, and a message is displayed asking
      the user to choose another word.

    * After every valid word: the score for that word is displayed,
      the remaining letters in the hand are displayed, and the user
      is asked to input another word.

    * The sum of the word scores is displayed when the hand finishes.

    * The hand finishes when there are no more unused letters.
      The user can also finish playing the hand by inputing two 
      exclamation points (the string '!!') instead of a word.

      hand: dictionary (string -> int)
      word_list: list of lowercase strings
      returns: the total score for the hand
      
    """

  # BEGIN PSEUDOCODE <-- Remove this comment when you implement this function
  # Keep track of the total score
  total_score = 0
  
  # As long as there are still letters left in the hand
  is_user_done = False
  
  while calculate_handlen(hand) > 0:
    # Display the hand
    print('\nCurrent hand:', end = ' ') 
    display_hand(hand)
    
    # Ask user for input
    word = input('Enter word, or "!!" to indicate that you are finished: ').lower()
        
    # If the input is two exclamation points:
    if word == '!!':
    # End the game (break out of the loop)
      is_user_done = True
      break
    
    # Otherwise (the input is not two exclamation points): no need to implement.
    # If the word is valid
    if is_valid_word(word, hand, word_list):
      # Tell the user how many points the word earned, and the updated total score
      total_score += get_word_score(word, calculate_handlen(hand))
      print('"{fword}" earned {fscore} points.'.format(fword = word, fscore = get_word_score(word, calculate_handlen(hand))), end = ' ')
      print('Total: {ftotal} points'.format(ftotal = total_score))

    # Otherwise (the word is not valid):
    # Reject invalid word (print a message)
    else:
      print('That is not a valid word. Please choose another word.')
    
    # update the user's hand by removing the letters of their inputted word
    hand = update_hand(hand, word)

  # Game is over (user entered '!!' or ran out of letters), so tell user the total score
  if is_user_done:
    print('Total score for this hand: {fscore} points'.format(fscore = total_score))
  else:
    print('\nRan out of letters. \nTotal score for this hand: {ftotal_score} points'.format(ftotal_score = total_score))
  
  # Return the total score as result of function
  return total_score

#
# Problem #6: Playing a game
#

#
# procedure you will use to substitute a letter in a hand
#

def substitute_hand(hand, letter):
  """ 
    Allow the user to replace all copies of one letter in the hand (chosen by user)
    with a new letter chosen from the VOWELS and CONSONANTS at random. The new letter
    should be different from user's choice, and should not be any of the letters
    already in the hand.

    If user provide a letter not in the hand, the hand should be the same.

    Has no side effects: does not mutate hand.

    For example:
        substitute_hand({'h':1, 'e':1, 'l':2, 'o':1}, 'l')
    might return:
        {'h':1, 'e':1, 'o':1, 'x':2} -> if the new letter is 'x'
    The new letter should not be 'h', 'e', 'l', or 'o' since those letters were
    already in the hand.
    
    hand: dictionary (string -> int)
    letter: string
    returns: dictionary (string -> int)
    """
  # PSEUDOCODE
  # copy hand to avoid mutating and instantiate the return value
  hand_substituted = hand.copy()

  # generate a random letter from the CONSONANTS and VOWELS that is not in hand.keys()
  ALL_LETTERS = VOWELS + CONSONANTS
  random_letter = random.choice(ALL_LETTERS)

  while random_letter in hand.keys():
    random_letter = random.choice(ALL_LETTERS)
    
  # modify the key associated with the chosen letter in hand_substituted if chosen letter in hand
  if letter in hand_substituted.keys():
    hand_substituted[random_letter] = hand[letter]
    del hand_substituted[letter]
    
  # return the substituted hand 
  return hand_substituted
  

def play_game(word_list):
  """
    Allow the user to play a series of hands

    * Asks the user to input a total number of hands

    * Accumulates the score for each hand into a total score for the 
      entire series
 
    * For each hand, before playing, ask the user if they want to substitute
      one letter for another. If the user inputs 'yes', prompt them for their
      desired letter. This can only be done once during the game. Once the
      substitue option is used, the user should not be asked if they want to
      substitute letters in the future.

    * For each hand, ask the user if they would like to replay the hand.
      If the user inputs 'yes', they will replay the hand and keep 
      the better of the two scores for that hand.  This can only be done once 
      during the game. Once the replay option is used, the user should not
      be asked if they want to replay future hands. Replaying the hand does
      not count as one of the total number of hands the user initially
      wanted to play.

            * Note: if you replay a hand, you do not get the option to substitute
                    a letter - you must play whatever hand you just had.
      
    * Returns the total score for the series of hands

    word_list: list of lowercase strings
    """
  
  # PSEUDOCODE
  # ask for total number of hand, initialize number of allowed letter substitution and allowed hand replay per series
  no_of_hands = int(input('Enter total number of hands: '))

  
  HAND_SIZE = 7
  SUBSITUTION_ALLOWED = 1
  REPLAY_ALLOWED = 1
  total_score = 0
  acceptable_yes_or_no_answers = ['yes', 'y', 'no', 'n']

  while no_of_hands > 0:
    # initialize total_score, and replay_hand
    score_per_hand = 0

    # deal a hand and display it
    hand = deal_hand(HAND_SIZE)
    print('\nCurrent hand:', end = ' ') 
    display_hand(hand)
  
    # before starting game, ask if user wants to substitute a letter
    if SUBSITUTION_ALLOWED > 0:
      # ask if user want to substitute a letter on their hand(can only be done once in entire series)
      will_substitute = None
      # ask the user indefinitely if input is not right
      while not will_substitute in acceptable_yes_or_no_answers:
        will_substitute = (input('Would you like to substitute a letter? '))
  
      # error check for substitute hand letter
      if will_substitute == 'yes' or will_substitute == 'y':
        letter = (input('Which letter would you like to replace? '))
        hand = substitute_hand(hand, letter)
        # can only used substitute_letter once
        SUBSITUTION_ALLOWED -= 1
        
        
    # MAIN PLAY_HAND INSTANCE
    score_per_hand += (play_hand(hand, word_list))

    # keep track of the final score per series
    total_score += score_per_hand
    
    # play_hand until HAND_SIZE is exhausted
    no_of_hands -= 1
    
  # after each hand, ask the user if they want to replay the hand. The user will keep the better score if they choose to replay
  # (can only be done once in the entire series)
    if REPLAY_ALLOWED:
      new_score = 0

      # if user chose to replay, they do not get the option to substitute a lette 
      will_replay = None
      while not will_replay in acceptable_yes_or_no_answers:
        will_replay = (input('\nWould you like to replay the hand? '))

      if will_replay == 'yes' or will_replay == 'y':
        new_score = (play_hand(hand, word_list))
        
        if new_score > score_per_hand: 
          score_per_hand = new_score
          total_score += score_per_hand
          continue
      
      REPLAY_ALLOWED -= 1
    
  # 5. returns the total score for the series of hands.
  return total_score
#
# Build data structures used for entire session and play game
# Do not remove the "if __name__ == '__main__':" line - this code is executed
# when the program is run directly, instead of through an import statement
#
if __name__ == '__main__':
  word_list = load_words()
  
  total_score = play_game(word_list)
  print('----------')
  print('Total score over all hands:', total_score)
  
