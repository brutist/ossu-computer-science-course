import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.Digraph;
import java.util.ArrayDeque;

public class SAP {
    private final BreadthFirstDirectedPaths bfsRootPaths;             // bfs path from root
    private final BreadthFirstDirectedPaths bfsAllVertices;           // bfs path from all vertices
    private final int root;                                           // root of the DAG
    private final int V;                                              // max vertex
    private final Digraph Graph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null)  throw new IllegalArgumentException("SAP cannot process null args");

        // create a deep copy of the mutable digraph
        G = new Digraph(G);
        Graph = G;

        // pick a root, if the graph is a DAG, picks THE root
        int r = 0;
        this.V = Graph.V();
        ArrayDeque<Integer> allVertices = new ArrayDeque<>();
        for (int i = 0; i < this.V; i++) {
            if (Graph.outdegree(i) == 0) {
                r = i;
            }
            allVertices.addLast(i);
        }
        root = r;
        bfsRootPaths = new BreadthFirstDirectedPaths(Graph, root);
        bfsAllVertices = new BreadthFirstDirectedPaths(Graph, allVertices);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        checkVertex(v);
        checkVertex(w);

        int length = 0;
        int UNREACHABLE = -1;
        Integer closestCommonAncestor = ancestor(v, w);

        if (closestCommonAncestor != UNREACHABLE) {
            ArrayDeque<Integer> rootToV = pathToV(v);
            ArrayDeque<Integer> rootToW = pathToV(w);

            // remove all vertices in path from the root until closest ancestor of v
            while (!closestCommonAncestor.equals(rootToV.removeLast())) {
                length++;
            }
            // remove all vertices in path from the root until closest ancestor of w
            while (!closestCommonAncestor.equals(rootToW.removeLast())) {
                length++;
            }
        }
        else {
            length = UNREACHABLE;
        }

        return length;
    }

    // a common ancestor of v and w that participates in the shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        checkVertex(v);
        checkVertex(w);

        int closestCommonAncestor = -1;
        ArrayDeque<Integer> rootToV = pathToV(v);
        ArrayDeque<Integer> rootToW = pathToV(w);

        // the closest common ancestor to v and w is the last similar vertex on the shortest path
        //  of source to v and source to w
        while (!(rootToV.isEmpty() || rootToW.isEmpty()) &&
                 rootToV.peekFirst().equals(rootToW.peekFirst())) {
            closestCommonAncestor = rootToV.removeFirst();
            rootToW.removeFirst();
        }

        return closestCommonAncestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        checkVertices(v);
        checkVertices(w);





    }

    // a common ancestor that participates in the shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        checkVertices(v);
        checkVertices(w);

        int UNREACHABLE = -1;
        int shortestLength = UNREACHABLE;
        int shortestAncestor = UNREACHABLE;

        for (int i : v) {
            for (int j : w) {
                int length = length(i, j);

                if (shortestLength == UNREACHABLE || length < shortestLength) {
                    shortestLength = length;
                    shortestAncestor = ancestor(i, j);
                }
            }
        }
        return  shortestAncestor;
    }

    // returns a queue of vertices that is the shortest path from the root to vertex v
    private ArrayDeque<Integer> pathToV(int v) {
        ArrayDeque<Integer> q = new ArrayDeque<>();
        if (bfsRootPaths.hasPathTo(v)) {
            for (Integer w : bfsRootPaths.pathTo(v))
                q.addFirst(w);
        }
        return q;
    }

    // throws an error if the given vertex is outside the graph vertex range
    private void checkVertex(int v) {
        if (v < 0 || v > V)
            throw new IllegalArgumentException("vertex given is outside the Graph's range");
    }

    private void checkVertices(Iterable<Integer> vertices) {
        for (Integer v : vertices) {
            if (v == null)
                throw new IllegalArgumentException("vertices should not contain null items");
            checkVertex(v);
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
