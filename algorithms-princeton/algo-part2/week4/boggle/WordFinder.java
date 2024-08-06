import java.util.ArrayList;
import java.util.HashSet;

public class WordFinder {
    private final BoggleBoard board;
    private final int totalTiles;
    private final ArrayList<Integer[]> adjacentTiles;
    private HashSet<String> validWords;
    private static final int minimumWordLength = 3;

    public WordFinder(BoggleBoard board) {
        totalTiles = board.rows() * board.cols();
        this.board = board;


        // precompute the adjacent tiles of all tiles in the board
        adjacentTiles = new ArrayList<>();
        for (int i = 0; i < totalTiles; i++)
            adjacentTiles.add(adjacentTiles(i));
    }

    // returns an iterable of all the valid words from tile i to tile j of the board
    public HashSet<String> findValidWord(int i, int j, WordTrie dictionary) {
        validWords = new HashSet<>();        // reset the words found

        // dfs from vertex u to vertex v to search all valid paths
        boolean[] visited = new boolean[totalTiles];
        DFS(i, j, new StringBuilder(), visited, dictionary);

        return validWords;
    }

    // identify the valid paths that satisfy the following constrains
    //  - a simple path between index u and v (which corresponds to a tile in the board)
    //  - a path of index corresponding to a word in the dictionary
    //      (prune search if the current path is not a prefix of any word in the dictionary)
    private void DFS(int u, int v, StringBuilder word, boolean[] visited, WordTrie dictionary) {
        if (visited[u]) return;

        visited[u] = true;
        updateWord(word, u, true);

        if (u == v) {
            String wordString = word.toString();
            if (word.length() >= minimumWordLength && dictionary.containsWord(wordString))  {
                validWords.add(wordString);
            }

            visited[u] = false;
            updateWord(word, u, false);
            return;
        }

        for (int next : adjacentTiles.get(u)) {
            updateWord(word, next, true);
            String prefix = word.toString();
            updateWord(word, next, false);
            // do not do dfs on paths that
            //      don't form prefix of a valid word
            //      paths that may contain duplicates
            if (!visited[next] && dictionary.wordPrefix(prefix))
                DFS(next, v,  word, visited, dictionary);
        }

        updateWord(word, u, false);
        visited[u] = false;
    }

    //  precompute this for every tile, there is no need to calculate again
    private Integer[] adjacentTiles(int u) {
        ArrayList<Integer> adj = new ArrayList<>();

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

        return adj.toArray(new Integer[0]);
    }

    private void updateWord(StringBuilder word, int i, boolean addLetter) {
        char c = board.getLetter(i / board.cols(), i % board.cols());

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
