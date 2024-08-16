import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayDeque;

public class TernarySearchTree<Value> {
    private int n;
    private Node root = new Node();

    private class Node {
        private Node middle, left, right;
        private Value val;
        private char ch;
    }

    public void put(String key, Value val) {
        root = put(root, key, val, 0);
        n++;
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

    public Iterable<String> keys() {
        ArrayDeque<String> queue = new ArrayDeque<>();
        collect(root, "", queue);
        return queue;
    }

    private void collect(Node x, String prefix, ArrayDeque<String> q) {
        if (x == null)      return;
        if (x.val != null)  q.addLast(prefix + x.ch);   // add last character

        collect(x.left, prefix, q);
        collect(x.middle, prefix + x.ch, q);
        collect(x.right, prefix, q);
    }

    public int size() {
        return n;
    }


    public static void main(String[] args) {

        // build symbol table from standard input
        TernarySearchTree<Integer> st = new TernarySearchTree<Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }

        // print results
        if (st.size() < 100) {
            StdOut.println("keys(\"\"):");
            for (String key : st.keys()) {
                StdOut.println(key + " " + st.get(key));
            }
            StdOut.println();
        }
    }
}
