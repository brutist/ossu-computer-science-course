import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private final Picture originalPicture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null)
            throw new IllegalArgumentException("cannot have null args in SeamCarver constructor");

        originalPicture = picture;
    }

    // current picture
    public Picture picture() {

    }

    // width of current picture
    public int width() {
        return originalPicture.width();
    }

    // height of current picture
    public int height() {
        return originalPicture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        validatePixelPosition(x, y);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {

    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {

    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        validateSeam(seam);
        if (originalPicture.width() <= 1)
            throw new IllegalArgumentException("cannot horizontally resize image with width of 1");

    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        validateSeam(seam);
        if (originalPicture.height() <= 1)
            throw new IllegalArgumentException("cannot vertically resize image with height of 1");

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
        if (x < 0 || x >= originalPicture.width())
            throw new IllegalArgumentException(String.format(message, x, originalPicture.width()));
        if (y < 0 || y >= originalPicture.height())
            throw new IllegalArgumentException(String.format(message, x, originalPicture.height()));
    }

    //  unit testing (optional)
    public static void main(String[] args) {

    }
}
