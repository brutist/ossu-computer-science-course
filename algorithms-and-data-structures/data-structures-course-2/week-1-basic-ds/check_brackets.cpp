#include <iostream>
#include <stack>
#include <string>

using std::cout;

struct Bracket {
    Bracket(char type, int position) : type(type), position(position) {}

    bool Matchc(char c) {
        if (type == '[' && c == ']')
            return true;

        if (type == '{' && c == '}')
            return true;

        if (type == '(' && c == ')')
            return true;

        return false;
    }

    char type;
    int position;
};

int main() {
    std::string text;
    getline(std::cin, text);

    std::stack<Bracket> opening_brackets_stack;
    bool match_success = true;
    int fail_position;
    for (int position = 0; position < text.size(); ++position) {
        char next = text[position];

        if (next == '(' || next == '[' || next == '{') {
            // case 1: opening bracket, push to stack
            opening_brackets_stack.push(Bracket(next, position));
        }

        if (next == ')' || next == ']' || next == '}') {
            // case 2: closing bracket but no opening brackets
            //       left, fail test
            if (opening_brackets_stack.empty()) {
                match_success = false;
                fail_position = position + 1;
            }

            // case 3: closing bracket but not matched,
            //      fail test
            Bracket prev = opening_brackets_stack.top();
            opening_brackets_stack.pop();
            if (!prev.Matchc(next)) {
                match_success = false;
                fail_position = position + 1;
            }
        }

        // check if test has failed
        if (!match_success) {
            cout << fail_position;
            break;
        }
    }

    if (opening_brackets_stack.empty() && match_success) {
        cout << "Success";
    }

    return 0;
}
