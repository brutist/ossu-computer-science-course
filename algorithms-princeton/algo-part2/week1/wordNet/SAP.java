import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private final Digraph G;
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null)  throw new IllegalArgumentException("SAP cannot process null args");

        DirectedCycle checkCycle = new DirectedCycle(G);
        if (checkCycle.hasCycle())
            throw new IllegalArgumentException("SAP cannot process cyclic graphs");


        this.G = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        checkVertex(v);
        checkVertex(w);

    }

    // a common ancestor of v and w that participates in the shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        checkVertex(v);
        checkVertex(w);

    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {

    }

    // a common ancestor that participates in the shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {

    }

    // throws an error if the given vertex is outside the graph vertex range
    private void checkVertex(int v) {
        if (v < 0 || v > G.V())
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
