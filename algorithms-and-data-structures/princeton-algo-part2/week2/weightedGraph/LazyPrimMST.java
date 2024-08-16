import java.util.ArrayDeque;
import java.util.PriorityQueue;

public class LazyPrimMST {
    private final ArrayDeque<Edge> mst = new ArrayDeque<>();

    public LazyPrimMST(EdgeWeightedGraph G) {
        /* Approach: Maintain a PQ of edges with (at least) one endpoint in mst
         *           Get the min of the PQ to determine the next edge to add to
         *           the mst.
         */

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        // add edges incident to zero to the PQ
        addToPQ(pq, G.adj(0));

        // keep track of the vertices that are already in the mst
        boolean[] included = new boolean[G.V()];

        while (mst.size() < G.V() - 1 && !pq.isEmpty()) {
            Edge min = pq.poll();
            int v = min.either(), w = min.other(v);

            if (included[v] && included[w])     continue;
            if (!included[v])                   addToPQ(pq, G.adj(v));
            if (!included[w])                   addToPQ(pq, G.adj(w));

            included[w] = true;
            included[v] = true;
            mst.add(min);
        }
    }

    private void addToPQ(PriorityQueue<Edge> pq, Iterable<Edge> es) {
        for (Edge e : es)
            pq.add(e);
    }

    public Iterable<Edge> mst() {
        return mst;
    }
}
