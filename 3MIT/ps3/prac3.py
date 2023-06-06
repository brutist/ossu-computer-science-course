import random
import matplotlib.pyplot as plt 


def normal_distribution(mu, sigma, num_samples):
    data = []

    # populate the data list with a gaussian distribution
    for i in range(num_samples):
        data.append(random.gauss(mu, sigma))

    v = plt.hist(data, bins=100, weights=len(data))



if __name__ == '__main__':

    weights = [1] * 3
    print(weights)
