#include <iostream>
#include <limits>
#include <string>
#include <vector>
#include <algorithm>

using std::min;
using std::numeric_limits;
using std::string;
using std::vector;
using std::string;

class EditDistance {
  public:
    const string str1;
    const string str2;
    const string alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    int find_min_edit(vector<vector<int>> &dp_array, int i, int j) {
        // Approach: The recurrence relation between the edit distance
        //          D(row,col) = min(insertion, deletion, mismatch/match).
        //              insertion - D(row, col-1) + 1
        //              deletion  - D(row-1, col) + 1
        //              match     - D(row-1, col-1)    if str1[row] == str2[col]
        //              mismatch  - D(row-1, col-1) +1 if str1[row] != str2[col]
        if (i == 0) {
            return j;    // instantiate first row D(0, j) = j 
        }

        if (j == 0) {
            return i;   // instantiate first column D(i, 0) = i
        }

        int insertion = dp_array[i][j - 1] + 1;
        int deletion = dp_array[i - 1][j] + 1;
        int mismatch = dp_array[i - 1][j - 1];
        if (str1.at(i - 1) != str2.at(j - 1)) { // row and col are 1-based index
            mismatch++;
        }

        return min(insertion, min(deletion, mismatch));
    }

    int edit_distance() {
        //          To keep track of the D(row,col) for all row and col, we can
        //          use dynamic programming and maintain a 2D vector (V)
        //          of length row and col. Entry in V(row,col) corresponds to
        //          D(row,col) which we can use to calculate D(row,col) for all
        //          row and col in column-major order.

        int max_row = str1.length();
        int max_col = str2.length();

        vector<vector<int>> dp_array(max_row + 1, vector<int>(max_col + 1, -1));
        // fill in dp_array at column-major order starting at D(1,1)
        for (int j = 0; j <= max_col; j++) {
            for (int i = 0; i <= max_row; i++) {
                int min = find_min_edit(dp_array, i, j);
                dp_array[i][j] = min;
            }
        }

        return dp_array[max_row][max_col];
    }

    int edit_distance_naive_util(int m, int n) {
        if (m == 0) return n;
        if (n == 0) return m;

        if (str1[m - 1] == str2[n - 1]) {
            return edit_distance_naive_util(m - 1, n - 1);
        }

        return 1 + min({edit_distance_naive_util(m, n - 1),         // insertion
                        edit_distance_naive_util(m - 1, n),         // deletion
                        edit_distance_naive_util(m - 1, n - 1)});   // mismatch
    }

    // wrapper function to calculate the edit distance recursively
    int edit_distance_naive() {
        return edit_distance_naive_util(str1.length(), str2.length());
    }

    string generate_random_string(int length) {
        string random_string;
        random_string.reserve(length);

        for (int i = 0; i < length; i++) {
            random_string += alphabet[rand() % alphabet.length() - 1];
        } 

        return random_string;
    }

    void stress_test_edit_distance() {
        srand(time(NULL));
        unsigned int test_counter = 0;
        int N_LIMIT = 7;

        while (true) {
            const int n = (rand() % N_LIMIT) + 1;
            const int m = (rand() % N_LIMIT) + 1;
            string str1 = generate_random_string(n);
            string str2 = generate_random_string(m);

            EditDistance ed = {str1, str2};
            int naive_answer = ed.edit_distance_naive();
            int fast_answer = ed.edit_distance();

            if (naive_answer != fast_answer) {
                std::cout << "Edit Distance NAIVE answer: " << naive_answer
                        << "\n";
                std::cout << "Edit Distance FAST answer: " << fast_answer << "\n\n";

                break;
            }

            test_counter++;
            if (test_counter % 10000 == 0) {
                std::cout << "TEST " << test_counter << "  PASSED\n";
            }
        }
    }

    void time_edit_distance_max_inputs() {
        srand(time(NULL));
        int M_LIMIT = 100;

        string str1 = generate_random_string(M_LIMIT);
        string str2 = generate_random_string(M_LIMIT);
        EditDistance ed = {str1, str2};
        double start_time = (double)clock() / CLOCKS_PER_SEC;
        ed.edit_distance();
        double time_diff = ((double)clock() / CLOCKS_PER_SEC) - start_time;

        std::cout << "The time elapsed for fast edit_distance with max input: "
                << time_diff << "\n";
    }
};

int main() {
    string str1;
    string str2;
    std::cin >> str1 >> str2;

    EditDistance ed = {str1, str2};
    std::cout << ed.edit_distance_naive() << std::endl;

    //ed.stress_test_edit_distance();
    ed.time_edit_distance_max_inputs();
    return 0;
}
