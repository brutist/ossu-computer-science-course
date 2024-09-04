#include <iostream>
#include <string>
#include <vector>
#include <limits>

using std::vector;
using std::string;
using std::numeric_limits;

vector<vector<int>> build_dp_array(const string &str1, const string &str2) {
    // use 1-based index for the strings
    int row = str1.length() + 1;
    int col = str2.length() + 1;
    vector<vector<int>> dp_array(row, vector<int>(col, -1));
    // instantiate first row with D(0, j) = j
    for (int j = 0; j <= col; j++) {
        dp_array[0][j] = j;
    }

    // instantiate first column with D(i, 0) = i
    for (int i = 0; i <= row; i++) {
        dp_array[i][0] = i;
    }

    return dp_array;
}

int find_optimal_edit(vector<int> dp_array, int i, int j) {
    
}

int edit_distance(const string &str1, const string &str2) {
    // Approach: The recurrence relation between the edit distance
    //          D(row,col) = min(insertion, deletion, mismatch, match).
    //          To keep track of the D(row,col) for all row and col, we can
    //          use dynamic programming and maintain a 2D vector (V)
    //          of length row and col. Entry in V(row,col) corresponds to 
    //          D(row,col) which we can use to calculate D(row,col) for all
    //          row and col in column-major order.

    vector<vector<int>> dp_array = build_dp_array(str1, str2);
    // fill-in the dp_array in column major order.
    //  D(i,j) = min{D(i,j-1)+1, D(i-1,j)+1, D(i-1,j-1)} 
    //                                       +1 if str[i] != str[j]
    int row = str1.length();
    int col = str.length();
    for (int i = 1; i <= row; i++) {
        int minimum = numeric_limits<int>::max();
        for (int j = 1; j <= col; j++) {
            
        }
    }

    return 0;
}

int main() {
    string str1;
    string str2;
    std::cin >> str1 >> str2;
    std::cout << edit_distance(str1, str2) << std::endl;
    return 0;
}
