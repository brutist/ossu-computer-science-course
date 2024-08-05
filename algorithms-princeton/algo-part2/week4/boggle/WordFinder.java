import java.util.ArrayDeque;
import java.util.HashMap;

public class WordFinder {
    private boolean[] visited;
    private final WordTrie dictionary;
    private final BoggleBoard board;
    private final HashMap<Integer, ArrayDeque<Integer>> adjacentTiles;
    private ArrayDeque<ArrayDeque<Integer>> validPaths;

    public WordFinder(BoggleBoard board, WordTrie dictionary) {
        int totalTiles = board.rows() * board.cols();
        this.board = board;
        this.dictionary = dictionary;
        validPaths = new ArrayDeque<>();
        visited = new boolean[totalTiles];

        // precompute the adjacent tiles of all tiles in the board
        adjacentTiles = new HashMap<>();
        for (int i = 0; i < totalTiles; i++)
            adjacentTiles.put(i, adjacentTiles(i));
    }

    public Iterable<String> getValidWords() {
        ArrayDeque<String> validWords = new ArrayDeque<>();

        for (ArrayDeque<Integer> path : validPaths) {
            String word = pathToWord(path);
            if (dictionary.containsWord(word))
                validWords.addLast(word);
        }
        return validWords;
    }

    // identify the valid paths that satisfy the following constrains
    //  - a simple path between index u and v (which corresponds to a tile in the board)
    //  - a path of index corresponding to a word in the dictionary
    //      (prune search if the current path is not a prefix of any word in the dictionary)
    public void addValidPaths(int u, int v) {
        // dfs from vertex u to vertex v to search all valid paths
        ArrayDeque<Integer> path = new ArrayDeque<>();
        DFS(u, v, path);
    }

    private void DFS(int u, int v, ArrayDeque<Integer> path) {
        if (visited[u]) return;

        visited[u] = true;
        path.addLast(u);

        if (u == v) {
            validPaths.addLast(path);
            visited[u] = false;
            path.removeLast();
            return;
        }

        for (int next : adjacentTiles.get(u)) {
            ArrayDeque<Integer> pathCopy = path.clone();
            pathCopy.addLast(next);
            String prefix = pathToWord(pathCopy);
            // avoid reusing the letter and do not do dfs on paths that don't form prefix of a valid word
            if (next != u && dictionary.wordPrefix(prefix))
                DFS(next, v, path);
        }

        path.removeLast();
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


    private String pathToWord(Iterable<Integer> path) {
        StringBuilder word = new StringBuilder();
        for (int i : path) {
            int row = i / board.cols();
            int col = i % board.cols();

            word.append(board.getLetter(row, col));
        }
        return word.toString();
    }
}
