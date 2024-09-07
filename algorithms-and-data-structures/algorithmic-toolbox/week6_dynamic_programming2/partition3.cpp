#include <iostream>
#include <vector>
#include <numeric>

using std::vector;
using std::accumulate;

int partition3_naive_util(vector<int> &A, int total) {
    // Approach: If all of the items in a have been considered,
    //          either in setA, setB, or setC. The total for 
}

int partition3_naive(vector<int> &A) {
    auto sum = accumulate(A.begin(), A.end());
    
    if (sum % 3 != 0) {
        int no_equal_partition = -1;
        return no_equal_partition;
    }

    return partition3_naive_util();
}

int main() {
    int n;
    std::cin >> n;
    vector<int> A(n);
    for (size_t i = 0; i < A.size(); ++i) {
        std::cin >> A[i];
    }
    std::cout << partition3(A) << '\n';
}
