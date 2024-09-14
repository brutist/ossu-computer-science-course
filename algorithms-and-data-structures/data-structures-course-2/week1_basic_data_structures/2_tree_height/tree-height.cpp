#include <algorithm>
#include <iostream>
#include <vector>
#if defined(__unix__) || defined(__APPLE__)
#include <sys/resource.h>
#endif

class Node;

class Node {
  public:
    int key;
    Node *parent;
    std::vector<Node *> children;

    Node() { this->parent = NULL; }

    void setParent(Node *theParent) {
        parent = theParent;
        parent->children.push_back(this);
    }
};

int max_tree_height(Node *root, int height) {
    if (root->children.size() == 0) {
        return height;
    }

    int max_height = 0;
    for (Node *r : root->children) {
        int child_height = max_tree_height(r, height + 1);
        max_height = std::max(max_height, child_height);
    }

    return max_height;
}

int main_with_large_stack_space() {
    std::ios_base::sync_with_stdio(0);
    int n;
    std::cin >> n;

    std::vector<Node> nodes;
    int root_index;
    nodes.resize(n);
    for (int child_index = 0; child_index < n; child_index++) {
        int parent_index;
        std::cin >> parent_index;

        if (parent_index >= 0)
            nodes[child_index].setParent(&nodes[parent_index]);

        // identify the index of the root node
        else
            root_index = child_index;

        nodes[child_index].key = child_index;
    }

    std::cout << max_tree_height(&nodes[root_index], 1) << std::endl;
    return 0;
}

int main(int argc, char **argv) {
#if defined(__unix__) || defined(__APPLE__)
    // Allow larger stack space
    const rlim_t kStackSize = 16 * 1024 * 1024; // min stack size = 16 MB
    struct rlimit rl;
    int result;

    result = getrlimit(RLIMIT_STACK, &rl);
    if (result == 0) {
        if (rl.rlim_cur < kStackSize) {
            rl.rlim_cur = kStackSize;
            result = setrlimit(RLIMIT_STACK, &rl);
            if (result != 0) {
                std::cerr << "setrlimit returned result = " << result
                          << std::endl;
            }
        }
    }

#endif
    return main_with_large_stack_space();
}
