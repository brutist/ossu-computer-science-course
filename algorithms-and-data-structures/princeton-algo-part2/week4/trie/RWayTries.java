public class RWayTries<Value> {
    private static final int R = 256;   // extended ASCII
    private Node root = new Node();

    private static class Node {
        private Object value;                   // use Objects since no generic array in java
        private Node[] next = new Node[R];      // characters are implicitly defined by link index

    }

    public void put(String key, Value val) {
        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Value val, int d) {
        // create a new node if this is a new longer key
        if (x == null)    x = new Node();
        if (d == key.length()) {
            x.value = val;
            return x;
        }

        // calculate the index of the char at d
        char c = key.charAt(d);     // apparently chars are implicitly converted to int
        x.next[c] = put(x.next[c], key, val, d + 1);
        return x;
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

    public Value get(String key) {
        Node x = get(root, key, 0);
        if (x == null)  return null;
        return (Value) x.value;     // cast necessary because x.values can only be objects
    }

    // get the value associated with the key in the trie
    private Node get(Node x, String key, int d) {
        if (x == null)              return null;
        if (key.length() == d)      return x;

        char c = key.charAt(d);
        return get(x.next[c], key, d + 1);
    }

}
