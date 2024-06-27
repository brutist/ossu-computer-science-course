import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    LineSegment[] maximalLineSegments;
    int maximalLineSegmentsCounter;
    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

        if (points == null) {
            throw new IllegalArgumentException("constructor only accepts non-empty Point[]");
        }

        // (will) contain all the maximal line segments that are made from collinear points
        //   because segments need to be made of at least 4 points and subsegments are not allowed,
        //   there could only be at max (n/4 + 1) line segments given n-points (this is a proposition)
        maximalLineSegments = new LineSegment[(points.length / 4) + 1];
        maximalLineSegmentsCounter = 0;
        // ith-index contains a Point that is its endpoint, it's initial endpoint is the points[i]
        Point[] endPoints = new Point[points.length];
        System.arraycopy(points, 0, endPoints, 0, points.length);


        // pick an origin point
        for (int i = 0; i < points.length; i++) {

            // array points which will be sorted according the slope they make with point[i]
            Point[] otherPoints = new Point[points.length - 1];
            int OPIndex = 0;

            // iterate to other points and sort according to the slope they make with point[i]
            for (int j = 0; j < points.length; j++) {
                if (points[j] == null) {
                    throw new IllegalArgumentException("Point[] should not contain null items");
                }

                if (j == i) {
                    continue;
                }

                if (points[j] == points[i]) {
                    throw new IllegalArgumentException("Point[] should not contain repeated points");
                }

                // populate an array of other points
                otherPoints[OPIndex] = points[j];
                OPIndex++;

            }
            // sort the array of other points by slope they make with the origin point (point[i])
            Arrays.sort(otherPoints, points[i].slopeOrder());

            // find the line segments formed by 3 or more collinear points
            for (int k = 0; k < otherPoints.length - 1; k++) {
                Double currentPointSlope = points[i].slopeTo(points[k]);
                Double nextPointSlope = points[i].slopeTo(points[k + 1]);


                if (currentPointSlope.equals(nextPointSlope)) {

                }



            }


        }
    }


    // the number of line segments
    public int numberOfSegments() {
        return maximalLineSegmentsCounter;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] result = new LineSegment[maximalLineSegmentsCounter];
        int n = maximalLineSegmentsCounter;

        for (int i = 0; i < n; i++) {
            if (maximalLineSegments[i] != null) {
                result[i] = maximalLineSegments[i];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Point[][] test = new Point[4][4];
        for (int i = 0; i < test.length; i++) {
            System.out.print("First row: ");
            for (int j = 0; j < test[i].length; j++) {
                System.out.printf(" %s ", test[i][j]);
            }
            System.out.print("\n");
        }
    }
}
