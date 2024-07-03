public class BinarySearchTree<Key extends Comparable<Key>, Value> {

    private Node root;

    // a parent node key is always bigger than its left child's key and larger
    //  than its right child's key
    private class Node {
        private final Key key;
        private Value val;
        private Node left, right;

        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
        }
    }

    public boolean isEmpty() {
        return root == null;
    }

    // add a key-val pair into the symbol table
    //  will overwrite if key already present
    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    // recursive implementation of put, searches for where to add and links the new node
    //  to its parent node until the root [barely understand it]
    // more concise and elegant than the non-recursive that I first came up with
    private Node put(Node node, Key key, Value val) {
        // return a new node in the position that you want to insert the new node
        if (node == null) return new Node(key, val);

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = put(node.left, key, val);
        }
        else if (cmp > 0) {
            node.right = put(node.right, key, val);
        }
        else {
            node.val = val;
        }
        return node;
    }

    // get the value associated with the key
    public Value get(Key key) {
        if (isEmpty()) {
            throw new IllegalCallerException("cannot call get() in an empty symbol table");
        }

        Node node = root;
        while (node != null) {
            if (key.compareTo(node.key) == 0) {
                return node.val;
            }
            else if (key.compareTo(node.key) < 0) {
                node = node.left;
            }
            else {
                node = node.right;
            }
        }
        return null;
    }

    // deletes the key-val pair in the symbol table
    public void delete(Key key) {
        Node node = root;
        while (node != null) {
            // node to be deleted has no child
            if (key.compareTo(node.key) == 0 && node.left == null && node.right == null) {
                node = null;
            }
            // node to be deleted only has right child
            else if (key.compareTo(node.key) == 0 && node.left == null && node.right != null) {
                node = node.right;
            }
            else if (key.compareTo(node.key) == 0 && node.left != null && node.right == null) {
                node = node.left;
            }
            // node to be deleted has both left and right children
            else if (key.compareTo(node.key) == 0) {

            }
        }
    }

    private void reconnect(BinarySearchTree<Key, Value> bst, Node orphan) {

    }

    // provides an iterable for this symbol table
    public Iterable<Key> iterator() {

    }
}
