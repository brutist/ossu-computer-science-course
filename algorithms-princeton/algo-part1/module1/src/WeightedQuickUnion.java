/* *****************************************************************************
 *  Name:              Mauring Jr
 *  Coursera User ID:  idk
 *  Last modified:     sometime in 2024
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class WeightedQuickUnion {
    private int[] id;
    private int[] treeSize;

    public WeightedQuickUnion(int n) {
        id = new int[n];
        treeSize = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
            treeSize[i] = 1;
        }
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public int root(int i) {
        while (i != id[i]) {
            // make every other node point to its grandparent
            //     to shorten the tree's length (path compression)
            id[i] = id[id[i]];

            i = id[i];
        }
        return i;
    }

    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        if (i == j) {
            return;
        }
        if (treeSize[i] < treeSize[j]) {
            id[i] = j;
            treeSize[j] += treeSize[i];
        }
        else {
            id[j] = i;
            treeSize[i] += treeSize[j];
        }
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        WeightedQuickUnion wqu = new WeightedQuickUnion(n);

        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (!wqu.connected(p, q)) {
                wqu.union(p, q);
                System.out.printf("%d %d\n", p, q);
            }
            else {
                System.out.printf("Already connected: %d %d\n", p, q);
            }
        }
    }
}
