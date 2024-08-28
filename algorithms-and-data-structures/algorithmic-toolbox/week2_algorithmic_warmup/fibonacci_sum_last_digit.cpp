#include <iostream>

using namespace std;

int sum_pisano_modulo_ten_of_first(long long n);
int fibonacci_sum_naive(long long n);
int fibonacci_sum_fast(long long n);
void stress_test_fibonacci_sum();
long long get_pisano_length(long long m);
void test_pisano_length();
void time_fibonacci_sum_fast();

int main() {
    long long n = 0;
    cin >> n;
    cout << fibonacci_sum_fast(n) << "\n";

    // stress_test_fibonacci_sum();
    // time_fibonacci_sum_fast();
}

int fibonacci_sum_naive(long long n) {
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
        sum = (sum + current) % 10;
    }

    return sum;
}

int fibonacci_sum_fast(long long n) {
    if(n <= 1)
        return n;

    // Summation of F(n) = F(n + 2) - 1
    // We know that the pisano length of modulo 10 is 60
    int M = 10;
    int pisano_ten_length = 60;

    // to calculate the last digit of F(n) we need to know the last digit of
    //  F(n + 2). F(n + 2)'s last digit can be calculated by looking at the
    //  pisano sequence (M = 10).
    int equivalence = n % pisano_ten_length;
    if(equivalence == 0)
        equivalence = pisano_ten_length;

    int previous = 0;
    int current = 1;
    for(int i = 0; i <= equivalence; i++)
    {
        int temp_previous = previous;
        previous = current;
        current = (temp_previous + previous) % M;
    }

    // wrap around if 0
    if(current == 0)
        return 9;
    else
        return current - 1;
}

void stress_test_fibonacci_sum() {
    srand(time(NULL));
    unsigned int test_counter = 0;
    while(true)
    {
        const long long n = (rand() % 10000000) + 1;
        long long naive_answer = fibonacci_sum_naive(n);
        long long fast_answer = fibonacci_sum_fast(n);

        if(naive_answer != fast_answer)
        {
            std::cout << "Fibonacci of: " << n << "  answer: " << naive_answer
                      << "  result: " << fast_answer << "\n";
            break;
        }

        test_counter++;
        if(test_counter % 1000 == 0)
        { std::cout << "TEST " << test_counter << "  PASSED\n"; }
    }
}

void time_fibonacci_sum_fast() {
    long long max_N = 100000000000000;

    double start_time = (double)clock() / CLOCKS_PER_SEC;

    fibonacci_sum_fast(max_N);

    double time_diff = ((double)clock() / CLOCKS_PER_SEC) - start_time;

    std::cout << "The time elapsed for max input: " << time_diff << "\n";
}