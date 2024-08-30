#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

// return the majority element in the vector (strictly > (n/2))
int get_majority_element_naive(vector<int> &a) {
    int n = a.size();
    for (int i = 0; i < n; i++) {
        int current_element = a[i];
        int count = 0;

        for (int j = 0; j < n; j++) {
            if (a[j] == current_element) {
                count++;
            }

            if (count > (n / 2)) {
                return current_element;
            }
        }
    }

    return -1;
}

int get_majority_element(vector<int> &a) {
    // Approach: Sort the vector first, and do a linear pass to identify if 
    //          an element is a majority element.
    vector<int> a_copy = a;
    sort(a_copy.begin(), a_copy.end());

    int n = a.size();
    int majority_threshold = n / 2;
    int element_checking;
    int element_checking_count;
    for (int i = 0; i < n; i++) {
        if (i == 0) {
            element_checking = a_copy[i];
            element_checking_count = 1;
        }

        else {
            // check if the current element is still the same with what's 
            //  being checked
            if (element_checking == a_copy[i]) {
                element_checking_count++;
            }
            else {
                element_checking = a_copy[i];
                element_checking_count = 1;
            }
        }

        if (element_checking_count > majority_threshold) {
            return element_checking;
        }
    }

    return -1;
}

void stress_test_majority_element() {
    srand(time(NULL));
    unsigned int test_counter = 0;
    int N_LIMIT = 1000;
    int VALUE_LIMIT = 1000;

    while (true) {
        const int n = (rand() % N_LIMIT) + 1;
        vector<int> search_array(n);

        for (int i = 0; i < n; i++) {
            search_array[i] = (rand() % VALUE_LIMIT) + 1;
        }

        int naive_answer = get_majority_element_naive(search_array);
        int fast_answer = get_majority_element(search_array);
        if (naive_answer != fast_answer) {
            for (int num : search_array) {
                cout << num << " ";
            }
            cout << "\n";
            cout << "Majority Element of n: " << n
                << "  answer: " << naive_answer
                << "  result: " << fast_answer << "\n";

            break;
        }

        test_counter++;
        if (test_counter % 10000 == 0) {
            cout << "TEST " << test_counter << "  PASSED\n";
        }
    
    }
}



int main() {
    int n;
    std::cin >> n;
    vector<int> a(n);
    for (size_t i = 0; i < a.size(); ++i) {
        std::cin >> a[i];
    }
    std::cout << (get_majority_element_naive(a) != -1) << '\n';

    stress_test_majority_element();
}
