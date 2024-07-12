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
            nodeRect = new RectHV(node.rect.xmin(), node.rect.ymin(), nodePos[(level + 1) % D], key[d]);
            //  TODO figure out how to make the fucking node rectangle.
            node.lb = insert(node.lb, key, level + 1, nodeRect);
        }
        else if (cmp > 0) {
            // TODO
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
        //TODO define what is rect, this critical to calculating if a point is inside the rect
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

        if (queryPos[d] < nodePos[d])   closer = nearest(query, node.lb, champion, level + 1);
        else                            closer = nearest(query, node.rt, champion, level + 1);

        // no need to check farther subtree if there is a closer point in the closer subtree
        if (closer != null && closer.distanceSquaredTo(query) < champion.distanceSquaredTo(query))
            champion = closer;
        // look at the farther subtree if the closest point discovered so far is farther than
        //  the query point and the rectangle corresponding to the node
        boolean rightNodeCloser = champion.distanceSquaredTo(query) > node.rt.rect.distanceSquaredTo(query);
        boolean leftNodeCloser = champion.distanceSquaredTo(query) > node.lb.rect.distanceSquaredTo(query);
        if (rightNodeCloser && queryPos[d] < nodePos[d])
            champion = nearest(query, node.rt, champion, level + 1);
        else if (leftNodeCloser && queryPos[d] > nodePos[d])
            champion = nearest(query, node.lb, champion, level + 1);

        return champion;
        // TODO Im not pruning trees yet.
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}




