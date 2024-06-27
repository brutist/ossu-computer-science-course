import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    ArrayList<LineSegment> lineSegmentsFound;
    ArrayList<Point> maxEndpoints;
    ArrayList<Point> minEndpoints;
    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        // throw an exception if points is null or it contains null
        checkNull(points);


        // (will) contain all the maximal line segments that are made from collinear points.
        //   segments can be made of at the very least 3 unique points and because subsegments are not allowed,
        //   there could only be at max (n/3 + 1) line segments given n-points (this is a proposition)
        lineSegmentsFound = new ArrayList<>();
        maxEndpoints = new ArrayList<>();
        minEndpoints = new ArrayList<>();

        // pick an origin point
        for (int i = 0; i < points.length; i++) {

            // array points which will be sorted according the slope they make with point[i]
            Point[] sortedPoints = new Point[points.length - 1];
            int OPIndex = 0;

            // iterate to other points and sort according to the slope they make with point[i]
            for (int j = 0; j < points.length; j++) {
                if (j == i) {
                    continue;
                }

                if (points[j].equals(points[i])) {
                    throw new IllegalArgumentException("Point[] should not contain repeated points");
                }

                // populate an array of other points
                sortedPoints[OPIndex] = points[j];
                OPIndex++;

            }

            // sort the array of other points by slope they make with the origin point (point[i])
            Arrays.sort(sortedPoints, points[i].slopeOrder());
            // mutate the maximalLineSegments instance variable to include valid line segments found
            findSegments(sortedPoints, points[i]);
        }
    }

    // directly mutates the instance variable maximalLineSegments to include all valid segments found
    private void findSegments(Point[] sortedPoints, Point origin) {
        // find the line segments formed by 3 or more collinear points (adjacent points with equal
        //   slopes to points[i])


        for (int k = 2; k < sortedPoints.length; k++) {
            Point minPoint = origin;
            Point maxPoint = origin;
            int equalPoints = 1;
            int current = k;
            int prev = k - 1;
            Point currentPoint = sortedPoints[current];
            Point prevPoint = sortedPoints[prev];
            Double currentPointSlope = origin.slopeTo(currentPoint);
            Double prevPointSlop = origin.slopeTo(prevPoint);


            while (currentPointSlope.equals(prevPointSlop)) {
                if (currentPoint.compareTo(minPoint) < 0) {
                    minPoint = currentPoint;
                }
                if (currentPoint.compareTo(maxPoint) > 0) {
                    maxPoint = currentPoint;
                }
                System.out.printf("%s -> ", currentPoint);
                equalPoints++;
                current--;
                prev--;
            }

            boolean validCollinearPoints = equalPoints >= 3;

            System.out.printf(" %s ::", origin);
            boolean validEndpoints = !endPointsFound(minPoint, maxPoint);
            if (validCollinearPoints && validEndpoints) {
                lineSegmentsFound.add(new LineSegment(minPoint, maxPoint));

                // update record of endpoints
                minEndpoints.add(minPoint);
                maxEndpoints.add(maxPoint);
            }

            System.out.print("\n");

        }
    }

    private boolean endPointsFound(Point min, Point max) {
        int i = 0;
        int len = minEndpoints.size();
        boolean endPointsFound = false;

        for (; i < len; i++) {
            /* critical - a point could be the minimum for several line segments, therefore the search
             *            for the pair should be exhaustive  */
            if (minEndpoints.get(i).equals(min)) {
                endPointsFound = endPointsFound || maxEndpoints.get(i).equals(max);
            }
        }
        return endPointsFound;
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
        System.out.println("Segments found : ");
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
