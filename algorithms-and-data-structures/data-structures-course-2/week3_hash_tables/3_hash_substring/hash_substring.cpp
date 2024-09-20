#include <cmath>
#include <iostream>
#include <string>
#include <vector>

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

size_t hash_func(const string &s) {
    static const size_t multiplier = 263;
    static const size_t prime = 1000000007;
    unsigned long long hash = 0;
    for (int i = static_cast<int>(s.size()) - 1; i >= 0; --i)
        hash = (hash * multiplier + s[i]) % prime;
    return hash;
}

void print_occurrences(const std::vector<int> &output) {
    for (size_t i = 0; i < output.size(); ++i)
        std::cout << output[i] << " ";
    std::cout << "\n";
}

ull base_raise_to(int B, size_t P, size_t prime) {
    ull h = 1;
    for (size_t i = 0; i < P; i++) {
        h = (h * B) % prime;
    }
    return h;
}

std::vector<size_t> precompute_hashes(const Data &input, size_t p, size_t b) {
    // Approach: Calculate the hash value of the pattern. Calculate
    //      the hash value of the fist substring of the text that is
    //      the same length as pattern. Slide over the text and
    //      calculate the hash value of hash(i+1) from hash(i) by
    //      subracting the contribution of text[i] and adding the
    //      contribution of text[i + 1 + pattern_length] to the hash.
    int T = input.text.size();
    int P = input.pattern.size();
    std::vector<size_t> H(T - P + 1);
    H[T - P] = hash_func(input.text.substr(T - P, P));
    ull y = base_raise_to(b, P, p);
    
    for (int i = T - P - 1; i >= 0; i--) {
        H[i] = ((b * H[i + 1]) + input.text[i] - (y * input.text[i + P])) % p;
    }

    return H;
}

std::vector<int> get_occurrences(const Data &input) {
    // choose suitable (B)ase and (P)rime
    size_t B = 263;
    size_t P = 1000000007;
    std::vector<int> occurrences;
    ull pattern_hash = hash_func(input.pattern);
    // precalculate the hashes for substring from 0 to txt[i - len(pattern)]
    std::vector<size_t> hashes = precompute_hashes(input, P, B);
    for (size_t i = 0; i < hashes.size(); i++) {
        // check if substring is really the same as the pattern or just a
        //  hash collision
        if (hashes[i] == pattern_hash) {
            if (input.text.substr(i, input.pattern.size()) == input.pattern)
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
