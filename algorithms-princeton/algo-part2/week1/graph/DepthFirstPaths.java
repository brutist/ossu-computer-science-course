import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

public class DepthFirstPaths {
    private final boolean[] visited;
    private final int[] edgeTo;
    private final int s;

    public DepthFirstPaths(Graph G, int s) {
        visited = new boolean[s];
        edgeTo = new int[s];
        this.s = s;
        dfs(G, s);
    }

    private void dfs(Graph G, int v) {
        visited[v] = true;
        for (int w : G.adj(v)) {
            if (!visited[w]) {
                dfs(G, w);
                edgeTo[w] = v;
            }
        }
    }

    public boolean hasPathTo(int v) {
        return visited[v];
    }

    public Iterable<Integer> pathTo(int v) {
        // if there is no path, return null
        if (!hasPathTo(v))  return null;

        // trace back the parents of vertex 'v' to the vertices leading to it until
        //  the source. Once the source has been reached, add it to the stack
        Stack<Integer> path = new Stack<>();
        for (int x = v; x != s; x = edgeTo[x]) {  // edgeTo[] is parent-link representation rooted at s
            path.push(v);
        }
        path.push(s);
        return path;
    }

    public static void main(String[] args) {
        Graph G = new Graph(new In());
        // need to create the graph via standard input
        int source = 2;
        DepthFirstPaths paths = new DepthFirstPaths(G, source);
        for (int v = 0; v < G.V(); v++) {
        if (paths.hasPathTo(v))
            System.out.println(v);
        }
    }
}
