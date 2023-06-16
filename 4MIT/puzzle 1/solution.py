

# Solution No. 1 - Simple Solution for Exercise 1 and 2
# determine the minimum effort to conform the caps' direction by
# identifying the intervals of similar directions and flipping the caps of 
# the people whose interval count is the smallest

caps = ['F', 'F', 'B', 'B', 'B', 'F', 'B',
        'B', 'B', 'F', 'F', 'B', 'F' ]
cap2 = ['F', 'F', 'B', 'B', 'B', 'F', 'B', 'B', 'B', 'F', 'F', 'F', 'F' ]

cap3 = ['F','F','B','H','B','F','B', 'B','B','F','H','F','F']

def pleaseConform(people):
    """ Prints the position of people that must flip their caps. Satisying the constraint that
        the amount of commands done is the lowest possible.

    Args:
    people (list of string) - 'F' means the cap is forward, 'B' is bacwards.
                                Assumes that the list only contain the mentioned letters.

    Returns: None
    """
    if len(people) == 0:
        print('There are no people in line')
        return None

    # record the caps' direction of people in the line
    orientations = {}
    direction, first = None, None
    backwards, forwards = 0, 0

    for i in range(len(people)):
        if direction == None:
            direction = people[i]
            first = i

        if people[i] != direction:
            orientations[(first, i - 1)] = people[i - 1]

            # record the number of interval
            if orientations[(first, i - 1)] == 'B':
                backwards += 1
            elif orientations[(first, i -g 1)] == 'F':
                forwards += 1
            
            direction = people[i]
            first = i
 
    # identify what direction to conform to
    if backwards < forwards:
        conform_to = 'B'
    else:
        conform_to = 'F'

    # flip the caps to the conform_to direction
    # ex. 'People in positions 2 through 4 flip your caps!'
    for k,v in orientations.items():
        if v != conform_to and not v == 'H':
            if k[0] != k[1]:
                print('People in positions {} through {} flip your caps!'.format(k[0], k[1]))
            else:
                print('Person at position {} flip your cap!'.format(k[0]))

    print()

def pleaseConform_v2(people):
    """ Prints the position of people that must flip their caps. Satisying the constraint that
        the amount of commands done is the lowest possible.

        This is a more efficient version of pleaseConform. It checks whether the next interval
        has the same direction from the previous. If it does not, flip the cap of that interval.

    Args:
    people (list of string) - 'F' means the cap is forward, 'B' is bacwards.
                                Assumes that the list only contain the mentioned letters.

    Returns: None
    """
    



if __name__ == '__main__':
    pleaseConform(cap3)
