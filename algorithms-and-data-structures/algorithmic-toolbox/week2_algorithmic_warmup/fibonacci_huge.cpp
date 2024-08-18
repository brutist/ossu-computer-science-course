#include <iostream>
#include <stdexcept>
#include <time.h>
#include "fib.h"

long long get_pisano_length(long long m);
long long get_fibonacci_huge_naive(long long n, long long m);
long long get_fibonacci_huge_fast(long long n, long long m);
void test_pisano_length();
void stress_test_get_fibonacci_huge();
void time_get_fibonacci_huge_fast();

int main() {
    long long n, m;
    std::cin >> n >> m;
    std::cout << get_fibonacci_huge_fast(n, m) << '\n';

	//test_pisano_length();
	//stress_test_get_fibonacci_huge();
	//time_get_fibonacci_huge_fast();
}

long long get_fibonacci_huge_naive(long long n, long long m) {
    if (n <= 1)
        return n % m;

    long long previous = 0;
    long long current  = 1;

    for (long long i = 0; i < n - 1; ++i) {
        long long tmp_previous = previous;
        previous = current;
        current = (tmp_previous + current) % m;
    }

    return current;
}

long long get_fibonacci_huge_fast(long long n, long long m) {
    // find the length of the pisano period
	long long pisano_length = get_pisano_length(m);

	// identify the position of the n with respect to the pisano period
	long long k = n % pisano_length;
	if (k == 0) {
		k = pisano_length;
	}

	// special case for this setup
	// increase the length of pisano if == 1, for the suceeding for loop to run
	if (pisano_length == 1) {
		k++; 
	}

	// get the value of the k in the pisano period
	long long previous = 0;
    long long current  = 1;

    for (long long i = 0; i < k - 1; i++) {
        int temp_previous = previous;
        previous = current;
        current = (temp_previous + current) % m;
    }

	return current;
}

long long get_pisano_length(long long m) {
	// special case
	if (m == 1)	return 1;

	// start at Fibonacci 2 [0, 1, 1]
	long long previous = 1;
	long long current = 1;
	long long index = 0;

	// pisano period repeats once the Fi % m = 0 and Fi+1 % m = 1
	while (previous != 0 || current != 1) {
		int next = (previous + current) % m;
		previous = current;
		current = next;
		index++;
	}
	// we're looking for the length, not the index
	return index + 1;
}

void test_pisano_length() {
	int LIMIT = 100;
	for (int i = 1; i < LIMIT; i++) {
		std::cout << "modulo: " << i << "  pisano length: " << get_pisano_length(i) << "\n";
	}
}

void stress_test_get_fibonacci_huge() {
	srand(time(NULL));
	unsigned int test_counter = 0;
    while (true) {
		const long long n = (rand() % 1000000) + 1;
		const long long m = (rand() % n + 1);
        long long naive_answer = get_fibonacci_huge_naive(n, m);
        long long fast_answer = get_fibonacci_huge_fast(n, m);

        if (naive_answer != fast_answer) {
            std::cout << "Fibonacci of: " << n << "  m: " << m << "  answer: " << naive_answer 
                        << "  result: " << fast_answer << "\n";
            break;
        }

		test_counter++;
		if (test_counter % 1000 == 0) {
			std::cout << "TEST " << test_counter << "  PASSED\n";
		} 
    }
}

void time_get_fibonacci_huge_fast() {
	long long max_N = 100000000000000;
	long long max_M = 1000;

	double start_time = (double) clock() / CLOCKS_PER_SEC;

	get_fibonacci_huge_fast(max_M, max_N);

	double time_diff = ((double) clock() / CLOCKS_PER_SEC) - start_time;

	std::cout << "The time elapsed for max input: " << time_diff << "\n";
}