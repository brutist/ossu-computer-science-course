#include <algorithm>
#include <iostream>
#include <vector>

using std::cin;
using std::cout;
using std::max;
using std::min;
using std::sort;
using std::vector;

int compute_min_refills (int dist, int tank, vector<int> &stops);
int compute_min_refills_naive (int dist, int tank, vector<int> &stops);
void stress_test_compute_min_refills ();

int
main ()
{
  int d = 0;
  cin >> d;
  int m = 0;
  cin >> m;
  int n = 0;
  cin >> n;

  vector<int> stops (n);
  for (int i = 0; i < n; ++i)
    cin >> stops.at (i);

  cout << compute_min_refills_naive (d, m, stops) << "\n";

  // stress_test_compute_min_refills();

  return 0;
}

int
compute_min_refills (int dist, int tank, vector<int> &stops)
{
  // Approach: Since the stops is a already sorted, we can think of it as
  // numbers
  //          in a line. We can find the minimum refills needed by greedily
  //          covering the leftmost stops. If there are stops separated by
  //          > tank distance, then it would not be possible to reach
  //          destination.

  // check for unreachable destinations
  int UNREACHABLE = -1;
  unsigned N = stops.size ();
  for (unsigned int i = 1; i < N; i++)
    {
      int prev_stop = stops[i - 1];
      int curr_stop = stops[i];

      bool next_stop_too_far = (curr_stop - prev_stop) > tank;
      bool last_stop_too_far_to_dest
          = (i == N - 1 && ((dist - curr_stop) > tank));
      if (next_stop_too_far || last_stop_too_far_to_dest)
        {
          return UNREACHABLE;
        }
    }

  int last_refill_stop = 0;
  unsigned int stop_counter = 0;
  for (unsigned int i = 0; i < N; i++)
    {
      // move through the stops until you need a refill
      int distance_from_last_refill = stops[i] - last_refill_stop;
      if (distance_from_last_refill > tank)
        {
          last_refill_stop = stops[i - 1];
          stop_counter++;
        }

      // check if you need to stop at the last stop before the destination
      if (i == N - 1)
        {
          int distance_from_last_refill_to_dist = dist - last_refill_stop;
          if (distance_from_last_refill_to_dist > tank)
            {
              stop_counter++; // refill at the last stop
            }
        }
    }

  return stop_counter;
}

// brute-force implementation for stress testing small number of stops
int
compute_min_refills_naive (int dist, int tank, vector<int> &stops)
{
  // check for all distribution of stops, and for each distributions check
  //  whether any stops are not within the tank distance.

  int n = stops.size ();
  int min_refills = n + 1; // Initialize with a large number, as we're looking
                           // for the minimum
  stops.push_back (dist);  // Add the final destination as a "stop"

  // There are 2^n subsets, and we will explore all subsets to find the minimum
  // refills
  for (int i = 0; i < (1 << n); ++i)
    {
      int current_refills = 0;
      int last_stop = 0;
      bool possible = true;

      for (int j = 0; j < n; ++j)
        {
          if (i & (1 << j))
            { // If the j-th stop is included in the subset
              if (stops[j] - last_stop > tank)
                {
                  possible = false;
                  break;
                }
              last_stop = stops[j];
              current_refills++;
            }
        }

      // Check the final leg of the journey from the last stop to the
      // destination
      if (dist - last_stop > tank)
        {
          possible = false;
        }

      // Update the minimum refills if this subset is valid and requires fewer
      // refills
      if (possible)
        {
          min_refills = min (min_refills, current_refills);
        }
    }

  return (min_refills == n + 1) ? -1 : min_refills;
}

void
stress_test_compute_min_refills ()
{
  srand (time (NULL));
  unsigned int test_counter = 0;
  int N_LIMIT = 10;
  int TANK_LIMIT = 400;

  while (true)
    {
      const int n = (rand () % N_LIMIT) + 1;
      const int tank = (rand () % TANK_LIMIT) + 1;
      vector<int> stops;

      // instantiate the weights and values
      for (int i = 0; i < n; i++)
        {
          stops.push_back (((rand () % tank) + 1) * (rand () % 2));
        }
      sort (stops.begin (), stops.end ());

      int naive_answer = compute_min_refills_naive (n, tank, stops);
      int fast_answer = compute_min_refills (n, tank, stops);

      if (naive_answer != fast_answer)
        {
          cout << "stops: ";
          for (int v : stops)
            {
              cout << v << " ";
            }
          cout << "\n";

          std::cout << "Car Fueling of n: " << n
                    << "  answer: " << naive_answer
                    << "  result: " << fast_answer
                    << " tank distance: " << tank << "\n";

          break;
        }

      test_counter++;
      if (test_counter % 1000 == 0)
        {
          std::cout << "TEST " << test_counter << "  PASSED\n";
        }
    }
}