#include <iostream>
#include <vector>

using std::string;
using std::cout;
using std::vector;


struct Answer {
    size_t i, j, len;
};

class Solver {
    string s;
    std::vector<int> H1;
    std::vector<int> H2;
    std::vector<long long> P1; // Precompute powers of x for modulo m1
    std::vector<long long> P2; // Precompute powers of x for modulo m2
    int x, m1, m2;

  public:
    Solver(const string &str) : s(str) {
        // initialize hash[k] and power[k] containing hashes of length k
        //  and base raise to k
        H1 = std::vector<int>(s.size() + 1, 0); 
        H2 = std::vector<int>(s.size() + 1, 0);
        P1 = std::vector<long long>(s.size() + 1, 1);
        P2 = std::vector<long long>(s.size() + 1, 1); 

        // Use pre-defined primes to avoid repeated modulo operations
        m1 = 1000000007;
        m2 = 1000000009;

        // Use a fixed base for speed and consistency (use large prime or
        // random)
        x = 31; // A common base in string hashing, or use 33, 37, etc.

        // Precompute hashes and powers of x
        int n = s.size();
        for (int i = 1; i <= n; ++i) {
            H1[i] = (s[i - 1] + 1LL * x * H1[i - 1]) % m1;
            H2[i] = (s[i - 1] + 1LL * x * H2[i - 1]) % m2;
            P1[i] = (1LL * P1[i - 1] * x) % m1; // x^i % m1
            P2[i] = (1LL * P2[i - 1] * x) % m2; // x^i % m2
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

Answer solve(const string &s, const string &t) {
    // Approach: Use binary search to search for common substring of length K in
    //      both strings by searching for a common substring of length K-1.
    //      Precompute hash values of all substrings of length k of s and t. Use
    //      several hash functions to reduce probability of collision. Store
    //      hash values of all substring of length k of the string s in a table,
    //      then go through all substrings of length k of the string t and check
    //      if the hash value of the sbustring is in the hash table.

    Solver solved_s = Solver(s);
    Solver solved_t = Solver(t);

    int low = 0, high = std::min(s.size(), t.size());
    Answer answer = {0, 0, 0};
    while (low <= high) {
        int mid = (low + high) / 2;
        if ()
    }

}

int main() {
    ios_base::sync_with_stdio(false), cin.tie(0);
    string s, t;
    while (cin >> s >> t) {
        auto ans = solve(s, t);
        cout << ans.i << " " << ans.j << " " << ans.len << "\n";
    }
}
