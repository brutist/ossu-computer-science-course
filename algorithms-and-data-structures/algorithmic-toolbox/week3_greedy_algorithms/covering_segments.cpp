#include <algorithm>
#include <climits>
#include <iostream>
#include <vector>

using namespace std;

struct Segment
{
  int start, end;
};

bool segment_sorter (Segment const &lhs, Segment const &rhs);
vector<int> optimal_points (vector<Segment> &segments);
vector<int> optimal_points_solution (vector<Segment> &segments);
void stress_test_optimal_points ();

int
main ()
{
  int n;
  std::cin >> n;
  vector<Segment> segments (n);
  for (size_t i = 0; i < segments.size (); ++i)
    {
      std::cin >> segments[i].start >> segments[i].end;
    }

  vector<int> points = optimal_points_solution (segments);
  std::cout << points.size () << "\n";
  for (size_t i = 0; i < points.size (); ++i)
    {
      std::cout << points[i] << " ";
    }

  stress_test_optimal_points ();
}

vector<int>
optimal_points (vector<Segment> &segments)
{
  // Approach: Select the minimum right endpoint. Remove all segments
  // containing
  //			that endpoint. Keep choosing minimum right endpoint
  // until done.

  // sort the segments by right endpoints
  sort (segments.begin (), segments.end (), &segment_sorter);

  // instantiate vector that will hold the minimum set of points
  int N = segments.size ();
  vector<int> points;
  points.reserve (N);

  int NO_POINT = -1;
  int min_right_endpoint = NO_POINT;
  for (int i = 0; i < N; i++)
    {
      int s = segments[i].start;
      int e = segments[i].end;

      if (i == 0)
        {
          min_right_endpoint = e;
        }
      // change the min_right_endpoint if current point doesn't contain it
      else if (min_right_endpoint < s)
        {
          points.push_back (min_right_endpoint);
          min_right_endpoint = e;
        }

      // check if there is an endpoint that must be added before the last
      //	segment has been considered (I think there is a much elegant
      // way 	to avoid this last check but call me idiot later).
      if (i == N - 1 && min_right_endpoint != NO_POINT)
        {
          points.push_back (min_right_endpoint);
        }
    }

  return points;
}

// got from the web, for stress testing
vector<int>
optimal_points_solution (vector<Segment> &segments)
{
  sort (segments.begin (), segments.end (), &segment_sorter);
  vector<int> points;
  vector<int> result;
  result.push_back (segments[0].end);
  int p = segments[0].end;

  for (unsigned int i = 1; i < segments.size (); i++)
    {
      if (segments[i].start > p)
        {
          p = segments[i].end;
          result.push_back (p);
        }
    }

  for (unsigned int i = 0; i < result.size (); i++)
    {
      points.push_back (result[i]);
    }
  return points;
}

bool
segment_sorter (Segment const &lhs, Segment const &rhs)
{
  return lhs.end < rhs.end;
}

void
stress_test_optimal_points ()
{
  srand (time (NULL));
  unsigned int test_counter = 0;
  int N_LIMIT = 100;
  int VALUE_LIMIT = 1000000000;

  while (true)
    {
      const int n = (rand () % N_LIMIT) + 1;
      vector<Segment> segments (n);

      // instantiate the segments
      for (int i = 0; i < n; i++)
        {
          int s = (rand () % VALUE_LIMIT) + 1;
          int range = (VALUE_LIMIT - s + 1);
          int e = rand () % range + s;

          segments[i].start = s;
          segments[i].end = e;
        }

      vector<int> naive_answer = optimal_points_solution (segments);
      vector<int> fast_answer = optimal_points (segments);

      if (naive_answer != fast_answer)
        {
          cout << "Segments: ";
          for (Segment w : segments)
            {
              cout << w.start << " " << w.end << "\n";
            }

          std::cout << "Covering Segments of n: " << n
                    << "  answer: " << naive_answer.size ()
                    << "  result: " << fast_answer.size () << "\n";

          break;
        }

      test_counter++;
      if (test_counter % 1000 == 0)
        {
          std::cout << "TEST " << test_counter << "  PASSED\n";
        }
    }
}