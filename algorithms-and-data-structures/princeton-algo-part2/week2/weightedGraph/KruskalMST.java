import edu.princeton.cs.algs4.UF;
import java.util.ArrayDeque;
import java.util.PriorityQueue;

public class KruskalMST {
    ArrayDeque<Edge> mst = new ArrayDeque<>();

    public KruskalMST(EdgeWeightedGraph G) {
    /* Approach: Sort the Edges of Graph G by weights and add the Edge to the
     *           tree if doing so would not create a cycle.
     *           To look for cycles, we can use the union-find data structure.
     *           With Edge e connecting v and w, if v and w are connected
     *           components, do not add e to the tree since doing so would create
     *           a cycle. */

        // edges sorted by weight
        PriorityQueue<Edge> edges = new PriorityQueue<>();
        for (Edge e : G.edges())
            edges.add(e);

        UF uf = new UF(G.V());
        while (mst.size() < G.V() - 1 && !edges.isEmpty() ) {
            Edge e = edges.poll();
            int v = e.either(), w = e.other(v);
            if (uf.find(v) != uf.find(w)) {
                uf.union(v, w);
                mst.addLast(e);
            }
        }
    }

    public Iterable<Edge> edges() {
        return mst;
    }
}
