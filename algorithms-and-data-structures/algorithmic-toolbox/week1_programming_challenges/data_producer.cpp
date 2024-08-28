#include <fstream>
#include <iostream>

int main() {
    int n = 200000;
    srand(time(NULL));

    // create and open a text file
    std::ofstream fn("dataset.txt");

    fn << n << "\n";

    // writer to the file
    for(int i = 0; i < n; i++)
    { fn << std::rand() << " "; }

    fn.close();

    return 0;
}