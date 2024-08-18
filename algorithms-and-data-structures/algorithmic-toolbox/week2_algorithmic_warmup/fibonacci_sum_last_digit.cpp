#include <iostream>
#include <vector>
#include "fib.h"
using namespace std;

vector<int> pisano_sequence_modulo_ten();
int fibonacci_sum_naive(long long n);
int fibonacci_sum_fast(long long n);
void stress_test_fibonacci_sum();


int main() {
    long long n = 0;
    cin >> n;
    cout << fibonacci_sum_naive(n);

    stress_test_fibonacci_sum();
}


int fibonacci_sum_naive(long long n) {
    if (n <= 1)
        return n;

    long long previous = 0;
    long long current  = 1;
    long long sum      = 1;

    for (long long i = 0; i < n - 1; ++i) {
        long long tmp_previous = previous;
        previous = current;
        current = tmp_previous + current;
        sum += current;
    }

    return sum % 10;
}

int fibonacci_sum_fast(long long n) {
    if (n <= 1)
        return n;

    int M = 10;
    vector<int> pisano_ten = pisano_sequence_modulo_ten();
    int multiplier = n / M;
    int remainder = n % M;

    // get the sum of pisano sequence of M = 10
    int sum_pisano_ten = 0;
    for (int i : pisano_ten) {
        sum_pisano_ten += i;
    }

    // calculate the sum of the first digits of Fib(0) -> Fib(n)
    // which is just the (sum of first digits * multiplier) + remaining first digits until n
    int sum = multiplier * sum_pisano_ten;
    for (int i = 0; i < remainder; i++) {
        sum += pisano_ten[i];
    }

    return sum;
}

// returns the pisano sequence of the fibonacci sequence with modulo 10
vector<int> pisano_sequence_modulo_ten() {
    int M = 10;
    long long pisano_length = get_pisano_length(M);

    int previous = 0;
    int current = 1;
    vector<int> sequence;
    for (int i = 0; i < pisano_length; i++) {
        if (i <= 1) {
            sequence.push_back(i);
            continue;
        }

        int temp_previous = previous;
        previous = current;
        current = (temp_previous + previous) % M;
        sequence.push_back(current);
    }

    return sequence;
}

void stress_test_fibonacci_sum() {
	srand(time(NULL));
	unsigned int test_counter = 0;
    while (true) {
		const long long n = (rand() % 30) + 1;
        long long naive_answer = fibonacci_sum_naive(n);
        long long fast_answer = fibonacci_sum_fast(n);

        if (naive_answer != fast_answer) {
            std::cout << "Fibonacci of: " << n << "  answer: " << naive_answer 
                        << "  result: " << fast_answer << "\n";
            break;
        }

		test_counter++;
		if (test_counter % 1000 == 0) {
			std::cout << "TEST " << test_counter << "  PASSED\n";
		} 
    }
}