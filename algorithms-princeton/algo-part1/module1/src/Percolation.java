/* *****************************************************************************
 *  Name:              Mauring Jr
 *  Coursera User ID:  idk
 *  Last modified:     sometime in 2024
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private byte[] sitesStatus;
    private byte blockedSite = 0;               // 000
    private final byte openSite = 4;                  // 100
    private final byte topConnectedSite = 6;          // 110
    private final byte bottomConnectedSite = 5;       // 101
    private final byte topNBottomConnectedSite = 7;   // 111
    private int openSitesCounter;
    private final int gridWidth;
    private WeightedQuickUnionUF sitesSet;
    private boolean percolationStatus;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("grid size should be more than zero");
        }
        // all zero-index column or row are not considered (left blank)
        gridWidth = n;
        openSitesCounter = 0;

        int totalSites = n * n;
        sitesStatus = new byte[totalSites];
        for (int i = 0; i < totalSites; i++) {
            sitesStatus[i] = blockedSite;
        }
        sitesSet = new WeightedQuickUnionUF(totalSites);
        percolationStatus = false;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        boolean rowInGrid = row > 0 && row <= gridWidth;
        boolean colInGrid = col > 0 && col <= gridWidth;
        if (!rowInGrid) {
            throw new IllegalArgumentException("row out of grid");
        }
        if (!colInGrid) {
            throw new IllegalArgumentException("column out of grid");
        }

        int currSite = ((row * gridWidth) + (col - gridWidth) - 1);
        byte currSiteStatus = sitesStatus[currSite];
        if (sitesStatus[currSite] != openSite) {
            sitesStatus[currSite] = openSite;
            openSitesCounter++;

            if (row == 1) {
                sitesStatus[currSite] = topConnectedSite;

                if (row == gridWidth) {
                    sitesStatus[currSite] = topNBottomConnectedSite;
                    percolationStatus = true;
                }
            }
            else if (row == gridWidth) {
                sitesStatus[currSite] = bottomConnectedSite;
            }

            int topRow = row - 1;
            int bottomRow = row + 1;
            int leftCol = col - 1;
            int rightCol = col + 1;

            int topSiteIndex = ((topRow * gridWidth) + (col - gridWidth) - 1);
            int bottomSiteIndex = ((bottomRow * gridWidth) + (col - gridWidth) - 1);
            int leftSiteIndex = ((row * gridWidth) + (leftCol - gridWidth) - 1);
            int rightSiteIndex = ((row * gridWidth) + (rightCol - gridWidth) - 1);


            int otherRoot = 0;
            byte otherRootStatus = 0;
            int newRoot = 0;
            byte combinedStatus = 0;
            // union only to already opened neighbours
            if (topRow > 0 && isOpen(topRow, col)) {
                otherRoot = sitesSet.find(topSiteIndex);
                otherRootStatus = sitesStatus[otherRoot];
                sitesSet.union(currSite, topSiteIndex);
                newRoot = sitesSet.find(currSite);
                combinedStatus = (byte) (otherRootStatus | currSiteStatus);
                sitesStatus[newRoot] = combinedStatus;
            }

            if (bottomRow <= gridWidth && isOpen(bottomRow, col)) {
                otherRoot = sitesSet.find(bottomSiteIndex);
                otherRootStatus = sitesStatus[otherRoot];
                sitesSet.union(currSite, bottomSiteIndex);
                newRoot = sitesSet.find(currSite);
                combinedStatus = (byte) (otherRootStatus | currSiteStatus);
                sitesStatus[newRoot] = combinedStatus;
            }

            if (leftCol > 0 && isOpen(row, leftCol)) {
                otherRoot = sitesSet.find(leftSiteIndex);
                otherRootStatus = sitesStatus[otherRoot];
                sitesSet.union(currSite, leftSiteIndex);
                newRoot = sitesSet.find(currSite);
                combinedStatus = (byte) (otherRootStatus | currSiteStatus);
                sitesStatus[newRoot] = combinedStatus;
            }

            if (rightCol <= gridWidth && isOpen(row, rightCol)) {
                otherRoot = sitesSet.find(rightSiteIndex);
                otherRootStatus = sitesStatus[otherRoot];
                sitesSet.union(currSite, rightSiteIndex);
                newRoot = sitesSet.find(currSite);
                combinedStatus = (byte) (otherRootStatus | currSiteStatus);
                sitesStatus[newRoot] = combinedStatus;
            }
            if (combinedStatus == topNBottomConnectedSite) {
                percolationStatus = true;
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        boolean rowInGrid = row > 0 && row <= gridWidth;
        boolean colInGrid = col > 0 && col <= gridWidth;
        if (!rowInGrid) {
            throw new IllegalArgumentException("row out of grid");
        }
        if (!colInGrid) {
            throw new IllegalArgumentException("column out of grid");
        }
        int root = sitesSet.find(((row * gridWidth) + (col - gridWidth) - 1));
        byte rootStatus = sitesStatus[root];
        return rootStatus >= openSite;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        boolean rowInGrid = row > 0 && row <= gridWidth;
        boolean colInGrid = col > 0 && col <= gridWidth;
        if (!rowInGrid) {
            throw new IllegalArgumentException("row out of grid");
        }
        if (!colInGrid) {
            throw new IllegalArgumentException("column out of grid");
        }
        int root = sitesSet.find(((row * gridWidth) + (col - gridWidth) - 1));
        byte rootStatus = sitesStatus[root];
        return rootStatus == topConnectedSite || rootStatus == topNBottomConnectedSite;

    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesCounter;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolationStatus;
    }

    // test client
    public static void main(String[] args) {
        int n = 10;
        Percolation modelA = new Percolation(n);
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                modelA.open(i, j);
                boolean connected = modelA.percolates();
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
                System.out.printf("Exception caught: %d\n", i + 1);
            }
        }
    }
}
