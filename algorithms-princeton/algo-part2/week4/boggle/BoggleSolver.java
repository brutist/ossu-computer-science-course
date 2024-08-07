import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.HashSet;

public class BoggleSolver {
    private static final int[] scores = {0, 0, 0, 1, 1, 2, 3, 5};   // 8-letter or more gains 11 points
    private static final int maximumScore = 11;
    private static final int maximumScoreLength = 8;
    private static final int minimumWordLength = 3;
    private final WordTrie dictionary;
    private BoggleBoard board;
    private HashSet<String> validWords;
    private Bag<Integer>[] adjacentTiles;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        this.dictionary = new WordTrie();
        for (String word : dictionary)
            this.dictionary.put(word);
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        this.board = board;
        int totalTiles = board.rows() * board.cols();

        // precompute the adjacent tiles of all tiles in the board
        adjacentTiles = (Bag<Integer>[]) new Bag[totalTiles];
        for (int k = 0; k < totalTiles; k++)
            adjacentTiles[k] = adjacentTiles(k);


        validWords = new HashSet<>();
        // dfs from vertex u to vertex v to search all valid paths
        for (int i = 0; i < totalTiles; i++)
            for (int j = 0; j < totalTiles; j++) {
                boolean[] visited = new boolean[totalTiles];
                if (i != j)     DFS(i, j, new StringBuilder(), visited);
            }

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

    // identify the valid paths that satisfy the following constrains
    //  - a simple path between index u and v (which corresponds to a tile in the board)
    //  - a path of index corresponding to a word in the dictionary
    //      (prune search if the current path is not a prefix of any word in the dictionary)
    private void DFS(int u, int v, StringBuilder word, boolean[] visited) {
        if (visited[u]) return;

        visited[u] = true;

        String curChar;
        char c = board.getLetter(u / board.cols(), u % board.cols());
        if (c == 'Q')   curChar = "QU";
        else            curChar = String.valueOf(c);
        String nextWord = word.append(curChar).toString();

        if (u == v) {
            if (nextWord.length() >= minimumWordLength && dictionary.containsWord(nextWord)) {
                validWords.add(nextWord);
            }
        }

        else {
            for (int next : adjacentTiles[u]) {
                // do not do dfs on paths that
                //      don't form prefix of a valid word
                //      paths that may contain duplicates
                char n = board.getLetter(next / board.cols(), next % board.cols());
                String increasedWord = word.toString() + n;
                if (!visited[next] && dictionary.wordPrefix(increasedWord)) {
                    DFS(next, v, word, visited);
                }
            }
        }

        visited[u] = false;
        for (int i = 0; i < curChar.length(); i++) {
            word.deleteCharAt(word.length() - 1);
        }
    }

    //  precompute this for every tile, there is no need to calculate again
    private Bag<Integer> adjacentTiles(int u) {
        Bag<Integer> adj = new Bag<>();

        int width = board.cols();
        int row = u / width;
        int col = u % width;

        if (row > 0) {
            adj.add(((row - 1) * width) + col);                                     // direct top
            if (col > 0)            adj.add(((row - 1) * width) + (col - 1));       // top-left
            if (col + 1 < width)    adj.add(((row - 1) * width) + (col + 1));       // top-right
        }

        if (col > 0)                adj.add((row * width) + (col - 1));             // left
        if (col + 1 < width)        adj.add((row * width) + (col + 1));             // right

        if (row + 1 < board.rows()) {
            adj.add(((row + 1) * width) + col);                                     // direct bottom
            if (col > 0)            adj.add(((row + 1) * width) + (col - 1));       // bottom-left
            if (col + 1 < width)    adj.add(((row + 1) * width) + (col + 1));       // bottom-right
        }

        return adj;
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