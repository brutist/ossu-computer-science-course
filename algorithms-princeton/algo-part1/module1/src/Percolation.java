/* *****************************************************************************
 *  Name:              Mauring Jr
 *  Coursera User ID:  idk
 *  Last modified:     sometime in 2024
 **************************************************************************** */

public class Percolation {
    // sites legend
    // zero {blocked}; one {open}; two {full}
    private int[][] grid;
    private int blocked = 0;
    private int open = 1;
    private int full = 2;
    private int openSitesCounter;
    private int gridSize;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("grid size should be more than zero");
        }

        n = n + 1;
        grid = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = blocked;
            }
        }
        openSitesCounter = 0;
        gridSize = n;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        boolean withInGrid = row > 0 && row <= gridSize && col > 0 && col <= gridSize;
        if (!withInGrid) {
            throw new IllegalArgumentException("args out of grid");
        }

        if (grid[row][col] != open) {
            grid[row][col] = open;
            openSitesCounter++;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        boolean withInGrid = row > 0 && row <= gridSize && col > 0 && col <= gridSize;
        if (!withInGrid) {
            throw new IllegalArgumentException("args out of grid");
        }
        return grid[row][col] == open;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        boolean withInGrid = row > 0 && row <= gridSize && col > 0 && col <= gridSize;
        if (!withInGrid) {
            throw new IllegalArgumentException("args out of grid");
        }
        return grid[row][col] == full;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesCounter;
    }

    // does the system percolate?
    public boolean percolates() {

        return false;
    }

    // test client
    public static void main(String[] args) {

    }
}
