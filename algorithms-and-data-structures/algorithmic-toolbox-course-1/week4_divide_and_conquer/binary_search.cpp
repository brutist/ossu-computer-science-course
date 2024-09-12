#include <cassert>
#include <iostream>
#include <vector>

using namespace std;

int binary_search_wrapper(const vector<int> &a, int x, int l, int r) {
    if (l == r && a[l] == x) {
        return l;
    }

    if (l == r)
        return -1;

    int middle = (l + r) / 2;
    if (a[middle] < x) {
        return binary_search_wrapper(a, x, middle + 1, r);
    } else {
        return binary_search_wrapper(a, x, l, middle);
    }
}

int binary_search(const vector<int> &a, int x) {
    int left = 0, right = (int)a.size();
    return binary_search_wrapper(a, x, left, right);
}

int linear_search(const vector<int> &a, int x) {
    for (size_t i = 0; i < a.size(); ++i) {
        if (a[i] == x)
            return i;
    }
    return -1;
}

void stress_test_search() {
    srand(time(NULL));
    unsigned int test_counter = 0;
    int N_LIMIT = 30000;
    int K_LIMIT = 100000;
    int VALUE_LIMIT = 1000000000;

    while (true) {
        const int n = (rand() % N_LIMIT) + 1;
        const int k = (rand() % K_LIMIT) + 1;
        vector<int> search_array(n);
        vector<int> to_search_array(k);

        for (int i = 0; i < n; i++) {
            search_array[i] = (rand() % VALUE_LIMIT) + 1;
        }

        for (int i = 0; i < k; i++) {
            to_search_array[i] = (rand() % VALUE_LIMIT) + 1;
        }

        for (int q : to_search_array) {
            int naive_answer = linear_search(search_array, q);
            int fast_answer = binary_search(search_array, q);

            if (naive_answer != fast_answer) {
                std::cout << "Binary Search of n: " << n
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
}

int main() {
    int n;
    std::cin >> n;
    vector<int> a(n);
    for (size_t i = 0; i < a.size(); i++) {
        std::cin >> a[i];
    }
    int m;
    std::cin >> m;
    vector<int> b(m);
    for (int i = 0; i < m; ++i) {
        std::cin >> b[i];
    }
    for (int i = 0; i < m; ++i) {
        // replace with the call to binary_search when implemented
        std::cout << binary_search(a, b[i]) << ' ';
    }

    stress_test_search();
}
