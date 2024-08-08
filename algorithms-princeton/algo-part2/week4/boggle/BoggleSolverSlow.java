import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.HashSet;
import java.util.LinkedList;

public class BoggleSolverSlow {
    private static final int[] scores = {0, 0, 0, 1, 1, 2, 3, 5};   // 8-letter or more gains 11 points
    private static final int maximumScore = 11;
    private static final int maximumScoreLength = 8;
    private final WordTrie dictionary;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolverSlow(String[] dictionary) {
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
        BoggleSolverSlow slowSolver = new BoggleSolverSlow(dictionary);

        for (int i = 1; i < args.length; i++) {
            BoggleBoard board = new BoggleBoard(args[i]);

            LinkedList<String> allWords = new LinkedList<>();
            LinkedList<String> wordsFound = new LinkedList<>();
            for (String w : slowSolver.getAllValidWords(board))
                allWords.add(w);

            for (String word : solver.getAllValidWords(board))
                wordsFound.add(word);

            LinkedList<String> missingWords = new LinkedList<>();
            LinkedList<String> extraWords = new LinkedList<>();
            int allWordsScore = 0;
            int wordsFoundScore = 0;
            for (String w : wordsFound) {
                wordsFoundScore += solver.scoreOf(w);
                if (!allWords.contains(w))
                    extraWords.add(w);
            }

            for (String w : allWords) {
                allWordsScore += solver.scoreOf(w);
                if (!wordsFound.contains(w))
                    missingWords.add(w);
            }

            StdOut.printf("Filename '%s'    Score = %d\n", args[i], allWordsScore);
            StdOut.printf("Solver score '%d' \n", wordsFoundScore);
            StdOut.printf("Words not found  '%s' \n", missingWords);
            StdOut.printf("Extra words found  '%s' \n\n", extraWords);
        }

        System.out.printf("Total Time: %f\n\n", stopwatch.elapsedTime());


        // speedtest
        Stopwatch timer = new Stopwatch();
        BoggleSolver fastSolver = new BoggleSolver(dictionary);
        int count = 0;
        while (count < 30000)
        {
            BoggleBoard board = new BoggleBoard();
            fastSolver.getAllValidWords(board);
            count++;
        }
        double time = timer.elapsedTime();
        double solPerSec = Math.floor(30000 / time * 100) / 100;
        double ratio = Math.floor(6175.83 / solPerSec * 100) / 100;
        StdOut.println("Total Time for 30000 random board is " + timer.elapsedTime());
        StdOut.println("reference solution per second is 6175.83");
        StdOut.println("student solution per second is " + solPerSec);
        StdOut.println("reference/student ratio is " + ratio);
    }
}