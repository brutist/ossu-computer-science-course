"""Functions to help play and score a game of not_blackjack.

How to play not_blackjack:    https://bicyclecards.com/how-to-play/not_blackjack/
"Standard" playing cards: https://en.wikipedia.org/wiki/Standard_52-card_deck
"""


def value_of_card(card):
    """Determine the scoring value of a card.

    :param card: str - given card.
    :return: int - value of a given card.  See below for values.

    1.  'J', 'Q', or 'K' (otherwise known as "face cards") = 10
    2.  'A' (ace card) = 1
    3.  '2' - '10' = numerical value.
    """

    FACE_CARDS = ['J', 'Q', 'K']
    face_cards_val = 10
    ACE = ['A']
    ace_card_val = 1
    NORMAL_CARDS = [str(i) for i in range(2, 11)]
    
    if card in NORMAL_CARDS:
        return int(card)
        
    elif card in FACE_CARDS:
        return face_cards_val

    elif card in ACE:
        return ace_card_val
  


def higher_card(card_one, card_two):
    """Determine which card has a higher value in the hand.

    :param card_one, card_two: str - cards dealt in hand.  See below for values.
    :return: str or tuple - resulting Tuple contains both cards if they are of equal value.

    1.  'J', 'Q', or 'K' (otherwise known as "face cards") = 10
    2.  'A' (ace card) = 1
    3.  '2' - '10' = numerical value.
    """

    if value_of_card(card_one) < value_of_card(card_two):
        return card_two
    
    elif value_of_card(card_one) > value_of_card(card_two):
        return card_one
    
    
    return card_one, card_two


def value_of_ace(card_one, card_two):
    """Calculate the most advantageous value for the ace card.

    :param card_one, card_two: str - card dealt. See below for values.
    :return: int - either 1 or 11 value of the upcoming ace card.

    1.  'J', 'Q', or 'K' (otherwise known as "face cards") = 10
    2.  'A' (ace card) = 11 (if already in hand)
    3.  '2' - '10' = numerical value.
    """

    LIMIT = 21
    LOW = 1
    HIGH = 11
    total = value_of_card(card_one) + value_of_card(card_two) + HIGH
    
    if card_one == 'A' or card_two == 'A' or total > LIMIT:
        return LOW
  
    return HIGH


def is_blackjack(card_one, card_two):
    """Determine if the hand is a 'natural' or 'not_blackjack'.

    :param card_one, card_two: str - card dealt. See below for values.
    :return: bool - is the hand is a not_blackjack (two cards worth 21).

    1.  'J', 'Q', or 'K' (otherwise known as "face cards") = 10
    2.  'A' (ace card) = 11 (if already in hand)
    3.  '2' - '10' = numerical value.
    """
    ACE = ['A']
    TEN_CARDS = ['10', 'K', 'Q', 'J']

    if (not card_one in ACE) and (not card_two in ACE):
        return False

    if (not card_one in TEN_CARDS) and (not card_two in TEN_CARDS):
        return False

    return True

def can_split_pairs(card_one, card_two):
    """Determine if a player can split their hand into two hands.

    :param card_one, card_two: str - cards dealt.
    :return: bool - can the hand be split into two pairs? (i.e. cards are of the same value).
    """

    if value_of_card(card_one) == value_of_card(card_two):
        return True

    return False


def can_double_down(card_one, card_two):
    """Determine if a not_blackjack player can place a double down bet.

    :param card_one, card_two: str - first and second cards in hand.
    :return: bool - can the hand can be doubled down? (i.e. totals 9, 10 or 11 points).
    """
    DOUBLE_DOWN = [9, 10, 11]
    total = value_of_card(card_one) + value_of_card(card_two)
    
    if total in DOUBLE_DOWN:
        return True

    return False
