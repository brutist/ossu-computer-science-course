public class TernarySearchTree<Value> {
    private Node root = new Node();

    private class Node {
        private Node middle, left, right;
        private Value val;
        private char ch;
    }

    public void put(String key, Value val) {
        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Value val, int d) {
        // create a node if x is null and associate it with the char at d
        char c = key.charAt(d);
        if (x == null) {
            x = new Node();
            x.ch = c;
        }
        // an example of gloriously covering all conditions
        if      (c < x.ch)                  x.left = put(x.left, key, val, d);
        else if (c > x.ch)                  x.right = put(x.right, key, val, d);
        else if (d < key.length() - 1)      x.middle = put(x.middle, key, val, d + 1);
        else                                x.val = val;
        return x;
    }

    public boolean contains(String key) {
        return get(root, key, 0) != null;
    }

    public Value get(String key) {
        Node result = get(root, key, 0);
        if (result == null)     return null;
        else                    return result.val;
    }

    private Node get(Node x, String key, int d) {
        char c = key.charAt(d);
        // search is a miss if we reach a null node
        if (x == null)                      return null;

        // still using that glorious use of conditionals in put method
        if      (c < x.ch)                  return get(x.left, key, d);
        else if (c > x.ch)                  return get(x.right, key, d);
        else if (d < key.length() - 1)      return get(x.middle, key, d + 1);
        else                                return x;
    }
}
