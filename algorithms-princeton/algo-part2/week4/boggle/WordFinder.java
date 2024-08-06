import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;

public class WordFinder {
    private final WordTrie dictionary;
    private final BoggleBoard board;
    private final int totalTiles;
    private final HashMap<Integer, ArrayDeque<Integer>> adjacentTiles;
    private HashSet<String> validWords;
    private static final int minimumWordLength = 3;

    public WordFinder(BoggleBoard board, WordTrie dictionary) {
        totalTiles = board.rows() * board.cols();
        this.board = board;
        this.dictionary = dictionary;

        // precompute the adjacent tiles of all tiles in the board
        adjacentTiles = new HashMap<>();
        for (int i = 0; i < totalTiles; i++)
            adjacentTiles.put(i, adjacentTiles(i));
    }

    // returns an iterable of all the valid words from tile i to tile j of the board
    public HashSet<String> findValidWord(int i, int j) {
        validWords = new HashSet<>();        // reset the words found

        // dfs from vertex u to vertex v to search all valid paths
        boolean[] visited = new boolean[totalTiles];
        ArrayDeque<Integer> path = new ArrayDeque<>();
        DFS(i, j, path, new StringBuilder(), visited);

        return validWords;
    }


    // identify the valid paths that satisfy the following constrains
    //  - a simple path between index u and v (which corresponds to a tile in the board)
    //  - a path of index corresponding to a word in the dictionary
    //      (prune search if the current path is not a prefix of any word in the dictionary)
    private void DFS(int u, int v, ArrayDeque<Integer> path, StringBuilder word, boolean[] visited) {
        if (visited[u]) return;

        visited[u] = true;
        path.addLast(u);
        pathToWord(word, u, true);

        if (u == v) {
            String wordString = word.toString();
            if (word.length() >= minimumWordLength && dictionary.containsWord(wordString))  {
                validWords.add(wordString);
            }

            visited[u] = false;
            int k = path.removeLast();
            pathToWord(word, k, false);
            return;
        }

        for (int next : adjacentTiles.get(u)) {
            pathToWord(word, next, true);
            String prefix = word.toString();
            pathToWord(word, next, false);
            // do not do dfs on paths that
            //      don't form prefix of a valid word
            //      paths that may contain duplicates
            if (next != u && !visited[next] && dictionary.wordPrefix(prefix))
                DFS(next, v, path, word,visited);
        }

        int x = path.removeLast();
        pathToWord(word, x, false);
        visited[u] = false;
    }

    //  precompute this for every tile, there is no need to calculate again
    private ArrayDeque<Integer> adjacentTiles(int u) {
        ArrayDeque<Integer> adj = new ArrayDeque<>();

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

    private void pathToWord(StringBuilder word, int i, boolean addLetter) {
        int row = i / board.cols();
        int col = i % board.cols();
        char c = board.getLetter(row, col);

        if (addLetter) {
            word.append(c);
            if (c == 'Q')   word.append('U');
        }

        else {
            word.deleteCharAt(word.length() - 1);
            if (c == 'Q')   word.deleteCharAt(word.length() - 1);
        }
    }
}
