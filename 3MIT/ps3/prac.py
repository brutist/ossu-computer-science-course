import random
import matplotlib.pyplot as plt
import pylab


def normal_data():
    dist, numSamples = [], 1000000


    for i in range(numSamples):
        dist.append(random.gauss(0, 100))

    v = pylab.hist(dist, bins=100, weights=[1/numSamples] * len(dist))

    pylab.xlabel('x')
    pylab.ylabel('Relate Frequency')

    print('Fraction within ~200 of mean =', sum(v[0][30:70]))
    plt.savefig('testplot.pdf')
    plt.show()

if __name__ == '__main__':
    normal_data()