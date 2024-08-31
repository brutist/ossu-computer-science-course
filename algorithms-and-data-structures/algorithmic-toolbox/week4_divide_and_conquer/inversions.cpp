#include <iostream>
#include <vector>
#include <tuple>

using namespace std;

long long get_number_of_inversions(vector<int> &a, vector<int> &b, size_t left,
                                   size_t right) {
    long long number_of_inversions = 0;
    if (right <= left + 1)
        return number_of_inversions;
    size_t ave = left + (right - left) / 2;
    number_of_inversions += get_number_of_inversions(a, b, left, ave);
    number_of_inversions += get_number_of_inversions(a, b, ave, right);
    // write your code here
    return number_of_inversions;
}


int merge(vector<int> &a, vector<int> &aux, int left_start, int left_end, int right_end) {
    for (int n = left_start; n <= right_end; n++) {
        aux[n] = a[n];
    }

    int inversion_count = 0;
    int i = left_start, j = left_end + 1;
    for (int k = left_start; k <= right_end; k++) {
        if (i > left_end) {
            a[k] = aux[j++];
        }

        else if (j > right_end) {
            a[k] = aux[i++];
            inversion_count++;
        }

        else if (aux[i] <= aux[j]) {
            a[k] = aux[i++];
        }

        else {
            a[k] = aux[j++];
            inversion_count++;
        }
    }

    return inversion_count;
}

int merge_sort(vector<int> &a, vector<int> &aux, int left_start, int right_end) {
    if (left_start >= right_end) {
        return 0;
    }

    int mid = left_start + ((right_end - left_start) / 2);
    merge_sort(a, aux, left_start, mid);
    merge_sort(a, aux, mid + 1, right_end);
    int inversions = merge(a, aux, left_start, mid, right_end);
    return inversions;
}

int merge_sort_wrapper(vector<int> &a) {
    vector<int> aux(a);

    int n = a.size();
    int inversions = merge_sort(a, aux, 0, n - 1);
    return inversions;
}


int main() {
    int n;
    std::cin >> n;
    vector<int> a(n);
    for (size_t i = 0; i < a.size(); i++) {
        std::cin >> a[i];
    }
    vector<int> b(a.size());
    std::cout << merge_sort(a, b, 0, a.size()) << '\n';
    for (int i : a) {
        cout << i << " ";
    }
}
