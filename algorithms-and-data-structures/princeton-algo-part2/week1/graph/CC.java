import edu.princeton.cs.algs4.Graph;

public class CC {
    private final boolean[] visited;
    private final int[] id;
    private int count;

    public CC(Graph G){
        visited = new boolean[G.V()];
        id = new int[G.V()];

        // run dfs for each unmarked vertex, keeping count of the times you do
        //    dfs which correspond to the number of disjoint components
        for (int i = 0; i < G.V(); i++) {
            if (!visited[i]) {
                dfs(G, i);
                count++;
            }
        }
    }

    public int count() {
        return count;
    }

    public int id(int v) {
        return id[v];
    }

    private void dfs(Graph G, int v) {
        visited[v] = true;
        id[v] = count;

        for (int w : G.adj(v)) {
            if (!visited[w]) {
                dfs(G, w);
            }
        }
    }
}

// this extremely short snippet really belies the complicated idea that makes this work
