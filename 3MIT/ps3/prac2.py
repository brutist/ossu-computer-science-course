import random

random.seed(0)
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
        trial (int)       - number of die rolls.

        prints the probability of getting the goal. Returns None
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

    return goal_achieved


if __name__ == '__main__':
    print(run_sim('3', 99999, True))