import edu.princeton.cs.algs4.Picture;

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
    public double energy(int col, int row) {
        validatePixelPosition(col, row);

        // energy of pixel at border is 1000
        if (col == 0 || col == picture.width() - 1)     return 1000.0;
        if (row == 0 || row == picture.height() - 1)    return 1000.0;

        int a = picture.getRGB(col - 1, row);       // left-side pixel
        int b = picture.getRGB(col + 1, row);       // right-side pixel
        int c = picture.getRGB(col, row - 1);       // top pixel
        int d = picture.getRGB(col, row + 1);       // bottom pixel

        return Math.sqrt(calculateDiff(a, b) + calculateDiff(c, d));
    }

    private int calculateDiff(int a, int b) {
        int aRed = (a >> 16) & 0xFF, aGreen = (a >> 8) & 0xFF, aBlue = a & 0xFF;
        int bRed = (b >> 16) & 0xFF, bGreen = (b >> 8) & 0xFF, bBlue = b & 0xFF;

        int xRed = (aRed - bRed);
        int xGreen = (aGreen - bGreen);
        int xBlue = (aBlue - bBlue);

        return (xRed * xRed) + (xBlue * xBlue) + (xGreen * xGreen);
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int totalPixels = width() * height();
        int[] pixelTo = new int[totalPixels];
        double[] distTo = new double[totalPixels];
        for (int i = width(); i < totalPixels; i++)
            distTo[i] = Double.POSITIVE_INFINITY;

        // find the shortest path from top to bottom
        for (int i = 0; i < totalPixels; i++) {
            int row = i / width(), col = i % width();
            // relax edges below this one
            for (int to : bottomPixels(col, row))
                relax(pixelTo, distTo, col, row, to);
        }

        // find the bottom row pixel that has the lowest distance from the top row
        double minDist = Double.POSITIVE_INFINITY;
        int lastPixelIndex = 0;
        for (int br = (ijToIndex(0, height() - 1)); br < totalPixels; br++) {
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

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int totalPixels = width() * height();
        int[] pixelTo = new int[totalPixels];
        double[] distTo = new double[totalPixels];
        for (int i = 0; i < totalPixels; i++)
            // all pixels are infinitely far away except for the first column
            if (i % width() != 0)
                distTo[i] = Double.POSITIVE_INFINITY;

        // find the shortest path from left to right
        for (int col = 0; col < width(); col++) {
            for (int row = 0; row < height(); row++) {
                // relax edges at the right of this one
                for (int to : rightPixels(col, row))
                    relax(pixelTo, distTo, col, row, to);
            }
        }

        // find the left-most pixel index that has the least distance
        double minDist = Double.POSITIVE_INFINITY;
        int lastPixelIndex = 0;
        for (int lr = width() - 1; lr < totalPixels; lr += width()) {
            if (distTo[lr] < minDist) {
                minDist = distTo[lr];
                lastPixelIndex = lr;
            }
        }

        // create the seam, by tracing parent link of pixel to
        int[] horizontalSeam = new int[width()];
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

    private void relax(int[] pixelTo, double[] distTo, int col, int row, int to) {
        int from = ijToIndex(col, row);
        int colTo = to % width();
        int rowTo = to / width();
        double energyTo = energy(colTo, rowTo);
        if (energyTo + distTo[from] < distTo[to]) {
            distTo[to] = energyTo + distTo[from];
            pixelTo[to] = from;
        }
    }

    // returns the index of the pixels that are at most 1 distance to the bottom of the given pixel
    private Iterable<Integer> bottomPixels(int col, int row) {
        ArrayDeque<Integer> adj = new ArrayDeque<>();
        if (row == height() - 1)        return adj;
        if (col > 0)                    adj.add(ijToIndex(col - 1, row + 1));
        if (col + 1 < width() - 1)      adj.add(ijToIndex(col + 1, row + 1));
        adj.add(ijToIndex(col, row + 1));

        return adj;
    }

    // returns the index of the pixels that are at most 1 distance to the right of the given pixel
    private Iterable<Integer> rightPixels(int col, int row) {
        ArrayDeque<Integer> adj = new ArrayDeque<>();
        if (col == width() - 1)         return adj;
        if (row > 0)                    adj.add(ijToIndex(col + 1, row - 1));
        if (row + 1 < height() - 1)     adj.add(ijToIndex(col + 1, row + 1));
        adj.add(ijToIndex(col + 1, row));

        return adj;
    }

    // convert row and columns to index (both are 0-based)
    private int ijToIndex(int col, int row) {
        return (row * width()) + col;
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

    private void validatePixelPosition(int col, int row) {
        String message = "%s is out of bounds: '%d' must be within 0 - '%d' ";
        if (col < 0 || col >= picture.width())
            throw new IllegalArgumentException(String.format(message, "x", col, picture.width() - 1));
        if (row < 0 || row >= picture.height())
            throw new IllegalArgumentException(String.format(message, "y", row, picture.height() - 1));
    }


    //  unit testing (optional)
    public static void main(String[] args) {

    }
}