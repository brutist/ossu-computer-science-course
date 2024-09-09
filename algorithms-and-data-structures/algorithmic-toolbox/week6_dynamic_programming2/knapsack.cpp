#include <iostream>
#include <vector>

using std::max;
using std::vector;

int optimal_weight_greedy(int W, const vector<int> &w) {
    int current_weight = 0;
    for (size_t i = 0; i < w.size(); ++i) {
        if (current_weight + w[i] <= W) {
            current_weight += w[i];
        }
    }
    return current_weight;
}

int optimal_weight__naive_util(int W, int taken, vector<int> w, int i) {
    if (i < 0)
        return taken;

    if (taken + w[i] > W) {
        return optimal_weight__naive_util(W, taken, w, i - 1);
    }

    return max(optimal_weight__naive_util(W, taken + w[i], w, i - 1),
               optimal_weight__naive_util(W, taken, w, i - 1));
}

int optimal_weight_naive(int W, const vector<int> &w) {
    return optimal_weight__naive_util(W, 0, w, w.size() - 1);
}

int optimal_weight(int W, const vector<int> &weights) {
    int num_items = weights.size();
    vector<vector<int>> dp(W + 1, vector<int>(num_items + 1));
    for (int w = 0; w <= W; w++) {
        for (int n = 0; n <= num_items; n++) {
            if (w == 0 || n == 0) {
                dp[w][n] = 0;
                continue;
            }

            dp[w][n] = dp[w][n - 1];
            if (weights[n - 1] <= w) {
                int val = dp[w - weights[n - 1]][n - 1] + weights[n - 1];

                if (dp[w][n] < val) {
                    dp[w][n] = val;
                }
            }
        }
    }

    return dp[W][num_items];
}

vector<int> generate_random_vector(int length, int MIN, int MAX) {
    vector<int> random_vector(length);
    for (int i = 0; i < length; i++) {
        random_vector[i] = MIN + rand() % ((MAX + 1) - MIN);
    }

    return random_vector;
}

void stress_test_optimal_weight() {
    srand(time(NULL));
    unsigned int test_counter = 0;
    int WEIGHT_LIMIT = 7;
    int ITEM_LIMIT = 300;
    int MIN_VALUE = 0;
    int MAX_VALUE = 100000;

    while (true) {
        const int n = (rand() % ITEM_LIMIT) + 1;
        const int capacity = (rand() % WEIGHT_LIMIT) + 1;
        vector<int> weights = generate_random_vector(n, MIN_VALUE, MAX_VALUE);

        int naive_answer = optimal_weight_naive(capacity, weights);
        int fast_answer = optimal_weight(capacity, weights);

        if (naive_answer != fast_answer) {
            std::cout << "KNAPSACK NAIVE answer: " << naive_answer << "\n";
            std::cout << "KNAPSACK FAST answer: " << fast_answer << "\n\n";

            break;
        }

        test_counter++;
        if (test_counter % 10000 == 0) {
            std::cout << "TEST " << test_counter << "  PASSED\n";
        }
    }
}

void time_optimal_weight_max_inputs() {
    srand(time(NULL));
    int W = 10000;
    int n = 300;
    int MIN_VALUE = 0;
    int MAX_VALUE = 100000;
    vector<int> weights = generate_random_vector(n, MIN_VALUE, MAX_VALUE);

    double start_time = (double)clock() / CLOCKS_PER_SEC;
    optimal_weight(W, weights);
    double time_diff = ((double)clock() / CLOCKS_PER_SEC) - start_time;

    std::cout << "The time elapsed for fast optimal_weight with max input: "
              << time_diff << "\n";
}

int main() {
    int n, W;
    std::cin >> W >> n;
    vector<int> w(n);
    for (int i = 0; i < n; i++) {
        std::cin >> w[i];
    }
    std::cout << optimal_weight(W, w) << '\n';

    // stress_test_optimal_weight();
    // time_optimal_weight_max_inputs();
}
