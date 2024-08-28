#include <algorithm>
#include <iostream>
#include <vector>

int64_t MaxPairwiseProduct(const std::vector<int64_t> &numbers) {
    int64_t max_product = 0;
    int n = numbers.size();

    for(int i = 0; i < n; ++i)
    {
        for(int j = 0; j < n; j++)
        {
            if(i != j)
            { max_product = std::max(max_product, numbers[i] * numbers[j]); }
        }
    }

    return max_product;
}

int64_t MaxPairwiseProductFast(const std::vector<int64_t> &numbers) {
    unsigned int max = 0;
    unsigned int s_max = 0;
    for(unsigned int i = 0; i < numbers.size(); i++)
    {
        if(numbers[max] < numbers[i])
        {
            unsigned int temp = max;
            max = i;
            s_max = temp;
        }
        else if(max == s_max || numbers[s_max] < numbers[i])
        { s_max = i; }
    }

    return numbers[max] * numbers[s_max];
}

const std::vector<int64_t> randomNumbers(int n) {
    srand(time(NULL));
    std::vector<int64_t> numbers(n);
    for(int i = 0; i < n; ++i)
    { numbers[i] = rand() % 2000000; }
    return numbers;
}

int main() {
    while(true)
    {
        const std::vector<int64_t> numbers = randomNumbers(200000);
        int64_t answer = MaxPairwiseProduct(numbers);
        int64_t result = MaxPairwiseProductFast(numbers);
        if(answer != result)
        {
            std::cout << "Answer: " << answer << "   Result: " << result
                      << "\n";
            for(int64_t i : numbers)
                std::cout << i << " ";
            return 1;
        }
    }
    return 0;
}
