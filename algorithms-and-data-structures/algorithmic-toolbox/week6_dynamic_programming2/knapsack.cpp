#include <iostream>
#include <vector>

using std::vector;
using std::max;

int optimal_weight_greedy(int W, const vector<int> &w) {
    int current_weight = 0;
    for (size_t i = 0; i < w.size(); ++i) {
        if (current_weight + w[i] <= W) {
            current_weight += w[i];
        }
    }
    return current_weight;
}

int optimal_weight__naive_util(int W, int taken ,vector<int> w, int i) {
    if (i < 0)  return taken;

    if (taken + w[i] > W) {
        return optimal_weight__naive_util(W, taken, w, i - 1);
    }

    return max(optimal_weight__naive_util(W, taken + w[i], w, i - 1),
               optimal_weight__naive_util(W, taken, w, i - 1));
}

int optimal_weight_naive(int W, const vector<int> &w) {
    return optimal_weight__naive_util(W, 0, w, w.size() - 1);
}

int optimal_weight(int W, const vector<int> &w) {
    int num_items = w.size();
    vector<vector<int>> dp(W + 1, vector<int>(num_items + 1));
    for (int i = 0; i <= W; i++) {
        for (int j = 0; j <= num_items; j++) {
            if (i == 0 || j == 0) {
                dp[i][j] = 0;
            }
            
            else if (w[j - 1] + dp[i - 1][j - 1] <= W) {
                dp[i][j] = dp[i - 1][j - 1] + w[j - 1];
            }

            else {
                dp[i][j] = dp[i - 1][j];
            }
        }
    }

    return dp[W][num_items];
}

int main() {
    int n, W;
    std::cin >> W >> n;
    vector<int> w(n);
    for (int i = 0; i < n; i++) {
        std::cin >> w[i];
    }
    std::cout << optimal_weight(W, w) << '\n';
}
