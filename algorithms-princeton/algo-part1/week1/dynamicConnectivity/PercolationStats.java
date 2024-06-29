/* *****************************************************************************
 *  Name:              Mauring Jr
 *  Coursera User ID:  idk
 *  Last modified:     sometime in 2024
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] percolationThresholds;
    private double percolationThresMean;
    private double percolationThresStddev;
    private int numTrials;
    private double CONFIDENCE_95 = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("invalid arguments given");
        }
        percolationThresholds = new double[trials];
        numTrials = n;

        for (int i = 0; i < trials; i++) {
            Percolation model = new Percolation(n);
            while (!model.percolates()) {
                int row = StdRandom.uniformInt(n) + 1;
                int column = StdRandom.uniformInt(n) + 1;
                model.open(row, column);
            }
            percolationThresholds[i] = model.numberOfOpenSites() / ((double) n * n);
        }
        percolationThresMean = StdStats.mean(percolationThresholds);
        percolationThresStddev = StdStats.stddev(percolationThresholds);
    }

    // sample mean of percolation threshold
    public double mean() {
        return percolationThresMean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return percolationThresStddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return percolationThresMean - ((CONFIDENCE_95 * percolationThresStddev) / Math.sqrt(
                numTrials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return percolationThresMean + ((CONFIDENCE_95 * percolationThresStddev) / Math.sqrt(
                numTrials));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, t);
        double mean = stats.mean();
        double stddev = stats.stddev();
        double confidenceLo = stats.confidenceLo();
        double confidenceHi = stats.confidenceHi();

        System.out.printf("mean                    = %s\n", mean);
        System.out.printf("stddev                  = %s\n", stddev);
        System.out.printf("95%% confidence interval = [%s, %s]\n", confidenceLo, confidenceHi);
    }
}