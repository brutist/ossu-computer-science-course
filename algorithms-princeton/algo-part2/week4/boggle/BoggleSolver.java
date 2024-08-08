import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.LinkedList;

public class BoggleSolver {
    private Integer[][] adjacentTiles;
    private BoggleBoard board;
    private Node root = new Node();       // root node

    private static class Node {
        private String word;                        // true if the path to this Node is a word in the trie
        private final Node[] next = new Node[26];   // characters are implicitly defined by link index
    }

    private void put(String key) {
        root = put(root, key, 0);
    }

    private Node put(Node x, String key, int d) {
        // create a new node if this is a new longer key
        if (x == null)              x = new Node();
        if (d == key.length()) {
            x.word = key;
            return x;
        }

        // calculate the index of the char at d
        char c = key.charAt(d);     // apparently chars are implicitly converted to int
        x.next[c - 'A'] = put(x.next[c - 'A'], key, d + 1);
        return x;
    }

    private boolean containsWord(String key) {
        Node x = get(root, key, 0);
        return x != null && x.word != null;
    }

    // get the node associated with the key in the trie
    private Node get(Node x, String key, int d) {
        if (x == null)              return null;
        if (key.length() == d)      return x;

        char c = key.charAt(d);
        return get(x.next[c - 'A'], key, d + 1);
    }

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        for (String word : dictionary)
            put(word);
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        this.board = board;
        int totalTiles = board.rows() * board.cols();

        // precompute the adjacent tiles of all tiles in the board
        int possibleNeighbors = 8;
        adjacentTiles = new Integer[totalTiles][possibleNeighbors];
        for (int k = 0; k < totalTiles; k++)
            adjacentTiles[k] = adjacentTiles(k);

        LinkedList<String> validWords = new LinkedList<>();
        boolean[] visited = new boolean[totalTiles];
        // dfs from vertex u to vertex v to search all valid paths, reuse the visited array by resetting
        for (int i = 0; i < totalTiles; i++) {
            for (int j = 0; j < totalTiles; j++) {
                if (i != j) {
                    Node N = getNextNode(root, i);
                    DFS(N, i, j, visited, validWords);
                }
            }
        }

        return validWords;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        int maximumScore = 11;
        int maximumScoreLength = 8;
        if (containsWord(word))  {
            int[] scores = {0, 0, 0, 1, 1, 2, 3, 5};   // 8-letter or more gains 11 points
            if (word.length() < maximumScoreLength)     return scores[word.length()];
            else                                        return maximumScore;
        }

        return 0;
    }

    // identify the valid paths that satisfy the following constrains
    //  - a simple path between index u and v (which corresponds to a tile in the board)
    //  - a path of index corresponding to a word in the dictionary
    //      (prune search if the current path is not a prefix of any word in the dictionary)
    // Approach is to DFS directly the trie containing the dictionary of words. Search for a
    //      possible word for every pair of tiles. The search path that would not be a prefix
    //      of a word will be disregarded since the DFS cannot continue anymore. Backtrack
    //      and find all possible paths
    private void DFS(Node node, int u, int v, boolean[] visited, LinkedList<String> validWords) {
        if (visited[u]) return;

        visited[u] = true;

        if (u == v && node.word != null) {
            int minimumWordLength = 3;      // for a boggle game
            String word = node.word;
            if (word.length() >= minimumWordLength)    validWords.add(word);
        }

        for (Integer next : adjacentTiles[u]) {
            // do not do dfs on paths that
            //      don't form prefix of a valid word and paths that contain duplicates
            if (next != null && !visited[next]) {   // the next null check for impossible neighbors
                Node nextNode = getNextNode(node, next);
                if (nextNode != null)
                    DFS(nextNode, next, v, visited, validWords);
            }
        }

        visited[u] = false;
    }

    private Node getNextNode(Node node, int nextTile) {
        char nextChar = board.getLetter(nextTile / board.cols(), nextTile % board.cols());
        if (node == null)   return null;
        Node nextNode = node.next[nextChar - 'A'];
        if (nextChar == 'Q' && nextNode != null)    nextNode = nextNode.next['U' - 'A'];
        return nextNode;
    }

    //  precompute this for every tile, there is no need to calculate again
    private Integer[] adjacentTiles(int u) {
        Integer[] adj = new Integer[8];

        int width = board.cols();
        int row = u / width;
        int col = u % width;

        if (row > 0) {
            adj[0] = (((row - 1) * width) + col);                                     // direct top
            if (col > 0)            adj[1] = (((row - 1) * width) + (col - 1));       // top-left
            if (col + 1 < width)    adj[2] = (((row - 1) * width) + (col + 1));       // top-right
        }

        if (col > 0)                adj[3] = ((row * width) + (col - 1));             // left
        if (col + 1 < width)        adj[4] = ((row * width) + (col + 1));             // right

        if (row + 1 < board.rows()) {
            adj[5] = (((row + 1) * width) + col);                                     // direct bottom
            if (col > 0)            adj[6] = (((row + 1) * width) + (col - 1));       // bottom-left
            if (col + 1 < width)    adj[7] = (((row + 1) * width) + (col + 1));       // bottom-right
        }

        return adj;
    }

    /*//    Comparing the slower version of BoggleSolver with this optimized one to see
      //      which words were included or were not supposed to be included.

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
    }                               */

    public static void main(String[] args) {
        Stopwatch timer = new Stopwatch();
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();

        BoggleSolver solver = new BoggleSolver(dictionary);
        int count = 0;
        while (count < 30000)
        {
            BoggleBoard board = new BoggleBoard();
            solver.getAllValidWords(board);
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