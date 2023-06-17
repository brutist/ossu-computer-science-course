
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
    # have a separate list of all start and end times, the hrs_entered correspond to celebrities
    #           coming at that hour, while the hrs_left correspond to celebrities leaving at that hour.
    hrs_entered, hrs_left = [], []

    for start, end in schedule:
        hrs_entered.append(start)
        hrs_left.append(end)

    # round down by int division
    start_time = min(hrs_entered) // 1
    end_time = max(hrs_left) // 1
    
    # sort list
    hrs_entered.sort()
    hrs_left.sort()

    # map the number of celebrities present (value) in each entrance time (key)
    celebs_attendance = {}
    for i in range(len(hrs_entered)):
        current_time = hrs_entered[i]
        celebs_present = 0

        for h in range(len(hrs_entered)):
            celeb_start_time = hrs_entered[h]
            celeb_end_time = hrs_left[h]

            if current_time >= celeb_start_time:
                celebs_present += 1
            if current_time >= celeb_end_time:
                celebs_present -= 1
    
        celebs_attendance[hrs_entered[i]] = celebs_present

    # identify the hr that has the maximum no. of celebs present
    best_hr = None
    max_present = None
    for hr, present in celebs_attendance.items():
        if not max_present or max_present < present:
            best_hr = hr
            max_present = present

    print('Best time to attend the party is at {} o\'clock : {} celebrities will be attending!'.format(best_hr, max_present))
    return best_hr


if __name__ == '__main__':

    sched = [(6, 8), (6, 12), (6, 7), (7, 8), (7, 10), (8, 9), (8, 10), (9, 12),
            (9, 10), (10, 11), (10, 12), (11, 12)]
    sched2 = [(6.0, 8.0), (6.5, 12.0), (6.5, 7.0), (7.0, 8.0), (7.5, 10.0), (8.0, 9.0),
          (8.0, 10.0), (9.0, 12.0), (9.5, 10.0), (10.0, 11.0), (10.0, 12.0), (11.0, 12.0)]
    sched3 = [(6, 7), (7,9), (10, 11), (10, 12), (8, 10), (9, 11), (6, 8),
          (9, 10), (11, 12), (11, 13), (11, 14)]


    bestTimeToPartySmart(sched)
    bestTimeToPartySmart(sched2)
    bestTimeToPartySmart (sched3)
        