import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> lineSegmentsFound;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        // throw an exception if points is null, or it contains null
        checkNull(points);

        // sort the array for easier checks for duplicates later
        Point[] sortedPoints = new Point[points.length];
        System.arraycopy(points, 0, sortedPoints, 0, points.length);
        Arrays.sort(sortedPoints);

        // (will) contain all the maximal line segments that are made from collinear points.
        //   segments can be made of at the very least 3 unique points and because subsegments are
        //   not allowed, there could only be at max (n/3 + 1) line segments given n-points
        //   (this is a proposition)
        lineSegmentsFound = new ArrayList<>();

        // pick an origin point
        for (int i = 0; i < sortedPoints.length; i++) {

            // array points which will be sorted according the slope they make with point[i]
            Point[] otherPoints = new Point[sortedPoints.length - 1];
            int OPIndex = 0;

            // iterate to other points and sort according to the slope they make with point[i]
            for (int j = 0; j < sortedPoints.length; j++) {
                if (j == i) {
                    continue;
                }

                if (sortedPoints[j].equals(sortedPoints[i])) {
                    throw new IllegalArgumentException("Point[] should not contain repeated points");
                }

                // populate an array of other points
                otherPoints[OPIndex] = sortedPoints[j];
                OPIndex++;
            }

            // sort the array of other points by slope they make with the origin point (point[i])
            Arrays.sort(otherPoints, sortedPoints[i].slopeOrder());
            // mutate the maximalLineSegments instance variable to include valid line segments found
            findSegments(otherPoints, sortedPoints[i]);
        }
    }

    // directly mutates the instance variable maximalLineSegments to include all valid segments found
    private void findSegments(Point[] otherPoints, Point origin) {
        int validPoints = 1;
        int minIndex = 0;
        for (int i = 1; i < otherPoints.length; i++) {
            // check for equal slopes
            Double currSlope = origin.slopeTo(otherPoints[i]);
            Double prevSlope = origin.slopeTo(otherPoints[i - 1]);
            // keep track of the minIndex for each set of collinear points
            if (currSlope.equals(prevSlope)) {
                // the origin must be the smallest among all the other points in the collinear points,
                //    if not then this is already a duplicate
                boolean originMin = origin.compareTo(otherPoints[minIndex]) < 0;
                if (originMin) {
                    validPoints++;
                }
                // check for the last element
                if (validPoints >= 3 && i == otherPoints.length - 1) {
                    lineSegmentsFound.add(new LineSegment(origin, otherPoints[i]));
                }
            }

            else {
                // only looking for line segments with at least 4 points (this includes the origin point)
                if (validPoints >= 3) {
                    lineSegmentsFound.add(new LineSegment(origin, otherPoints[i - 1]));
                }
                validPoints = 1;
                minIndex = i;
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
        return lineSegmentsFound.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] segmentsFound = new LineSegment[lineSegmentsFound.size()];
        return lineSegmentsFound.toArray(segmentsFound);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        System.out.printf("Segments found : %s\n", collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
