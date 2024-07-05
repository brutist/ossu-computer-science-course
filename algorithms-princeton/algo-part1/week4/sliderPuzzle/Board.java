import java.util.Arrays;

public class Board {
    private final int[] goal = {1, 2, 3, 4, 5, 6, 7, 8, 0};
    private final int[] tiles;
    private final int N;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        N = tiles.length;

        this.tiles = new int[N * N];
        for (int i = 0; i < N; i++) {
            System.arraycopy(tiles[i], 0, this.tiles, (i * N), N);
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < N; i++) {
            result.append(" ").append(tiles[i]);

            if ((i + 1) % N == 0) {
                result.append("\n");
            }
        }
        return tiles.length + "\n" + result;
    }

    // board dimension n
    public int dimension() {
        return N;
    }

    // number of tiles out of place
    public int hamming() {
        int score = 0;
        for (int i = 0; i < tiles.length; i++) {
            // don't count the blank cell [zero value]
            // score refers to the no. of tiles in wrong position
            if (tiles[i] != 0 && tiles[i] != goal[i]) {
                score++;
            }
        }
        return score;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int distance = 0;
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] != goal[i]) {
                distance += manhattan(tiles[i], i);
            }
        }
        return distance;
    }

    // calculate the manhattan distance between the given val in the position i, with the goal position
    private int manhattan(int val, int i) {
        int j;
        for (int k = 0; k < goal.length; k++) {
            // look for the index of val in the goal
            if (goal[k] == val) {
                j = k;
            }
        }
        int iRow = i / N;
        int iCol = i % N;
        int jRow = j / N;
        int jCol = j % N;
        // manhattan distance is deltaX + deltaY
        return Math.abs(iRow - jRow) + Math.abs(iCol - jCol);
    }

    // is this board the goal board?
    public boolean isGoal() {
        return Arrays.equals(goal, tiles);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this)  return true;        // check for true equality
        if (y == null)  return false;       // check for null
        if (y.getClass() != this.getClass())  return false; // unequal class are not equal

        Board other = (Board) y;
        // boards are true if they are of the same size and the state arrays are equal
        return N == other.N && Arrays.equals(other.tiles, tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }
}
