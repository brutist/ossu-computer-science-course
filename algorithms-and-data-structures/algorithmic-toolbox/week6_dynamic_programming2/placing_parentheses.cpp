#include <algorithm>
#include <cassert>
#include <iostream>
#include <limits>
#include <string>
#include <vector>

using namespace std;
using vll = vector<long long>;

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
    // base case
    if (i == j) {
        int num = stoi(exp.substr(i, 1));
        return {num, num};
    }

    long long min_eval = numeric_limits<long long>::max();
    long long max_eval = numeric_limits<long long>::min();
    for (int k = i + 1; k < j; k += 2) {
        char op = exp[k];
        vector<long long> left = min_and_max(exp, i, k - 1);
        vector<long long> right = min_and_max(exp, k + 1, j);
        long long a = eval(left[1], right[1], op);
        long long b = eval(left[1], right[0], op);
        long long c = eval(left[0], right[1], op);
        long long d = eval(left[0], right[0], op);

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
    vector<long long> min_max = min_and_max(exp, 0, exp.size() - 1);
    return max(min_max[0], min_max[1]);
}

long long get_maximum_value(const string &exp) {
    // Appproach: Maintain a dp array containing the
    //          minimum a subexpression from i to k - m(i, k) and
    //          the maximum of a subexpression M(i, k). The dp
    //          can then be expanded by increasing length of the
    //          subexpressions (j - i). The maximum_value will be
    //          the M(1, n) in an expression of size n.

    // n corresponds to the number of digits here
    // An expression is d0,op0,d1,op1,d2,op2...opn-1,dn.
    int n = (exp.size() / 2);
    // vll[0] = min, vll[1] = max
    vector<vector<vll>> dp(n + 1, vector<vll>(n + 1, vll(2)));
    // instantiate all j - i = 0 with the digit[i] itself
    for (int i = 0, k = 0; i <= n; i++, k++) {
        long long val = (exp[k] - '0') + 1;
        dp[i][i] = {val, val};
    }

    // go through the subproblems in order of increasing size(j - i)
    for (int s = 0; s <= n; s++) {
        for (int i = 0; i <= n - s; i++) {
            int j = i + s;
            dp[i][j] = min_and_max(exp, (2 * i), (2 * j));
        }
    }

    return max(dp[0][n][0], dp[0][n][1]);
}

string generate_valid_expression(int length) {
    if (length == 0 || length == 1) {
        length = 3;
    }

    if (length % 2 == 0) {
        length--;
    }

    string random_string;
    random_string.reserve(length);
    vector<char> operations = {'*', '+', '-'};

    for (int i = 0; i < length; i++) {
        if (i % 2 != 0) {
            random_string += operations[rand() % operations.size()];
        }

        else { // digits are only from 0 - 9
            random_string += to_string(rand() % 10);
        }
    }

    return random_string;
}

void stress_test_get_maximum_value(bool verbose = true) {
    srand(time(NULL));
    unsigned int test_counter = 0;
    int N_LIMIT = 15;
    int FAILED_TEST = 0;
    int THRESHOLD = 10;

    while (true) {
        const int n = (rand() % N_LIMIT) + 1;
        string exp = generate_valid_expression(n);

        long long naive_answer = get_maximum_value_naive(exp);
        long long fast_answer = get_maximum_value(exp);

        if (naive_answer != fast_answer) {
            cout << "answer: " << naive_answer << " input: " << exp << "\n";

            if (verbose) {
                cout << "PARTITION NAIVE answer: " << naive_answer << "\n";
                cout << "PARTITION FAST answer: " << fast_answer << "\n\n";
            }

            FAILED_TEST++;
            if (FAILED_TEST == THRESHOLD) {
                break;
            }
        }

        test_counter++;
        if (test_counter % 10000 == 0 && verbose) {
            std::cout << "TEST " << test_counter << "  PASSED\n";
        }
    }
}

int main() {
    string s;
    std::cin >> s;
    std::cout << get_maximum_value(s) << '\n';

    // stress_test_get_maximum_value();
}
