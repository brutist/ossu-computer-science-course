#include <algorithm>
#include <sstream>
#include <iostream>
#include <vector>
#include <string>

using namespace std;

bool concatenate_sort(const string& A, const string& B);
string largest_number(vector<string> a);


int main() {
	int n;
	std::cin >> n;
	vector<string> a(n);
	for (size_t i = 0; i < a.size(); i++) {
		std::cin >> a[i];
	}
	std::cout << largest_number(a);
}


string largest_number(vector<string> a) {
	sort(a.rbegin(), a.rend(), &concatenate_sort);

	string result = "";
	for (string s : a) {
		result += s;
	}

	return result;
}

bool concatenate_sort(const string& A, const string& B) {
	return (A + B) < (B + A);
}