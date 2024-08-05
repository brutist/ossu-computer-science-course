import java.util.ArrayDeque;

public class WordTrie{
    private static final int R = 26;            // only uppercase letters
    private Node root = new Node();             // root node
    private static final int adjuster = 65;     // puts 'A' at index 0 of the trie

    private static class Node {
        private boolean isWord;                  // true if the path to this Node is a word in the trie
        private final Node[] next = new Node[R]; // characters are implicitly defined by link index
    }

    public void put(String key) {
        root = put(root, key, 0);
    }

    private Node put(Node x, String key, int d) {
        // create a new node if this is a new longer key
        if (x == null)              x = new Node();
        if (d == key.length()) {
            x.isWord = true;
            return x;
        }


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
        if (x == null)     return false;
        else               return x.isWord;
    }

    // get the node associated with the key in the trie
    private Node get(Node x, String key, int d) {
        if (x == null)              return null;
        if (key.length() == d)      return x;


        char c = key.charAt(d);
        return get(x.next[c - adjuster], key, d + 1);
    }

    public Iterable<String> allWords() {
        ArrayDeque<String> words = new ArrayDeque<>();
        collect(root, new StringBuilder(), words);
        return words;
    }

    private void collect(Node x, StringBuilder word, ArrayDeque<String> results) {
        if (x == null)  return;
        if (x.isWord)   results.addLast(word.toString());

        for (char c = 0; c < R; c++) {
            char letter = (char) ((int) c + adjuster);
            word.append(letter);
            collect(x.next[c], word, results);
            word.deleteCharAt(word.length() - 1);
        }
    }
}
