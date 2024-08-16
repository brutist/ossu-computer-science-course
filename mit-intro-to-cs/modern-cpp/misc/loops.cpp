#include <iostream>
#include <vector>

int main(int argc, char const *argv[]) {
    const int counter = 10;
    std::vector<int> vec = {1, 2, 4, 4, 5};
    for (int i = 0; i < vec.size(); i++)
        std::cout << vec[i] << std::endl;

    for (int i : vec)
        std::cout << i << std::endl;

    // this is a reference to the original vec
    for (const auto& i : vec)
        std::cout <<  i << std::endl;
}