import partySmart
import random

# Exercise 1 - Suppose that there is only a range of time that you can attend the party.

def bestTimeToPartySmart_mine(schedule, ystart = 0, yend = 23):
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
            
            # small optimization
            if current_time < celeb_start_time and current_time > celeb_end_time:
                break

            if current_time >= celeb_start_time:
                celebs_present += 1
            if current_time >= celeb_end_time:
                celebs_present -= 1
    
        celebs_attendance[hrs_entered[i]] = celebs_present

    # identify the hr that has the maximum no. of celebs present
    best_hr = None
    max_present = None
    for hr, present in celebs_attendance.items():
        if (not max_present or max_present < present) and hr >= ystart and hr < yend:
            best_hr = hr
            max_present = present

    print('Best time to attend the party is at {} o\'clock : {} celebrities will be attending!'.format(best_hr, max_present))
    return best_hr

def test_best_time(length, trials):
   
    for t in range(trials):
        # create a sched for each trial
        sched = []
        for l in range(1, length + 2):
            start = random.choice(range(0, 15))
            end = random.choice(range(10, 23))
            while end <= start:
                end = random.choice(range(8, 17))

            sched.append((start, end))

        if partySmart.bestTimeToPartySmart(sched) != bestTimeToPartySmart_mine(sched):
            print(sched)
            raise AssertionError('TEST FAILED')
        
        print('Test No. {} SUCESS...'.format(t + 1))

def tests_more_lengths(lengths, trials):
    for l in lengths:
        test_best_time(l, trials)

def bestTimeToPartySmart_weights(sched, ystart=0, yend=23):
    schedule = sorted(sched)
    # consolidate 'similar' celebrities into one
    celeb_weights = {}
    entry_times = []
    for start, end, w in schedule:
        entry_times.append(start)
        try:
            celeb_weights[(start, end)] += w
        except:
            celeb_weights[(start, end)] = w

    # shit time complexity but works for now
    # instantiate celeb_present
    celeb_present = {}
    for time in entry_times:
        celeb_present[time] = 0

        for (celeb_start, celeb_end), weight in celeb_weights.items():
            # account for celebrities present during the party
            if time >= celeb_start and time < celeb_end:
                celeb_present[time] += weight
    
    best_hr = None
    max_weight = None
    for hr, weight in celeb_present.items():
        if (not max_weight) or weight > max_weight:
            best_hr = hr
            max_weight = weight
 
    print('Best time to attend the party is at {} o\'clock : {} is the likeability of the celebrities attending!'.format(best_hr, max_weight))
    return best_hr


def test_best_times(num_trials):
    for t in range(num_trials):
        
        # create a random schedule
        sched = []
        sched_weights = []
        for i in range(1000):
            start = random.choice(range(0, 15))
            end = random.choice(range(10, 23))
            while end <= start:
                end = random.choice(range(8, 17))

            sched.append((start, end))
            sched_weights.append((start, end, 1))

        # test on two methods   
        if bestTimeToPartySmart_mine(sched) != bestTimeToPartySmart_weights(sched_weights):
            raise AssertionError('TEST FAILED')
        
        print('SUCCESSFUL TEST NO. {}'.format(t + 1))


if __name__ == '__main__':

    #LENGTHS = range(20)
    #TRIALS = 50
    #tests_more_lengths(LENGTHS, TRIALS)

    sched = [(6.0, 8.0, 2), (6.5, 12.0, 1), (6.5, 7.0, 2), (7.0, 8.0, 2), (7.5, 10.0, 3), (8.10, 9.0, 2),
           (8.0, 10.0, 1), (9.0, 12.0, 2), (9.5, 10.0, 4), (10.0, 11.0, 2), (10.0, 12.0, 3), (11.0, 12.0, 7)]
    bestTimeToPartySmart_weights(sched)

    
    num_trials = 60
    test_best_times(num_trials)