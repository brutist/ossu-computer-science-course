#include <algorithm>
#include <cmath>
#include <iomanip>
#include <iostream>
#include <sstream>
#include <string>
#include <vector>
#include <limits>
#include <tuple>

using namespace std;

double dist_squared(int x1, int y1, int x2, int y2) {
    return ((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2));
}

double minimal_distance_naive(vector<int> &x, vector<int> &y) {
    double min_distance_squared = numeric_limits<double>::max();
    int n = min(x.size(), y.size());
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            double current_dist_squared = dist_squared(x[i], y[i], x[j], y[j]);
            if(current_dist_squared < min_distance_squared) {
                min_distance_squared = current_dist_squared;
            }
        }
    }

    return sqrt(min_distance_squared);
}

vector<int> sort_index(const vector<int> &keys) {
    int n = keys.size();
    vector<int> idx(n, 0);
    for (int i = 0; i < n; i++) {
        idx[i] = i;
    }

    sort(idx.begin(), idx.end(), 
        [&](const int& a, const int& b) {   // apparently, this is lambda in c++
            return (keys[a] < keys[b])
        }
    );

    return idx;
}

double minimal_distance(vector<int> &x, vector<int> &y) {
    // Maintain an index of the original vectors sorted in their x and y values
    vector<int> x_idx = sort_index(x);
    vector<int> y_idx = sort_index(y);

    int n = min(x.size(), y.size());


    return 0.0;
}

int main() {
    size_t n;
    cin >> n;
    vector<int> x(n);
    vector<int> y(n);
    for (size_t i = 0; i < n; i++) {
        cin >> x[i] >> y[i];
    }
    cout << fixed;
    cout << setprecision(4) << minimal_distance_naive(x, y) << "\n";
}
