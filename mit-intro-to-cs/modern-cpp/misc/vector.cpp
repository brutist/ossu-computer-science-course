#include <vector>
#include <iostream>

int main(int argc, char const *argv[]) {
    std::vector<int> vec = {1, 3, 2, 4};
    std::cout << vec.front() << " " << vec.back() << std::endl;
    const std::string myString = "";
    return 0;
}