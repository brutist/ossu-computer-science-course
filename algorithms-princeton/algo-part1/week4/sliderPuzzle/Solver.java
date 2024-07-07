import edu.princeton.cs.algs4.Queue;

public class Solver {
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)    throw new IllegalArgumentException("constructor does not take null");


    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return false;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable())      return null;
        return new Queue<>();
    }

    // test client (see below)
    public static void main(String[] args) {

    }
}
