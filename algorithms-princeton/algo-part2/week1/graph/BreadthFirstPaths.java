import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

public class BreadthFirstPaths {
    private final boolean[] visited;
    private final int[] edgeTo;
    private final int s;

    public BreadthFirstPaths(Graph G, int s) {
        visited = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        // create an external queue to keep track of the vertices to visit
        Queue<Integer> toVisit = new Queue<>();
        // helper method for bfs
        bfs(G, toVisit, s);
    }

    private void bfs(Graph G, Queue<Integer> toVisit, int s) {
        toVisit.enqueue(s);
        while (!toVisit.isEmpty()) {
            int v = toVisit.dequeue();

            for (int w : G.adj(v)) {
                // skip this vertex if it has been visited
                if (visited[w]) continue;

                toVisit.enqueue(w);
                visited[w] = true;
                edgeTo[w] = v;
            }
        }
    }

    public boolean hasPathTo(int v) {
        return visited[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (s == v) return null;

        Stack<Integer> path = new Stack<>();
        for (int x = v; x != s; x = edgeTo[x]) {
            path.push(x);
        }
        path.push(s);
        return path;
    }
}




















