public class TernarySearchTree<Value> {
    private Node root = new Node();

    private class Node {
        private Node middle;
        private Node left;
        private Node right;
        private char ch;
        private Value val;
    }

    public void put(String key, Value val) {
        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Value val, int d) {
        // create a node if x is null and associate it with the char at d
        if (x == null) {
            x = new Node();
            x.ch = key.charAt(d);
        }
        if (d == key.length())              x.val = val;
        else if (key.charAt(d) == x.ch)     x.middle = put(x.middle, key, val, d + 1);
        else if (key.charAt(d) < x.ch)      x.left = put(x.left, key, val, d);
        else if (key.charAt(d) > x.ch)      x.right = put(x.right, key, val, d);
        return x;
    }

    public boolean contains(String key) {

    }

    public Value get(String key) {

    }
}
