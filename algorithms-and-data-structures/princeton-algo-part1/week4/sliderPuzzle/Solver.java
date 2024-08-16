import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.Comparator;


public class Solver {
    private final SearchNode solutionNode;

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

        // init for initial node, manhattan and hamming PQ the given board
        SearchNode initialNode = new SearchNode(initial, null, 0);
        MinPQ<SearchNode> initialPQ = new MinPQ<>(new ManhattanPriority()); // using manhattan priority
        initialPQ.insert(initialNode);

        // init for twin node, manhattan and hamming PQ the twin board
        Board twin = initial.twin();
        SearchNode twinNode = new SearchNode(twin, null, 0);
        MinPQ<SearchNode> twinPQ = new MinPQ<>(new ManhattanPriority());  // using manhattan priority
        twinPQ.insert(twinNode);

        // while goal has not been reached for both PQs
        while (true) {
            // adding valid neighbors of the original node
            SearchNode i = initialPQ.delMin();
            if (i.board.isGoal()) {
                solutionNode = i;
                break;
            }
            for (Board b : i.board.neighbors()) {
                if (i.previousNode == null || !b.equals(i.previousNode.board)) {
                    initialPQ.insert(new SearchNode(b, i, i.moves + 1));
                }
            }

            // adding valid neighbors of the twin node
            SearchNode t = twinPQ.delMin();
            if (t.board.isGoal()) {
                solutionNode = null;
                break;
            }
            for (Board b : t.board.neighbors()) {
                if (t.previousNode == null || !b.equals(t.previousNode.board)) {
                    twinPQ.insert(new SearchNode(b, t, t.moves + 1));
                }
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solutionNode != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable())  return -1;
        return solutionNode.moves;
    }

    // sequence of boards with the shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (isSolvable()) {
            Stack<Board> stack = new Stack<>();
            SearchNode node = solutionNode;

            while (node != null) {
                stack.push(node.board);
                node = node.previousNode;
            }
            return stack;
        }
        // null if not solvable
        return null;
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
