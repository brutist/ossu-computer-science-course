#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

// return the majority element in the vector (strictly > (n/2))
int get_majority_element_naive(vector<int> &a) {
    int n = a.size();
    for (int i = 0; i < n; i++) {
        int current_element = a[i];
        int count = 0;

        for (int j = 0; j < n; j++) {
            if (a[j] == current_element) {
                count++;
            }

            if (count > (n / 2)) {
                return current_element;
            }
        }
    }

    return -1;
}

int get_majority_element_fast(vector<int> &a) {
    // Approach: Sort the vector first, and do a linear pass to identify if
    //          an element is a majority element.
    vector<int> a_copy = a;
    sort(a_copy.begin(), a_copy.end());

    int n = a.size();
    int majority_threshold = n / 2;
    int element_checking;
    int element_checking_count;
    for (int i = 0; i < n; i++) {
        if (i == 0) {
            element_checking = a_copy[i];
            element_checking_count = 1;
        }

        else {
            // check if the current element is still the same with what's
            //  being checked
            if (element_checking == a_copy[i]) {
                element_checking_count++;
            } else {
                element_checking = a_copy[i];
                element_checking_count = 1;
            }
        }

        if (element_checking_count > majority_threshold) {
            return element_checking;
        }
    }

    return -1;
}

int get_majority_element_improved_helper(vector<int> &a, int start, int end) {
    // Approach: Recusively search for the majority element of each half of the
    // vector.
    //          Divide the array into two halves. An element is the majority of
    //          the whole array if it is at least the majority of one of the
    //          halves.

    // the element in a single-item vector is the majority element
    if (start == end) {
        return a[start];
    }

    // Identify the majority element for each half of the vector[start - end]
    int half = (start + end) / 2;
    int left_majority = get_majority_element_improved_helper(a, start, half);
    int right_majority = get_majority_element_improved_helper(a, half + 1, end);

    int left_majority_count = 0;
    int right_majority_count = 0;
    for (int i = start; i <= end; i++) {
        if (left_majority == a[i])
            left_majority_count++;
        else if (right_majority == a[i])
            right_majority_count++;
    }

    int half_of_total_elements = ((end - start) + 1) / 2;
    if (left_majority_count > half_of_total_elements) {
        return left_majority;
    }

    else if (right_majority_count > half_of_total_elements) {
        return right_majority;
    }

    else {
        return -1; // no majority element
    }
}

int get_majority_element(vector<int> &a) {
    int end = a.size() - 1;
    return get_majority_element_improved_helper(a, 0, end);
}

void stress_test_majority_element() {
    srand(time(NULL));
    unsigned int test_counter = 0;
    int N_LIMIT = 100;
    int VALUE_LIMIT = 100000;

    while (true) {
        const int n = (rand() % N_LIMIT) + 1;
        vector<int> search_array(n);

        for (int i = 0; i < n; i++) {
            search_array[i] = (rand() % VALUE_LIMIT) + 1;
        }

        int naive_answer = get_majority_element_naive(search_array);
        int fast_answer = get_majority_element_fast(search_array);
        int faster_answer = get_majority_element(search_array);
        if (naive_answer != fast_answer) {
            for (int num : search_array) {
                cout << num << " ";
            }
            cout << "\n";
            cout << "Majority Element of n: " << n
                 << "  answer: " << naive_answer << "  fast: " << fast_answer
                 << "\n";

            break;
        }

        if (naive_answer != faster_answer) {
            for (int num : search_array) {
                cout << num << " ";
            }
            cout << "\n";
            cout << "Majority Element of n: " << n
                 << "  answer: " << naive_answer
                 << "  faster: " << faster_answer << "\n";

            break;
        }

        test_counter++;
        if (test_counter % 10000 == 0) {
            cout << "TEST " << test_counter << "  PASSED\n";
        }
    }
}

vector<double> time_get_majority_element() {
    srand(time(NULL));
    int N_LIMIT = 100000;
    int VALUE_LIMIT = 1000000000;
    const int n = (rand() % N_LIMIT) + 1;
    vector<int> search_array(n);

    for (int i = 0; i < n; i++) {
        search_array[i] = (rand() % VALUE_LIMIT) + 1;
    }

    double naive_start_time = (double)clock() / CLOCKS_PER_SEC;
    get_majority_element_fast(search_array);
    double naive_time_diff =
        ((double)clock() / CLOCKS_PER_SEC) - naive_start_time;

    double start_time = (double)clock() / CLOCKS_PER_SEC;
    get_majority_element(search_array);
    double time_diff = ((double)clock() / CLOCKS_PER_SEC) - start_time;

    vector<double> time_results{naive_time_diff, time_diff};
    return time_results;
}

void average_time_get_majority_element(int samples) {
    vector<vector<double>> times;
    for (int i = 0; i < samples; i++) {
        times.push_back(time_get_majority_element());
    }

    double fast_time = 0.0;
    double faster_time = 0.0;
    for (vector<double> t : times) {
        fast_time += t[0];
        faster_time += t[1];
    }

    std::cout << "The average time elapsed for fast with max input: "
              << fast_time / samples << "\n";
    std::cout << "The average time elapsed for faster with max input: "
              << faster_time / samples << "\n";
}

int main() {
    int n;
    std::cin >> n;
    vector<int> a(n);
    for (size_t i = 0; i < a.size(); ++i) {
        std::cin >> a[i];
    }
    std::cout << (get_majority_element_naive(a) != -1) << '\n';

    stress_test_majority_element();
    // average_time_get_majority_element(1000);
}
