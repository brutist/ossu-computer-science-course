import random
from matplotlib import pyplot as plt
from matplotlib import pylab
import scipy.integrate
from prac2 import get_mean_and_std

def normal_distribution(mu, sigma, num_samples):
    data = []

    # populate the data list with a gaussian distribution
    for i in range(num_samples):
        data.append(random.gauss(mu, sigma))

    v = plt.hist(data, bins=100, weights=[1/num_samples]*len(data))
    
    plt.xlabel('x')
    plt.ylabel('Relative Frequency')
    #plt.show()
    print('Estimated coverage of ~200 std:', sum(v[0][30:70]))
    
def gaussian(x, mu, sigma):
    """ Returns a gaussian value corresponding to an x with a given mean and std."""
    factor1 = (1/(sigma*((2*pylab.pi)**0.5)))
    factor2 = pylab.e** -((x-mu)**2/(2*sigma**2))
    return factor1 * factor2

def check_empirical_rule(num_trials):
    """ Creates a file that list the fraction of data that falls within [1, 1.96, 3] 
        standard deviation on a gaussian distribution.

    :params num_trials(int) - number of trials to be done
    """
    f = open('check_empirical_rule.txt', 'w')

    for trial in range(num_trials):
        mu = random.randint(-10, 10)
        sigma = random.randint(1, 10)
        f.write('For mu = ' + str(mu) + ' and sigma = ' + str(sigma) + '\n')

        for sigma_range in (1, 1.96, 3):
            area = scipy.integrate.quad(gaussian, mu-(sigma_range*sigma), mu+(sigma_range*sigma), (mu, sigma))[0]
            
            f.write('Fraction within ' + str(sigma_range) + ' std = ' + str(round(area, 4)) + '\n')

        f.write('\n')

def roulette_plot(num_pockets, num_trials):
    """ Shows a plot of the result of num_trials number of roulette games with num_pockets number of pockets.
    
    :params num_pockets (int) - total number of pockets to bet on
            num_trials (int) - number of roulette games

    return (float) - average probability of all pockets, rounded to 4 decimal places.
    """
    # populate the dictionary of pockets mapped to the number of times it was drawn.
    pocket_choices = range(1, num_pockets+1)
    pockets = {}
    for i in pocket_choices:
        pockets[i] = 0

    # play num_trials number of roulette games and record the result in the pockets dictionary
    for i in range(num_trials):
        result = random.choice(pocket_choices)
        pockets[result] += 1

    # plot the result
    x_axis = []
    y_axis = []

    for k, v in pockets.items():
        x_axis.append(k)
        y_axis.append((v/num_trials)*100) # to show y_axis in percentages

    plt.plot(x_axis, y_axis)
    plt.xlabel('Pockets')
    plt.ylabel('Occurrence in percent (%)')
    plt.show()

    # calculates the mean of all pockets probabilities.
    mean = round(sum(y_axis) / len(y_axis), 4)

    return mean

def plot_means(num_dice, num_rolls, num_bins, legend, color, style):
    """ Shows a histogram of sample means of a continuous dice roll (includes floats instead of just ints)
        in num_dice sample size with num_rolls no. of trials.

    :params num_dice (int) - number of trials
            num_rolls (int) - sample size
            num_bins (int) - number of bins in histogram
            legend (str) - legend for the histogram
            color (str) - color for the histogram
            style (str) - style for the histogram

    return (float) - tuple of size 2, (mean, standard deviation) of the means of trials (sample means).
    """
    means = []

    for i in range(num_rolls // num_dice):
        values = 0
        # simulate dice rolls num_dice times
        for j in range(num_dice):
            values += random.random() * 5
        # record the current mean to the list of means
        means.append(values/float(num_dice))
    
    # create the histogram
    pylab.hist(means, num_bins, color=color, label=legend, weights=[1/len(means)]*len(means), hatch=style)
    # return a tuple of size 2 - (mean, standard deviation) of the means
    return get_mean_and_std(means)

if __name__ == '__main__':

    #normal_distribution(0, 100, 1000000)
    #check_empirical_rule(20)

    #mean = roulette_plot(36, 100000)
    #print('Probability per pocket = ', mean)

    # CHECK THE CENTRAL LIMIT THEOREM FOR A CONTINUOUS DIE 
    DIE = 1
    ROLLS = 100000
    BINS = 19
    LEGEND = '1 die'
    COLOR = 'b'
    STYLE = '*'
    mean, std = plot_means(DIE, ROLLS, BINS, LEGEND, COLOR, STYLE)
    print('Sample means of 1 die:', mean, 'with an std:', std)

    DIE = 50
    ROLLS = 10000000
    BINS = 19
    LEGEND = 'Mean of 50 dice'
    COLOR = 'r'
    STYLE = '//'
    mean, std = plot_means(DIE, ROLLS, BINS, LEGEND, COLOR, STYLE)
    print('Sample means of 50 dice:', mean, 'with an std:', std)
    
    pylab.title('Means of Continuous Die')
    pylab.xlabel('Values')
    pylab.ylabel('Probability')
    pylab.legend()
    pylab.show()
