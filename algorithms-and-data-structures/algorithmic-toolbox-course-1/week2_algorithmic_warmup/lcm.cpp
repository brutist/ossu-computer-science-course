#include <iostream>

long long lcm_naive(int a, int b) {
    for (long l = 1; l <= (long long)a * b; ++l)
        if (l % a == 0 && l % b == 0)
            return l;

    return (long long)a * b;
}

// an implementation of Euclid GCD algorithm
int gcd_fast(int a, int b) {
    if (b == 0)
        return a;

    int remainder_a = a % b;
    return gcd_fast(b, remainder_a);
}

// the lcm is just the product divided by their greatest common divisor
long long lcm_fast(int a, int b) {
    return ((long long)a * b) / (long long)gcd_fast(a, b);
}

void stress_test_lcm() {
    srand(time(NULL));
    unsigned test_counter = 0;
    while (true) {
        int a = std::rand() % 10000;
        int b = std::rand() % 10000;

        long long naive_answer = lcm_naive(a, b);
        long long fast_answer = lcm_fast(a, b);

        if (naive_answer != fast_answer) {
            std::cout << "a: " << a << " b: " << b << " naive: " << naive_answer
                      << "  fast: " << fast_answer;
        }

        test_counter++;
        if (test_counter % 100 == 0) {
            std::cout << "TEST " << test_counter << "  PASSED\n";
        }
    }
}

int main() {
    int a, b;
    std::cin >> a >> b;
    std::cout << lcm_fast(a, b) << std::endl;

    stress_test_lcm();
    return 0;
}
