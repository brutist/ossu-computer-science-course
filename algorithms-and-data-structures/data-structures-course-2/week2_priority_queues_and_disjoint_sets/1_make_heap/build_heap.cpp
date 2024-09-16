#include <algorithm>
#include <iostream>
#include <vector>

using std::cin;
using std::cout;
using std::make_pair;
using std::pair;
using std::swap;
using std::vector;

class HeapBuilder {
  private:
    vector<int> data_;
    vector<pair<int, int>> swaps_;

    void WriteResponse() const {
        cout << swaps_.size() << "\n";
        for (unsigned int i = 0; i < swaps_.size(); ++i) {
            cout << swaps_[i].first << " " << swaps_[i].second << "\n";
        }
    }

    void ReadData() {
        int n;
        cin >> n;
        data_.resize(n);
        for (int i = 0; i < n; ++i)
            cin >> data_[i];
    }

    void GenerateSwaps() {
        swaps_.clear();
        // Approach: Since the leafs already satisfy the heap property,
        //      we can recursively sink the elements from n/2 until
        //      1 to transform the vector into a heap. Since this is a
        //      min heap, parents that are bigger should be replaced with
        //      the smallest children.
        int n = data_.size();
        for (int i = (n / 2) - 1; i >= 0; i--) {
            sink(i, n);
        }

    }

    void sink(int i, int size) {
        int min_index = i;
        int l = (i * 2) + 1;
        int r = (i * 2) + 2;

        if (l < size && data_[l] < data_[min_index]) {
            min_index = l;
        }

        if (r < size && data_[r] < data_[min_index]) {
            min_index = r;
        }

        if (i != min_index) {
            swap(data_[i], data_[min_index]);
            swaps_.push_back(make_pair(i, min_index)); // record the swap
            sink(min_index, size); // recursively sink affected subtree
        }
    }

  public:
    void Solve() {
        ReadData();
        GenerateSwaps();
        WriteResponse();
    }
};

int main() {
    std::ios_base::sync_with_stdio(false);
    HeapBuilder heap_builder;
    heap_builder.Solve();
    return 0;
}
