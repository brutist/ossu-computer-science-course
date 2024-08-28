#include <cstdlib>
#include <iostream>

int gcd_naive(int a, int b)
{
    int current_gcd = 1;
    for(int d = 2; d <= a && d <= b; d++)
        {
            if(a % d == 0 && b % d == 0)
                {
                    if(d > current_gcd)
                        {
                            current_gcd = d;
                        }
                }
        }
    return current_gcd;
}

// an implementation of Euclid GCD algorithm
int gcd_fast(int a, int b)
{
    if(b == 0)
        return a;

    int remainder_a = a % b;
    return gcd_fast(b, remainder_a);
}

int stress_test_gcd()
{
    srand(time(NULL));
    unsigned test_counter = 0;
    while(true)
        {
            int a = std::rand() % 20000000;
            int b = std::rand() % 20000000;

            int naive_answer = gcd_naive(a, b);
            int fast_answer = gcd_fast(a, b);

            if(naive_answer != fast_answer)
                {
                    std::cout << "a: " << a << " b: " << b
                              << " naive: " << naive_answer
                              << "  fast: " << fast_answer;
                }

            test_counter++;
            if(test_counter % 100 == 0)
                {
                    std::cout << "TEST " << test_counter << "  PASSED\n";
                }
        }
}

int main()
{
    int a, b;
    std::cin >> a >> b;
    std::cout << gcd_fast(a, b) << std::endl;

    stress_test_gcd();
    return 0;
}
