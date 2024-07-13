import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Arrays;

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
        if (p == null)      return;
        if (contains(p))    return;

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
        if (cmp < 0) {
            // calculate the rect of the key at the last moment
            if (node.lb == null) {
                double xMax;
                double yMax;
                if (d == 1) {
                    // insert vertical node
                    xMax = node.rect.xmax();
                    yMax = nodePos[1];
                }
                else {
                    // insert horizontal node
                    xMax = nodePos[0];
                    yMax = node.rect.ymax();
                }
                rect = new RectHV(node.rect.xmin(), node.rect.ymin(), xMax, yMax);
            }

            node.lb = insert(node.lb, key, level + 1, rect);
        }
        else if (cmp > 0) {
            // calculate the rect of the key at the last moment
            if (node.rt == null) {
                double xMin;
                double yMin;
                if (d == 1) {
                    // insert vertical node
                    xMin = node.rect.xmin();
                    yMin = nodePos[1];
                }
                else {
                    // insert horizontal node
                    xMin = nodePos[0];
                    yMin = node.rect.ymin();
                }
                rect = new RectHV(xMin, yMin, node.rect.xmax(), node.rect.ymax());
            }
            node.rt = insert(node.rt, key, level + 1, rect);
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
        Node search;
        if (cmp == 0 && Arrays.equals(nodePos, key)) {
            return node;
        }
        else if (cmp == 0) {
            search = contains(node.rt, key, level + 1);
            double[] searchPos = new double[D];
            if (search != null) {
                searchPos[0] = search.p.x();
                searchPos[1] = search.p.y();
            }
            if (!Arrays.equals(searchPos, key))
                search = contains(node.lb, key, level + 1);
        }
        else if (cmp > 0)   search = contains(node.rt, key, level + 1);
        else                search = contains(node.lb, key, level + 1);

        return search;
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

        // draw the left/bottom node
        draw(node.lb, level + 1);
        // draw the right/top node
        draw(node.rt, level + 1);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)   throw new IllegalArgumentException("cannot call range with null args");

        Queue<Point2D> q = new Queue<>();
        range(q, root, rect);
        return q;
    }

    private void range(Queue<Point2D> q, Node node, RectHV rect) {
        if (node == null)   return;

        if (rect.contains(node.p)) {
            q.enqueue(node.p);
        }
        if (node.rt != null && rect.intersects(node.rt.rect)) {
            // recursively search right if key could only be in right partition
            range(q, node.rt, rect);
        }
        if (node.lb != null && rect.intersects(node.lb.rect)) {
            // recursively search left if key could only be in left partition
            range(q, node.lb, rect);
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

        if (node.p.distanceSquaredTo(query) < champion.distanceSquaredTo(query)) {
            champion = node.p;
        }
        // check the closer subtree first
        if (queryPos[d] < nodePos[d])   champion = nearest(query, node.lb, champion, level + 1);
        else                            champion = nearest(query, node.rt, champion, level + 1);

        // look at the farther subtree if the closest point discovered so far is farther than
        //  the query point and the rectangle corresponding to the node
        boolean rightTreeCloser = node.rt != null &&
                node.rt.rect.distanceSquaredTo(query) <= champion.distanceSquaredTo(query);
        boolean leftTreeCloser = node.lb != null &&
                node.lb.rect.distanceSquaredTo(query) <= champion.distanceSquaredTo(query);
        if (rightTreeCloser && queryPos[d] < nodePos[d])
            champion = nearest(query, node.rt, champion, level + 1);

        else if (leftTreeCloser && queryPos[d] > nodePos[d]) {
            champion = nearest(query, node.lb, champion, level + 1);
        }

        return champion;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        // TODO methods that must be revised in PointSET
        //      contains()
        //      nearest()
        //      size() - something like off by 1 error

        // TODO methods to revise in KdTree
        //      size() - off by 1 error
        //      contains()
        //      range()
        //      nearest()
    }
}




