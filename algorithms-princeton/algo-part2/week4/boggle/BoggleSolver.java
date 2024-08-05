import java.util.*;

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
        return computeValidWords(board);
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (dictionary.containsWord(word))  return scores[word.length()];
        else                                return 0;
    }

    private HashSet<String> computeValidWords(BoggleBoard board) {
        int height = board.rows();
        int width = board.cols();
        int totalTiles = height * width;

        // identify the valid paths from and to all vertices (just simple paths)
        ArrayDeque<LinkedList<Integer>> validPaths = new ArrayDeque<>();
        for (int i = 0; i < totalTiles; i++) {

            for (int j = 0; j < totalTiles; j++) {
                if (i != j) addValidPaths(board, i, j, validPaths);
            }
        }

        // convert the valid paths to a word, discard paths that doesn't form a valid word
        HashSet<String> validWords = new HashSet<>();
        while (!validPaths.isEmpty()) {
            validWords.add(pathToWord(board, validPaths.removeFirst()));
        }

        return validWords;
    }

    // identify the valid paths that satisfy the following constrains
    //  - a simple path between index u and v (which corresponds to a tile in the board)
    //  - a path of index corresponding to a word in the dictionary
    //      (prune search if the current path is not a prefix of any word in the dictionary)
    private void addValidPaths(BoggleBoard board, int u, int v, ArrayDeque<LinkedList<Integer>> validPaths) {
        // precompute the adjacent tiles of all tiles in the board
        int totalTiles = board.rows() * board.cols();
        HashMap<Integer, LinkedList<Integer>> adjacentTiles = new HashMap<>();
        for (int i = 0; i < totalTiles; i++)
            adjacentTiles.put(i, adjacentTiles(board, i));

        // dfs from vertex u to vertex v to search all valid paths
        boolean[] visited = new boolean[totalTiles];

        // TODO implement pathfinding and prune
    }


    //  precompute this for every tile, there is no need to calculate again
    private LinkedList<Integer> adjacentTiles(BoggleBoard board, int u) {
        LinkedList<Integer> adj = new LinkedList<>();

        int width = board.cols();
        int row = u / width;
        int col = u % width;

        if (row > 0) {
            adj.addLast(((row - 1) * width) + col);                                     // direct top
            if (col > 0)            adj.addLast(((row - 1) * width) + (col - 1));       // top-left
            if (col + 1 < width)    adj.addLast(((row - 1) * width) + (col + 1));       // top-right
        }

        if (col > 0)                adj.addLast((row * width) + (col - 1));             // left
        if (col + 1 < width)        adj.addLast((row * width) + (col + 1));             // right

        if (row + 1 < board.rows()) {
            adj.addLast(((row + 1) * width) + col);                                     // direct bottom
            if (col > 0)            adj.addLast(((row + 1) * width) + (col - 1));       // bottom-left
            if (col + 1 < width)    adj.addLast(((row + 1) * width) + (col + 1));       // bottom-right
        }

        return adj;
    }


    private String pathToWord(BoggleBoard board, Iterable<Integer> path) {
        StringBuilder word = new StringBuilder();
        for (int i : path) {
            int row = i / board.cols();
            int col = i % board.cols();

            word.append(board.getLetter(row, col));
        }
        return word.toString();
    }
}