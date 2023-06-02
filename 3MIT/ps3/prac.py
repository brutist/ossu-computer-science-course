import random
from matplotlib import pyplot as plt
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


def throwNeedles(numNeedles):
    inCircle = 0
    for i in range(1, numNeedles):
        x = random.random()
        y = random.random()
        
        if (x*x + y*y)**0.5 <= 1.0:
            inCircle += 1
    
    return (4*inCircle)/float(numNeedles)


# ==========================================================================================================================================
#                                               MonteCarlo Simulation of calculating π.
# Setup - A circle and a square is on the table. The circle has a radius of 1 unit. Its area is πr^2 or π unit. The square has sides of 
#         1 unit. Its area will be s^2 or 1 unit. 
#
# Main Idea - We will use randomness of a falling ball to the table to calculate the value of π. 
#               a. We will simulate balls falling using a Ball object. Its drop location will be represented with a Location object which
#                   has x,y components.
#               b. We will count how many balls fell into the two shapes and reason out that since the difference in area of square and 
#                   circle is π, then it stands to reason that the probability of balls falling onto the circle is π bigger than the square.
#                   Therefore, we can calculate the value of pie by no. of balls in circle / no. of balls in square.
# ==========================================================================================================================================

class Location(object):
    def __init__(self, x, y):
        self.x = x
        self.y = y

    def getX(self):
        return self.x

    def getY(self):
        return self.y

    def __eq__(self, other):
        if self.x == other.getX() and self.y == other.getY():
            return True

        return False
    
    def __str__(self):
        return '(' + str(self.x) + ', ' + str(self.y) + ')'

class Ball(object):
    '''
    Simulate ball(s) falling on a table.
    '''

    def dropBall(self):
        '''
        l (int) - length of the table.
        w (int) - width of the table.

        returns Location object - random location on the table
        '''
        LENGTH = 12
        WIDTH = 3
        x = random.randint(-LENGTH, LENGTH + 1)
        y = random.randint(-WIDTH, WIDTH + 1)

        return Location(x, y)
    
    def dropBalls(self, n):
        '''
        l (int) - length of table
        w (int) - width of table
        n (int, > 1) - number of balls to drop

        return a list of Location objects where the ball falls on the table
        '''
        locations = []
        for i in range(n):
            locations.append(self.dropBall())
        
        return locations
        

class Shape(object):
    def __init__(self):
        CENTER = Location(0, 0)
        self.origin = CENTER
    
    def isBallIn(self, loc):
        '''
        This is a parent class of Square and Circle class.

        Location (x, y tuple of ints) - the location of the ball 
        '''
        raise NotImplementedError


class Circle(Shape):
    '''
    The circle is in the right-hand side of the table.

    The circle has a radius of 1 unit. 
    '''
    def __init__(self):
        CENTER = Location(-3, 0)
        self.origin = CENTER
        self.radius = 2

    def distance(self, other):
        x = (self.getX() - other.getX())**2
        y = (self.getY() - other.getY())**2

        return (x + y)**0.5

    def isBallIn(self, loc):
        if self.distance(self.origin, loc) <= self.radius:
            return True

        return False


class Square(Shape):
    '''
    The square is in the left-hand side of the table.

    The square has sides of length 1 unit.
    '''
    def __init__(self):
        Shape.__init__(self)
        CENTER = Location(3, 0)
        self.origin = CENTER

    def isBallIn(self, loc):
        if loc.getX() >= 2 and loc.getX() <= 4 and loc.getY() >= -1 and loc.getY() <= 1:
            return True

        return False


class CalculatePi(object):
    '''
    A class that calculates pi using Monte Carlo Simulation
    '''

    def __init__(self):
        self.ball = Ball()
        self.square = Square()
        self.circle = Circle()

    def generatePi(self, n): # n is number of ball dropped, the higher the better the accuracy
        nSquare = 0
        nCircle = 0
        for loc in self.ball.dropBalls(n):
            if self.square.isBallIn(loc):
                nSquare += 1
            elif self.circle.isBallIn(loc):
                nCircle += 1
            
        return nCircle/nSquare



if __name__ == '__main__':
    #normal_data()

    # Buffon-Laplace Method on Finding Pi
    #pi = throwNeedles(100000000)
    #print('Buffon Laplace Pi:', pi)

    # Monte Carlo Simulation on Finding Pi
    p = CalculatePi()
    pi = p.generatePi(1000000)
    print('Monte Carlo Simulation Pi:', pi)