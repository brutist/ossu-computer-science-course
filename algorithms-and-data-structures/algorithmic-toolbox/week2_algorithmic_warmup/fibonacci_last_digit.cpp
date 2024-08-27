#include <iostream>
#include <stdexcept>

int
get_fibonacci_last_digit_naive (int n)
{
  if (n <= 1)
    return n;

  int previous = 0;
  int current = 1;

  for (int i = 0; i < n - 1; ++i)
    {
      int tmp_previous = previous;
      previous = current;
      current = (tmp_previous + current) % 10;
    }

  return current;
}

int
get_fibonacci_last_digit_fast (int n)
{
  if (n <= 1)
    return n;

  // We know that the pisano length of modulo 10 is 60
  int M = 10;
  int pisano_ten_length = 60;

  // to calculate the last digit of F(n) we can look at the its corresponding
  // position in the pisano sequence (M = 10).
  int equivalence = n % pisano_ten_length;
  if (equivalence == 0)
    equivalence = pisano_ten_length;

  int previous = 0;
  int current = 1;
  for (int i = 0; i < equivalence - 1; i++)
    {
      int temp_previous = previous;
      previous = current;
      current = (temp_previous + previous) % M;
    }

  return current;
}

void
stress_test_fibonacci_last_digit ()
{
  srand (time (NULL));
  unsigned int test_counter = 0;

  while (true)
    {
      int n = rand () % 40;

      int naive_answer = get_fibonacci_last_digit_naive (n);
      int fast_answer = get_fibonacci_last_digit_fast (n);

      if (naive_answer != fast_answer)
        {
          std::cout << "Fibonacci of: " << n << "  Answer: " << naive_answer
                    << "  Result: " << fast_answer;
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
test_fast_fibonacci_last_digit (int n)
{
  for (int i = 0; i < n + 1; i++)
    {
      int answer = get_fibonacci_last_digit_fast (i);

      std::cout << "Fibonacci " << i << " : " << answer << "\n";
    }
}

int
main ()
{
  int n;
  std::cin >> n;
  int c = get_fibonacci_last_digit_fast (n);
  std::cout << c << '\n';

  stress_test_fibonacci_last_digit ();
  // test_fast_fibonacci_last_digit(n);
}
