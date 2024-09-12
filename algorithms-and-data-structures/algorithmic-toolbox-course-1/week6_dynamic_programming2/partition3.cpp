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

    int partition3() {
        int sum = accumulate(A.begin(), A.end(), 0);
        if (sum % 3 != 0) {
            return false;
        }
        int size = A.size();

        vector<vector<int>> dp(sum + 1, vector<int>(sum + 1, 0));
        dp[0][0] = true;

        // process the numbers one by one
        for (int i = 0; i < size; i++) {
            for (int j = sum; j >= 0; --j) {
                for (int k = sum; k >= 0; --k) {
                    if (dp[j][k]) {
                        dp[j + A[i]][k] = true;
                        dp[j][k + A[i]] = true;
                    }
                }
            }
        }

        return dp[sum / 3][sum / 3];
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
    int N_LIMIT = 20;
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

void time_partition3() {
    int n = 20;
    int MIN_VALUE = 1;
    int MAX_VALUE = 30;
    vector<int> weights = generate_random_vector(n, MIN_VALUE, MAX_VALUE);
    SumPartition sp = {weights};

    double start_time = (double)clock() / CLOCKS_PER_SEC;
    sp.partition3();
    double time_diff = ((double)clock() / CLOCKS_PER_SEC) - start_time;

    std::cout << "The time elapsed for fast partition3 with max input: "
              << time_diff << "\n";
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

    // stress_test_partition3();
    // time_partition3();
}