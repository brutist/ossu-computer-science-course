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
        PriorityQueue<Edge> edges = new PriorityQueue<>();


    }

    public Iterable<Edge> edges() {

    }
}
