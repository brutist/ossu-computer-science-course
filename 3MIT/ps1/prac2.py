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
        cowsData[cow[0]] = cow[1]
    
    return cowsData

data = load_cows('ps1_cow_data.txt')
print(data)