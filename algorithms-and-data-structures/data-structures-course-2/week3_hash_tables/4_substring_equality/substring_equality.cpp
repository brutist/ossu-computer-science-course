#include <iostream>
#include <vector>

using std::cin;
using std::cout;
using std::make_pair;
using std::pair;
using std::string;
using std::vector;

class Solver {
    string s;
    std::vector<pair<int, int>> H;
    // Precompute powers of x for modulo {m1,m2}
    std::vector<pair<long long, long long>> P;
    int m1, m2;

  public:
    Solver(const string &str)
        : s(str), H(vector<pair<int, int>>(1, make_pair(0, 0))),
          P(vector<pair<long long, long long>>(1, make_pair(1, 1))) {
        // Use pre-defined primes to avoid repeated modulo operations
        m1 = 1000000007;
        m2 = 1000000009;

        // Use a fixed base for speed and consistency (use large prime or
        // random)
        int x = 31; // A common base in string hashing, or use 33, 37, etc.

        // Precompute hashes and powers of x
        int n = s.size();
        H.reserve(n);
        P.reserve(n);
        for (int i = 1; i <= n; ++i) {
            long long hash1 = (s[i - 1] + 1LL * x * H[i - 1].first);
            long long hash2 = (s[i - 1] + 1LL * x * H[i - 1].second);
            long long power1 = (1LL * P[i - 1].first * x);
            long long power2 = (1LL * P[i - 1].second * x);
            // only take the modulo if necessary
            hash1 = (hash1 > m1 ? hash1 : hash1 % m1);
            hash2 = (hash2 > m2 ? hash1 : hash2 % m2);
            power1 = (power1 > m1 ? power1 : power1 % m1);
            power2 = (power2 > m2 ? power2 : power2 % m2);

            H.push_back(make_pair(hash1, hash2));
            P.push_back(make_pair(power1, power2));
        }
    }

    inline bool ask(int a, int b, int l) {
        // Use inline to reduce function call overhead
        int ha1 = (H[a + l].first - ((1LL * H[a].first * P[l].first) % m1) % m1);
        int ha2 = (H[a + l].second - ((1LL * H[a].second * P[l].second) % m2) % m2);
        int hb1 = (H[b + l].first - ((1LL * H[b].first * P[l].first) % m1) % m1);
        int hb2 = (H[b + l].second - ((1LL * H[b].second * P[l].second) % m2) % m2);

        // Compare the two hashes
        return ha1 == hb1 && ha2 == hb2;
    }
};

int main() {
    std::ios_base::sync_with_stdio(0), cin.tie(0);

    string s;
    int q;
    cin >> s >> q;
    Solver solver(s);
    for (int i = 0; i < q; i++) {
        int a, b, l;
        cin >> a >> b >> l;
        cout << (solver.ask(a, b, l) ? "Yes\n" : "No\n");
    }
}
