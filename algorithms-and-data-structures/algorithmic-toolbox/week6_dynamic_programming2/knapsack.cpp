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
    if (taken + w[i] > W || i < 0)     return W;

    return max(optimal_weight__naive_util(W, taken + w[i], w, i - 1), 
               optimal_weight__naive_util(W, taken, w, i - 1));
}

int optimal_weight_naive(int W, const vector<int> &w) {
    return optimal_weight__naive_util(W, 0,w, w.size() - 1);
}

int main() {
    int n, W;
    std::cin >> W >> n;
    vector<int> w(n);
    for (int i = 0; i < n; i++) {
        std::cin >> w[i];
    }
    std::cout << optimal_weight_naive(W, w) << '\n';
}
