#include <iostream>

int get_change_naive(int m) {
    // list of possible denominations
    int denominations[3] = {1, 5, 10};

    int change_sum = 0;
    int num_coins = 0;
    while(m > change_sum)
    {
        int remains = m - change_sum;

        num_coins++;
        if(remains >= denominations[2])
        { change_sum += denominations[2]; }

        else if(remains >= denominations[1])
        { change_sum += denominations[1]; }

        else
        { change_sum += denominations[0]; }
    }

    return num_coins;
}

int get_change(int m) {
    // Approach: Greedily get the coins that would make the maximum
    //			possible change smaller than m for the biggest
    // denomination, 			continue the process with the remainder
    // until the last possible denom. 			Since the smallest
    // denomination is '1' and m is an int, there will 			always
    // be combination of denominations that would equal m.
    int tens = m / 10;
    int tens_remainder = m % 10;

    int fives = tens_remainder / 5;
    int fives_remainder = tens_remainder % 5;

    int ones = fives_remainder;

    return tens + fives + ones; // number of coins to make change m
}

void stress_test_get_change() {
    srand(time(NULL));
    unsigned int test_counter = 0;
    while(true)
    {
        const int m = (rand() % 1000) + 1;
        int naive_answer = get_change_naive(m);
        int fast_answer = get_change(m);

        if(naive_answer != fast_answer)
        {
            std::cout << "get_change of: " << m << "  answer: " << naive_answer
                      << "  result: " << fast_answer << "\n";
            break;
        }

        test_counter++;
        if(test_counter % 1000 == 0)
        { std::cout << "TEST " << test_counter << "  PASSED\n"; }
    }
}

int main() {
    int m;
    std::cin >> m;
    std::cout << get_change(m) << '\n';

    stress_test_get_change();
}
