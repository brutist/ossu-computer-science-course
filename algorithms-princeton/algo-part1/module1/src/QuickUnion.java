/* *****************************************************************************
 *  Name:              Mauring Jr
 *  Coursera User ID:  idk
 *  Last modified:     sometime in 2024
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class QuickUnion {
    private int[] id;

    public QuickUnion(int n) {
        id = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public int root(int i) {
        while (i != id[i]) {
            i = id[i];
        }
        return i;
    }

    public void union(int p, int q) {
        int proot = root(p);
        int qroot = root(q);
        id[proot] = qroot;
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        QuickUnion uf = new QuickUnion(n);

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
