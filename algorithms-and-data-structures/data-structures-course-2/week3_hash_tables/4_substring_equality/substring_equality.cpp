#include <iostream>
#include <vector>

using namespace std;

class Solver {
    string s;
    std::vector<int> H1;
    std::vector<int> H2;
    std::vector<long long> P1;  // Precompute powers of x for modulo m1
    std::vector<long long> P2;  // Precompute powers of x for modulo m2
    int x, m1, m2;

  public:
    Solver(const string &str) : s(str), H1(s.size() + 1, 0), H2(s.size() + 1, 0), P1(s.size() + 1, 1), P2(s.size() + 1, 1) {
        // Use pre-defined primes to avoid repeated modulo operations
        m1 = 1000000007;
        m2 = 1000000009;

        // Use a fixed base for speed and consistency (use large prime or random)
        x = 31;  // A common base in string hashing, or use 33, 37, etc.

        // Precompute hashes and powers of x
        int n = s.size();
        for (int i = 1; i <= n; ++i) {
            H1[i] = (s[i - 1] + 1LL * x * H1[i - 1]) % m1;
            H2[i] = (s[i - 1] + 1LL * x * H2[i - 1]) % m2;
            P1[i] = (1LL * P1[i - 1] * x) % m1;  // x^i % m1
            P2[i] = (1LL * P2[i - 1] * x) % m2;  // x^i % m2
        }
    }

    inline bool ask(int a, int b, int l) {
        // Use inline to reduce function call overhead
        int hash_a1 = (H1[a + l] - 1LL * H1[a] * P1[l] % m1 + m1) % m1;
        int hash_a2 = (H2[a + l] - 1LL * H2[a] * P2[l] % m2 + m2) % m2;
        int hash_b1 = (H1[b + l] - 1LL * H1[b] * P1[l] % m1 + m1) % m1;
        int hash_b2 = (H2[b + l] - 1LL * H2[b] * P2[l] % m2 + m2) % m2;
        
        // Compare the two hashes
        return hash_a1 == hash_b1 && hash_a2 == hash_b2;
    }
};


int main() {
    ios_base::sync_with_stdio(0), cin.tie(0);

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
