import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {
    private Node root;
    private int size;

    // the nodes of the kdTree
    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
    }

    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (contains(p))    return;

    }

    // does the set contain point p?
    public boolean contains(Point2D p) {

    }

    // draw all points to standard draw
    public void draw() {
        draw(root);
    }

    // TODO must draw the red/blue subdivisions as well
    private void draw(Node node) {
        if (node == null)   return;
        // draw the node
        node.p.draw();
        // draw the left/bottom node
        draw(node.lb);
        // draw the right/top node
        draw(node.rt);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {

    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {

    }
    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}