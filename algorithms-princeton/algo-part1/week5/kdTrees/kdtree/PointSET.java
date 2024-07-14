import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

import java.util.TreeSet;

public class PointSET {
    private final TreeSet<Point2D> pointSet;
    // construct an empty set of points
    public PointSET() {
        // a treeSet ordered with x vals
        pointSet = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    // number of points in the set
    public int size() {
        return pointSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("cannot insert null items");
        pointSet.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("cannot lookup null items");
        return pointSet.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : pointSet) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("cannot call range with null args");
        Queue<Point2D> q = new Queue<>();
        for (Point2D p : pointSet) {
            if (rect.contains(p)) {
                q.enqueue(p);
            }
        }
        return q;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("cannot call nearest with null args");
        Point2D champ = null;
        for (Point2D that : pointSet) {
            // pick the nearest to p
            if (champ == null || champ.distanceSquaredTo(p) > that.distanceSquaredTo(p)) {
                champ = that;
            }
        }
        return champ;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        // for each command-line argument
        for (String filename : args) {
            // create a PointSet
            PointSET pSET = new PointSET();

            // read in the points specified in the file
            In in = new In(filename);
            while (!in.isEmpty()) {
                double x = in.readDouble();
                double y = in.readDouble();
                pSET.insert(new Point2D(x, y));
            }

            // draw the PointSET
            pSET.draw();
        }
    }
}































