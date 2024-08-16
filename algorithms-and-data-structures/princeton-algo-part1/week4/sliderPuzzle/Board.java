import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class Board {
    // the goal board is in row-major order and blank is represented with 0
    // the goal board is implicitly defined here, this code will not work if the goal board is not
    //  in row-major order
    private final int[] tiles;
    private final int N;
    private final int blankIndex;
    private final int twinIndexA;
    private final int twinIndexB;
    private final int hammingDist;
    private final int manhattanDist;

    // signify row and column position of a tile in the board
    private class Position {
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
    }

    // create a board from a n^2 array of tiles
    public Board(int[][] tiles) {
        N = tiles.length;
        this.tiles = new int[N * N];

        int hammingScore = 0;
        int manhattanDist = 0;
        int blankIndex = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                // instantiate tiles array
                this.tiles[(i * N) + j] = tiles[i][j];

                // for calculating manhattan distance
                Position goalPos = indexToPosition(tiles[i][j] - 1);
                Position tilePos = new Position(i, j);

                // save the position of blank tile, do not calculate the distance/score of blank tile
                if (tiles[i][j] == 0) {
                    blankIndex = (i * N) + j;
                }
                else {
                    // caching hamming distance for optimization
                    // a tile is misplaced if the val x is not in position x + 1
                    if (tiles[i][j] != ((i * N) + j) + 1) {
                        hammingScore++;
                    }
                    // caching manhattan distance for optimization
                    // a tile is misplaced if the tilePos is not equal to the goalPos
                    //  manhattan distance is deltaX + deltaY
                    if (!tilePos.equals(goalPos)) {
                        manhattanDist += Math.abs(tilePos.row - goalPos.row) +
                                         Math.abs(tilePos.col - goalPos.col);
                    }
                }
            }
        }
        // cached the hamming score, and the index of the empty cell
        hammingDist = hammingScore;
        this.manhattanDist = manhattanDist;
        this.blankIndex = blankIndex;

        // produce a twin by getting two random indexes and swapping the elements in those indexes
        // just the first indexes that is not a blank index
        int k = this.blankIndex;
        int j = this.blankIndex;
        for (int i = 0; i < this.tiles.length; i++) {
            if (k == this.blankIndex && i != this.blankIndex)               k = i;
            else if (j == this.blankIndex && i != this.blankIndex)          j = i;
            else if (j != this.blankIndex && j != k)                        break;
        }
        twinIndexA = k;
        twinIndexB = j;
    }

    // string representation of this board
    public String toString() {
        StringBuilder boardString = new StringBuilder().append(N).append("\n");
        for (int i = 0; i < tiles.length; i++) {
            // first column (1-space only), 2-spaces separating each column
            if (i % N == 0)     boardString.append(" ");
            else                boardString.append("  ");

            boardString.append(tiles[i]);
            // newline for the last column
            if (i % N == N - 1)     boardString.append("\n");
        }
        return boardString.toString();
    }

    // board dimension n
    public int dimension() {
        return N;
    }

    // number of tiles out of place
    public int hamming() {
        return hammingDist;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattanDist;
    }

    // convert an index of the tile into a position [row, col]
    //  this is useful for calculating manhattan distance
    private Position indexToPosition(int i) {
        int row = i / N;
        int col = i % N;
        return new Position(row, col);
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
        return N == other.N && Arrays.equals(other.tiles, tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> q = new Queue<>();
        // add neighbors of board to the queue
        neighbors(q);
        return q;
    }

    private void neighbors(Queue<Board> q) {
        int rowEmpty = blankIndex / N;
        int colEmpty = blankIndex % N;
        // move blank up
        if (rowEmpty > 0) {
            q.enqueue(produceNeighbor(rowEmpty, colEmpty, rowEmpty - 1, colEmpty));
        }
        // move blank down
        if (rowEmpty < N - 1) {
            q.enqueue(produceNeighbor(rowEmpty, colEmpty, rowEmpty + 1, colEmpty));
        }
        // move blank left
        if (colEmpty > 0) {
            q.enqueue(produceNeighbor(rowEmpty, colEmpty, rowEmpty, colEmpty - 1));
        }
        // move blank right
        if (colEmpty < N - 1) {
            q.enqueue(produceNeighbor(rowEmpty, colEmpty, rowEmpty, colEmpty + 1));
        }
    }

    // produce a new board by swapping the elements in the specified rows and columns([a,b],[c,d]) of this board
    private Board produceNeighbor(int a, int b, int c, int d) {
        int[][] neighbor = new int[N][N];
        for (int i = 0; i < tiles.length; i++) {
            Position p = indexToPosition(i);
            neighbor[p.row][p.col] = tiles[i];
        }
        swap(neighbor, a, b, c, d);
        return new Board(neighbor);
    }

    // swap the element in index[a][b] with element in index[c][d]
    private void swap(int[][] source, int a, int b, int c, int d) {
        int temp = source[a][b];
        source[a][b] = source[c][d];
        source[c][d] = temp;
    }

    // a board that is obtained by exchanging any pair of tiles
    // pick randomly two adjacent tiles to swap, can't pick the blank tile
    public Board twin() {
        int a = twinIndexA / N;
        int b = twinIndexA % N;
        int c = twinIndexB / N;
        int d = twinIndexB % N;
        return produceNeighbor(a, b, c, d);
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

        // testing neighbors() visually
        int[][] neighborsInput1 = {{0, 1, 3}, {4, 2, 5},{7, 8, 6}};     // 2 neighbors
        int[][] Answer1a = {{4, 1, 3}, {0, 2, 5}, {7, 8, 6}};           // move down
        int[][] Answer1b = {{1, 0, 3}, {4, 2, 5}, {7, 8, 6}};           // move left
        Queue<Board> neighborsAnswers1 = new Queue<>();
        neighborsAnswers1.enqueue(new Board(Answer1a));
        neighborsAnswers1.enqueue(new Board(Answer1b));

        int[][] neighborsInput2 = {{1, 0, 3}, {4, 2, 5}, {7, 8, 6}};    // 3 neighbors
        int[][] Answer2a = {{1, 2, 3}, {4, 0, 5}, {7, 8, 6}};           // move down
        int[][] Answer2b = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};           // move left
        int[][] Answer2c = {{1, 3, 0}, {4, 2, 5}, {7, 8, 6}};           // move right
        Queue<Board> neighborsAnswers2 = new Queue<>();
        neighborsAnswers2.enqueue(new Board(Answer2a));
        neighborsAnswers2.enqueue(new Board(Answer2b));
        neighborsAnswers2.enqueue(new Board(Answer2c));

        int[][] neighborsInput3 = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};    // 4 neighbors
        int[][] Answer3a = {{8, 0, 3}, {4, 1, 2}, {7, 6, 5}};           // move up
        int[][] Answer3b = {{8, 1, 3}, {4, 6, 2}, {7, 0, 5}};           // move down
        int[][] Answer3c = {{8, 1, 3}, {0, 4, 2}, {7, 6, 5}};           // move right
        int[][] Answer3d = {{8, 1, 3}, {4, 2, 0}, {7, 6, 5}};           // move left
        Queue<Board> neighborsAnswers3 = new Queue<>();
        neighborsAnswers3.enqueue(new Board(Answer3a));
        neighborsAnswers3.enqueue(new Board(Answer3b));
        neighborsAnswers3.enqueue(new Board(Answer3c));
        neighborsAnswers3.enqueue(new Board(Answer3d));

        Board neighborsBoard1 = new Board(neighborsInput1);
        Board neighborsBoard2 = new Board(neighborsInput2);
        Board neighborsBoard3 = new Board(neighborsInput3);

        boolean result = true;
        for (Board b : neighborsBoard1.neighbors()) {
            Board current = neighborsAnswers1.dequeue();
            if (!b.equals(current)) {
                System.out.printf("given board:\n%s\n", neighborsBoard1);
                System.out.printf("FAILED got:\n%s\n", current);
                result = false;
            }
        }
        for (Board b : neighborsBoard2.neighbors()) {
            Board current = neighborsAnswers2.dequeue();
            if (!b.equals(current)) {
                System.out.printf("given board:\n%s\n", neighborsBoard2);
                System.out.printf("FAILED got:\n%s\n", current);
                result = false;
            }
        }
        for (Board b : neighborsBoard3.neighbors()) {
            Board current = neighborsAnswers3.dequeue();
            if (!b.equals(current)) {
                System.out.printf("given board:\n%s\n", neighborsBoard3);
                System.out.printf("FAILED got:\n%s\n", current);
                result = false;
            }
        }
        if (result) {
            System.out.println("neighbors() test SUCCESS");
        }
    }
}

























