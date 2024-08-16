import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

import java.util.NoSuchElementException;

public class Digraph {
    private final int V;
    private final Bag<Integer>[] vertices;

    public Digraph(int v) {
        this.V = v;
        vertices = (Bag<Integer>[]) new Bag[v]; // ugly cast nothing we can do with Java restrictions
        for (int i = 0; i < v; i++) {
            vertices[i] = new Bag<Integer>();
        }
    }

    public Digraph(In in) {
        if (in == null) throw new IllegalArgumentException("argument is null");
        try {
            this.V = in.readInt();
            if (V < 0) throw new IllegalArgumentException("number of vertices in a Graph must be non-negative");
            vertices = (Bag<Integer>[]) new Bag[V];
            for (int v = 0; v < V; v++) {
                vertices[v] = new Bag<Integer>();
            }
            int E = in.readInt();
            if (E < 0) throw new IllegalArgumentException("number of edges in a Graph must be non-negative");
            for (int i = 0; i < E; i++) {
                int v = in.readInt();
                int w = in.readInt();
                validateVertex(v);
                validateVertex(w);
                addEdge(v, w);
            }
        }
        catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in Graph constructor", e);
        }
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    public void addEdge(int v, int w) {
        vertices[v].add(w); // an undirected graph will have both connections
    }

    public Iterable<Integer> adj(int v) {
        return vertices[v];
    }

    public int V() {
        return V;
    }

    public int E() {
        int count = 0;
        for (int i = 0; i < V; i++) {
            count += vertices[i].size();
        }
        return count;
    }
}
