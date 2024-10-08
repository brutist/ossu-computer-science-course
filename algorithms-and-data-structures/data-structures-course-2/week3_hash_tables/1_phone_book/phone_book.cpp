#include <iostream>
#include <string>
#include <unordered_map>
#include <vector>

using std::cin;
using std::string;
using std::unordered_map;
using std::vector;

struct Query {
    string type, name;
    int number;
};

bool contains_number(unordered_map<int, string> &contacts, int num) {
    return contacts.find(num) != contacts.end();
}

vector<Query> read_queries() {
    int n;
    cin >> n;
    vector<Query> queries(n);
    for (int i = 0; i < n; ++i) {
        cin >> queries[i].type;
        if (queries[i].type == "add")
            cin >> queries[i].number >> queries[i].name;
        else
            cin >> queries[i].number;
    }
    return queries;
}

void write_responses(const vector<string> &result) {
    for (size_t i = 0; i < result.size(); ++i)
        std::cout << result[i] << "\n";
}

vector<string> process_queries(const vector<Query> &queries) {
    vector<string> result;
    // Keep list of all existing (i.e. not deleted yet) contacts.
    unordered_map<int, string> contacts;
    for (size_t i = 0; i < queries.size(); ++i) {
        string name = queries[i].name;
        int num = queries[i].number;
        if (queries[i].type == "add") {
            // if we already have contact with such number,
            // we should rewrite contact's name
            if (contains_number(contacts, num)) 
                contacts[num] = name;
            // otherwise, just add it
            else 
                contacts.insert({num, name});
        }

        else if (queries[i].type == "del") {
            if (!contains_number(contacts, num))
                continue;
            
            contacts.erase(num);
        }

        else {
            string response = "not found";
            if (contains_number(contacts, num)) 
                response = contacts[num];

            result.push_back(response);
        }
    }

    return result;
}

int main() {
    write_responses(process_queries(read_queries()));
    return 0;
}
