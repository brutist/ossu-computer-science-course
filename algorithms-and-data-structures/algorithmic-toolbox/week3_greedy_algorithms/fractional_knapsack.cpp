#include <iostream>
#include <cmath>
#include <iomanip>
#include <algorithm>
#include <vector>

using namespace std;

int compare_value_per_unit(double val_per_unit_1, double val_per_unit_2);
bool equal_values(double value1, double value2, double precision);
double get_optimal_value_naive(int capacity, vector<int> weights, vector<int> values);
int get_most_weighted_value(vector<double> weights, vector<double> values);
double get_optimal_value(int capacity, vector<int> weights, vector<int> values);
void stress_test_get_optimal_value();

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

    stress_test_get_optimal_value();
    return 0;
}


double get_optimal_value_naive(int capacity, vector<int> weights, vector<int> values) {
    double value = 0.0;
    double bag_capacity = capacity;
    if (bag_capacity <= 0)  return  value;
    // create a deep copy of the weights and values vectors
    vector<double> weights_copy;
    vector<double> values_copy;
    weights_copy.reserve(weights.size());
    values_copy.reserve(values.size());

    for (unsigned int i = 0; i < weights.size(); i++) {
        weights_copy.push_back(weights[i]);
        values_copy.push_back(values[i]);
    }

    // Approach: Greedily fit the item that has the biggest value per unit.
    //          Repeat the process until knapsack is full or there are no items left.
    while (bag_capacity > 0) {

        // find the index of an item that has the biggest value per unit
        int max = get_most_weighted_value(weights_copy, values_copy);

        // if max is -1 then, there are no remaining items to take
        if (max < 0)    break;

        double current_max_weight = weights_copy[max];
        double weight_to_add = min(current_max_weight, bag_capacity);
        double value_per_unit_max = values_copy[max] / weights_copy[max];
        value += (weight_to_add * value_per_unit_max);

        // decrease the capacity by the weight that will be added to the knapsack
        bag_capacity -= weight_to_add;
        // subtract the weight added to knapsack to the weight of available things
        weights_copy[max] = weights_copy[max] - weight_to_add;
    }

    return value;
}

double get_optimal_value(int capacity, vector<int> weights, vector<int> values) {
    double value = 0.0;
    double total_capacity = capacity;
    // Approach: Greedily fit the item that has the biggest value per unit.
    //          Instead of linearly finding the maximal value per unit, sort the 
    //          items and pick the first available item and add it to the knapsack.
    //          This approach would simplify the O(n^2) naive approach to O(nlogn).

    // create a vector of (values per weight, available weight)
    vector<tuple<double, double>> values_per_weights;
    values_per_weights.reserve(weights.size());
    for (unsigned int i = 0; i < weights.size(); i++) {
        values_per_weights.push_back(make_tuple(((double) values[i] / weights[i]), weights[i]));
    }

    // sort the values per weights vector
    sort(values_per_weights.begin(), values_per_weights.end());

    // go through the sorted items and add as much of the maximal item
    for (unsigned int i = values_per_weights.size() - 1; i >= 0; i--) {
        if (total_capacity <= 0)    break;

        tuple<double, double> current_max = values_per_weights[i];
        const double vpw_max = get<0>(current_max);
        const double weight_max = min((get<1>(current_max)), total_capacity);

        value += (weight_max * vpw_max);
        total_capacity -= weight_max;
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

void stress_test_get_optimal_value() {
    srand(time(NULL));
    unsigned int test_counter = 0;
    int N_LIMIT = 10;
    int CAPACITY_LIMIT = 10;
    int WEIGHT_LIMIT = 10;
    int VALUE_LIMIT = 10;

    while (true) {
        const int n = (rand() % N_LIMIT) + 1;
        const int capacity = (rand() % CAPACITY_LIMIT);
        vector<int> weights;
        vector<int> values;
        // instantiate the weights and values
        for (int i = 0; i < n; i++) {
            weights.push_back((rand() % WEIGHT_LIMIT) + 1);
            values.push_back((rand() % VALUE_LIMIT) + 1);
        }

        double naive_answer = get_optimal_value_naive(capacity, weights, values);
        double fast_answer = get_optimal_value(capacity, weights, values);

        if (!equal_values(naive_answer, fast_answer, 4)){
            std::cout << "Fractional Knapsack of n: " << n << "  answer: " 
                        << naive_answer << "  result: " << fast_answer << "\n";
            break;
        }

        test_counter++;
        if (test_counter % 1000 == 0)
        {
            std::cout << "TEST " << test_counter << "  PASSED\n";
        }
    }
}