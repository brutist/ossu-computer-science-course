import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Stack;

public class DijkstraSP {
    private final double[] distTo;
    private final DirectedEdge[] edgeTo;
    private final IndexMinPQ<Double> pq;

    public DijkstraSP(EdgeWeightedDigraph G, int source) {
        distTo = new double[G.V()];          // best distance to ith vertex so far
        edgeTo = new DirectedEdge[G.V()];    // shortest edge leading to ith vertex so far
        pq = new IndexMinPQ<>(G.V());        // priority queue with weights as priorities

        for (int v = 0; v < G.V(); v ++)
            distTo[v] = Double.POSITIVE_INFINITY;

        // consider vertices in increasing order of distance from source, start at source since
        //   it is closest to itself
        distTo[source] = 0.0;

        pq.insert(source, 0.0);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            relaxEdges(G, v);
        }
    }

    public Iterable<DirectedEdge> shortestPathTo(int v) {
        Stack<DirectedEdge> SP = new Stack<>();
        for (DirectedEdge de = edgeTo[v]; de != null; de = edgeTo[de.from()])
            SP.push(de);

        return SP;
    }

    // relaxes all edges pointing from v
    private void relaxEdges(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge de : G.adj(v)) {
            int to = de.to();
            // if we've found a shorter edge from v to w then, make that the
            //    shortest path to w so far
            if (distTo[to] > distTo[v] + de.weight()) {
                distTo[to] = distTo[v] + de.weight();
                edgeTo[to] = de;
                if (pq.contains(to))    pq.decreaseKey(to, distTo[to]);
                else                    pq.insert(to, distTo[to]);
            }
        }
    }
}
