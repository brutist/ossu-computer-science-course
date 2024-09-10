#include <algorithm>
#include <cassert>
#include <iostream>
#include <limits>
#include <string>
#include <vector>

using std::count;
using std::max;
using std::min;
using std::numeric_limits;
using std::stoi;
using std::string;
using std::vector;

long long eval(long long a, long long b, char op) {
    if (op == '*') {
        return a * b;
    }

    else if (op == '+') {
        return a + b;
    }

    else if (op == '-') {
        return a - b;
    }

    else {
        assert(0);
    }
}

vector<long long> min_and_max(const string &exp, int i, int j) {
    // find the operation
    int num_operations = 0;
    int idx = i;
    for (int k = i; k < j; k++) {
        char c = exp[k];
        if (c == '*' || c == '+' || c == '-') {
            num_operations++;
            idx = k;
        }
    }

    // base case
    if (num_operations == 1) {
        long long operand1 = stoi(exp.substr(i, idx - i));
        long long operand2 = stoi(exp.substr(idx + 1, j - idx));
        long long ans = eval(operand1, operand2, exp[idx]);
        return {ans, ans};
    }

    long long min_eval = numeric_limits<long long>::max();
    long long max_eval = -numeric_limits<long long>::max();
    vector<char> ops = {'*', '+', '-'};
    for (int k = i; k < j; k++) {
        char op = exp[k];
        if (count(ops.begin(), ops.end(), op) == 0) {
            continue;
        }

        long long min_left = min_and_max(exp, i, k)[0];
        long long max_left = min_and_max(exp, i, k)[1];
        long long min_right = min_and_max(exp, k + 1, j)[0];
        long long max_right = min_and_max(exp, k + 1, j)[1];

        long long a = eval(max_left, max_right, op);
        long long b = eval(max_left, min_right, op);
        long long c = eval(min_left, max_right, op);
        long long d = eval(min_left, min_right, op);

        min_eval = min({min_eval, a, b, c, d});
        max_eval = max({max_eval, a, b, c, d});
    }

    return {min_eval, max_eval};
}

long long get_maximum_value_naive(const string &exp) {
    // Approach: Let M(i,j) be the expression composed of
    //          two subexpressions and the operation between
    //          M(i,k) opk M(k+1, j). Find the minimum and
    //          maximum values of the expressions and pick
    //          the largest when opk is applied to it.
    vector<long long> min_max = min_and_max(exp, 0, exp.size());
    return max(min_max[0], min_max[1]);
}

int main() {
    string s;
    std::cin >> s;
    std::cout << get_maximum_value_naive(s) << '\n';
}
