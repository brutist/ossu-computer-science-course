import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {
    private Node root;          // root node of tree
    private int size;           // size of tree
    private final int D = 2;    // no. of dimensions

    // the nodes of the kdTree
    private static class Node {
        private final Point2D p;    // the point
        private RectHV rect;        // the axis-aligned rectangle corresponding to this node
        private Node lb;            // the left/bottom subtree
        private Node rt;            // the right/top subtree

        public Node(Point2D point) {
            p = point;
            // TODO figure out how to calculate rect???
        }
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
        if (p == null)  return;

        double[] key = {p.x(), p.y()};
        root = insert(root, key, 0);
        size++;
    }

    private Node insert(Node node, double[] key, int level) {
        if (node == null)  return new Node(new Point2D(key[0], key[1]));

        double[] nodePos = {node.p.x(), node.p.y()};
        int cmp = Double.compare(key[level % D], nodePos[level % D]);
        if (cmp < 0)        node.lb = insert(node.lb, key, level + 1);
        else if (cmp > 0)   node.rt = insert(node.rt, key, level + 1);

        // just return this node if there is a node containing the same position, do not add because
        //      this is a SET of points (no duplicates)
        return node;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        double[] pos = {p.x(), p.y()};
        return contains(root, pos,0) != null;
    }

    private Node contains(Node node, double[] key, int level) {
        if (node == null)   return null;

        // simplify whether x or y-comparison
        double[] nodePos = {node.p.x(), node.p.y()};
        int cmp = Double.compare(key[level % D], nodePos[level % D]);
        if (cmp == 0)       return node;
        else if (cmp > 0)   return contains(node.rt, key, level + 1);
        else                return contains(node.lb, key, level + 1);
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
        if (rect == null)   throw new IllegalArgumentException("cannot call range with null args");

        Queue<Point2D> q = new Queue<>();
        range(q, root, rect, 0);
        return q;
    }

    private void range(Queue<Point2D> q, Node node, RectHV rect, int level) {
        if (node == null)   return;

        double[] key = {node.p.x(), node.p.y()};
        double[] maxRect = {rect.xmax(), rect.ymax()};
        double[] minRect = {rect.xmin(), rect.ymin()};
        int d = level % D;    // discriminator
        if (rect.contains(node.p)) {
            q.enqueue(node.p);
            // recursively search left and right if the line is within the rectangle
            range(q, node.lb, rect, level + 1);
            range(q, node.rt, rect, level + 1);
        }
        else if (key[d] > maxRect[d]) {
            // recursively search right if key could only be in right partition
            range(q, node.rt, rect, level + 1);
        }
        else if (key[d] < minRect[d]) {
            // recursively search left if key could only be in left partition
            range(q, node.lb, rect, level + 1);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)  throw new IllegalArgumentException("cannot call nearest on null arguments");

        // start at the root and point at root is also the nearest point
        return nearest(p, root, root.p, 0);
    }

    private Point2D nearest(Point2D query, Node node, Point2D nearest, int level) {
        if (node == null)   return nearest;

        double[] nodePos = {node.p.x(), node.p.y()};
        double[] nearestPos = {nearest.x(), nearest.y()};
        double[] queryPos = {query.x(), query.y()};
        int d = level % D;  // discriminator

        if (node.p.distanceSquaredTo(query) < nearest.distanceSquaredTo(query)) {
            // if the nearest is to the left of the current nearest and the previous nearest then
            //      search only the left of the current nearest
            if (nodePos[d] < nearestPos[d])         nearest = nearest(query, node.lb, node.p, level + 1);
            // if the nearest is to the right of the current nearest and the previous nearest then
            //      search only the right of the current nearest
            else if (nodePos[d] > nearestPos[d])    nearest = nearest(query, node.rt, node.p, level + 1);
        }
        else {
            if (nodePos[d] < nearestPos[d])     nearest = nearest()
        }

        return nearest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}


























