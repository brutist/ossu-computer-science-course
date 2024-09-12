#include <cstdlib>
#include <iostream>

// pick a random number from 0-99
// guess the number by stdin
//  if guess is correct => You have won
//  if guess < target   => guess is smaller
//  if guess > target   => guess is larger
int main() {
    // Start message
    std::cout << "Guess the number from 0 - 99" << std::endl;

    srand(time(NULL));
    int random_num = std::rand() % 100;
    bool guessed = false;
    while (!guessed) {
        int guess;
        std::cin >> guess;

        if (guess == random_num) {
            std::cout << "You have won!" << std::endl;
            guessed = true;
        } else if (guess < random_num) {
            std::cout << "Your guess is smaller than the number!" << std::endl;
        }

        else {
            std::cout << "Your guess is bigger than the number!" << std::endl;
        }
    }

    return 0;
}