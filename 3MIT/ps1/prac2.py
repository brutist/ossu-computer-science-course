from ps1_partition import get_partitions

def load_cows(filename):
    """
    Read the contents of the given file.  Assumes the file contents contain
    data in the form of comma-separated cow name, weight pairs, and return a
    dictionary containing cow names as keys and corresponding weights as values.

    Parameters:
    filename - the name of the data file as a string

    Returns:
    a dictionary of cow name (string), weight (int) pairs
    """
    # open file and save the contents to data
    filhand = open(filename)
    data = filhand.read()
    filhand.close()

    # turn the data into a list
    pairs = data.split('\n')
    
    cowsData = {}
    for pair in pairs:
        cow = pair.split(',')
        cowsData[cow[0]] = int(cow[1])
    
    return cowsData

def greedy_cow_transport(cows,limit=10):
    """
    Uses a greedy heuristic to determine an allocation of cows that attempts to
    minimize the number of spaceship trips needed to transport all the cows. The
    returned allocation of cows may or may not be optimal.
    The greedy heuristic should follow the following method:

    1. As long as the current trip can fit another cow, add the largest cow that will fit
        to the trip
    2. Once the trip is full, begin a new trip to transport the remaining cows

    Does not mutate the given dictionary of cows.

    Parameters:
    cows - a dictionary of name (string), weight (int) pairs
    limit - weight limit of the spaceship (an int)
    
    Returns:
    A list of lists, with each inner list containing the names of cows
    transported on a particular trip and the overall list containing all the
    trips
    """
    # copy cows dict and initialize empty list of trips and cows that have already been taken
    cows = cows.copy()
    trips = []
    takenCows = []
    
    # sort the cows; heaviest first
    cowsName = sorted(cows.keys(), key=cows.get, reverse=True)
    
    # iterate through all of the cows
    for i in range(len(cowsName)):
        currentWeight = 0
        currentTrip =[]

        # if the cow fits into the spaceship, take it
        if (not cowsName[i] in takenCows) and cows[cowsName[i]] <= limit:
            takenCows.append(cowsName[i])
            currentTrip.append(cowsName[i])
            currentWeight += cows[cowsName[i]]

            # iterate through the remaining cows to see if other fits with the other
            for j in range(i+1, len(cowsName)):
                if (not cowsName[j] in takenCows) and currentWeight + cows[cowsName[j]] <= limit:
                    takenCows.append(cowsName[j])
                    currentTrip.append(cowsName[j])
                    currentWeight += cows[cowsName[j]]
        
        # if nothing fits skip over
        if currentTrip != []:
            trips.append(currentTrip)

    return trips


def brute_force_cow_transport(cows,limit=10):
    """
    Finds the allocation of cows that minimizes the number of spaceship trips
    via brute force.  The brute force algorithm should follow the following method:

    1. Enumerate all possible ways that the cows can be divided into separate trips 
        Use the given get_partitions function in ps1_partition.py to help you!
    2. Select the allocation that minimizes the number of trips without making any trip
        that does not obey the weight limitation
            
    Does not mutate the given dictionary of cows.

    Parameters:
    cows - a dictionary of name (string), weight (int) pairs
    limit - weight limit of the spaceship (an int)
    
    Returns:
    A list of lists, with each inner list containing the names of cows
    transported on a particular trip and the overall list containing all the
    trips
    """
    cowsCopy = cows.copy()
    maxTrip = len(cows)
    result = []
    # this provides all of the possible trip combinations
    for possibleTrips in get_partitions(cowsCopy):
        trip = 0
        tripPossible = True
        # iterate through all possible combinations of trips
        while tripPossible and trip < len(possibleTrips):
            tripWeight = 0

            # iterate through the trips and check if all trips conform to the weight limit
            for cow in possibleTrips[trip]:
                tripWeight += cowsCopy[cow]
                if tripWeight > limit:
                    tripPossible = False
                    break
            
            trip += 1
            
        # pick the smallest number of trip (optimal solution)
        if tripPossible:
            if len(possibleTrips) < maxTrip:
                result = possibleTrips
                maxTrip = len(possibleTrips)
            
            
    result.sort(key=len)
    return result
    

if __name__ == '__main__':

    cows = load_cows('ps1_cow_data.txt')
    print('Testing with data v.1...')
    greedy = greedy_cow_transport(cows,limit=10)
    bruteForce = brute_force_cow_transport(cows, limit=10)
    print(greedy, 'Number of trips = ', len(greedy))
    print(bruteForce, 'Number of trips = ', len(bruteForce))

    cows = load_cows('ps1_cow_data_2.txt')
    print('Testing with data v.2...')
    greedy = greedy_cow_transport(cows,limit=10)
    bruteForce = brute_force_cow_transport(cows, limit=10)
    print(greedy, 'Number of trips = ', len(greedy))
    print(bruteForce, 'Number of trips = ', len(bruteForce))
    
    cows = load_cows('test_data.txt')
    print('Testing with test data...')
    greedy = greedy_cow_transport(cows,limit=10)
    bruteForce = brute_force_cow_transport(cows, limit=10)
    print(greedy, 'Number of trips = ', len(greedy))
    print(bruteForce, 'Number of trips = ', len(bruteForce))