import edu.princeton.cs.algs4.Stack;

public class TopologicalSort {
    private final boolean[] visited;
    private final Stack<Integer> reversePost;

    public TopologicalSort(Graph G) {
        reversePost = new Stack<>();
        visited = new boolean[G.V()];

        for (int v = 0; v < G.V(); v++) {
            if (!visited[v]) {
                dfs(G, v);
            }
        }
    }

    private void dfs(Graph G, int v) {
        visited[v] = true;
        for (int w : G.adj(v))
            if (!visited[w])    dfs(G, w);

        // when done doing dfs on a particular vertex, add it to the ordering
        reversePost.push(v);
    }

    public Iterable<Integer> reversePost() {
        return reversePost;
    }
}
