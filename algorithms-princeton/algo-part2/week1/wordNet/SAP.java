import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.Digraph;
import java.util.HashMap;
import java.util.HashSet;


public class SAP {
    private final Digraph Graph;                    // the digraph
    private final HashMap<Long, int[]> cache;       // key-{length, ancestor} pair
    private final Long emptyKey = (long) -1;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null)  throw new IllegalArgumentException("SAP cannot process null args");

        // create a deep copy of the mutable digraph
        Graph = new Digraph(G);
        cache = new HashMap<>();
        Long empty = (long) -1;
        cache.put(empty, new int[] {-1, -1});
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        long key = sap(v, w);
        return cache.get(key)[0];       // returns the length
    }

    // a common ancestor of v and w that participates in the shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        long key = sap(v, w);
        return cache.get(key)[1];       // returns the ancestor
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        long key = sap(v, w);
        return cache.get(key)[0];       // returns the length
    }

    // a common ancestor that participates in the shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        long key = sap(v, w);
        return cache.get(key)[1];       // returns the ancestor
    }

    // caches the shortest ancestral length and the ancestor of given vertices,
    //      returns the key for the given vertices
    private long sap(int v, int w) {
        checkVertex(v);
        checkVertex(w);

        // check if there is work to do
        long key = produceKey(v, w);
        if (cache.containsKey(key))
            return key;

        // create the shortest paths with given vertices as sources
        BreadthFirstDirectedPaths pathsV = new BreadthFirstDirectedPaths(Graph, v);
        BreadthFirstDirectedPaths pathsW = new BreadthFirstDirectedPaths(Graph, w);

        // search for the shortest ancestral length and ancestor
        int sapDist = -1;
        int sapAncestor = -1;
        for (int i = 0; i < Graph.V(); i++) {
            boolean initialDist = sapDist == -1;
            boolean reachable = pathsV.hasPathTo(i) && pathsW.hasPathTo(i);
            if (reachable && (initialDist || pathsV.distTo(i) + pathsW.distTo(i) < sapDist)) {
                sapDist = pathsV.distTo(i) + pathsW.distTo(i);
                sapAncestor = i;
            }
        }

        // add to cache then return the key
        int[] value = {sapDist, sapAncestor};
        cache.put(key, value);
        return key;
    }

    // a little different from the other sap method, not really the most elegant solution
    //      but it is more performant than if we need to figure out the sources
    private long sap(Iterable<Integer> v, Iterable<Integer> w) {
        checkVertices(v);
        checkVertices(w);

        // create the shortest paths of the sets
        BreadthFirstDirectedPaths pathsV = new BreadthFirstDirectedPaths(Graph, v);
        BreadthFirstDirectedPaths pathsW = new BreadthFirstDirectedPaths(Graph, w);

        // search for the shortest ancestral length, common ancestor and
        //  the vertices in v and w that produced it
        int sapDist = -1;
        int sapAncestor = -1;
        int sourceV = Integer.MAX_VALUE;
        int sourceW = Integer.MAX_VALUE;
        for (int i = 0; i < Graph.V(); i++) {
            boolean initialDist = sapDist == -1;
            boolean reachable = pathsV.hasPathTo(i) && pathsW.hasPathTo(i);

            if (reachable && (initialDist || pathsV.distTo(i) + pathsW.distTo(i) < sapDist)) {
                sapDist = pathsV.distTo(i) + pathsW.distTo(i);
                sapAncestor = i;
                sourceV = pathsV.pathTo(i).iterator().next();
                sourceW = pathsW.pathTo(i).iterator().next();
            }
        }

        // no ancestral paths for all pairs in v and w
        if (sapDist == -1)  return emptyKey;
        // add to cache then return the key
        long key = produceKey(sourceV, sourceW);
        int[] value = {sapDist, sapAncestor};
        cache.put(key, value);
        return key;
    }

    // use a cantor pairing function to obtain a unique number for each pairing of v and w
    //  the key produced is similar for inputs <v, w> and <w, v>
    private long produceKey(int v, int w) {
        int min = Integer.min(v, w);
        int max = Integer.max(v, w);
        return (((long) (min + max) * (min + max + 1) / 2) + max);
    }

    // throws an error if the given vertex is outside the graph vertex range
    private void checkVertex(int v) {
        if (v < 0 || v >= Graph.V())
            throw new IllegalArgumentException("vertex given is outside the Graph's range");
    }

    private void checkVertices(Iterable<Integer> vertices) {
        if (vertices == null)
            throw new IllegalArgumentException("iterable should not be null");

        for (Integer v : vertices) {
            // check for nulls
            if (v == null)
                throw new IllegalArgumentException("vertices should not contain null items");
            // check if the value is within range
            checkVertex(v);
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            HashSet<Integer> v = new HashSet<>();
            for (int i = 0; i < 3; i++) {
                v.add(StdIn.readInt());
            }

            HashSet<Integer> w = new HashSet<>();
            for (int i = 0; i < 3; i++) {
                w.add(StdIn.readInt());
            }

            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
