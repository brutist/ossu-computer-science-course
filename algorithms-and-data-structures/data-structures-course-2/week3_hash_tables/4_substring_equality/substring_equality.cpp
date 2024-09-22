#include <iostream>
#include <vector>

using namespace std;

class Solver {
    string s;
    std::vector<int> H1;
    std::vector<int> H2;
    int x;

  public:
    Solver(string s) : s(s), H1(s.size(), 0), H2(s.size(), 0), x(0) {
        // Approach: Suppose a polynomial hash function is defined as H(s,e),
        // where s is the starting index and e is the end index of a substring.
        // The hash of a substring H(a, a+l) is equal to the H(0, l) - H(0, a).
        // That is the hash of a substring is the difference between the hash
        // from 0 to l minus the hash from 0 - a. We can use this property of
        // poly hash to precalculate the H(0,n) for each substring of length n.

        // pick the base and modulo
        int m1 = 1000000007;
        int m2 = 1000000009;
        // select a random base
        srand(time(NULL));
        x = (rand() % 1000000000) + 1;

        // compute the hashes of H(0, n) H1 using m1, and H2 using m2 as primes
        int S = s.size();
        for (int i = 1; i <= S; i++) {
            // H1[i] = (s[i] + (x * H1[i - 1])) % m1;
            // H2[i] = (s[i] + (x * H2[i - 1])) % m2;
            H1[i] = hash_func(s.substr(0, i), m1, x);
            H2[i] = hash_func(s.substr(0, i), m2, x);
        }
    }

    int hash_func(const string &s, int p, int b) {
        unsigned long long hash = 0;
        for (int i = static_cast<int>(s.size()) - 1; i >= 0; --i)
            hash = (hash * b + s[i]) % p;
        return hash;
    }

    bool ask(int a, int b, int l) {
        int hash_a1 = H1[a + l] - H1[a];
        int hash_a2 = H2[a + l] - H2[a];
        int hash_b1 = H1[b + l] - H1[b];
        int hash_b2 = H2[b + l] - H2[b];
        // if both hash of the substrings are the same, then they are most
        //  probably equal strings
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
