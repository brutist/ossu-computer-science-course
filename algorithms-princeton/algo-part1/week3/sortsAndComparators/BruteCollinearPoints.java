import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

    private LineSegment[] collinearSegments;
    private int colSegCount;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        // throw an Illegal argument exception if points is null, or it contains null items
        checkNull(points);

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

                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Point[] should not contain repeated points");
                }
                // points[i] must be the lowest to avoid duplicates
                int ij = points[i].compareTo(points[j]);
                if (ij >= 0) {
                    continue;
                }

                // calculate the slope that points[j] makes with points[i]
                Double jSlope = points[i].slopeTo(points[j]);

                for (int k = 0; k < N; k++) {
                    if (k == j || k == i) {
                        continue;
                    }
                    // calculate the slope that points[k] makes with points[i]
                    Double kSlope = points[i].slopeTo(points[k]);
                    boolean equalJKSlope = jSlope.equals(kSlope);
                    int jk = points[j].compareTo(points[k]);

                    // no need to check other points if not equal
                    if (!equalJKSlope || jk >= 0) {
                        continue;
                    }

                    for (int m = 0; m < N; m++) {
                        if (m == i || m == j || m == k) {
                            continue;
                        }

                        Double mSlope = points[i].slopeTo(points[m]);
                        // a line segment is formed when the slope formed by Point p to
                        //   other Points q, r, s are equal
                        boolean lineSegFormed = jSlope.equals(mSlope);
                        // true if we have already found a line whose endpoints are i and m
                        boolean endPointsIncluded = endPoints[i] == m || endPoints[m] == i;

                        // check if the formed segment is valid
                        //   a segment is valid if it is either decreasing or increasing
                        //   i.e. if a segment p -> q -> r -> s was found, make sure it is impossible
                        //        to mistakenly add q -> p -> r -> s (not a valid segment)
                        // vertical and horizontal segments are also increasing or decreasing segments
                        //  no need equal points check because equal points are not allowed
                        boolean validSegment = points[k].compareTo(points[m]) < 0;

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
    private void checkNull(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("argument should not be null");
        }
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("argument should not contain any null items");
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
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        BruteCollinearPoints bruteforce = new BruteCollinearPoints(points);
        LineSegment[] segments = bruteforce.segments();
        System.out.printf("Segments found: %s\n", segments.length);
        for (LineSegment s : segments) {
            System.out.println(s);
        }


    }
}
