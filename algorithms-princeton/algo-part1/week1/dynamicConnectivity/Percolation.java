/* *****************************************************************************
 *  Name:              Mauring Jr
 *  Coursera User ID:  idk
 *  Last modified:     sometime in 2024
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final byte[] sitesStatus;
    private final static byte blockedSite = 0;              // 000
    private final static byte openSite = 4;                 // 100
    private final static byte topConnectedSite = 6;         // 110
    private final static byte topNBottomConnectedSite = 7;  // 111
    private int openSitesCounter;
    private final int gridWidth;
    private final WeightedQuickUnionUF sitesSet;
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
        if (sitesStatus[currSite] == blockedSite) {
            sitesStatus[currSite] = openSite;
            openSitesCounter++;

            int firstRow = 1;
            if (row == firstRow) {
                sitesStatus[currSite] = topConnectedSite;
                currSiteStatus = topConnectedSite;
                if (row == gridWidth) {
                    sitesStatus[currSite] = topNBottomConnectedSite;
                    currSiteStatus = topNBottomConnectedSite;
                    percolationStatus = true;
                }
            }
            else if (row == gridWidth) {
                byte bottomConnectedSite = (byte) 0b00000101;
                sitesStatus[currSite] = bottomConnectedSite;
                currSiteStatus = bottomConnectedSite;
            }

            int topRow = row - 1;
            int bottomRow = row + 1;
            int leftCol = col - 1;
            int rightCol = col + 1;

            int topSiteIndex = ((topRow * gridWidth) + (col - gridWidth) - 1);
            int bottomSiteIndex = ((bottomRow * gridWidth) + (col - gridWidth) - 1);
            int leftSiteIndex = ((row * gridWidth) + (leftCol - gridWidth) - 1);
            int rightSiteIndex = ((row * gridWidth) + (rightCol - gridWidth) - 1);

            byte otherRootStatus;
            // union only to already opened neighbours
            if (topRow > 0 && isOpen(topRow, col)) {
                otherRootStatus = sitesStatus[sitesSet.find(topSiteIndex)];
                sitesSet.union(currSite, topSiteIndex);
                currSiteStatus = (byte) (otherRootStatus | currSiteStatus);
                sitesStatus[sitesSet.find(currSite)] = currSiteStatus;
            }

            if (bottomRow <= gridWidth && isOpen(bottomRow, col)) {
                otherRootStatus = sitesStatus[sitesSet.find(bottomSiteIndex)];
                sitesSet.union(currSite, bottomSiteIndex);
                currSiteStatus = (byte) (otherRootStatus | currSiteStatus);
                sitesStatus[sitesSet.find(currSite)] = currSiteStatus;
            }

            if (leftCol > 0 && isOpen(row, leftCol)) {
                otherRootStatus = sitesStatus[sitesSet.find(leftSiteIndex)];
                sitesSet.union(currSite, leftSiteIndex);
                currSiteStatus = (byte) (otherRootStatus | currSiteStatus);
                sitesStatus[sitesSet.find(currSite)] = currSiteStatus;
            }

            if (rightCol <= gridWidth && isOpen(row, rightCol)) {
                otherRootStatus = sitesStatus[sitesSet.find(rightSiteIndex)];
                sitesSet.union(currSite, rightSiteIndex);
                currSiteStatus = (byte) (otherRootStatus | currSiteStatus);
                sitesStatus[sitesSet.find(currSite)] = currSiteStatus;
            }

            if (currSiteStatus == topNBottomConnectedSite) {
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
        byte rootStatus = sitesStatus[((row * gridWidth) + (col - gridWidth) - 1)];
        return rootStatus != blockedSite;
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
        int n = 5;
        Percolation modelA = new Percolation(n);
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (j == 3) {
                    modelA.open(i, j);
                    if (!(modelA.isOpen(i, j) && modelA.isFull(i, j))) {
                        System.out.printf("Not opened or full i= %d j= %d\n", i, j);
                    }
                }
            }
        }

        // call methods with invalid arguments
        Percolation modelB = new Percolation(n);
        int[] rows = { -1, 11, 0, 5, 5 };
        int[] cols = { 5, 5, 5, -1, 11 };
        int i = 0;
        int testPassed = 0;
        for (; i < rows.length; i++) {
            try {
                modelB.open(rows[i], cols[i]);
            }
            catch (IllegalArgumentException e) {
                testPassed++;
            }
        }
        if (testPassed == rows.length) {
            System.out.printf("Catch Exception KeyIndexCounting %d completed:\n", i + 1);
        }

        // manual checking
        int m = 5;
        Percolation modelC = new Percolation(m);
        while (!modelC.percolates()) {
            int row = StdRandom.uniformInt(m) + 1;
            int col = StdRandom.uniformInt(m) + 1;
            modelC.open(row, col);
            System.out.printf("Row: %d  Column: %d\n", row, col);
            if (!modelC.isFull(row, col)) {
                System.out.printf("Not full i= %d j= %d\n", row, col);
            }
            if (!modelC.isOpen(row, col)) {
                System.out.printf("Not opened i= %d j= %d\n", row, col);
            }
        }
    }
}
