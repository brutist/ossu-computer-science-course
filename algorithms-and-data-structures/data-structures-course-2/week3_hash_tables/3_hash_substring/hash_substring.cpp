#include <iostream>
#include <string>
#include <vector>
#include <cmath>

using std::string;
typedef unsigned long long ull;

struct Data {
    string pattern, text;
};

Data read_input() {
    Data data;
    std::cin >> data.pattern >> data.text;
    return data;
}

ull hash_func(const string &s, ull multiplier, ull prime) {
    ull hash = 0;
    for (int i = static_cast<int>(s.size()) - 1; i >= 0; --i)
        hash = (hash * multiplier + s[i]) % prime;
    return hash;
}

void print_occurrences(const std::vector<int> &output) {
    for (size_t i = 0; i < output.size(); ++i)
        std::cout << output[i] << " ";
    std::cout << "\n";
}

std::vector<int> get_occurrences(const Data &input) {
    // Approach: Calculate the hash value of the pattern. Calculate
    //      the hash value of the fist substring of the text that is
    //      the same length as pattern. Slide over the text and
    //      calculate the hash value of hash(i+1) from hash(i) by
    //      subracting the contribution of text[i] and adding the
    //      contribution of text[i + 1 + pattern_length] to the hash.

    // choose suitable base and modulo
    ull B = 263;
    ull P = 1000000007;

    // calculate the hash value of the pattern
    ull pattern_hash = hash_func(input.pattern, B, P);

    // calculate the hash value of the first substring of the text
    //  that is the same length as pattern
    size_t pattern_length = input.pattern.size();
    std::vector<int> occurrences;
    ull hash;
    for (size_t i = 0; i + pattern_length < input.text.size(); i++) {
        // calculate the first substring hash
        if (i == 0) {
            string first_substring = input.text.substr(i, pattern_length);
            hash = hash_func(first_substring, B, P);
        }

        // update hash[i+1] by subtracting contribution of text[i] and adding
        //  contribution of text[i + 1 +  pattern_length]
        else {
            hash = (hash - (input.text[i - pattern_length] * (std::pow(B, pattern_length - 1))) % P) * B + input.text[i];
        }

        // check if substring is really the same as the pattern or just a
        //  hash collision
        if (hash == pattern_hash &&
            input.text.substr(i, pattern_length) == input.pattern) {
            occurrences.push_back(i);
        }
    }

    return occurrences;
}

int main() {
    std::ios_base::sync_with_stdio(false);
    print_occurrences(get_occurrences(read_input()));
    return 0;
}
