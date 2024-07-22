import edu.princeton.cs.algs4.Bag;

public class EdgeWeightedGraph {
        private final int V;        // no of vertices
        private Bag<Edge>[] vertices;

    public EdgeWeightedGraph(int V) {
        // create an adjacency list of the vertices with the index 'i' containing
        //  its bag of associated edges connecting 'i' to other vertex
        this.V = V;
        vertices = (Bag<Edge>[]) new Bag[V];    // unavoidable ugly cast
        for (int v = 0; v < V; v++)
            vertices[v] = new Bag<>();
    }

    public void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);
        // add the edge to both vertices
        vertices[v].add(e);
        vertices[w].add(e);
    }

    public Iterable<Edge> adj(int v) {
        return vertices[v];
    }
}
