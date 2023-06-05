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

def simulate_games(trials, num_spins, pocket, bet, to_print=False):
    """ Simulate trials number of games with each trail having num_spins amount of games.

    :params trials (int) - number of trials or samples
            num_spins (int) - number of spins or sample size
            pocket (int) - pocket that you want to bet on
            bet (int) - amount to bet per game
            to_print (bool) - prints data per trial for debugging, if True

    return (dict) - dictionary with 'game type'(str) as keys and a'verage expected result'(float) 
                    in percent as values
    """
    result = {}
    # This is the different types of roulettes were playing
    games = [FairRoulette(), EuropeanRoulette(), AmericanRoulette()]
    # map the type of game with its result
    for g in games:
        result[g.__str__()] = []

    # simulate trial amounts of num_spins
    for i in range(1, trials + 1):
        if to_print:
            print('Simulate', trials, 'trials of', num_spins, 'spins each.')
        else:
            print('Simulating trial number', i, '...')
        for game in games:
            exp_return = play_roulettte(game, num_spins, pocket, bet, False)
            result[game.__str__()].append(exp_return)

            if to_print:
                print('Exp. return for', game, '=', str(100*exp_return), '%')
        
        if to_print:
            print()

    for k,v in result.items():
        result[k] = 100 * sum(v)/len(v)

    return result

def get_mean_and_std(data):
    """ Calculates the mean and standard deviation from a given data.
    :params data (list of int) - list of data

    returns tuple - (mean, standard deviation) calculated from the given data
    """
    mean = sum(data) / len(data)
    # calculate std by multiplying two values: summation of the difference of x and mean,
    # and 1/no. of items
    factor1 = 0
    for n in data:
        factor1 += ((n - mean)**2)

    factor2 = 1 / len(data)

    std = (factor1 * factor2)**0.5

    return mean, std

def find_pocket_return(game, trials, num_spins, to_print=False):
    returns = []

    for i in range(trials):
        returns.append(play_roulettte(game, num_spins, pocket=2, bet=1, to_print=False))

    return returns

def test_empirical_rule(games, trials, to_print=False):
    """ Use empirical rule to calculate the confidence interval of expected returns in different types of 
        roulette games.

        :params games (list of roulette object) - list of roulette games that we are testing
                trials (int) - number of trials per games. Sample sizes will be [1000, 100000, 1000000]
                to_print (bool) - if True, prints data associated with EACH trials.

        returns dict - game name(str) as keys and list of tuples (sample_size, mean in %, std in %) as values.
    """
    result = {}
    # instantiate the dictionary keys with string names of the game
    for game in games:
        result[game.__str__()] = []

    for num_spins in [1000, 100000, 1000000]:
        print('\nSimulate betting a pocket for', trials, 'trials of', num_spins, 'spins each')

        for game in games:
            returns = find_pocket_return(game, trials, num_spins, to_print)
            mean, std = get_mean_and_std(returns)
            # append the spins, mean and std to the dictionary
            result[game.__str__()].append((num_spins, 100*mean, 100*std))

            print('Expected return for', game, '=', str(round(100*mean, 3)) + '%', 
            '+/-', str(round(100*1.96*std, 3)) +'% with 95% confidence')
    
    return result
    
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

    #TRIALS = 20
    #NUM_SPINS = 1000000
    #POCKET = 2
    #BET = 1
    #result = simulate_games(TRIALS, NUM_SPINS, POCKET, BET, False)
    #print(result)

    games = [FairRoulette(), EuropeanRoulette(), AmericanRoulette()]
    TRIALS = 20
    test_empirical_rule(games, TRIALS)