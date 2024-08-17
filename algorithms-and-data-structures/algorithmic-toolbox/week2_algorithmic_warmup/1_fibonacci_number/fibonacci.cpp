#include <iostream>
#include <cassert>

// The following code calls a naive algorithm for computing a Fibonacci number.
//
// What to do:
// 1. Compile the following code and run it on an input "40" to check that it is slow.
//    You may also want to submit it to the grader to ensure that it gets the "time limit exceeded" message.
// 2. Implement the fibonacci_fast procedure.
// 3. Remove the line that prints the result of the naive algorithm, comment the lines reading the input,
//    uncomment the line with a call to test_solution, compile the program, and run it.
//    This will ensure that your efficient algorithm returns the same as the naive one for small values of n.
// 4. If test_solution() reveals a bug in your implementation, debug it, fix it, and repeat step 3.
// 5. Remove the call to test_solution, uncomment the line with a call to fibonacci_fast (and the lines reading the input),
//    and submit it to the grader.

long long fibonacci_naive(int n) {
    long long k = n;
    if (k <= 1)
        return k;

    return fibonacci_naive(k - 1) + fibonacci_naive(k - 2);
}

long long fibonacci_fast(int n) {
    if (n <= 0) return 0;
    if (n == 1) return 1;

    // calculate an array of answers
    long long fib_answers[n + 1];
    fib_answers[0] = 0;     // instantiate the first 2 values to start
    fib_answers[1] = 1;
    for (int i = 2; i < n + 1; i++) {
        fib_answers[i] = fib_answers[i - 1] + fib_answers[i - 2];
    }

    return fib_answers[n];
}

void test_solution(int n) {
    for (int i = 0; i < n + 1; i++) {
        long long fast_answer = fibonacci_fast(i);
        long long naive_answer = fibonacci_naive(i);

        if (fast_answer != naive_answer) {
            std::cout << "Answer: " <<  naive_answer << " Result: " << fast_answer << "\n";
            break;
        }

        std::cout << "Fibonacci " <<  i << " = " << fast_answer << "\n";
    }
}

int main() {
    int n = 0;
    std::cin >> n;
    std::cout << fibonacci_fast(n);

    //test_solution(n);

    return 0;
}
