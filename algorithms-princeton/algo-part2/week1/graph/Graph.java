import edu.princeton.cs.algs4.Bag;

public class Graph {
    private final int V;
    private final Bag<Integer>[] vertices;

    public Graph(int v) {
        this.V = v;
        vertices = (Bag<Integer>[]) new Bag[v]; // ugly cast nothing we can do with Java restrictions
        for (int i = 0; i < v; i++) {
            vertices[i] = new Bag<Integer>();
        }
    }

    public void addEdge(int v, int w) {
        vertices[v].add(w);
        vertices[w].add(v);
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
