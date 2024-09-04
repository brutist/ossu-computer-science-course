#include <algorithm>
#include <iostream>
#include <limits>
#include <vector>

using std::min;
using std::numeric_limits;
using std::vector;

vector<int> optimal_sequence_naive(int n) {
    std::vector<int> sequence;
    while (n >= 1) {
        sequence.push_back(n);
        if (n % 3 == 0) {
            n /= 3;
        } else if (n % 2 == 0) {
            n /= 2;
        } else {
            n = n - 1;
        }
    }
    reverse(sequence.begin(), sequence.end());
    return sequence;
}

int get_optimal_size(int n, int current_size, vector<int> &memo) {
    if (n <= 1) {
        return current_size;
    }

    int memo_last_index = memo.size() - 1;
    if (memo_last_index >= n) {
        return memo[n] + current_size;
    }

    int optimal = numeric_limits<int>::max();
    if (n >= 3 && (n % 3) == 0) {
        optimal = min(optimal, get_optimal_size(n / 3, current_size + 1, memo));
    }

    if (n >= 2 && (n % 2) == 0) {
        optimal = min(optimal, get_optimal_size(n / 2, current_size + 1, memo));
    }

    if (n > 1) {
        optimal = min(optimal, get_optimal_size(n - 1, current_size + 1, memo));
    }

    return optimal;
}

vector<int> build_sequence(vector<int> &memo, int n) {
    int index = memo[n];
    vector<int> sequence(index + 1, n);
    while (n > 1) {
        if (n >= 3 && (n % 3) == 0 && memo[n / 3] == index - 1) {
            n = n / 3;
        }

        else if (n >= 2 && (n % 2) == 0 && memo[n / 2] == index - 1) {
            n = n / 2;
        }

        else {
            n = n - 1;
        }

        sequence[index - 1] = n;
        index--;
    }

    return sequence;
}

vector<int> optimal_sequence(int n) {
    // Approach: The optimal sequence length of S(n) is just
    //          min(S(n/3), S(n/2), S(n-1)) + 1.

    vector<int> memo;
    for (int i = 0; i <= n; i++) {
        int optimal = get_optimal_size(i, 0, memo);
        memo.push_back(optimal);
    }

    return build_sequence(memo, n);
}

void stress_test_optimal_sequence() {
    srand(time(NULL));
    unsigned int test_counter = 0;
    int N_LIMIT = 1000;

    while (true) {
        const int n = (rand() % N_LIMIT) + 1;
        vector<int> naive_answer = optimal_sequence_naive(n);
        vector<int> fast_answer = optimal_sequence(n);
        int naive_size = naive_answer.size();
        int fast_size = fast_answer.size();

        // checking if fast answer yields an optimal sequence that is
        //  greater in size than the naive answer (incorrect greedy but works
        //  well with testing)
        if (fast_size > naive_size || fast_answer[fast_size - 1] != n) {
            std::cout << "Primitive Calculator NAIVE size: " << naive_size
                      << "\n";
            std::cout << "Calculator FAST size: " << fast_size << "\n\n";

            break;
        }

        test_counter++;
        if (test_counter % 10000 == 0) {
            std::cout << "TEST " << test_counter << "  PASSED\n";
        }
    }
}

void time_optimal_sequence_max_inputs() {
    srand(time(NULL));
    int M_LIMIT = 100000;

    double start_time = (double)clock() / CLOCKS_PER_SEC;
    optimal_sequence(M_LIMIT);
    double time_diff = ((double)clock() / CLOCKS_PER_SEC) - start_time;

    std::cout << "The time elapsed for fast optimal_sequence with max input: "
              << time_diff << "  seconds \n";
}



int main() {
    int n;
    std::cin >> n;
    vector<int> sequence = optimal_sequence(n);
    std::cout << sequence.size() - 1 << std::endl;
    for (size_t i = 0; i < sequence.size(); ++i) {
        std::cout << sequence[i] << " ";
    }

    std::cout << "\n";
    //stress_test_optimal_sequence();
    time_optimal_sequence_max_inputs();
}
