#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

long long max_dot_product(vector<int> a, vector<int> b);

int main() {
    size_t n;
    std::cin >> n;
    vector<int> a(n), b(n);
    for (size_t i = 0; i < n; i++) {
        std::cin >> a[i];
    }

    for (size_t i = 0; i < n; i++) {
        std::cin >> b[i];
    }
    std::cout << max_dot_product(a, b) << std::endl;
}

long long max_dot_product(vector<int> a, vector<int> b) {
    // Approach: The maximum dot product between the two vectors can be
    //          calculated by sorting the two array and then pairing the
    //          same ranking value to get the highest possible sum.

    sort(a.rbegin(), a.rend());
    sort(b.rbegin(), b.rend());

    int max_index = max(a.size(), b.size());
    long long sum = 0;
    for (int i = 0; i < max_index; i++) {
        sum += (long long)a[i] * b[i];
    }

    return sum;
}