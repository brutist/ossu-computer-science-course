#include <algorithm>
#include <iostream>
#include <vector>

using std::max;
using std::vector;

class LCS3 {
  public:
    vector<int> &a;
    vector<int> &b;
    vector<int> &c;

    int lcs3_naive_util(int i, int j, int k) {
        if (i == 0 || j == 0 || k == 0) {
            return 0;
        }

        if (a[i - 1] == b[j - 1] && b[j - 1] == c[k - 1]) {
            return 1 + lcs3_naive_util(i - 1, j - 1, k - 1);
        }

        // there are 3 possible cases left [insert, delete, mismatch]
        // but insert and delete must be applied to all vectors, so
        // we get a total of 7 cases
        return max({lcs3_naive_util(i - 1, j - 1, k - 1), // mismatch
                    lcs3_naive_util(i - 1, j, k), lcs3_naive_util(i, j - 1, k),
                    lcs3_naive_util(i, j, k - 1),
                    lcs3_naive_util(i - 1, j - 1, k),
                    lcs3_naive_util(i, j - 1, k - 1),
                    lcs3_naive_util(i - 1, j, k - 1)});
    }

    // wrapper function for naively finding the longest subsequence
    //  of vectors a, b and c.
    int lcs3_naive() { return lcs3_naive_util(a.size(), b.size(), c.size()); }

    int find_max_subsequence(vector<vector<vector<int>>> &dp_array, int i,
                             int j, int k) {
        if (i == 0 || j == 0 || k == 0) {
            return 0; // instantiate first row and first column with 0
        }

        int ins1 = dp_array[i - 1][j][k];
        int ins2 = dp_array[i][j - 1][k];
        int ins3 = dp_array[i][j][k - 1];
        int del1 = dp_array[i - 1][j - 1][k];
        int del2 = dp_array[i][j - 1][k - 1];
        int del3 = dp_array[i - 1][j][k - 1];
        int match = dp_array[i - 1][j - 1][k - 1];
        if (a[i - 1] == b[j - 1] &&
            b[j - 1] == c[k - 1]) { // row and col are 1-based index
            match++;
        }

        return max({ins1, ins2, ins3, del1, del2, del3, match});
    }

    int lcs3() {
        int max_i = a.size();
        int max_j = b.size();
        int max_k = c.size();
        vector<vector<vector<int>>> dp_array(
            max_i + 1, vector<vector<int>>(max_j + 1, vector<int>(max_k + 1)));
        // fill in dp_array at column-major order starting at D(1,1)
        for (int k = 0; k <= max_k; k++) {
            for (int j = 0; j <= max_j; j++) {
                for (int i = 0; i <= max_i; i++) {
                    int max = find_max_subsequence(dp_array, i, j, k);
                    dp_array[i][j][k] = max;
                }
            }
        }

        return dp_array[max_i][max_j][max_k];
    }

    vector<int> generate_random_vector(int length, int MIN, int MAX) {
        vector<int> random_vector(length);
        for (int i = 0; i < length; i++) {
            random_vector[i] = MIN + rand() % ((MAX + 1) - MIN);
        }

        return random_vector;
    }

    void stress_test_lcs3() {
        srand(time(NULL));
        unsigned int test_counter = 0;
        int N_LIMIT = 5;
        int MIN_VALUE = -1000000000;
        int MAX_VALUE = 1000000000;

        while (true) {
            const int n = (rand() % N_LIMIT) + 1;
            const int m = (rand() % N_LIMIT) + 1;
            const int q = (rand() % N_LIMIT) + 1;
            vector<int> v1 = generate_random_vector(n, MIN_VALUE, MAX_VALUE);
            vector<int> v2 = generate_random_vector(m, MIN_VALUE, MAX_VALUE);
            vector<int> v3 = generate_random_vector(q, MIN_VALUE, MAX_VALUE);

            LCS3 lcs = {v1, v2, v3};
            int naive_answer = lcs.lcs3_naive();
            int fast_answer = lcs.lcs3();

            if (naive_answer != fast_answer) {
                std::cout << "Longest Subsequence 3 NAIVE answer: "
                          << naive_answer << "\n";
                std::cout << "Longest Subsequence 3 FAST answer: "
                          << fast_answer << "\n\n";

                break;
            }

            test_counter++;
            if (test_counter % 10000 == 0) {
                std::cout << "TEST " << test_counter << "  PASSED\n";
            }
        }
    }
};

int main() {
    size_t an;
    std::cin >> an;
    vector<int> a(an);
    for (size_t i = 0; i < an; i++) {
        std::cin >> a[i];
    }
    size_t bn;
    std::cin >> bn;
    vector<int> b(bn);
    for (size_t i = 0; i < bn; i++) {
        std::cin >> b[i];
    }
    size_t cn;
    std::cin >> cn;
    vector<int> c(cn);
    for (size_t i = 0; i < cn; i++) {
        std::cin >> c[i];
    }

    LCS3 lcs = {a, b, c};
    std::cout << lcs.lcs3() << std::endl;

    lcs.stress_test_lcs3();
}
