#include <iostream>

using namespace std;

struct Answer {
    size_t i, j, L;
};

Answer solve(const string &s, const string &t) {
    // Approach: Precompute hash values of all substrings of length k of s and t.
    //      Use several hash functions to reduce probability of collision.



    
}

int main() {
    ios_base::sync_with_stdio(false), cin.tie(0);
    string s, t;
    while (cin >> s >> t) {
        auto ans = solve(s, t);
        cout << ans.i << " " << ans.j << " " << ans.L << "\n";
    }
}
