#include <iostream>
#include <numeric>
#include <vector>

using std::accumulate;
using std::vector;
using std::cout;

class SumPartition {
  public:
    vector<int> &A;

    int optimal_weight(int W, vector<int> &included) {
        // returns the optimal weight that can be fitted
        //  into the knapsack with capacity W. This function
        //  also mutates the included vector to update the
        //  index of the items that has been included to the 
        //  optimal weight.
        int num_items = A.size();
        vector<vector<int>> dp(W + 1, vector<int>(num_items + 1));
        for (int w = 0; w <= W; w++) {
            for (int i = 0; i <= num_items; i++) {
                if (w == 0 || i == 0) {
                    dp[w][i] = 0;
                }

                else if (A[i - 1] <= w && included[i - 1] == 0) {
                    int val = dp[w - A[i - 1]][i - 1] + A[i - 1];

                    if (dp[w][i] < val) {
                        dp[w][i] = val;
                        included[i - 1] = 1;
                    }
                }

                else if (included[i - 1] == 0) {
                    dp[w][i] = dp[w][i - 1];
                    included[i - 1] = 1;
                }
            }
        }
        for (vector<int> v : dp) {
            print_array(v);
        }
        return dp[W][num_items];
    }

    void print_array(vector<int> v) {
        cout << "printing array: ";
        for (int i : v) {
            cout << i << " ";
        }
        cout << "\n";
    }

    int partition3() {
        // Approach: The set of int can only be partitioned to three
        //          equal parts if the sum of the set is divisible
        //          by three. If it is possible to partition the set
        //          into three equal parts, we can identify if it
        //          is possible to fill in three 0/1 knapsack of
        //          weight = sum / 3.
        int NO_PARTITION = 0;
        int CAN_PARTITION = 1;
        auto sum = accumulate(A.begin(), A.end(), 0);
        if (sum % 3 != 0) {
            return NO_PARTITION;
        }

        // keep track of the included integers in the three sets
        int partition = sum % 3;
        vector<int> included(A.size() + 1, 0); // 1-based index
        // if the weights of all the sets is equal to the sum / 3
        // then there is a way to partition such that the partitions
        // have equal sums
        int setA = optimal_weight(partition, included);
        int setB = optimal_weight(partition, included);
        int setC = optimal_weight(partition, included);
            
        if (setA == setB && setB == setC && setC == partition) {
            return CAN_PARTITION;
        }
        
        return NO_PARTITION;
    }
};

int main() {
    int n;
    std::cin >> n;
    vector<int> A(n);
    for (size_t i = 0; i < A.size(); ++i) {
        std::cin >> A[i];
    }

    SumPartition sp = {A};
    std::cout << sp.partition3() << '\n';
}
