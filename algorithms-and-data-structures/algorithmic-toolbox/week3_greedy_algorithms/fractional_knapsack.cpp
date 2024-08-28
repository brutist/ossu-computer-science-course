#include <algorithm>
#include <cmath>
#include <iomanip>
#include <iostream>
#include <vector>

using namespace std;

bool equal_values(double value1, double value2, double precision);
double
get_optimal_value_naive(int capacity, vector<int> weights, vector<int> values);
int get_most_weighted_value(vector<int> &weights, vector<int> &values,
                            vector<int> &included);
double
get_optimal_value(int capacity, vector<int> weights, vector<int> values);
void stress_test_get_optimal_value();

int main()
{
    int n;
    int capacity;
    std::cin >> n >> capacity;
    vector<int> values(n);
    vector<int> weights(n);
    for(int i = 0; i < n; i++)
        {
            std::cin >> values[i] >> weights[i];
        }

    double optimal_value = get_optimal_value_naive(capacity, weights, values);

    cout << fixed << setprecision(4) << optimal_value << endl;

    stress_test_get_optimal_value();
    return 0;
}

double
get_optimal_value_naive(int capacity, vector<int> weights, vector<int> values)
{
    double value = 0.0;
    double total_capacity = capacity;
    int N = weights.size();
    vector<int> included(N, 0);

    // Approach: Greedily fit the item that has the biggest value per unit.
    //          Repeat the process until knapsack is full or there are no items
    //          left.
    for(int i = 0; i < N; i++)
        {
            // find the index of an item that has the biggest value per unit
            int max = get_most_weighted_value(weights, values, included);

            // stop adding items if knapsack is full or ther are no remaining
            // items to add
            if(total_capacity <= 0 || max < 0)
                break;

            double current_max_weight = weights[max];
            double weight_to_add = min(current_max_weight, total_capacity);
            double value_per_unit_max = values[max] / (double)weights[max];
            value += (weight_to_add * value_per_unit_max);
            // decrease the capacity by the weight that will be added to the
            // knapsack
            total_capacity -= weight_to_add;
            // set max item to be already included in the knapsack
            included[max] = 1;
        }

    return value;
}

double get_optimal_value(int capacity, vector<int> weights, vector<int> values)
{
    double value = 0.0;
    double total_capacity = capacity;

    // Approach: Greedily fit the item that has the biggest value per unit.
    //          Instead of linearly finding the maximal value per unit, sort
    //          the items and pick the first available item and add it to the
    //          knapsack. This approach would simplify the O(n^2) naive
    //          approach to O(nlogn).

    // create a vector of (values per weight, available weight, index)
    vector<tuple<double, double>> values_per_weights;
    values_per_weights.reserve(weights.size());
    for(unsigned int i = 0; i < weights.size(); i++)
        {
            double vpu = values[i] / (double)weights[i];
            values_per_weights.push_back(make_tuple(vpu, weights[i]));
        }

    // sort the values per weights vector
    sort(values_per_weights.rbegin(), values_per_weights.rend());

    // go through the sorted items and add as much of the maximal item
    for(unsigned int i = 0; i < values_per_weights.size(); i++)
        {
            if(total_capacity <= 0)
                break;

            tuple<double, double> current_max = values_per_weights[i];
            const double vpw_max = get<0>(current_max);
            const double weight_max
                = min((get<1>(current_max)), total_capacity);

            value += (weight_max * vpw_max);
            total_capacity -= weight_max;
        }

    return value;
}

int get_most_weighted_value(vector<int> &weights, vector<int> &values,
                            vector<int> &included)
{
    int N = weights.size();
    int max_index = -1;
    for(int i = 0; i < N; i++)
        {
            if(weights[i] <= 0 || included[i] || i == max_index)
                continue;

            else if(max_index < 0)
                {
                    max_index = i;
                }

            else
                {
                    double vpu1
                        = values[max_index] / (double)weights[max_index];
                    double vpu2 = values[i] / (double)weights[i];
                    if(vpu1 < vpu2)
                        {
                            max_index = i;
                        }
                }
        }

    return max_index;
}

bool equal_values(double value1, double value2, double precision)
{
    return abs(value1 - value2) < pow(10, -precision);
}

void stress_test_get_optimal_value()
{
    srand(time(NULL));
    unsigned int test_counter = 0;
    int N_LIMIT = 1000;
    int CAPACITY_LIMIT = 2000000;
    int WEIGHT_LIMIT = 2000000;
    int VALUE_LIMIT = 2000000;

    while(true)
        {
            const int n = (rand() % N_LIMIT) + 1;
            const int capacity = (rand() % CAPACITY_LIMIT);
            vector<int> weights;
            vector<int> values;
            // instantiate the weights and values
            for(int i = 0; i < n; i++)
                {
                    weights.push_back((rand() % WEIGHT_LIMIT) + 1);
                    values.push_back((rand() % VALUE_LIMIT) + 1);
                }

            double naive_answer
                = get_optimal_value_naive(capacity, weights, values);
            double fast_answer = get_optimal_value(capacity, weights, values);

            if(!equal_values(naive_answer, fast_answer, 4))
                {
                    cout << "weights: ";
                    for(int w : weights)
                        {
                            cout << w << " ";
                        }
                    cout << "\n";

                    cout << "values: ";
                    for(int v : values)
                        {
                            cout << v << " ";
                        }
                    cout << "\n";

                    std::cout << "Fractional Knapsack of n: " << n
                              << "  answer: " << naive_answer
                              << "  result: " << fast_answer
                              << " capacity: " << capacity << "\n";

                    break;
                }

            test_counter++;
            if(test_counter % 1000 == 0)
                {
                    std::cout << "TEST " << test_counter << "  PASSED\n";
                }
        }
}