#include <algorithm>
#include <iostream>
#include <queue>
#include <vector>

using std::cin;
using std::cout;
using std::make_pair;
using std::pair;
using std::priority_queue;
using std::vector;
using tt_pair = pair<int, long long>; // thread-time pair

class JobQueue {
  private:
    int num_workers_;
    vector<int> jobs_;

    vector<int> assigned_workers_;
    vector<long long> start_times_;


    // Define a custom comparator as a struct
    struct greater_tt_pair {
        bool operator()(const tt_pair& A, const tt_pair& B) const {
            if (A.second != B.second) {
                return A.second > B.second;
            }

            long long A_weight = A.first + A.second;
            long long B_weight = B.first + B.second;
            // This defines a min-heap based on the sum of pairs
            return  A_weight > B_weight;
        }
    };

    void WriteResponse() const {
        int n = jobs_.size();
        for (int i = 0; i < n; ++i) {
            cout << assigned_workers_[i] << " " << start_times_[i] << "\n";
        }
    }

    void ReadData() {
        int m;
        cin >> num_workers_ >> m;
        jobs_.resize(m);
        for (int i = 0; i < m; ++i)
            cin >> jobs_[i];
    }

    void AssignJobs() {
        // Approach: Maintain a min-priority that would contains a pair
        //      of [thread, time]. Its priority is just the time + thread.
        //      Meaning the minimum is the thread with the lowest time
        //      (currently done processing) + index. Linearly go thru each
        //      job and the lowest priority tt_pair will take in the job,
        //      update the tt_pair by increasing it with jobs_[i].

        // resize assigned_workers_ and start_times_
        int n = jobs_.size();
        assigned_workers_.resize(n);
        start_times_.resize(n);
        priority_queue<tt_pair, vector<tt_pair>, greater_tt_pair> tt_pair_pq;
        for (int i = 0; i < num_workers_; i++) {
            tt_pair_pq.push(make_pair(i, 0));
        }

        for (int i = 0; i < n; i++) {
            tt_pair min = tt_pair_pq.top();
            tt_pair_pq.pop();

            // keep track of the assigned thread and start time
            assigned_workers_[i] = min.first;
            start_times_[i] = min.second;

            // update the tt_pair and push it back to the pq
            min.second += jobs_[i];
            tt_pair_pq.push(min);
        }
    }

  public:
    void Solve() {
        ReadData();
        AssignJobs();
        WriteResponse();
    }
};

int main() {
    std::ios_base::sync_with_stdio(false);
    JobQueue job_queue;
    job_queue.Solve();
    return 0;
}
