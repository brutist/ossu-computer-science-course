#include <iostream>
#include <vector>
#include <tuple>
#include <algorithm>

using namespace std;

long long get_number_of_inversions_naive(vector<int> &a) {
    long long number_of_inversions = 0;
    int n = a.size();
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            if (a[i] > a[j]) {
                number_of_inversions++;
            }
        }
    }

    return number_of_inversions;
}

long long merge(vector<int> &a, vector<int> &aux, int left_start, int left_end, int right_end) {
    // copy
    for (int n = left_start; n <= right_end; n++) {
        aux[n] = a[n];
    }

    long long inversion_count = 0;
    int i = left_start, j = left_end + 1;
    for (int k = left_start; k <= right_end; k++) {
        if (i > left_end) {
            a[k] = aux[j++];
        }

        else if (j > right_end) {
            a[k] = aux[i++];
        }

        else if (aux[i] <= aux[j]) {
            a[k] = aux[i++];
        }

        else {
            a[k] = aux[j++];
            inversion_count += (left_end - i) + 1;
        }
    }

    return inversion_count;
}

long long merge_sort(vector<int> &a, vector<int> &aux, int left_start, int right_end) {
    if (left_start >= right_end) {
        return 0;
    }

    int mid = left_start + ((right_end - left_start) / 2);
    long long left_inversions = merge_sort(a, aux, left_start, mid);
    long long right_inversions = merge_sort(a, aux, mid + 1, right_end);
    long long whole_inversions = merge(a, aux, left_start, mid, right_end);

    return left_inversions + right_inversions + whole_inversions;
}

long long merge_sort_wrapper(vector<int> &a) {
    int n = a.size();
    vector<int> aux(n);

    long long inversions = merge_sort(a, aux, 0, n - 1);

    return inversions;
}

void stress_test_get_inversions() {
    srand(time(NULL));
    unsigned int test_counter = 0;
    int N_LIMIT = 100;
    int VALUE_LIMIT = 1000000000;

    while (true) {
        const int n = (rand() % N_LIMIT) + 1;
        vector<int> sort_array(n);

        for (int i = 0; i < n; i++) {
            sort_array[i] = (rand() % VALUE_LIMIT) + 1;
        }

        long long naive_answer = get_number_of_inversions_naive(sort_array);
        long long fast_answer = merge_sort_wrapper(sort_array);

        if (naive_answer != fast_answer ||
            !is_sorted(sort_array.begin(), sort_array.end())) {

            cout << "Naive quicksort answer: ";
            for (int num : sort_array) {
                cout << num << " ";
            }
            cout << "\n";

            std::cout << "Inversions of n size: " << n
                    << "  answer: " << naive_answer
                    << "  result: " << fast_answer << "\n";

            break;
        }

        test_counter++;
        if (test_counter % 10000 == 0) {
            cout << "TEST " << test_counter << "  PASSED\n";
        }
    }
}



int main() {
    int n;
    std::cin >> n;
    vector<int> a(n);
    for (size_t i = 0; i < a.size(); i++) {
        std::cin >> a[i];
    }

    vector<int> b(a.size());
    std::cout << merge_sort_wrapper(a) << '\n';

    //stress_test_get_inversions();
}
