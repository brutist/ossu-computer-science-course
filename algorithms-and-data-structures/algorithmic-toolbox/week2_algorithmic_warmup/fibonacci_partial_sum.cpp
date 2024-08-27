#include <iostream>

long long get_fibonacci_partial_sum_naive (long long from, long long to);
long long get_fibonacci_partial_sum_fast (long long from, long long to);
int fibonacci_sum_fast (long long n);
void stress_test_get_fibonacci_partial_sum_naive ();
void time_get_fibonacci_partial_sum_naive ();

int
main ()
{
  long long from, to;
  std::cin >> from >> to;
  std::cout << get_fibonacci_partial_sum_naive (from, to) << '\n';

  // stress_test_get_fibonacci_partial_sum_naive();
  time_get_fibonacci_partial_sum_naive ();
}

long long
get_fibonacci_partial_sum_naive (long long from, long long to)
{
  long long sum = 0;

  long long current = 0;
  long long next = 1;

  for (long long i = 0; i <= to; ++i)
    {
      if (i >= from)
        {
          sum += current;
        }

      long long new_current = next;
      next = next + current;
      current = new_current;
    }

  return sum % 10;
}

// Approach: S(from, to) = S(to) - S(from)
//           S(from, to) = F(to + 2) - 1 - (F(from + 1) - 1), since S(n) = F(n
//           + 2) S(from, to) = F(to + 2) - F(from + 1)
long long
get_fibonacci_partial_sum_fast (long long from, long long to)
{
  // get sum of S(from) and S(to)
  int sum_from = fibonacci_sum_fast (from);
  int sum_to = fibonacci_sum_fast (to);

  int M = 10;
  return (sum_to - sum_from) % M;
}

int
fibonacci_sum_fast (long long n)
{
  if (n <= 1)
    return n;

  // Summation of F(n) = F(n + 2) - 1
  // We know that the pisano length of modulo 10 is 60
  int M = 10;
  int pisano_ten_length = 60;

  // to calculate the last digit of F(n) we need to know the last digit of
  //  F(n + 2). F(n + 2)'s last digit can be calculated by looking at the
  //  pisano sequence (M = 10).
  int equivalence = n % pisano_ten_length;
  if (equivalence == 0)
    equivalence = pisano_ten_length;

  int previous = 0;
  int current = 1;
  for (int i = 0; i <= equivalence; i++)
    {
      int temp_previous = previous;
      previous = current;
      current = (temp_previous + previous) % M;
    }

  // wrap around if 0
  if (current == 0)
    return 9;
  else
    return current - 1;
}

void
stress_test_get_fibonacci_partial_sum_naive ()
{
  srand (time (NULL));
  unsigned int test_counter = 0;
  while (true)
    {
      const long long from = (rand () % 10000000) + 1;
      const long long to = (rand () % 1000000) + 1;
      long long naive_answer = get_fibonacci_partial_sum_naive (from, to);
      long long fast_answer = get_fibonacci_partial_sum_naive (from, to);

      if (naive_answer != fast_answer)
        {
          std::cout << "Fibonacci from: " << from << " to: " << to
                    << "  answer: " << naive_answer
                    << "  result: " << fast_answer << "\n";
          break;
        }

      test_counter++;
      if (test_counter % 1000 == 0)
        {
          std::cout << "TEST " << test_counter << "  PASSED\n";
        }
    }
}

void
time_get_fibonacci_partial_sum_naive ()
{
  const long long max_N = 100000000000000;

  double naive_start_time = (double)clock () / CLOCKS_PER_SEC;
  get_fibonacci_partial_sum_naive (max_N, max_N);
  double naive_time_diff
      = ((double)clock () / CLOCKS_PER_SEC) - naive_start_time;

  double start_time = (double)clock () / CLOCKS_PER_SEC;
  get_fibonacci_partial_sum_fast (max_N, max_N);
  double time_diff = ((double)clock () / CLOCKS_PER_SEC) - start_time;

  std::cout << "The time elapsed for naive with max input: " << naive_time_diff
            << "\n";
  std::cout << "The time elapsed for fast with max input: " << time_diff
            << "\n";
}