import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;


public class Solver {

    private static class SearchNode {
        private final Board board;
        private final SearchNode previousNode;
        private final int moves;
        private final int manhattanPriority;
        private final int hammingPriority;

        public SearchNode(Board current, SearchNode prev, int moves) {
            board = current;
            previousNode = prev;
            this.moves = moves;
            manhattanPriority = board.manhattan() + moves;
            hammingPriority = board.hamming() + moves;
        }
    }

    private static class ManhattanPriority implements Comparator<SearchNode> {
        public int compare(SearchNode a, SearchNode b) {
            return Integer.compare(a.manhattanPriority, b.manhattanPriority);
        }
    }

    private static class HammingPriority implements Comparator<SearchNode> {
        public int compare(SearchNode a, SearchNode b) {
            return Integer.compare(a.hammingPriority, b.hammingPriority);
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)    throw new IllegalArgumentException("constructor does not take null");

        // instantiate the min-priority queue, moves done queue
        MinPQ<SearchNode> manhattanPQ = new MinPQ<>(new ManhattanPriority());
        MinPQ<SearchNode> hammingPQ = new MinPQ<>(new HammingPriority());

        // initial board, 0 moves, null previous search node
        SearchNode initialNode = new SearchNode(initial, null, 0);
        manhattanPQ.insert(initialNode);
        hammingPQ.insert(initialNode);

        while (!hammingPQ.min().board.isGoal() || !manhattanPQ.min().board.isGoal()) {
            // delete the minimums
            SearchNode manhattanMinNode = manhattanPQ.delMin();
            SearchNode hammingMinNode = hammingPQ.delMin();

            // insert all valid neighbors for manhattan function
            for (Board b : manhattanMinNode.board.neighbors()) {
                // avoid inserting the move that is the same as the board of the previous search node
                if (b != manhattanMinNode.previousNode.board) {
                    manhattanPQ.insert(new SearchNode(b, manhattanMinNode, manhattanMinNode.moves + 1));
                }
            }

            // insert all valid neighbors for hamming function
            for (Board b : hammingMinNode.board.neighbors()) {
                // avoid inserting the move that is the same as the board of the previous search node
                if (b != hammingMinNode.previousNode.board) {
                    hammingPQ.insert(new SearchNode(b, hammingMinNode, hammingMinNode.moves + 1));
                }
            }
        }


    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return false;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable())  return -1;
        return 0;
    }

    // sequence of boards with the shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable())      return null;
        return new Queue<>();
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
