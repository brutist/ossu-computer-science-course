import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;

public class Board {
    private final int[][] goal = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    private final int[][] tiles;
    private final int N;
    private int rowEmpty;
    private int colEmpty;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        N = tiles.length;

        this.tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] == 0) {
                    rowEmpty = i;
                    colEmpty = j;
                }
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                result.append(" ").append(tiles[i][j]);
                // newline after the last column
                if (j == (N - 1)) {
                    result.append("\n");
                }
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
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // don't count the blank cell [emptyTile]
                // score refers to the no. of tiles in wrong position
                if (i != rowEmpty && j != colEmpty && tiles[i] != goal[i]) {
                    score++;
                }
            }
        }
        return score;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int distance = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] != goal[i][j] && i != rowEmpty && j != colEmpty) {
                    distance += manhattan(tiles[i][j], i, j);
                }
            }
        }
        return distance;
    }

    // calculate the manhattan distance between the given val in the given row and column
    //  with the goal position
    private int manhattan(int val, int row, int col) {
        int distance = 0;
        // assumes that the val is in the goal array
        for (int i = 0; i < N; i++) {
            // look for the index of val in the goal
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] == goal[i][j]) {
                    distance = Math.abs(row - i) + Math.abs(col - j);
                }
            }
        }
        return distance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return Arrays.deepEquals(goal, tiles);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this)  return true;                        // check for true equality
        if (y == null)  return false;                       // check for null
        if (y.getClass() != this.getClass())  return false; // unequal class are not equal

        Board other = (Board) y;
        // boards are true if they are of the same size and the state arrays are equal
        return N == other.N && Arrays.deepEquals(other.tiles, tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> q = new Queue<>();
        neighbors(q);
        return q;
    }

    private void neighbors(Queue<Board> q) {
        // move blank up
        if (rowEmpty > 0) {
            q.enqueue(new Board(produceNeighbor(rowEmpty, colEmpty, 0, colEmpty)));
        }
        // move blank down
        if (rowEmpty < N - 1) {
            q.enqueue(new Board(produceNeighbor(rowEmpty, colEmpty, rowEmpty + 1, colEmpty)));
        }
        // move blank left
        if (colEmpty > 0) {
            q.enqueue(new Board(produceNeighbor(rowEmpty, colEmpty, rowEmpty, colEmpty - 1)));
        }
        // move blank right
        if (colEmpty < N - 1) {
            q.enqueue(new Board(produceNeighbor(rowEmpty, colEmpty, rowEmpty, colEmpty + 1)));
        }
    }

    // produce a new int[] by swapping the ith and jth elements in the this.tiles array
    private int[][] produceNeighbor(int a, int b, int c, int d) {
        int[][] neighbor = new int[N][N];
        for (int i = 0; i < N; i++) {
            System.arraycopy(tiles[i], 0, neighbor[i], 0, N);
        }
        swap(neighbor, a, b, c, d);
        return neighbor;
    }

    // swap the element in index[a][b] with element in index[c][d]
    private void swap(int[][] source, int a, int b, int c, int d) {
        int temp = source[a][b];
        source[a][b] = source[c][d];
        source[c][d] = temp;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return this;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }
}
