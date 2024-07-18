import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Digraph;

public class SAP {
    private int root;                                               // root of the DAG
    private int V;                                                  // max vertex
    private final BreadthFirstDirectedPaths shortestPathsFromRoot;  // the shortest paths from root to other vertex

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null)  throw new IllegalArgumentException("SAP cannot process null args");

        for (int v = 0; v < G.V(); v++) {
            if (G.outdegree(v) == 0) {
                root = v;
            }
        }

        shortestPathsFromRoot = new BreadthFirstDirectedPaths(G, root);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        checkVertex(v);
        checkVertex(w);

        int UNREACHABLE = -1;
        int closestCommonAncestor = ancestor(v, w);
        // return -1 if there is no common ancestor between the two vertices
        if (closestCommonAncestor == UNREACHABLE)
            return UNREACHABLE;
        else



    }

    // a common ancestor of v and w that participates in the shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        checkVertex(v);
        checkVertex(w);

        int closestCommonAncestor = -1;
        int[] fromV = pathToRoot(v);
        int[] fromW = pathToRoot(w);
        int i = fromV.length;
        int j = fromW.length;


        return closestCommonAncestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {

    }

    // a common ancestor that participates in the shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {

    }

    // assumes that there is path from v to the root
    // returns that path in an array format
    private int[] pathToRoot(int v) {
        int[] fromV = new int[shortestPathsFromRoot.distTo(v)];
        int i = fromV.length;
        for (int w : shortestPathsFromRoot.pathTo(v)) {
            fromV[i--] = w;
        }
        return fromV;
    }

    // throws an error if the given vertex is outside the graph vertex range
    private void checkVertex(int v) {
        if (v < 0 || v > V)
            throw new IllegalArgumentException("vertex given is outside the Graph's range");
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
