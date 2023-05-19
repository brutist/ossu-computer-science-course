###########################
# 6.0002 Problem Set 1a: Space Cows 
# Name: Jonathan Mauring Jr
# Collaborators: None
# Time: start - May 18, 2023

from ps1_partition import get_partitions
import time

#================================
# Part A: Transporting Space Cows
#================================

# Problem 1
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

    # turn the data into a list of cows name with their weight separated with a comma
    pairs = data.split('\n')
    
    # create a dict with name of cows as keys and their weight (int) as values
    cowsData = {}
    for pair in pairs:
        cow = pair.split(',')
        cowsData[cow[0]] = cow[1]
    
    return cowsData

# Problem 2
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
        
        # append the currentTrip to all trips
        if currentTrip != []:
            trips.append(currentTrip)

    return trips

# Problem 3
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

        if tripPossible:
            if len(possibleTrips) < maxTrip:
                result = possibleTrips
                maxTrip = len(possibleTrips)
            
            
    result.sort(key=len)
    return result
        
# Problem 4
def compare_cow_transport_algorithms():
    """
    Using the data from ps1_cow_data.txt and the specified weight limit, run your
    greedy_cow_transport and brute_force_cow_transport functions here. Use the
    default weight limits of 10 for both greedy_cow_transport and
    brute_force_cow_transport.
    
    Print out the number of trips returned by each method, and how long each
    method takes to run in seconds.

    Returns:
    Does not return anything.
    """
    # TODO: Your code here
    pass


if __name__ == '__main__':
    
    pass
