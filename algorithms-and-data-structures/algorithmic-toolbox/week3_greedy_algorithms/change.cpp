#include <iostream>

int get_change(int m) {
  // list of possible denominations
  int denominations[3] = {1, 5, 10};

  int change_sum = 0;
  int num_coins = 0;
  while (m > change_sum) {
    int remains = m - change_sum;

    num_coins++;
    if (remains >= denominations[2]) {
      change_sum += denominations[2];
    }

    else if (remains >= denominations[1]) {
      change_sum += denominations[1];
    }

    else {
      change_sum += denominations[0];
    }
  }

  return num_coins;
}

int main() {
  int m;
  std::cin >> m;
  std::cout << get_change(m) << '\n';
}
