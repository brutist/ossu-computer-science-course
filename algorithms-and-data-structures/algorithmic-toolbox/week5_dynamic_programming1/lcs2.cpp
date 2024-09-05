#include <iostream>
#include <vector>
#include <algorithm>

using std::vector;
using std::max;

// a class for finding the longest common subsequence
class LCS {
  public:
    vector<int> &a;
    vector<int> &b;

    int lcs_naive_util(int n, int m) {
        if (n == 0 || m == 0)   return 0;

        if (a[n - 1] == b[m - 1]) {
            return 1 + lcs_naive_util(n - 1, m - 1);
        }

        return max({lcs_naive_util(n - 1, m),           // remove a[last]
                    lcs_naive_util(n, m - 1),           // remove ab[last]
                    lcs_naive_util(n - 1, m - 1)});     // remove a[last] and b[last]
    }

    // wrapper for naive implementation of finding the longest common subsequence
    int lcs2_naive() {
        return lcs_naive_util(a.size(), b.size());
    }

    int find_max_subsequence(vector<vector<int>> &dp_array, int i, int j) {
        // Approach: The recurrence relation between the max subsequence M(a,b) of two
        //          vectors Va and Vb is M(a,b) = max(M(a-1, b), M(a, b-1), M(a-1, b-1))
        if (i == 0 || j == 0) {
            return 0;    // instantiate first row and first column with 0
        }

        int insertion = dp_array[i][j - 1];
        int deletion = dp_array[i - 1][j];
        int match = dp_array[i - 1][j - 1];
        if (a[i - 1] == b[j - 1]) {             // row and col are 1-based index
            match++;
        }

        return max(insertion, max(deletion, match));
    }

    int lcs2() {
        int max_row = a.size();
        int max_col = b.size();
        vector<vector<int>> dp_array(max_row + 1, vector<int>(max_col + 1, -1));
        // fill in dp_array at column-major order starting at D(1,1)
        for (int j = 0; j <= max_col; j++) {
            for (int i = 0; i <= max_row; i++) {
                int max = find_max_subsequence(dp_array, i, j);
                dp_array[i][j] = max;
            }
        }

        return dp_array[max_row][max_col];
    }

    vector<int> generate_random_vector(int length, int MIN, int MAX) {
        vector<int> random_vector(length);
        for (int i = 0; i < length; i++) {
            random_vector[i] = MIN + rand() % ((MAX + 1) - MIN);
        } 

        return random_vector;
    }

    void stress_test_lcs2() {
        srand(time(NULL));
        unsigned int test_counter = 0;
        int N_LIMIT = 7;
        int MIN_VALUE = -1000000000;
        int MAX_VALUE = 1000000000;

        while (true) {
            const int n = (rand() % N_LIMIT) + 1;
            const int m = (rand() % N_LIMIT) + 1;
            vector<int> v1 = generate_random_vector(n, MIN_VALUE, MAX_VALUE);
            vector<int> v2 = generate_random_vector(m, MIN_VALUE, MAX_VALUE);

            LCS lcs = {v1, v2};
            int naive_answer = lcs.lcs2_naive();
            int fast_answer = lcs.lcs2();

            if (naive_answer != fast_answer) {
                std::cout << "Longest Subsequence NAIVE answer: " << naive_answer
                        << "\n";
                std::cout << "Longest Subsequence FAST answer: " << fast_answer << "\n\n";

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
    size_t n;
    std::cin >> n;
    vector<int> a(n);
    for (size_t i = 0; i < n; i++) {
        std::cin >> a[i];
    }

    size_t m;
    std::cin >> m;
    vector<int> b(m);
    for (size_t i = 0; i < m; i++) {
        std::cin >> b[i];
    }

    LCS lcs = {a, b};
    std::cout << lcs.lcs2() << std::endl;

    lcs.stress_test_lcs2();
}
