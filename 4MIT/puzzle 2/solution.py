
# Exercise 1 - Suppose that there is only a range of time that you can attend the party.

def bestTimeToPartySmart(schedule):
    """ Calculates the best time to party (maximum number of celebrities present) given a 
        schedule and your own range of available time.
    
    Args:
    schedule (list of tuples) - Each tuple in the schedule is composed of the start time (i) and 
                                end time (j). The particular celebrity is present at the turn of 
                                ith hour and will have left by the jth hour [i, j).
    ystart (int) - start of your available time to party
    yend (int) - end of your available time to party.

    Return best_time (int) - the best hour to party.
    """
    HOURS_IN_DAY = 24
    party_hours = [0] * HOURS_IN_DAY
    

    # have a separate list of all start and end times, the hrs_entered correspond to celebrities
    #           coming at that hour, while the hrs_left correspond to celebrities leaving at that hour.
    start_hour = None
    end_hour = None
    hrs_entered, hrs_left = [], []

    for start, end in schedule:
        hrs_entered.append(start)
        hrs_left.append(end)

        # identify the party start and end time
        if not start_hour or start < start_hour:
            start_hour = int(start)
        if not end_hour or end > end_hour:
            end_hour = int(end)

 
    # record how many celebrities present in each hour (24 hr format, hr is the index of the list)
    current_celebs = 0
    for h in range(start_hour, end_hour + 1):
        current_celebs += hrs_entered.count(h) - hrs_left.count(h)
        party_hours[h] = current_celebs

    # identify the hr that has the maximum no. of celebs present
    best_hr = party_hours.index(max(party_hours))

    return best_hr


if __name__ == '__main__':

    sched = [(6, 8), (6, 12), (6, 7), (7, 8), (7, 10), (8, 9), (8, 10), (9, 12),
            (9, 10), (10, 11), (10, 12), (11, 12)]
    sched2 = [(6.0, 8.0), (6.5, 12.0), (6.5, 7.0), (7.0, 8.0), (7.5, 10.0), (8.0, 9.0),
          (8.0, 10.0), (9.0, 12.0), (9.5, 10.0), (10.0, 11.0), (10.0, 12.0), (11.0, 12.0)]
    sched3 = [(6, 7), (7,9), (10, 11), (10, 12), (8, 10), (9, 11), (6, 8),
          (9, 10), (11, 12), (11, 13), (11, 14)]


    print(bestTimeToPartySmart(sched))
    print(bestTimeToPartySmart(sched2))
    print(bestTimeToPartySmart (sched3))
        