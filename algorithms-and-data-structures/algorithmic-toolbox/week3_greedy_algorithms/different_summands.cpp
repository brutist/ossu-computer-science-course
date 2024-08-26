#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

vector<int> optimal_summands(int n);
vector<int> optimal_summands_naive(int n);
void stress_test_optimal_summands();


int main()
{
	int n;
	std::cin >> n;
	vector<int> summands = optimal_summands(n);
	std::cout << summands.size() << '\n';
	for (size_t i = 0; i < summands.size(); ++i)
	{
		std::cout << summands[i] << ' ';
	}

	stress_test_optimal_summands();
}

vector<int> optimal_summands_naive(int n)
{
	// Approach: Pick the smallest summand possible (+1) such that the previous summand <
	//			than the current summand. If remaining n is smaller than the next
	//			summand, then last summand will be remaining n.

	int curr_prize = 1;
	vector<int> summands;
	while (n > 0)
	{
		if (n - curr_prize < curr_prize + 1)
		{
			summands.push_back(n);
			break;
		}

		summands.push_back(curr_prize);
		n -= curr_prize;
		curr_prize++;
	}

	return summands;
}

vector<int> optimal_summands(int n)
{
	// Approach: Sum of n natural numbers n is equal to (k (k+1)) / 2.
	//			We can use this formula to speed up the algorithm from O(n)
	//			O(k), in which k is the number of summands. Which would 
	//			also correspond to the minimum number of summands of n.
	vector<int> summands;
	if (n <= 0)	return summands;

	int k = 1;
	int sum = 0;
	int next_sum = ((k + 1) * (k + 2)) / 2;
	while (next_sum < n) {
		int next_summand = k + 1;
		next_sum = ((k + 1) * (k + 2)) / 2;

		// terminate if the next sum is going to exceed n
		if (next_sum > n) {
			summands.push_back(n - sum);
			break;
		}

		// keep adding +1 to the summand as long as the next_sum allows it
		//	keep track of the current summand
		else {
			summands.push_back(k);
			sum = (k * (k + 1)) / 2;;
			k = next_summand;
		}
	}

	return summands;
}

void stress_test_optimal_summands()
{
	srand(time(NULL));
	unsigned int test_counter = 0;
	int N_LIMIT = 10;

	while (true)
	{
		const int n = (rand() % N_LIMIT) + 1;
		
		vector<int> naive_answer = optimal_summands_naive(n);
		vector<int> fast_answer = optimal_summands(n);

		if (naive_answer != fast_answer)
		{
			for (int i : fast_answer) {
				cout << i << " ";
			}
			cout << "\n";

			std::cout << "Optimal Summands of n: " << n << "  answer: "
					  << naive_answer.size() << "  result: " << fast_answer.size() << "\n";

			break;
		}

		test_counter++;
		if (test_counter % 1000 == 0)
		{
			std::cout << "TEST " << test_counter << "  PASSED\n";
		}
	}
}
