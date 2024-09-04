#include <algorithm>
#include <iostream>
#include <limits>
#include <vector>

using namespace std;

int get_change_naive_util(int m, int num_coins, int denoms[]) {
    if (m == 0) {
        return num_coins;
    }

    int min_coins = numeric_limits<int>::max();
    for (int i = 0; i < 3; i++) {
        if (m >= denoms[i]) {
            min_coins =
                min(min_coins, get_change_naive_util(m - denoms[i],
                                                     num_coins + 1, denoms));
        }
    }

    return min_coins;
}

int get_change_naive(int m) {
    int denoms[] = {1, 3, 4};
    return get_change_naive_util(m, 0, denoms);
}

int get_change_util(int m, int coins, int memo[], vector<int> &denoms) {
    if (memo[m] != 0) {
        return memo[m] + coins;
    }

    if (m == 0) {
        return coins;
    }

    int n = denoms.size();
    int min_coins = numeric_limits<int>::max();
    for (int i = 0; i < n; i++) {
        if (m >= denoms[i]) {
            min_coins = min(min_coins, get_change_util(m - denoms[i], coins + 1,
                                                       memo, denoms));
        }
    }

    return min_coins;
}

// memoisation implementation
int get_change(int m) {
    int memo[m + 1] = {0};

    int change = 1;
    vector<int> denoms = {1, 3, 4};
    while (change <= m) {
        int min_coins = get_change_util(change, 0, memo, denoms);
        memo[change] = min_coins;
        change++;
    }

    return memo[m];
}

void stress_test_get_change() {
    srand(time(NULL));
    unsigned int test_counter = 0;
    int M_LIMIT = 25;

    while (true) {
        const int m = (rand() % M_LIMIT) + 1;
        double naive_answer = get_change_naive(m);
        double fast_answer = get_change(m);

        if (naive_answer != fast_answer) {
            cout << "Change DP NAIVE answer: " << naive_answer << "\n";
            cout << "Change DP FAST answer: " << fast_answer << "\n\n";

            break;
        }

        test_counter++;
        if (test_counter % 10000 == 0) {
            cout << "TEST " << test_counter << "  PASSED\n";
        }
    }
}

void time_get_change_max_inputs() {
    srand(time(NULL));
    int M_LIMIT = 1000;

    double start_time = (double)clock() / CLOCKS_PER_SEC;
    get_change(M_LIMIT);
    double time_diff = ((double)clock() / CLOCKS_PER_SEC) - start_time;

    std::cout << "The time elapsed for fast get_change with max input: "
              << time_diff << "\n";
}

int main() {
    int m;
    std::cin >> m;
    std::cout << get_change(m) << '\n';

    // stress_test_get_change();
    //  time_get_change_max_inputs();
}
