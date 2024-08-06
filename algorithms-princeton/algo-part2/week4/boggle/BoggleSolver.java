import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.HashSet;

public class BoggleSolver {
    private static final int[] scores = {0, 0, 0, 1, 1, 2, 3, 5};   // 8-letter or more gains 11 points
    private static final int maximumScore = 11;
    private static final int maximumScoreLength = 8;
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
        WordFinder wordFinder = new WordFinder(board);
        for (int i = 0; i < totalTiles; i++)
            for (int j = 0; j < totalTiles; j++)
                if (i != j)     validWords.addAll(wordFinder.findValidWord(i,j, dictionary));

        return validWords;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (dictionary.containsWord(word))  {
            if (word.length() < maximumScoreLength)     return scores[word.length()];
            else                                        return maximumScore;
        }

        return 0;
    }

    public static void main(String[] args) {
        Stopwatch stopwatch = new Stopwatch();

        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);

        for (int i = 1; i < args.length; i++) {
            BoggleBoard board = new BoggleBoard(args[i]);
            int score = 0;
            for (String word : solver.getAllValidWords(board)) {
                score += solver.scoreOf(word);
            }
            StdOut.printf("Filename '%s'    Score = %d\n", args[i], score);
        }

        System.out.printf("Total Time: %f\n", stopwatch.elapsedTime());
    }
}