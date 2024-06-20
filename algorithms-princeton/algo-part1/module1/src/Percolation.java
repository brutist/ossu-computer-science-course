/* *****************************************************************************
 *  Name:              Mauring Jr
 *  Coursera User ID:  idk
 *  Last modified:     sometime in 2024
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // sites legend
    // false {blocked}; true {open}

    // percolate grid is 1-based index
    private boolean[][] percolateGrid;
    private int openSitesCounter;
    private int gridWith;

    // unionSet is 1-based index with 1->n as percolate sites,
    //  n+1 is topVirtualSite connected to all top sites
    //  n+2 is bottomVirtualSite connected to all bottom sites
    private WeightedQuickUnionUF unionSet;
    private int topVirtualSite;
    private int bottomVirtualSite;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("grid size should be more than zero");
        }
        // the grid starts at (1,1)
        // all zero-index column or row are not considered (left blank)
        gridWith = n;
        openSitesCounter = 0;

        int numTiles = n + 2;  // zero index columns are not considered
        percolateGrid = new boolean[numTiles][numTiles];
        for (int i = 1; i < numTiles; i++) {
            for (int j = 1; j < numTiles; j++) {
                percolateGrid[i][j] = false;
            }
        }

        // create an instance of weightedQuickUnionUF with n+3 elements
        // index 1->n are the percolate sites
        // n+1 is the top site connected to all first row sites
        // n+2 is the bottom site connected to all last row sites
        // index 0 is not considered to simplify converting to row and columns
        int totalSites = n * n;
        topVirtualSite = totalSites + 1;
        bottomVirtualSite = totalSites + 2;
        unionSet = new WeightedQuickUnionUF(totalSites + 3);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        boolean rowInGrid = row > 0 && row <= gridWith;
        boolean colInGrid = col > 0 && col <= gridWith;
        if (!rowInGrid) {
            throw new IllegalArgumentException("row out of grid");
        }
        if (!colInGrid) {
            throw new IllegalArgumentException("column out of grid");
        }

        int currRowAndColIndex = (row * gridWith) + (col - gridWith);
        boolean open = true;
        if (percolateGrid[row][col] != open) {
            percolateGrid[row][col] = open;
            openSitesCounter++;

            if (row == 1) {
                unionSet.union(topVirtualSite, currRowAndColIndex);
            }
            else if (row == gridWith) {
                unionSet.union(bottomVirtualSite, currRowAndColIndex);
            }
        }

        int topRow = row - 1;
        int bottomRow = row + 1;
        int leftCol = col - 1;
        int rightCol = col + 1;

        int topSiteIndex = (topRow * gridWith) + (col - gridWith);
        int bottomSiteIndex = (bottomRow * gridWith) + (col - gridWith);
        int leftSiteIndex = (row * gridWith) + (leftCol - gridWith);
        int rightSiteIndex = (row * gridWith) + (rightCol - gridWith);

        // union only to already opened neighbours
        if (topRow > 0 && isOpen(topRow, col)) {
            unionSet.union(currRowAndColIndex, topSiteIndex);
        }

        if (bottomRow <= gridWith && isOpen(bottomRow, col)) {
            unionSet.union(currRowAndColIndex, bottomSiteIndex);
        }

        if (leftCol > 0 && isOpen(row, leftCol)) {
            unionSet.union(currRowAndColIndex, leftSiteIndex);
        }

        if (rightCol <= gridWith && isOpen(row, rightCol)) {
            unionSet.union(currRowAndColIndex, rightSiteIndex);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        boolean rowInGrid = row > 0 && row <= gridWith;
        boolean colInGrid = col > 0 && col <= gridWith;
        if (!rowInGrid) {
            throw new IllegalArgumentException("row out of grid");
        }
        if (!colInGrid) {
            throw new IllegalArgumentException("column out of grid");
        }
        return percolateGrid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        boolean rowInGrid = row > 0 && row <= gridWith;
        boolean colInGrid = col > 0 && col <= gridWith;
        if (!rowInGrid) {
            throw new IllegalArgumentException("row out of grid");
        }
        if (!colInGrid) {
            throw new IllegalArgumentException("column out of grid");
        }

        // if it's not open it is not full
        if (!isOpen(row, col)) {
            return false;
        }
        else {
            int mainRoot = unionSet.find(topVirtualSite);
            int root = unionSet.find((row * gridWith) + (col - gridWith));
            return mainRoot == root;
        }
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
        Percolation modelA = new Percolation(n);
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                modelA.open(i, j);
                boolean connected = modelA.unionSet.find(modelA.topVirtualSite)
                        == modelA.unionSet.find(modelA.bottomVirtualSite);
                if (connected) {
                    System.out.printf("connected to bottom at i= %d j= %d\n", i, j);
                }
            }
        }
        System.out.println(modelA.percolates());

        // call methods with invalid arguments
        Percolation modelB = new Percolation(n);
        int[] rows = { -1, 11, 0, 5, 5 };
        int[] col = { 5, 5, 5, -1, 11 };
        for (int i = 0; i < rows.length; i++) {
            try {
                modelB.open(rows[i], col[i]);
            }
            catch (IllegalArgumentException e) {
                System.out.printf("Exception catched: %d\n", i + 1);
            }
        }
    }
}
