#include <algorithm>
#include <cstdlib>
#include <iostream>
#include <vector>

using namespace std;

int partition2(vector<int> &a, int l, int r) {
    int x = a[l];
    int j = l;
    for (int i = l + 1; i <= r; i++) {
        if (a[i] <= x) {
            j++;
            swap(a[i], a[j]);
        }
    }
    swap(a[l], a[j]);
    return j;
}

vector<int> three_way_partition(vector<int> &a, int l, int r) {
    int x = a[l];
    int j = l;
    int m1 = l;
    int m2 = l;
    for (int i = l + 1; i <= r; i++) {
        if (a[i] == x) {
            j++;
            m2++;
        } else if (a[i] < x) {
            j++;
            swap(a[i], a[j]);
        }
    }

    // move the items m1 - m2 to a[j] to a[j - n], n being (m2 - m1) + 1;
    // keep track of the upperbound and lowerbound of the same key
    int equal_key_lower_bound = j;
    for (int i = m1; i <= m2; i++) {
        swap(a[i], a[j]);
        j--;
    }
    int equal_key_upper_bound = j;

    vector<int> equal_key_indexes = {equal_key_lower_bound,
                                     equal_key_upper_bound};
    return equal_key_indexes;
}

void randomized_quick_sort(vector<int> &a, int l, int r) {
    if (l >= r) {
        return;
    }

    int k = l + rand() % (r - l + 1);
    swap(a[l], a[k]);
    int m = partition2(a, l, r);

    randomized_quick_sort(a, l, m - 1);
    randomized_quick_sort(a, m + 1, r);
}

void three_way_quick_sort(vector<int> &a, int l, int r) {
    if (l >= r) {
        return;
    }

    int k = l + rand() % (r - l + 1);
    swap(a[l], a[k]);
    vector<int> m = three_way_partition(a, l, r);

    randomized_quick_sort(a, l, m[0] - 1);
    randomized_quick_sort(a, m[1] + 1, r);
}

void randomized_quick_sort_wrapper(vector<int> &a) {
    int end = a.size() - 1;
    randomized_quick_sort(a, 0, end);
}

void three_way_quick_sort_wrapper(vector<int> &a) {
    int end = a.size() - 1;
    three_way_quick_sort(a, 0, end);
}

void stress_test_quicksort() {
    srand(time(NULL));
    unsigned int test_counter = 0;
    int N_LIMIT = 1000;
    int VALUE_LIMIT = 1000000000;

    while (true) {
        const int n = (rand() % N_LIMIT) + 1;
        vector<int> sort_array1(n);

        for (int i = 0; i < n; i++) {
            sort_array1[i] = (rand() % VALUE_LIMIT) + 1;
        }

        vector<int> sort_array2(sort_array1);

        randomized_quick_sort_wrapper(sort_array1);
        three_way_quick_sort_wrapper(sort_array2);

        if (sort_array1 != sort_array2 ||
            !is_sorted(sort_array1.begin(), sort_array1.end()) ||
            !is_sorted(sort_array2.begin(), sort_array2.end())) {

            cout << "Naive quicksort answer: ";
            for (int num : sort_array1) {
                cout << num << " ";
            }
            cout << "\n";

            cout << "Three-way quicksort answer: ";
            for (int num : sort_array2) {
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

int main() {
    int n;
    std::cin >> n;
    vector<int> a(n);
    for (size_t i = 0; i < a.size(); ++i) {
        std::cin >> a[i];
    }
    randomized_quick_sort(a, 0, a.size() - 1);
    for (size_t i = 0; i < a.size(); ++i) {
        std::cout << a[i] << ' ';
    }

    cout << "\n";
    stress_test_quicksort();
}
