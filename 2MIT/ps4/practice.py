class Coordinate(object):
    def __init__(self, x, y):
        self.x = x
        self.y = y
    
    def __str__(self):
        return "<" + str(self.x) + "," + str(self.y) + ">"
        

    def distance(self, other):
        x_diff_sqr = (self.x - other.x) ** 2
        y_diff_sqr = (self.y - other.y) ** 2
        distance = (x_diff_sqr + y_diff_sqr) ** 0.5

        return distance


# Create Fraction class
class Fraction(object):
    def __init__(self, num, denom):
        assert type(num) == int and type(denom) == int
        self.num = num
        self.denom = denom

# add fraction
    def __add__(self, other):
        lcm = self.denom * other.denom
        num = self.num * (other.denom) + (other.num * (self.denom))
        return Fraction(num, lcm)

# subtract fraction
    def subtract(self, other):
        lcm = self.denom * other.denom
        num = self.num * (other.denom) - (other.num * (self.denom))
        return Fraction(num, lcm)

# print fraction representation
    def __str__(self):
        return str(self.num) + "/" + str(self.denom)

# multiply fraction
    def mult(self, other):
        num = self.num * other.num
        denom = self.denom * other.denom
        return Fraction(num, denom)

# turn fraction into float
    def __float__(self):
        return self.num / self.denom

# inverse a fraction
    def inverse(self):
        return Fraction(self.denom, self.num)


c = Coordinate(3,4)
origin = Coordinate(0,0)

distance = c.distance(origin)
print(distance)
print(c)

f1 = Fraction(2,3)
f2 = Fraction(3,4)

print('f1 =', f1, 'f2 =', f2)
print('Add = ', f1 + f2)
print('Subtract = ', f1.subtract(f2))
print('Multiply', f1.mult(f2))
print('Float', float(f1))
print('Inverse', f1.inverse())
