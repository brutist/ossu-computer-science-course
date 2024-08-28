#include <iostream>

int fibonacci_sum_squares_naive(long long n);
int fibonacci_last_digit(long long n);
int fibonacci_sum_squares_fast(long long n);
void stress_test_fibonacci_sum_squares();

int main()
{
    long long n = 0;
    std::cin >> n;
    std::cout << fibonacci_sum_squares_naive(n);

    stress_test_fibonacci_sum_squares();
}

int fibonacci_sum_squares_naive(long long n)
{
    if(n <= 1)
        return n;

    long long previous = 0;
    long long current = 1;
    long long sum = 1;

    for(long long i = 0; i < n - 1; ++i)
        {
            long long tmp_previous = previous;
            previous = current;
            current = (tmp_previous + current) % 10;
            sum = (sum + (current * current)) % 10;
        }

    return sum;
}

// Approach: Apparently, ∑F(n)^2  = F(n) x F(n + 1)
int fibonacci_sum_squares_fast(long long n)
{
    int fib_n_last = fibonacci_last_digit(n);
    int fib_n1_last = fibonacci_last_digit(n + 1);
    return (fib_n_last * fib_n1_last) % 10;
}

int fibonacci_last_digit(long long n)
{
    if(n <= 1)
        return n;

    // We know that the pisano length of modulo 10 is 60
    int M = 10;
    int pisano_ten_length = 60;

    // to calculate the last digit of F(n) we can look at the its corresponding
    // position in the pisano sequence (M = 10).
    int equivalence = n % pisano_ten_length;
    if(equivalence == 0)
        equivalence = pisano_ten_length;

    int previous = 0;
    int current = 1;
    for(int i = 0; i < equivalence - 1; i++)
        {
            int temp_previous = previous;
            previous = current;
            current = (temp_previous + previous) % M;
        }

    return current;
}

void stress_test_fibonacci_sum_squares()
{
    srand(time(NULL));
    unsigned int test_counter = 0;
    while(true)
        {
            const long long n = (rand() % 100000) + 1;
            long long naive_answer = fibonacci_sum_squares_naive(n);
            long long fast_answer = fibonacci_sum_squares_fast(n);

            if(naive_answer != fast_answer)
                {
                    std::cout << "Fibonacci sum of squares n: " << n
                              << "  answer: " << naive_answer
                              << "  result: " << fast_answer << "\n";
                    break;
                }

            test_counter++;
            if(test_counter % 1000 == 0)
                {
                    std::cout << "TEST " << test_counter << "  PASSED\n";
                }
        }
}
