#include <cassert>
#include <deque>
#include <iostream>
#include <queue>
#include <vector>

using std::cin;
using std::cout;
using std::deque;
using std::max;
using std::queue;
using std::vector;

class QueueWithMax {
    queue<int> q;
    deque<int> max_dq;

  public:
    void Push(int value) {
        q.push(value);

        while (!max_dq.empty() && max_dq.back() < value) {
            max_dq.pop_back();
        }

        max_dq.push_back(value);
    }

    void Pop() {
        assert(!q.empty() && !max_dq.empty());

        if (q.front() == max_dq.front()) {
            max_dq.pop_front();
        }

        q.pop();
    }

    int Max() const {
        assert(!q.empty() && !max_dq.empty());
        return max_dq.front();
    }
};

void max_sliding_window(vector<int> const &A, int w) {
    // Approach: Maintain a max_queue that would crawl through
    //          the entire vector. Oldest item will be dequeued,
    //          and newest item will be enqueued. Calling max,
    //          should have O(1) to ensure O(n) for the entire
    //          function.
    QueueWithMax queue;
    int n = A.size();
    // add first w elements
    for (int i = 0; i < w; i++) {
        queue.Push(A[i]);
    }
    // print the max for first window
    cout << queue.Max() << " ";

    for (int i = w; i < n; i++) {
        queue.Pop();                // remove oldest element
        queue.Push(A[i]);           // add current element
        cout << queue.Max() << " "; // print max
    }
}

int main() {
    int n = 0;
    cin >> n;

    vector<int> A(n);
    for (int i = 0; i < n; ++i)
        cin >> A.at(i);

    int w = 0;
    cin >> w;

    max_sliding_window(A, w);

    return 0;
}
