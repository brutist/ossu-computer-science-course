/* *****************************************************************************
 *  Name:              Mauring Jr
 *  Coursera User ID:  idk
 *  Last modified:     sometime in 2024
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class UFind {
    private int[] arr;

    public UFind(int n) {
        arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }
    }

    public void union(int p, int q) {
        for (int i = 0; i < arr.length; i++) {
            if (p == arr[i] || i == p) {
                arr[i] = q;
            }
        }
    }

    public boolean connected(int p, int q) {
        return arr[p] == arr[q];
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        UFind uf = new UFind(n);

        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (!uf.connected(p, q)) {
                uf.union(p, q);
                System.out.printf("%d %d\n", p, q);
            }
        }
    }
}
