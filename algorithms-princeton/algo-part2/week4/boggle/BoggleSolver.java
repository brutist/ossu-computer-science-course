import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;

public class BoggleSolver {
    private static final int[] scores = {0, 0, 0, 1, 1, 2, 3, 5, 11, 11, 11, 11, 11, 11, 11, 11, 11};
    private ArrayDeque<String> validWords;
    private final HashSet<String> dictionary;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        validWords = new ArrayDeque<>();
        this.dictionary = new HashSet<>();
        this.dictionary.addAll(Arrays.asList(dictionary));
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {

    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (dictionary.contains(word))  return scores[word.length()];
        else                            return 0;
    }
}