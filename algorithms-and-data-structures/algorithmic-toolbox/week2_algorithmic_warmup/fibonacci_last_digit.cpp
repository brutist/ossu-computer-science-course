#include <iostream>
#include <stdexcept>

int get_fibonacci_last_digit_naive(int n) {
    if (n <= 1)
        return n;

    int previous = 0;
    int current  = 1;

    for (int i = 0; i < n - 1; ++i) {
        int tmp_previous = previous;
        previous = current;
        current = tmp_previous + current;
    }

    return current % 10;
}


int get_fibonacci_last_digit_fast(int n) {
    if (n <= 1)     
        return n;

    int previous = 0;
    int current = 1;

    for (int i = 0; i < n - 1; i++) {
        int temp_previous = previous;
        previous = current;
        current = (temp_previous + current) % 10;
    }

    return current;
}


void stress_test_fibonacci_last_digit(int n) {
    if (n > 50) {
        throw std::invalid_argument("stress_test_fibonacci_last_digit cannot take args > 200");
    }
    
    for (int i = 0; i < n + 1; i++) {
        int naive_answer = get_fibonacci_last_digit_naive(i);
        int fast_answer = get_fibonacci_last_digit_fast(i);

        if (naive_answer != fast_answer) {
            std::cout << "Fibonacci of :" << i << "Answer: " << naive_answer 
                        << "  Result: " << fast_answer;
            break;
        }
    }

}

void test_fast_fibonacci_last_digit(int n) {
    for (int i = 0; i < n + 1; i++) {
        int answer = get_fibonacci_last_digit_fast(i);

        std::cout << "Fibonacci " << i << " : " << answer << "\n";
    }
}

int main() {
    int n;
    std::cin >> n;
    int c = get_fibonacci_last_digit_fast(n);
    std::cout << c << '\n';

    //stress_test_fibonacci_last_digit(n);
    //test_fast_fibonacci_last_digit(n);
    }
