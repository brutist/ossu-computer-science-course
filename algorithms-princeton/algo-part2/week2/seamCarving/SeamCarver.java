import edu.princeton.cs.algs4.Picture;

import java.awt.Color;
import java.util.ArrayDeque;

public class SeamCarver {
    private Picture picture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null)
            throw new IllegalArgumentException("cannot have null args in SeamCarver constructor");

        this.picture = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        validatePixelPosition(x, y);

        // energy of pixel at border is 1000
        if (x == 0 || x == picture.width())     return 1000.0;
        if (y == 0 || y == picture.height())    return 1000.0;

        Color a = picture.get(x - 1, y);       // left-side pixel
        Color b = picture.get(x + 1, y);       // right-side pixel
        Color c = picture.get(x, y + 1);      // top pixel
        Color d = picture.get(x, y - 1);      // bottom pixel

        int xRed = (a.getRed() - b.getRed());
        int xBlue = (a.getBlue() - b.getBlue());
        int xGreen = (a.getGreen() - b.getGreen());
        int yRed = (c.getRed() - d.getRed());
        int yBlue = (c.getBlue() - d.getBlue());
        int yGreen = (c.getGreen() - d.getGreen());

        int centralDiffX = (xRed * xRed) + (xBlue * xBlue) + (xGreen * xGreen);
        int centralDiffY = (yRed * yRed) + (yBlue * yBlue) + (yGreen * yGreen);
        return Math.sqrt(centralDiffX + centralDiffY);
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int totalPixels = width() * height();
        int[] pixelTo = new int[totalPixels];
        double[] distTo = new double[totalPixels];
        for (int i = 0; i < totalPixels; i++) {
            if (i < width())    distTo[i] = 0.0;
            // all other pixels are initially infinitely far
            else                distTo[i] = Double.POSITIVE_INFINITY;
        }

        // find the shortest path from virtual top to the bottom row
        for (int i = 0; i < totalPixels; i++) {
            int row = i / width(), col = i % width();
            // relax edges below this one
            for (int to : bottomPixels(row, col))
                relax(pixelTo, distTo, row, col, to);
        }

        // find the bottom row pixel that has the lowest distance from the top row
        double minDist = Double.POSITIVE_INFINITY;
        int lastPixelIndex = 0;
        for (int br = (ijToIndex(height() - 1, 0)); br < totalPixels; br++) {
            if (distTo[br] < minDist) {
                minDist = distTo[br];
                lastPixelIndex = br;
            }
        }

        // create the seam, by tracing parent link of pixel to
        int[] verticalSeam = new int[height()];
        int pixelIndex = lastPixelIndex;
        for (int i = verticalSeam.length - 1; i >= 0; i--) {
            verticalSeam[i] = pixelIndex % width();
            pixelIndex = pixelTo[pixelIndex];
        }

        return verticalSeam;
    }

    private void relax(int[] pixelTo, double[] distTo, int row, int col, int to) {
        int from = ijToIndex(row, col);
        if (energy(row, col) + distTo[from] < distTo[to]) {
            distTo[to] = energy(row, col) + distTo[from];
            pixelTo[to] = from;
        }
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        // virtual top - connected to all first row pixels, virtual bottom - connected to all last row pixels
        double[] virtualDistTo = {0.0, Double.POSITIVE_INFINITY};   // [virtual top, virtual bottom]
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        validateSeam(seam);
        if (picture.width() <= 1)
            throw new IllegalArgumentException("cannot horizontally resize image with width of 1");


    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        validateSeam(seam);
        if (picture.height() <= 1)
            throw new IllegalArgumentException("cannot vertically resize image with height of 1");

    }

    // returns the index of the pixels that are at most 1 distance to the bottom of the given pixel
    private Iterable<Integer> bottomPixels(int row, int col) {
        ArrayDeque<Integer> adj = new ArrayDeque<>();
        if (row == height() - 1)    return adj;
        if (col > 0)                adj.add(ijToIndex(row + 1, col - 1));
        if (col < width())          adj.add(ijToIndex(row + 1, col + 1));
        adj.add(ijToIndex(row + 1, col));

        return adj;
    }

    // returns the index of the pixels that are at most 1 distance to the right of the given pixel
    private Iterable<Integer> rightPixels(int row, int col) {
        ArrayDeque<Integer> adj = new ArrayDeque<>();
        if (col == width() - 1)     return adj;
        if (row > 0)                adj.add(ijToIndex(row - 1, col + 1));
        if (row < height())         adj.add(ijToIndex(row + 1, col + 1));
        adj.add(ijToIndex(row, col + 1));

        return adj;
    }

    // convert row and columns to index (both are 0-based)
    private int ijToIndex(int i, int j) {
        return (i * width()) + j;
    }

    private void validateSeam(int[] seam) {
        if (seam == null)
            throw new IllegalArgumentException("cannot have null args in removeHorizontalSeam");

        for (int i = 1; i < seam.length; i++) {
            double difference = seam[i] - seam[i - 1];
            if (Math.abs(difference) > 1) {
                String message = "adjacent pixel difference in a seam should be at most 1 pixel";
                throw new IllegalArgumentException(message);
            }
        }
    }

    private void validatePixelPosition(int x, int y) {
        String message = "x is out of bounds: '%d' must be within 0 - '%d' ";
        if (x < 0 || x >= picture.width())
            throw new IllegalArgumentException(String.format(message, x, picture.width()));
        if (y < 0 || y >= picture.height())
            throw new IllegalArgumentException(String.format(message, x, picture.height()));
    }


    //  unit testing (optional)
    public static void main(String[] args) {

    }
}
