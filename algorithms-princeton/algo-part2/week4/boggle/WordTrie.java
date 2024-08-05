public class WordTrie{
    private static final int R = 26;            // only uppercase letters
    private Node root = new Node();
    private static final int adjuster = 65;     // puts 'A' at index 0 of the trie

    private static class Node {
        private boolean isWord;                 // true if the path to this Node is a word in the trie
        private Node[] next = new Node[R];      // characters are implicitly defined by link index
    }

    public void put(String key) {
        root = put(root, key, 0);
    }

    private Node put(Node x, String key, int d) {
        // create a new node if this is a new longer key
        if (x == null)              x = new Node();
        if (d == key.length())      return x;


        // calculate the index of the char at d
        char c = key.charAt(d);     // apparently chars are implicitly converted to int
        x.next[c - adjuster] = put(x.next[c - adjuster], key, d + 1);
        return x;
    }

    public boolean wordPrefix(String key) {
        Node x = get(root, key, 0);
        return x != null;
    }

    public boolean containsWord(String key) {
        Node x = get(root, key, 0);
        if (x == null)  return false;
        else            return x.isWord;
    }

    // get the value associated with the key in the trie
    private Node get(Node x, String key, int d) {
        if (x == null)              return null;
        if (key.length() == d) {
            x.isWord = true;
            return x;
        }

        char c = key.charAt(d);
        return get(x.next[c - adjuster], key, d + 1);
    }
}
