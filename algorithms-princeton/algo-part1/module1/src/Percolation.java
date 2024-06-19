/* *****************************************************************************
 *  Name:              Mauring Jr
 *  Coursera User ID:  idk
 *  Last modified:     sometime in 2024
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // sites legend
    // zero {blocked}; one {open}; two {full}
    private int[][] percolateGrid;
    private int blocked = 0;
    private int open = 1;
    private int full = 2;
    private int openSitesCounter;
    private int gridWith;
    private WeightedQuickUnionUF unionSet;
    private int topVirtualSite;
    private int bottomVirtualSite;
    private int rowAndColLimit;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("grid size should be more than zero");
        }
        // the grid starts at (1,1)
        // all zero-index column or row are not considered (left blank)
        gridWith = n;
        rowAndColLimit = n + 2;  // zero and last val are not included
        openSitesCounter = 0;

        percolateGrid = new int[rowAndColLimit][rowAndColLimit];
        for (int i = 1; i < rowAndColLimit; i++) {
            for (int j = 1; j < rowAndColLimit; j++) {
                percolateGrid[i][j] = blocked;
            }
        }

        // create an instance of weightedQuickUnionUF with n+2 elements
        // n is the top site connected to all first row sites
        // n+1 is the bottom site connected to all last row sites
        int totalSites = n * n;
        int lastSite = totalSites - 1;
        topVirtualSite = totalSites;
        bottomVirtualSite = topVirtualSite + 1;
        unionSet = new WeightedQuickUnionUF(totalSites + 2);

        for (int i = 0; i < n; i++) {
            unionSet.union(topVirtualSite, i);
            unionSet.union(bottomVirtualSite, lastSite - i);
        }
    }

    // convert rows and columns to unionSet index
    private int toIndex(int row, int col) {
        row -= 1;
        col -= 1;
        return (row * gridWith) + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        boolean rowInGrid = row > 0 && row < rowAndColLimit;
        boolean colInGrid = col > 0 && col < rowAndColLimit;
        if (!rowInGrid) {
            throw new IllegalArgumentException("row out of grid");
        }
        if (!colInGrid) {
            throw new IllegalArgumentException("column out of grid");
        }

        if (percolateGrid[row][col] != open) {
            percolateGrid[row][col] = open;
            openSitesCounter++;
        }
        int topRow = row - 1;
        int bottomRow = row + 1;
        int leftCol = col - 1;
        int rightCol = col + 1;

        // union only to already opened immediate neighbours
        if (topRow > 0 && isOpen(topRow, col)) {
            unionSet.union(toIndex(row, col), toIndex(topRow, col));
        }
        if (bottomRow <= gridWith && isOpen(bottomRow, col)) {
            unionSet.union(toIndex(row, col), toIndex(bottomRow, col));
        }
        if (leftCol > 0 && isOpen(row, leftCol)) {
            unionSet.union(toIndex(row, col), toIndex(row, leftCol));
        }
        if (rightCol <= gridWith && isOpen(row, rightCol)) {
            unionSet.union(toIndex(row, col), toIndex(row, rightCol));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        boolean rowInGrid = row > 0 && row < rowAndColLimit;
        boolean colInGrid = col > 0 && col < rowAndColLimit;
        if (!rowInGrid) {
            throw new IllegalArgumentException("row out of grid");
        }
        if (!colInGrid) {
            throw new IllegalArgumentException("column out of grid");
        }
        return percolateGrid[row][col] == open;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        boolean rowInGrid = row > 0 && row < rowAndColLimit;
        boolean colInGrid = col > 0 && col < rowAndColLimit;
        if (!rowInGrid) {
            throw new IllegalArgumentException("row out of grid");
        }
        if (!colInGrid) {
            throw new IllegalArgumentException("column out of grid");
        }
        return percolateGrid[row][col] == full;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesCounter;
    }

    // does the system percolate?
    public boolean percolates() {
        return unionSet.find(topVirtualSite) == unionSet.find(bottomVirtualSite);
    }

    // test client
    public static void main(String[] args) {
        int n = 10;
        Percolation model = new Percolation(n);
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n + 1; j++) {
                if (j == 2) {
                    model.open(i, j);
                }
            }
        }
        System.out.println(model.percolates());
    }
}
