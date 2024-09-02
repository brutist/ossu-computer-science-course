#include <algorithm>
#include <iostream>
#include <numeric>
#include <tuple>
#include <vector>

using namespace std;

vector<int> fast_count_segments(vector<int> &starts, vector<int> &ends,
                                vector<int> &query_points) {

    // Approach: Use sweep line algorithm. Coodinates are classified into
    //          three <start, end, query points>. Sort the coordinates
    //          in ascending order. Do a linear scan while maintaining a
    //          counter that would increase if you encounter an start point,
    //          decrease if you encounter an end point. if a query point is
    //          encountered, the value of the counter is the result for that
    //          query point.

    // Points are defined to be: {coordinate, classification, original index}
    int START = 0;
    int QUERY = 1;
    int END = 2;
    vector<tuple<int, int, int>> points;

    // add the starts and ends of segments to the points vector
    int segment_size = min(starts.size(), ends.size());
    for (int i = 0; i < segment_size; i++) {
        tuple<int, int, int> start_point = {starts[i], START, i};
        tuple<int, int, int> end_point = {ends[i], END, i};
        points.push_back(start_point);
        points.push_back(end_point);
    }

    // add the query points to the points vector
    int query_size = query_points.size();
    for (int i = 0; i < query_size; i++) {
        tuple<int, int, int> query = {query_points[i], QUERY, i};
        points.push_back(query);
    }

    vector<int> cnt(query_size);
    sort(points.begin(), points.end());
    int counter = 0;
    int total_points = points.size();
    for (int i = 0; i < total_points; i++) {
        int classification = get<1>(points[i]);

        if (classification == START) {
            counter++;
        }

        else if (classification == END) {
            counter--;
        }

        else if (classification == QUERY) {
            int query_index = get<2>(points[i]);
            cnt[query_index] = counter;
        }
    }

    return cnt;
}

vector<int> naive_count_segments(vector<int> &starts, vector<int> &ends,
                                 vector<int> &points) {
    vector<int> cnt(points.size());
    for (size_t i = 0; i < points.size(); i++) {
        for (size_t j = 0; j < starts.size(); j++) {
            cnt[i] += starts[j] <= points[i] && points[i] <= ends[j];
        }
    }
    return cnt;
}

void stress_test_count_segments() {
    srand(time(NULL));
    unsigned int test_counter = 0;
    int N_LIMIT = 100;
    int MIN_VALUE = -100000000;
    int MAX_VALUE = 100000000;

    while (true) {
        const int n = (rand() % N_LIMIT) + 1;
        vector<int> starts;
        vector<int> ends;
        vector<int> query_points;

        for (int i = 0; i < n; i++) {
            int r = MIN_VALUE + rand() % (MAX_VALUE - MIN_VALUE + 1);
            starts.push_back(r);
            ends.push_back(r + rand() % (MAX_VALUE - r + 1));
        }

        vector<int> naive_answer =
            naive_count_segments(starts, ends, query_points);
        vector<int> fast_answer =
            fast_count_segments(starts, ends, query_points);

        if (naive_answer != fast_answer) {

            cout << "Counting Points in NAIVE answer: ";
            for (int num : naive_answer) {
                cout << num << " ";
            }
            cout << "\n";

            cout << "Counting Points in FAST answer: ";
            for (int num : fast_answer) {
                cout << num << " ";
            }
            cout << "\n";

            break;
        }

        test_counter++;
        if (test_counter % 10000 == 0) {
            cout << "TEST " << test_counter << "  PASSED\n";
        }
    }
}

void time_count_segments_max_inputs(int samples) {
    srand(time(NULL));
    int N_LIMIT = 50000;
    int MIN_VALUE = -100000000;
    int MAX_VALUE = 100000000;
    vector<int> starts;
    vector<int> ends;
    vector<int> query_points;

    for (int i = 0; i < N_LIMIT; i++) {
        int r = MIN_VALUE + rand() % (MAX_VALUE - MIN_VALUE + 1);
        starts.push_back(r);
        ends.push_back(r + rand() % (MAX_VALUE - r + 1));
    }

    double start_time = (double)clock() / CLOCKS_PER_SEC;
    fast_count_segments(starts, ends, query_points);
    double time_diff = ((double)clock() / CLOCKS_PER_SEC) - start_time;

    std::cout << "The time elapsed for fast with max input: " << time_diff
              << "\n";
}

int main() {
    int n, m;
    std::cin >> n >> m;
    vector<int> starts(n), ends(n);
    for (size_t i = 0; i < starts.size(); i++) {
        std::cin >> starts[i] >> ends[i];
    }
    vector<int> points(m);
    for (size_t i = 0; i < points.size(); i++) {
        std::cin >> points[i];
    }
    // use fast_count_segments
    vector<int> cnt = fast_count_segments(starts, ends, points);
    for (size_t i = 0; i < cnt.size(); i++) {
        std::cout << cnt[i] << ' ';
    }

    // stress_test_count_segments();
    // time_count_segments_max_inputs(1000);
}
