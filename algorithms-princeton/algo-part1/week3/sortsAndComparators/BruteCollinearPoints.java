public class BruteCollinearPoints {

    LineSegment[] collinearSegments;
    int colSegCount;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("constructor only accepts non-empty Point[]");
        }

        // (will) contain all the line segments that are made from collinear points
        //   because segments need to be made of at least 4 points and subsegments are not allowed,
        //   there could only be at max (n/4 + 1) line segments given n-points (this is a proposition)
        collinearSegments = new LineSegment[(points.length / 4) + 1];
        colSegCount = 0;
        // ith index represent points[i], true if point[i] is already made to a segment
        boolean[] includedEndPoints = new boolean[points.length];

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
                        // true if this line segment was already found
                        boolean endPointsIncluded = includedEndPoints[i] && includedEndPoints[m];

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
                            includedEndPoints[i] = true;
                            includedEndPoints[m] = true;
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
        Point a = new Point(0, -3);  // a -> d are collinear
        Point b = new Point(2, -2);
        Point c = new Point(4, -1);
        Point d = new Point(6, 0);

        Point e = new Point(2, -3); // e-b-f-g are collinear
        Point f = new Point(2, 0);
        Point g = new Point(2, -1);

        Point h = new Point(6, 1); // d-h-i-j are collinear
        Point i = new Point(6, 2);
        Point j = new Point(6, 3);

        Point k = new Point(79, 89); // not collinear
        Point l = new Point(53, 59);

        // simple tests for finding collinear segments
        Point[] points = {a, b, c, d, e, f, g, h, i, j, k, l};
        BruteCollinearPoints bruteForce = new BruteCollinearPoints(points);
        System.out.printf("Number of segments found: %d\n", bruteForce.numberOfSegments());
        for (LineSegment ls : bruteForce.segments()) {
            System.out.println(ls);
        }

        // testing IllegalArgumentException
        Point[] nullConstructor = null;
        Point[] arrayContainsNull = {a, b, null};
        Point[] arrayContainsRepeat = {a, a, b, c, d};
        Point[][] exceptionTests = {nullConstructor, arrayContainsNull, arrayContainsRepeat};

        for (Point[] exceptionTest : exceptionTests) {
            try {
                BruteCollinearPoints bf = new BruteCollinearPoints(exceptionTest);
            } catch (IllegalArgumentException x) {
                System.out.println("IllegalArgumentException tests PASSED");
            }
        }

    }
}
