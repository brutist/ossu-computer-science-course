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
        return new Picture(picture);
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

        int a = picture.getRGB(x - 1, y);       // left-side pixel
        int b = picture.getRGB(x + 1, y);       // right-side pixel
        int c = picture.getRGB(x, y + 1);      // top pixel
        int d = picture.getRGB(x, y - 1);      // bottom pixel

        int aRed = (a >> 16) & 0xFF, aGreen = (a >> 8) & 0xFF, aBlue = a & 0xFF;
        int bRed = (b >> 16) & 0xFF, bGreen = (b >> 8) & 0xFF, bBlue = b & 0xFF;
        int cRed = (c >> 16) & 0xFF, cGreen = (c >> 8) & 0xFF, cBlue = c & 0xFF;
        int dRed = (d >> 16) & 0xFF, dGreen = (d >> 8) & 0xFF, dBlue = d & 0xFF;

        int xRed = (aRed - bRed);
        int xGreen = (aGreen - bGreen);
        int xBlue = (aBlue - bBlue);
        int yRed = (cRed - dRed);
        int yGreen = (cGreen - dGreen);
        int yBlue = (cBlue - dBlue);

        int centralDiffX = (xRed * xRed) + (xBlue * xBlue) + (xGreen * xGreen);
        int centralDiffY = (yRed * yRed) + (yBlue * yBlue) + (yGreen * yGreen);
        return Math.sqrt(centralDiffX + centralDiffY);
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int totalPixels = width() * height();
        int[] pixelTo = new int[totalPixels];
        double[] distTo = new double[totalPixels];
        for (int i = width(); i < totalPixels; i++)
            distTo[i] = Double.POSITIVE_INFINITY;

        // find the shortest path from top to bottom
        for (int i = 0; i < totalPixels - width(); i++) {
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
        int totalPixels = width() * height();
        int[] pixelTo = new int[totalPixels];
        double[] distTo = new double[totalPixels];
        for (int i = width(); i < totalPixels; i++)
            distTo[i] = Double.POSITIVE_INFINITY;

        // find the shortest path from right to left columns
        for (int i = 0; i < totalPixels; i++) {
            int row = i / width(), col = i % width();
            // relax edges below this one
            for (int to : rightPixels(row, col))
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
        int[] horizontalSeam = new int[height()];
        int pixelIndex = lastPixelIndex;
        for (int i = horizontalSeam.length - 1; i >= 0; i--) {
            horizontalSeam[i] = pixelIndex / width();
            pixelIndex = pixelTo[pixelIndex];
        }

        return horizontalSeam;
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
        if (col < width() - 1)      adj.add(ijToIndex(row + 1, col + 1));
        adj.add(ijToIndex(row + 1, col));

        return adj;
    }

    // returns the index of the pixels that are at most 1 distance to the right of the given pixel
    private Iterable<Integer> rightPixels(int row, int col) {
        ArrayDeque<Integer> adj = new ArrayDeque<>();
        if (col == width() - 1)     return adj;
        if (row > 0)                adj.add(ijToIndex(row - 1, col + 1));
        if (row < height() - 1)     adj.add(ijToIndex(row + 1, col + 1));
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
        String message = "%s is out of bounds: '%d' must be within 0 - '%d' ";
        if (x < 0 || x >= picture.width())
            throw new IllegalArgumentException(String.format(message, "x",x, picture.width() - 1));
        if (y < 0 || y >= picture.height())
            throw new IllegalArgumentException(String.format(message, "y",y, picture.height() - 1));
    }


    //  unit testing (optional)
    public static void main(String[] args) {

    }
}