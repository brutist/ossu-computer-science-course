import edu.princeton.cs.algs4.BST;
import edu.princeton.cs.algs4.Queue;

public class Board {
    private final BST<Integer, Position> goal;
    private final BST<Integer, Position> tiles;
    private final int N;
    private int rowEmpty;
    private int colEmpty;
    private final String boardString;

    // signify row and column position of a tile in the board
    private class Position implements Comparable<Position> {
        private final int row;
        private final int col;

        public Position(int i, int j) {
            row = i;
            col = j;
        }

        public boolean equals(Object that) {
            if (that == this)  return true;                        // check for true equality
            if (that == null)  return false;                       // check for null
            if (that.getClass() != this.getClass())  return false; // unequal class are not equal

            Position other = (Position) that;
            // positions are equal if they have the same row and column
            return other.row == row && other.col == col;
        }

        public int compareTo(Position other) {
            boolean equal = row == other.row && col == other.col;
            boolean less = row < other.row || (row == other.row && col < other.col);
            if (equal)          return  0;
            else if (less)      return -1;
            else                return  1;
        }
    }

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        N = tiles.length;
        StringBuilder string = new StringBuilder().append(N).append("\n");
        this.tiles = new BST<>();
        goal = new BST<>();
        int g = 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] == 0) {
                    rowEmpty = i;
                    colEmpty = j;
                }
                // instantiate goal BST with row-major order
                goal.put(g++, new Position(i, j));
                if (i == N - 1 && j == N - 1) {
                    goal.put(0, new Position(i, j));
                }
                this.tiles.put(tiles[i][j], new Position(i, j));

                // instantiate the string representation
                if (j == 0)         string.append(" ");
                else                string.append("  ");

                string.append(tiles[i][j]);
                if (j == N - 1)     string.append("\n");
            }
        }
        boardString = string.toString();
    }

    // string representation of this board
    public String toString() {
        return boardString;
    }

    // board dimension n
    public int dimension() {
        return N;
    }

    // number of tiles out of place
    public int hamming() {
        // count misplaced tile, do not include to the count the blank cell [key = 0]
        int score = 0;
        for (int key = 1; key < tiles.size(); key++) {
            // a tile is misplaced if its positions are not equal
            if (!tiles.get(key).equals(goal.get(key))) {
               score++;
           }
        }
        return score;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int distance = 0;
        for (int key = 1; key < tiles.size(); key++) {
            Position tilePos = tiles.get(key);
            Position goalPos = goal.get(key);
            // calculate the manhattan distance if positions are not equal
            if (!tilePos.equals(goalPos)) {
                distance += Math.abs(tilePos.row - goalPos.row) + Math.abs(tilePos.col - goalPos.col);
            }
        }
        return distance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this)  return true;                        // check for true equality
        if (y == null)  return false;                       // check for null
        if (y.getClass() != this.getClass())  return false; // unequal class are not equal

        Board other = (Board) y;
        // boards are true if they are of the same size and the state BSTs are equal
        boolean samePositions = true;
        // if tile positions are similar then the board is similar
        for (int key = 0; key < tiles.size(); key++) {
            // the value is the position of the key
            if (!tiles.get(key).equals(other.tiles.get(key))) {
                samePositions = false;
            }
        }
        return N == other.N && samePositions;
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
        for (int key = 0; key < tiles.size(); key++) {
            int row = tiles.get(key).row;
            int col = tiles.get(key).col;
            neighbor[row][col] = key;
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
        // testing toString()
        int[][] toStringInput = {{1, 0, 3}, {4, 2, 5}, {7, 8, 6}};
        String toStringAnswer = "3\n 1  0  3\n 4  2  5\n 7  8  6\n";
        Board toStringBoard = new Board(toStringInput);
        String toStringResult = toStringBoard.toString();

        if (!toStringAnswer.equals(toStringResult)) {
            System.out.println("toString() test FAILED");
            System.out.printf("got: \n%s instead of: \n%s", toStringResult, toStringAnswer);
        }
        else {
            System.out.println("toString() test SUCCESS");
            System.out.println(toStringResult);
        }

        // testing dimension()
        int dimensionAnswer = 3;
        int dimensionResult = toStringBoard.dimension();
        if (dimensionResult != dimensionAnswer) {
            System.out.println("dimension() test FAILED");
            System.out.printf("got: %s instead of: %s", dimensionResult, dimensionAnswer);
        }
        else {
            System.out.println("dimension() test SUCCESS");
        }

        // testing hamming()
        int[][] hammingInput = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board hammingBoard = new Board(hammingInput);
        int hammingAnswer = 5;
        int hammingResult = hammingBoard.hamming();
        if (hammingAnswer != hammingResult) {
            System.out.println("hamming() test FAILED");
            System.out.printf("got: %s instead of: %s", hammingResult, hammingAnswer);
        }
        else {
            System.out.println("hamming() test SUCCESS");
        }

        // testing manhattan()
        Board manhattanBoard = hammingBoard;
        int manhattanAnswer = 10;
        int manhattanResult = hammingBoard.manhattan();
        if (manhattanAnswer != manhattanResult) {
            System.out.println("manhattan() test FAILED");
            System.out.printf("got: %s instead of: %s\n", manhattanResult, manhattanAnswer);
        }
        else {
            System.out.println("manhattan() test SUCCESS");
        }

        // testing equals()
        Board hammingBoardEqual = new Board(hammingInput);
        Board hammingBoardNonEqual = new Board(toStringInput);
        boolean testTrueEquality = manhattanBoard.equals(hammingBoard);
        boolean testEqualityByValues = hammingBoardEqual.equals(hammingBoard);
        boolean testUnequalBoards = !hammingBoardNonEqual.equals(hammingBoard);
        if (!(testUnequalBoards && testEqualityByValues && testTrueEquality)) {
            System.out.println("equals() test FAILED");
        }
        else {
            System.out.println("equals() test SUCCESS");
        }

    }
}

























