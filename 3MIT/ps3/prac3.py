import random
from matplotlib import pyplot as plt
from matplotlib import pylab
import scipy.integrate

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

if __name__ == '__main__':

    #normal_distribution(0, 100, 1000000)
    check_empirical_rule(20)