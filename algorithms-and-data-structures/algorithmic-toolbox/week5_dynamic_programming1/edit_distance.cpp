#include <iostream>
#include <limits>
#include <string>
#include <vector>

using std::min;
using std::numeric_limits;
using std::string;
using std::vector;

class EditDistance {
  public:
    const string str1;
    const string str2;

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
};

int main() {
    string str1;
    string str2;
    std::cin >> str1 >> str2;

    EditDistance ed = {str1, str2};
    std::cout << ed.edit_distance() << std::endl;
    return 0;
}
