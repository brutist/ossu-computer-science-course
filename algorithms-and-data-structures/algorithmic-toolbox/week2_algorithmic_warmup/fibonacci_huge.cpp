#include <iostream>

long long get_fibonacci_huge_naive(long long n, long long m) {
    if (n <= 1)
        return n;

    long long previous = 0;
    long long current  = 1;

    for (long long i = 0; i < n - 1; ++i) {
        long long tmp_previous = previous;
        previous = current;
        current = tmp_previous + current;
    }

    return current % m;
}

int get_pisano_length(long long m);
long long get_fibonacci_huge_fast(long long n, long long m) {
    // find the length of the pisano period
	int pisano_length = get_pisano_length(m);

	// identify the position of the n mod m with respect to the pisano period
	long long k = n % pisano_length;

	// get the value of the k in the pisano period
	long long previous = 0;
	long long current = 1;
	for (int i = 0; i < k - 1; i++) {
		long long temp_previous = previous;
		previous = current;
		current = (temp_previous + current) % m;
	}

	return current;
}

int get_pisano_length(long long m) {
	// special case
	if (m == 1)	return 0;

	// start at Fibonacci 2 [0, 1, 1]
	int previous = 1;
	int current = 1;
	int index = 0;

	// pisano period repeats once the Fi % m = 0 and Fi+1 % m = 1
	while (previous != 0 && current != 1) {
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
		std::cout << "modulo: " << i << " pisano length: " << get_pisano_length(i) << "\n";
	}
}

int main() {
    long long n, m;
    std::cin >> n >> m;
    std::cout << get_fibonacci_huge_fast(n, m) << '\n';

	test_pisano_length();
}
