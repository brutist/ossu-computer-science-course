#include <algorithm>
#include <iostream>
#include <vector>

using std::vector;

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

int main() {
    int n;
    std::cin >> n;
    vector<int> a(n);
    for (size_t i = 0; i < a.size(); ++i) {
        std::cin >> a[i];
    }
    std::cout << (get_majority_element_naive(a) != -1) << '\n';
}
