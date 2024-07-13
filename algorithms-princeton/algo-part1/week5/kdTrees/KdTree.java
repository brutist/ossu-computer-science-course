import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private Node root;          // root node of tree
    private int size;           // size of tree
    private final int D = 2;    // no. of dimensions

    // the nodes of the kdTree
    private static class Node {
        private final Point2D p;    // the point
        private final RectHV rect;  // the axis-aligned rectangle corresponding to this node
        private Node lb;            // the left/bottom subtree
        private Node rt;            // the right/top subtree

        public Node(Point2D point, RectHV rect) {
            p = point;
            this.rect = rect;
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
        RectHV rect = new RectHV(0, 0, 1, 1);  // algs4 representation of a unit rectangle
        root = insert(root, key, 0, rect);
        size++;
    }

    private Node insert(Node node, double[] key, int level, RectHV rect) {
        if (node == null)  return new Node(new Point2D(key[0], key[1]), rect);

        double[] nodePos = {node.p.x(), node.p.y()};
        int d = level % D;
        int cmp = Double.compare(key[d], nodePos[d]);
        RectHV nodeRect;
        if (cmp < 0) {
            nodeRect = new RectHV(node.rect.xmin(), node.rect.ymin(), nodePos[d], node.rect.ymax());
            node.lb = insert(node.lb, key, level + 1, nodeRect);
        }
        else if (cmp > 0) {
            nodeRect = new RectHV(nodePos[d], node.rect.ymin(), node.rect.xmax(), node.rect.ymax());
            node.rt = insert(node.rt, key, level + 1, nodeRect);
        }

        // just return this node if there is a node containing the same position, do not add because
        //      this is a SET of points (no duplicates)
        return node;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        double[] pos = {p.x(), p.y()};
        return contains(root, pos, 0) != null;
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
        draw(root, 0);
    }

    // TODO must draw the red/blue subdivisions as well
    private void draw(Node node, int level) {
        if (node == null)   return;

        boolean vertical = level % D == 0;
        boolean horizontal = level % D == 1;

        // draw the subdivisions
        StdDraw.setPenRadius();
        if (vertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        }
        if (horizontal) {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        }
        // draw the point
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.p.draw();
        node.rect.draw();

        // draw the left/bottom node
        draw(node.lb, level + 1);
        // draw the right/top node
        draw(node.rt, level + 1);
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

    private Point2D nearest(Point2D query, Node node, Point2D champion, int level) {
        if (node == null)   return champion;

        // there is a point equal to the query
        if (query.equals(node.p))   return node.p;

        // check for more complicated cases
        double[] nodePos = {node.p.x(), node.p.y()};
        double[] queryPos = {query.x(), query.y()};
        int d = level % D;  // discriminator

        Point2D closer;
        if (node.p.distanceSquaredTo(query) < champion.distanceSquaredTo(query)) {
            champion = node.p;
        }
        // check the closer subtree first
        if (queryPos[d] < nodePos[d])   closer = nearest(query, node.lb, champion, level + 1);
        else                            closer = nearest(query, node.rt, champion, level + 1);

        // no need to check farther subtree if there is a closer point is closer on all possible points
        //    in the farther subtree
        if (closer != null && closer.distanceSquaredTo(query) < champion.distanceSquaredTo(query))
            champion = closer;
        // look at the farther subtree if the closest point discovered so far is farther than
        //  the query point and the rectangle corresponding to the node
        if (node.rt != null && node.rt.rect.distanceSquaredTo(query) < champion.distanceSquaredTo(query)) {
            if (queryPos[d] < nodePos[d])       champion = nearest(query, node.rt, champion, level + 1);
        }
        else if (node.lb != null && node.lb.rect.distanceSquaredTo(query) < champion.distanceSquaredTo(query)) {
            if (queryPos[d] > nodePos[d])       champion = nearest(query, node.lb, champion, level + 1);
        }

        return champion;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}




