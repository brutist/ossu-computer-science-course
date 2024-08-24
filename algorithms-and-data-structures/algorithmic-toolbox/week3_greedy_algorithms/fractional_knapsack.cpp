#include <iostream>
#include <cmath>
#include <iomanip>
#include <algorithm>
#include <vector>

using namespace std;

int compare_value_per_unit(double val_per_unit_1, double val_per_unit_2);
bool equal_values(double value1, double value2, double precision);
double get_optimal_value(int capacity, vector<int> weights, vector<int> values);
int get_most_weighted_value(vector<double> weights, vector<double> values);


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

    std::cout << fixed << setprecision(4) << optimal_value << std::endl;
    return 0;
}


double get_optimal_value(int capacity, vector<int> weights, vector<int> values) {
    // create a deep copy of the weights and values vectors
    vector<double> weights_copy;
    vector<double> values_copy;
    weights_copy.reserve(weights.size());
    values_copy.reserve(values.size());

    for (unsigned int i = 0; i < weights.size(); i++) {
        weights_copy.push_back(weights[i]);
        values_copy.push_back(values[i]);
    }

    double value = 0.0;
    double total_weight = 0.0;
    // Approach: Greedily fit the item that has the biggest value per unit.
    //          Repeat the process until knapsack is full or there are no items left.
    while (total_weight < capacity) {
        double remaining_capacity = capacity - total_weight;

        // find the index of an item that has the biggest value per unit
        int max = get_most_weighted_value(weights_copy, values_copy);

        // if max is -1 then, there are no remaining items to take
        if (max < 0)    break;

        double current_max_weight = weights_copy[max];
        double weight_to_add = min(current_max_weight, remaining_capacity);
        double value_per_unit_max = values_copy[max] / weights_copy[max];
        value += (weight_to_add * value_per_unit_max);

        // add the item to the knapsack
        total_weight = total_weight + weight_to_add;
        // subtract the weight added to knapsack to the weight of available things
        weights_copy[max] = weights_copy[max] - weight_to_add;
    }

    return value;
}

int get_most_weighted_value(vector<double> weights, vector<double> values) {
    int N = weights.size();
    int min_index = -1;      
    for (int i = 0; i < N; i++) {
        if (weights[i] <= 0) continue;

        else if (min_index < 0) {
            min_index = i;
        }

        else {
            double vpu1 = values[min_index] / weights[min_index];
            double vpu2 = values[i] / weights[i];
            if (compare_value_per_unit(vpu1, vpu2) < 0) {
                min_index = i;
            }
        }
    }

    return min_index;
}


int compare_value_per_unit(double val_per_unit_1, double val_per_unit_2) {
    return val_per_unit_1 - val_per_unit_2;
}

bool equal_values(double value1, double value2, double precision) {
    return abs(value1 - value2) < pow(10, -precision);
}