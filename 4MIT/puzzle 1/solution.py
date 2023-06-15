

# Solution No. 1 - Simple Solution
# determine the minimum effort to conform the caps' direction by
# identifying the intervals of similar directions and flipping the caps of 
# the people whose interval count is the smallest

caps = ['F', 'F', 'B', 'B', 'B', 'F', 'B',
        'B', 'B', 'F', 'F', 'B', 'F' ]
cap2 = ['F', 'F', 'B', 'B', 'B', 'F', 'B', 'B', 'B', 'F', 'F', 'F', 'F' ]

def naive_conform(people):
    """ Prints the position of people that must flip their caps. Satisying the constraint that
        the amount of commands done is the lowest possible.

    Args:
    people (list of string) - 'F' means the cap is forward, 'B' is bacwards.
                                Assumes that the list only contain the mentioned letters.

    Returns: None
    """
    # record the caps' direction of people in the line
    orientations = {}
    direction = None
    first = None

    for i in range(len(people)):
        if direction == None:
            direction = people[i]
            first = i

        elif people[i] != direction:
            orientations[(first, i - 1)] = people[i - 1]
            direction = people[i]
            first = i

    # get the smallest no. of interval (least no. of commands)
    same = 0
    diff = 0

    for interval in orientations.values():
        if interval == 'B':
            same += 1
        else:
            diff += 1
    
    # identify what direction to conform to
    if same < diff:
        conform_to = 'B'

    else:
        conform_to = 'F'

    # flip the caps to the conform_to direction
    # ex. 'People in positions 2 through 4 flip your caps!'
    for k,v in orientations.items():
        if v != conform_to:
            if k[0] != k[1]:
                print('People in positions {} through {} flip your caps!'.format(k[0], k[1]))
            else:
                print('Person at position {} flip your cap!'.format(k[0]))

naive_conform(caps)