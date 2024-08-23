#include <iostream>
#include <cmath>
#include <algorithm>
#include <vector>

using namespace std;

bool compare_value_per_unit(int value1, int weight1, int value2, int weight2);
bool equal_values(double value1, double value2, double precision);
double get_optimal_value(int capacity, vector<int> weights, vector<int> values);
int get_most_weighted_value(vector<int> weights, vector<int> values);


int main() {
    int n;
    int capacity;
    std::cin >> n >> capacity;
    vector<int> values(n);
    vector<int> weights(n);
    for (int i = 0; i < n; i++) {
        std::cin >> values[i] >> weights[i];
    }

    double optimal_value = get_optimal_value(capacity, weights, values);

    std::cout.precision(10);
    std::cout << optimal_value << std::endl;
    return 0;
}


double get_optimal_value(int capacity, vector<int> weights, vector<int> values) {
    double value = 0.0;

    // Approach: Greedily fit the item that has the biggest value per unit.
    //          Repeat the process until knapsack is full or there are no items left.
    while (value < capacity) {
        double remaining_capacity = capacity - value;

        // find the item that has the biggest value per unit
        int max = get_most_weighted_value(weights, values);

        double weight_to_add = min(weights[max], remaining_capacity);
        value += weight_to_add;
        weights[max] = weights[max] - weight_to_add;
    }

    return value;
}

int get_most_weighted_value(vector<int> weights, vector<int> values) {
    int N = weights.size();
    int min_index = 0;      
    for (int i = 0; i < N; i++) {
        int m = min_index;
        if (compare_value_per_unit(values[m], weights[m], values[i], weights[i]) < 0) {
            min_index = i;
        }
    }

    return min_index;
}


bool compare_value_per_unit(int value1, int weight1, int value2, int weight2) {
    // items that don't have weights are not considered
    if (weight2 <= 0)   return 1;

    double val_per_unit_1 = value1 / weight1;
    double val_per_unit_2 = value2 / weight2;
    return val_per_unit_1.compare(val_per_unit_2);
}

bool equal_values(double value1, double value2, double precision) {
    return std:abs(value1 - value2) < pow(10, -precision);
}