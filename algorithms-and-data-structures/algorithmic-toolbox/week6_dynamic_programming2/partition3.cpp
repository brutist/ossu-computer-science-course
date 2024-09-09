#include <iostream>
#include <numeric>
#include <vector>

using std::accumulate;
using std::cout;
using std::string;
using std::vector;
using VI = vector<int>;
void print_array(vector<int> v, string name);

class SumPartition {
  public:
    vector<int> &A;

    int optimal_weight(int W, vector<int> &included) {
        // returns the optimal weight that can be fitted
        //  into the knapsack with capacity W. This function
        //  also mutates the included vector to update the
        //  index of the items that has been included to the
        //  optimal weight.
        int num_items = A.size();
        vector<vector<int>> dp(W + 1, vector<int>(num_items + 1));
        for (int w = 0; w <= W; w++) {
            for (int i = 0; i <= num_items; i++) {
                if (w == 0 || i == 0) {
                    dp[w][i] = 0;
                }

                dp[w][i] = dp[w][i - 1];
                if (A[i - 1] <= w && included[i - 1] == 0) {
                    int val = dp[w - A[i - 1]][i - 1] + A[i - 1];

                    if (dp[w][i] < val) {
                        dp[w][i] = val;
                    }
                }
            }
        }
        // backtrack to identify the included items and update
        //  included vector
        for (int n = num_items, w = W; n > 0; n--) {
            if (dp[w][n] > dp[w][n - 1]) {
                w = dp[w][n] - A[n - 1];
                included[n - 1] = 1;
            }
        }

        return dp[W][num_items];
    }

    int partition3() {
        // Approach: The set of int can only be partitioned to three
        //          equal parts if the sum of the set is divisible
        //          by three. If it is possible to partition the set
        //          into three equal parts, we can identify if it
        //          is possible to fill in three 0/1 knapsack of
        //          weight = sum / 3.
        int sum = accumulate(A.begin(), A.end(), 0);
        if (sum % 3 != 0) {
            return 0;
        }

        // keep track of the included integers in the three sets
        int partition = sum / 3;
        vector<int> included(A.size() + 1, 0); // 1-based index
        // if the weights of all the sets is equal to the sum / 3
        // then there is a way to partition such that the partitions
        // have equal sums
        int setA = optimal_weight(partition, included);
        int setB = optimal_weight(partition, included);
        int setC = optimal_weight(partition, included);

        if (setA == setB && setB == setC && setC == partition) {
            return 1;
        }

        return 0;
    }

    bool canPartition() {
        int sum = accumulate(A.begin(), A.end(), 0);
        if (sum % 3 != 0)
            return false;

        return go(sum / 3);
    }

    // solution from internet for stress testing
    // (A)rray to partition and (T)arget value of each (P)art
    bool go(int T, VI P = VI(3), int i = 0) {
        auto N = A.size(), M = P.size();
        if (i == N)
            return P[0] == T && P[1] == T && P[2] == T;

        for (auto j{0}; j < M; ++j) {
            P[j] += A[i];
            if (P[j] <= T && go(T, P, i + 1))
                return true;
            P[j] -= A[i];
        }

        return false;
    }
};

vector<int> generate_random_vector(int length, int MIN, int MAX) {
    vector<int> random_vector(length);
    for (int i = 0; i < length; i++) {
        random_vector[i] = MIN + rand() % ((MAX + 1) - MIN);
    }

    return random_vector;
}

void print_array(vector<int> v, string name) {
    cout << "printing " << name << ": ";
    for (int i : v) {
        cout << i << " ";
    }
    cout << "\n";
}

void stress_test_partition3(bool verbose = true) {
    srand(time(NULL));
    unsigned int test_counter = 0;
    int N_LIMIT = 10;
    int MIN_VALUE = 1;
    int MAX_VALUE = 30;
    int FAILED_TEST = 0;
    int THRESHOLD = 20;

    while (true) {
        const int n = (rand() % N_LIMIT) + 1;
        vector<int> weights = generate_random_vector(n, MIN_VALUE, MAX_VALUE);
        SumPartition sp = {weights};

        int naive_answer = sp.partition3();
        int fast_answer = sp.canPartition();

        if (naive_answer != fast_answer) {
            cout << "answer: " << naive_answer << " input: ";
            print_array(weights, "weights");

            if (verbose) {
                std::cout << "PARTITION SOLUTION answer: " << naive_answer
                          << "\n";
                std::cout << "PARTITION INTERNET answer: " << fast_answer
                          << "\n\n";
            }

            FAILED_TEST++;
            if (FAILED_TEST == THRESHOLD) {
                break;
            }
        }

        test_counter++;
        if (test_counter % 10000 == 0 && verbose) {
            std::cout << "TEST " << test_counter << "  PASSED\n";
        }
    }
}

int main() {
    int n;
    std::cin >> n;
    vector<int> A(n);
    for (size_t i = 0; i < A.size(); ++i) {
        std::cin >> A[i];
    }

    SumPartition sp = {A};
    std::cout << sp.partition3() << '\n';

    stress_test_partition3();
}
