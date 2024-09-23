#include <iostream>

using namespace std;

struct Answer {
    size_t i, j, L;
};

Answer solve(const string &s, const string &t) {
    Answer ans = {0, 0, 0};
    for (size_t i = 0; i < s.size(); i++)
        for (size_t j = 0; j < t.size(); j++)
            for (size_t L = 0; i + L <= s.size() && j + L <= t.size(); L++)
                if (L > ans.L && s.substr(i, L) == t.substr(j, L))
                    ans = {i, j, L};
    return ans;
}

int main() {
    ios_base::sync_with_stdio(false), cin.tie(0);
    string s, t;
    while (cin >> s >> t) {
        auto ans = solve(s, t);
        cout << ans.i << " " << ans.j << " " << ans.L << "\n";
    }
}
