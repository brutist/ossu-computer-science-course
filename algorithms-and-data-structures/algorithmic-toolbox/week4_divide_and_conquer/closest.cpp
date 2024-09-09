#include <algorithm>
#include <cmath>
#include <iomanip>
#include <iostream>
#include <limits>
#include <string>
#include <vector>

using namespace std;

class SearchPoints {
  public:
    vector<int> x;
    vector<int> y;

    double dist_squared(int x1, int y1, int x2, int y2) {
        double x_sq = (long long)(x1 - x2) * (x1 - x2);
        double y_sq = (long long)(y1 - y2) * (y1 - y2);
        return x_sq + y_sq;
    }

    double minimal_distance_naive(vector<int> &idx, int start, int end) {
        double min_distance_squared = numeric_limits<double>::max();

        for (int i = start; i <= end; i++) {
            for (int j = i + 1; j <= end; j++) {
                double current_dist_squared =
                    dist_squared(x[idx[i]], y[idx[i]], x[idx[j]], y[idx[j]]);
                if (current_dist_squared < min_distance_squared) {
                    min_distance_squared = current_dist_squared;
                }
            }
        }

        return sqrt(min_distance_squared);
    }

    double minimal_distance_naive_wrapper() {
        int n = x.size();
        vector<int> idx(n);
        for (int i = 0; i < n; i++) {
            idx[i] = i;
        }
        return minimal_distance_naive(idx, 0, n - 1);
    }

    vector<int> sort_index(const vector<int> &keys, int start, int end) {
        vector<int> idx;
        for (int i = start; i <= end; i++) {
            idx.push_back(i);
        }

        sort(idx.begin(), idx.end(),
             [&](const int &a, const int &b) { return (keys[a] < keys[b]); });

        return idx;
    }

    double strip_closest(vector<int> &idx, double min_dist) {
        double min = min_dist * min_dist;

        // sort by y coordinates
        sort(idx.begin(), idx.end(),
             [&](const int &a, const int &b) { return (y[a] < y[b]); });

        int n = idx.size();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n && (y[idx[j]] - y[idx[i]]) < min; j++) {
                double curr_dist =
                    dist_squared(x[idx[j]], y[idx[j]], x[idx[i]], y[idx[i]]);
                if (curr_dist < min) {
                    min = curr_dist;
                }
            }
        }

        return sqrt(min);
    }

    double minimal_distance_util(vector<int> &idx, int start, int end) {
        // Approach: Recursively find the minimum distance (d1 and d2) between
        //          two points in the two half of the sorted (by x-coors)
        //          points. Let d be the minimum between (d1 and d2). Search a
        //          closer point within -d and +d of the boundary. Return the
        //          closer point

        int n = (end - start) + 1;
        if (n <= 3) {
            return minimal_distance_naive(idx, start, end);
        }

        // Find the midpoint and recursive calculate the smallest distance of
        //  the left and right subsections. Find the minimum between the two.
        int mid = (start + end) / 2;
        double d1 = minimal_distance_util(idx, start, mid);
        double d2 = minimal_distance_util(idx, mid + 1, end);
        double d = min(d1, d2);

        // Create a vector of indexes to the x and y values that is within
        //  -d -> +d of middle boundary
        int mid_x = x[idx[mid]];
        vector<int> strip_indexes;
        for (int i = start; i <= end; i++) {
            double delta_x = abs(x[idx[i]] - mid_x);
            if (delta_x <= d) {
                strip_indexes.push_back(idx[i]);
            }
        }

        double strip_min_dist = strip_closest(strip_indexes, d);
        return min(d, strip_min_dist);
    }

    double minimal_distance() {
        // Maintain an index of the original vectors sorted in their x and y
        // values
        int start = 0;
        int end = min(x.size(), y.size()) - 1;
        vector<int> sorted_x_idx = sort_index(x, start, end);

        // use recursive function minimal_distance_util to find the
        //  smallest distance
        return minimal_distance_util(sorted_x_idx, start, end);
    }
};

void stress_test_minimal_distance() {
    srand(time(NULL));
    unsigned int test_counter = 0;
    int N_LIMIT = 100;
    int MIN_VALUE = -1000000000;
    int MAX_VALUE = 1000000000;

    while (true) {
        const int n = (rand() % N_LIMIT) + 1;
        vector<int> x;
        vector<int> y;
        for (int i = 0; i < n; i++) {
            x.push_back(MIN_VALUE + rand() % (MAX_VALUE - MIN_VALUE + 1));
            y.push_back(MIN_VALUE + rand() % (MAX_VALUE - MIN_VALUE + 1));
        }

        vector<int> original_x(x);
        vector<int> original_y(y);

        SearchPoints points;
        points.x = x;
        points.y = y;

        double naive_answer = points.minimal_distance_naive_wrapper();
        double fast_answer = points.minimal_distance();
        double epsilon = 0.000001;

        if (abs(naive_answer - fast_answer) > epsilon) {
            if (points.x != original_x || points.y != original_y) {
                cout << "Mutation on minimal_distance()\n";
            }

            cout << "x: ";
            for (int i : x) {
                string spacer = "";
                if (i >= 0) {
                    spacer = " ";
                }
                cout << spacer << i << "  ";
            }
            cout << "\n";

            cout << "y: ";
            for (int i : y) {
                string spacer = "";
                if (i >= 0) {
                    spacer = " ";
                }
                cout << spacer << i << "  ";
            }
            cout << "\n";

            cout << "Closest Points NAIVE answer: " << naive_answer << "\n";
            cout << "Closest Points FAST answer: " << fast_answer << "\n\n";

            break;
        }

        test_counter++;
        if (test_counter % 10000 == 0) {
            cout << "TEST " << test_counter << "  PASSED\n";
        }
    }
}

void time_closest() {
    srand(time(NULL));
    int N_LIMIT = 200000;
    int MIN_VALUE = -1000000000;
    int MAX_VALUE = 1000000000;

    vector<int> x;
    vector<int> y;
    for (int i = 0; i < N_LIMIT; i++) {
        x.push_back(MIN_VALUE + rand() % (MAX_VALUE - MIN_VALUE + 1));
        y.push_back(MIN_VALUE + rand() % (MAX_VALUE - MIN_VALUE + 1));
    }

    SearchPoints points;
    points.x = x;
    points.y = y;

    double start_time = (double)clock() / CLOCKS_PER_SEC;
    points.minimal_distance();
    double time_diff = ((double)clock() / CLOCKS_PER_SEC) - start_time;

    std::cout << "The time elapsed for fast with max input: " << time_diff
              << " seconds \n";
}

int main() {
    size_t n;
    cin >> n;
    vector<int> x(n);
    vector<int> y(n);
    for (size_t i = 0; i < n; i++) {
        cin >> x[i] >> y[i];
    }

    SearchPoints points;
    points.x = x;
    points.y = y;
    cout << fixed;
    cout << setprecision(4) << points.minimal_distance() << "\n";

    // stress_test_minimal_distance();
    // time_closest();
}
