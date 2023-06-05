import random

def roll_die():
    ''' Returns a random int from 1 - 6'''
    return random.choice([1,2,3,4,5,6])

def rolls_die(n=5):
    '''' Rolls a die n times and prints the result as a string . Returns None'''
    result = ''
    for i in range(n):
        result = result + str(roll_die())

    return result


def test_rolls(m =10, n =5):
    ''' Do m number of test_roll_die() with n number of roll_die each. 
        Prints the result and returns None  '''
    
    for i in range(m):
        test_roll_die(n)


def run_sim(goal, trials, to_print=False):
    '''' Simulate n number of dice rolls. 
        Parameters
        goal (string) - expected result from die rolls. Assumes the 
                        string is only composed of numbers 1-6.
        trial (int)   - number of die rolls.

        prints the probability of getting the goal, if to_print=True.
        Returns sim_prob(float with 8 decimals only) - simulation probability 
                            that the goal is drawn.
    '''
    goal_achieved = 0
    for i in range(trials):
        result = rolls_die(len(goal))
        if goal == result:
            goal_achieved += 1
    
    act_prob = round(1/(6 ** len(goal)), 8)
    sim_prob = round(goal_achieved / trials, 8)
    
    if to_print:
        print('Actual probability of', goal, '=', act_prob)
        print('Simulation probability of', goal, '=', sim_prob)
        print()

    return sim_prob

def run_sims(goal, trials, samples, to_print=False):
    ''' Simulate n number of run_sim()
        Parameters:
        goal (string) - expected result from die rolls. Assumes the 
                        string is only composed of numbers 1-6.
        trial (int)   - number of die rolls.
        samples (int) - number of samples drawn for each trials.

        prints the probability of getting the goal, if to_print=True.
        returns mean (float) - mean of the probabilities of all samples.
    '''
    probs = []
    for i in range(samples):
        probability = run_sim(goal, trials, to_print)
        probs.append(probability)
    
    mean = sum(probs) / len(probs)

    return mean


def same_date(num_people, num_same):
    possible_dates = range(366)
    birthdays = [0] * 366

    for i in range(num_people):
        birth_date = random.choice(possible_dates)
        birthdays[birth_date] += 1

    return max(birthdays) >= num_same


class FairRoulette(object):
    def __init__(self):
        self.pockets = []

        # append all of the possible outcomes in a roulette
        for i in range(1,37):
            self.pockets.append(i)

        self.ball = None
        self.pocket_odds = len(self.pockets) - 1

    def spin(self):
        self.ball = random.choice(self.pockets)

    def bet_pocket(self, pocket, amount):
        if self.ball == pocket:
            return amount*self.pocket_odds
        else:
            return -amount

    def __str__(self):
        return 'Fair Roulette'

class EuropeanRoulette(FairRoulette):
    def __init__(self):
        FairRoulette.__init__(self)
        self.pockets.append('0')
    def __str__(self):
        return 'European Roulette'

class AmericanRoulette(EuropeanRoulette):
    def __init__(self):
        EuropeanRoulette.__init__(self)
        self.pockets.append('00')
    def __str__(self):
        return 'American Roulette'

def play_roulettte(game, num_spins, pocket, bet, to_print=True):
    """ Play roulette game num_spins times.
    :params game (class) - type of roulette to be played
            num_spins (int) - no. of times to play roulette
            pocket (int) - pocket to bet on. assumes input is 1-36 only.
            bet (int) - bet per game
    
    return float - average return per game of roulette
    """
    total = 0
    for i in range(num_spins):
        game.spin()
        total += game.bet_pocket(pocket, bet)

    if to_print:
        print(num_spins, 'spins of', game)
        print('Expected return betting', pocket, '=', str(100*(total/num_spins)), '%')
        print()

    return total/num_spins

def simulate_games(trials, num_spins, pocket, bet, to_print=True):
        # This is the different types of roulettes were playing
    games = [FairRoulette(), EuropeanRoulette(), AmericanRoulette()]
    
    for i in range(trials):
        if to_print:
            print('Simulate', trials, 'trials of', num_spins, 'spins each.')

        for game in games:
            exp_return = play_roulettte(game, num_spins, pocket, bet, False)

            if to_print:
                print('Exp. return for', game, '=', str(100*exp_return), '%')
        
        print()
    
if __name__ == '__main__':
    GOAL = '1111'
    TRIALS = 100000
    SAMPLES = 50
    #mean = run_sims(GOAL, TRIALS, SAMPLES, True)
    #print('Estimated probability:', mean)

    #PEOPLE = 100
    #SAME = 4
    #print(same_date(PEOPLE, SAME))

    #game = FairRoulette()
    #POCKET = 2
    #BET = 1
    #for num_spin in (1, 100, 1000000):
    #    for i in range(3):
    #        play_roulettte(game, num_spin, POCKET, BET, True)

    TRIALS = 20
    NUM_SPINS = 1000000
    POCKET = 2
    BET = 1
    simulate_games(TRIALS, NUM_SPINS, POCKET, BET)
            