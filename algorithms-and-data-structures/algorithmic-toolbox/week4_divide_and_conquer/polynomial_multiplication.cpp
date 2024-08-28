#include <vector>
#include <iostream>

using namespace std;

vector<int> poly_mult_naive(vector<int> A, vector<int> B, int n) {
    int product_size = (2 * n) - 1;
    vector<int> product(product_size, 0);

    for(int i = 0; i < n; i++)
    {
        for(int j = 0; j < n; j++)
        { product[i + j] += (A[i] * B[j]); }
    }

    return product;
}

int main() {
    int n;
    vector<int> A(n);
    vector<int> B(n);

    cin >> n;
    for(int i = 0; i < n; i++)
    { cin >> A[i] >> B[i]; }

    for(int i : poly_mult_naive(A, B, n))
    { cout << i << " "; }

    return 0;
}