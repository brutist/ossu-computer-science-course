import edu.princeton.cs.algs4.Queue;

public class BinarySearchTree<Key extends Comparable<Key>, Value> {

    private Node root;

    // a parent node key is always bigger than its left child's key and larger
    //  than its right child's key
    private class Node {
        private final Key key;
        private Value val;
        private Node left, right;
        private int count;

        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
            this.count = 1;
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
        if (cmp < 0)        node.left = put(node.left, key, val);
        else if (cmp > 0)   node.right = put(node.right, key, val);
        else                node.val = val;
        node.count = 1 + size(node.left) + size(node.right);
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

    public Key getMax() {
        if (isEmpty()) {
            throw new IllegalCallerException("cannot call getMax() on empty BST");
        }
        Node node = root;
        while (node.right != null) {
            node = node.right;
        }
        return node.key;
    }

    public Key getMin() {
        if (isEmpty()) {
            throw new IllegalCallerException("cannot call getMin() on empty BST");
        }
        Node node = root;
        while (node.left != null) {
            node = node.left;
        }
        return node.key;
    }

    public Key floor(Key key) {
        // find the node of the floor
        Node node = floor(root, key);
        // if the node is null then return it, otherwise return the node's key
        if (node == null)  return null;
        return node.key;
    }

    private Node floor(Node x, Key key) {
        // if node is null then there is no floor
        if (x == null) return null;

        int cmp = key.compareTo(x.key);
        // the floor of a key can be itself
        if (cmp == 0)     return x;
        // if the key is smaller than the node's key then the floor is in the left subtree
        if (cmp < 0)      return floor(x.left, key);

        // explore right subtree
        // if there is a node's key in the right subtree that is <= key then that's the floor
        // else the previous smaller node's key is the floor
        Node t = floor(x.right, key);
        if (t != null)    return t;
        else              return x;
    }

    public Key ceiling(Key key) {
        Node node = ceiling(root, key);
        if (node == null) return null;
        return node.key;
    }

    private Node ceiling(Node root, Key key) {
        if (root == null)   return null;

        int cmp = key.compareTo(root.key);
        if (cmp == 0)       return root;
        if (cmp > 0)        return ceiling(root.right, key);

        Node t = ceiling(root.right, key);
        if (t != null)      return t;
        else                return root;
    }

    // return the number of keys that are smaller than the given key
    public int rank(Key key) {
        return rank(key, root);
    }

    // an ingenious helper function for rank()
    private int rank(Key key, Node x) {
        if (x == null)   return 0;

        int cmp = key.compareTo(x.key);
        if (cmp > 0)        return 1 + size(x.left) + rank(key, x.right);
        else if (cmp < 0)   return rank(key, x.left);
        else                return size(x.left);
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null)  return 0;
        return x.count;
    }

    public void deleteMin() {
        root = deleteMin(root);
    }

    // deletes the minimum of the current BST
    private Node deleteMin(Node x) {
        if (x.left == null)  return x.right;
        // update the count as well
        x.left = deleteMin(x.left);
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    public void deleteMax() {
        root = deleteMax(root);
    }

    private Node deleteMax(Node x) {
        if (x.right == null)    return x.left;
        // update the count as well
        x.right = deleteMax(x.right);
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    // deletes the key-val pair in the symbol table using Hibbard deletion
    //      a glorious and simple method to delete a node in a BST
    public void delete(Key key) {
        root = delete(root, key);
    }

    private Node delete(Node x, Key key) {
        // trying to delete a non-existent key will lead to nothing
        if (x == null)  return null;

        int cmp = key.compareTo(x.key);
        if      (cmp < 0)    x.left  = delete(x.left, key);
        else if (cmp > 0)    x.right = delete(x.right, key);
        // we've found the key
        else {
            // no right child
            if (x.right == null)  return x.left;
            // no left child
            if (x.left == null)   return x.right;

            // there are two children [left and right]
            // replace with successor
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        // update subtree counts
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    // return the node that contains the minimum key
    private Node min(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // provides an iterable for this symbol table
    public Iterable<Key> keys() {
        Queue<Key> q = new Queue<Key>();
        inorder(root, q);
        return q;
    }

    // another lovely thing to insert all keys into the queue
    // provides keys in ascending order
    private void inorder(Node root, Queue<Key> q) {
        if (root == null)   return;
        inorder(root.left, q);
        q.enqueue(root.key);
        inorder(root.right, q);
    }
}
