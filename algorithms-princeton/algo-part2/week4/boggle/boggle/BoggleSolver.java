import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.HashSet;

public class BoggleSolver {
    private static final int[] scores = {0, 0, 0, 1, 1, 2, 3, 5, 11, 11, 11, 11, 11, 11, 11, 11, 11};
    private final WordTrie dictionary;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        this.dictionary = new WordTrie();
        for (String word : dictionary)
            this.dictionary.put(word);
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        int totalTiles = board.rows() * board.cols();

        // calculate the valid words produced from one tile to another,
        //  accumulate as you iterate through all pairs of tiles
        HashSet<String> validWords = new HashSet<>();
        WordFinder wordFinder = new WordFinder(board, dictionary);
        for (int i = 0; i < totalTiles; i++)
            for (int j = 0; j < totalTiles; j++)
                if (i != j)     validWords.addAll(wordFinder.findValidWord(i,j));

        return validWords;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (dictionary.containsWord(word))  return scores[word.length()];
        else                                return 0;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}