/* *****************************************************************************
 *  Name:              Mauring Jr
 *  Coursera User ID:  idk
 *  Last modified:     sometime in 2024
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class QuickFind {
    private int[] id;

    public QuickFind(int n) {
        id = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
    }

    public void union(int p, int q) {
        int pid = id[p];
        int qid = id[q];
        for (int i = 0; i < id.length; i++) {
            if (pid == id[i]) {
                id[i] = qid;
            }
        }
    }
 
    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        QuickFind uf = new QuickFind(n);

        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (!uf.connected(p, q)) {
                uf.union(p, q);
                System.out.printf("%d %d\n", p, q);
            }
            else {
                System.out.printf("Already connected: %d %d\n", p, q);
            }
        }
    }
}
