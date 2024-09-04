#include <algorithm>
#include <iostream>
#include <vector>
#include <limits>

using std::vector;
using std::numeric_limits;
using std::min;

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

int get_optimal_size(int n, int current_size ,vector<int> memo) {
    if (n == 1) {
        return current_size;
    }

    int memo_last_index = memo.size() - 1;
    if (memo_last_index >= n) {
        return memo[n];
    }

    int optimal = numeric_limits<int>::max();
    if (n > 3 && (n / 3) == 0) {
        optimal = min(optimal, get_optimal_size(n / memo[n / 3], 0, memo));
    }

    if (n > 2 && (n / 2) == 0) {
        optimal = min(optimal, get_optimal_size(n / 2, memo[n / 2], memo));
    }

    if (n > 1) {
        optimal = min(optimal, get_optimal_size(n - 1, memo[n - 1], memo));
    }

    return optimal;
}


vector<int> optimal_sequence(int n) {
    // Approach: The optimal sequence S(n) is just the S(k) with size 
    //          min(S(n/3), S(n/2), S(n-1)). S(n) is then [S(k), n].

    vector<int> memo = {0, 1}; // length of S(n) is in memo[n], S(1) = 1 
    for (int i = 1; i <= n; i++) {
        int optimal = get_optimal_size(i, 0, memo);
        memo.push_back(optimal);
    }
    int ans = memo[n];
    return ans;
}

int main() {
    int n;
    std::cin >> n;
    vector<int> sequence = optimal_sequence(n);
    std::cout << sequence.size() - 1 << std::endl;
    for (size_t i = 0; i < sequence.size(); ++i) {
        std::cout << sequence[i] << " ";
    }
}
