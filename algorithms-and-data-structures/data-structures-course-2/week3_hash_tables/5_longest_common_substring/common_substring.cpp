#include <cassert>
#include <iostream>
#include <vector>

using std::cout;
using std::make_pair;
using std::pair;
using std::string;
using std::vector;

struct Answer {
    size_t i, j, len;
};

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

    pair<int, int> get_hashes(int a, int l) {
        assert(a + l <= static_cast<int>(H.size()));

        int ha1 = (H[a + l].first - (H[a].first * P[l].first % m1) % m1);
        int ha2 = (H[a + l].second - (H[a].second * P[l].second % m2) % m2);

        // Compare the two hashes
        return make_pair(ha1, ha2);
    }

    // returns the start index i and j of equal substrings of length l in
    // strings in s and t
    pair<int, int> equal_starts_index(Solver &t, int T, int l) {
        int S = s.size();
        for (int i = 0; i + l <= S; i++) {
            for (int j = 0; j + l <= T; j++) {
                pair<int, int> hash_s = get_hashes(i, l);
                pair<int, int> hash_t = t.get_hashes(j, l);

                if (hash_s == hash_t) {
                    return make_pair(i, j);
                }
            }
        }

        return make_pair(-1, -1);
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

    int S = s.size(), T = t.size();
    int low = 0, high = std::min(S, T);
    Answer answer = {0, 0, 0};
    // binary search for the length of maximum equal substrings in s and t
    while (low <= high) {
        size_t mid = (low + high) / 2;
        pair<int, int> starts = solved_s.equal_starts_index(solved_t, T, mid);
        if (starts.first != -1 && starts.second != -1) {
            low = mid + 1;
            answer = {starts.first, starts.second, mid};
        }

        else {
            high = mid - 1;
        }
    }

    return answer;
}

int main() {
    std::ios_base::sync_with_stdio(false), std::cin.tie(0);
    string s, t;
    while (std::cin >> s >> t) {
        auto ans = solve(s, t);
        std::cout << ans.i << " " << ans.j << " " << ans.len << "\n";
    }
}
