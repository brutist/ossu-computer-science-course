import java.util.ArrayList;

public class BruteCollinearPoints {

    ArrayList<LineSegment> collinearSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("constructor only accepts non-empty Point[]");
        }

        // (will) contain all the line segments that are made from collinear points
        collinearSegments = new ArrayList<>();

        int N = points.length;
        for (int i = 0; i < N; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("Point[] should not contain null items");
            }

            for (int j = 0; j < N; j++) {
                if (j == i) {
                    continue;
                }
                else if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Point[] should not contain repeated points");
                }

                for (int k = 0; k < N; k++) {
                    if (k == j || k == i) {
                        continue;
                    }

                    for (int m = 0; m < N; m++) {
                        if (m == i || m == j || m == k) {
                            continue;
                        }



                        // TODO check for collinearity here!!
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {

    }

    // the line segments
    public LineSegment[] segments() {

    }
}
