import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

    private LineSegment[] collinearSegments;
    private int colSegCount;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("constructor only accepts non-empty Point[]");
        }

        // (will) contain all the line segments that are made from collinear points
        //   because segments need to be made of at least 4 points and subsegments are not allowed,
        //   there could only be at max (n/4 + 1) line segments given n-points (this is a proposition)
        collinearSegments = new LineSegment[(points.length / 3) + 1];
        colSegCount = 0;
        // ith index contains it endpoints
        int[] endPoints = new int[points.length];
        // endpoint of ith element is i
        for (int i = 0; i < endPoints.length; i++) {
            endPoints[i] = i;
        }

        int N = points.length;
        for (int i = 0; i < N; i++) {

            for (int j = 0; j < N; j++) {
                if (j == i) {
                    continue;
                }

                if (points[j] == null) {
                    throw new IllegalArgumentException("Point[] should not contain null items");
                }

                if (points[i].compareTo(points[j]) == 0) {
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

                        Double jSlope = points[i].slopeTo(points[j]);
                        Double kSlope = points[i].slopeTo(points[k]);
                        Double mSlope = points[i].slopeTo(points[m]);
                        // a line segment is formed when the slope formed by Point p to
                        //   other Points q, r, s are equal
                        boolean lineSegFormed = jSlope.equals(kSlope) && jSlope.equals(mSlope);
                        // true if we have already found a line whose endpoints are i and m
                        boolean endPointsIncluded = endPoints[i] == m || endPoints[m] == i;

                        // check if the formed segment is valid
                        //   a segment is valid if it is either decreasing or increasing
                        //   i.e. if a segment p -> q -> r -> s was found, make sure it is impossible
                        //        to mistakenly add q -> p -> r -> s (not a valid segment)
                        int ij = points[i].compareTo(points[j]);
                        int jk = points[j].compareTo(points[k]);
                        int km = points[k].compareTo(points[m]);

                        // vertical and horizontal segments are also increasing or decreasing segments
                        //  no need equal points check because equal points are not allowed
                        boolean increasingSegment = ij < 0 && jk < 0 && km < 0;
                        boolean decreasingSegment = ij > 0 && jk > 0 && km > 0;
                        boolean validSegment = increasingSegment || decreasingSegment;

                        if (lineSegFormed && validSegment && (!endPointsIncluded)) {
                            if (endPoints[i] != m) {
                                endPoints[i] = m;
                            }
                            else if (endPoints[m] != i) {
                                endPoints[m] = i;
                            }

                            // include this collinear segment
                            collinearSegments[colSegCount] = new LineSegment(points[i], points[m]);
                            colSegCount++;
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return colSegCount;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] result = new LineSegment[colSegCount];
        for (int i = 0; i < colSegCount; i++) {
            if (collinearSegments[i] != null) {
                result[i] = collinearSegments[i];
            }
        }
        return result;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        int maxX = 0;
        int maxY = 0;
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();

            if (Math.abs(x) > maxX) {
                maxX = Math.abs(x);
            }
            if (Math.abs(y) > maxY) {
                maxY = Math.abs(y);
            }

            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, maxX);
        StdDraw.setYscale(0, maxX);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
