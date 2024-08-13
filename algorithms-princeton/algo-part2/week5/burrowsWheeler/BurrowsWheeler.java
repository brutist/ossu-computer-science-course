import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.ArrayDeque;

public class BurrowsWheeler {
    public static final int R = 256;        // extended ASCII

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        StringBuilder word = new StringBuilder();
        while (!BinaryStdIn.isEmpty()) {
            word.append(BinaryStdIn.readChar());
        }

        CircularSuffixArray csa = new CircularSuffixArray(word.toString());
        StringBuilder lasts = new StringBuilder();
        int first = 0;          // the position of the suffix that is the same as the original word
        for (int i = 0; i < csa.length(); i++) {
            int startIndex = csa.index(i);
            int lastIndex = startIndex - 1;

            // find the suffix that form the original word
            if (startIndex == 0) {
                lastIndex = csa.length() - 1;
                first = i;
            }
            lasts.append(word.charAt(lastIndex));
        }
        BinaryStdOut.write(first);
        BinaryStdOut.write(lasts.toString());
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();        // row index at which the original string appears
        StringBuilder bwt = new StringBuilder();  // last column of sorted rotation
        while (!BinaryStdIn.isEmpty())
            bwt.append(BinaryStdIn.readChar());

        // use key-index counting to get the first column of the sorted rotation
        // which is just the sorted version of the bwt
        int N = bwt.length();
        int[] count = new int[R + 1];
        // count frequencies
        for (int i = 0; i < N; i++)     count[bwt.charAt(i) + 1]++;
        // compute the cumulates
        for (int r = 0; r < R; r++)     count[r + 1] += count[r];

        // create a sorted bwt which corresponds to the first column in of the sorted suffixes
        // also create an array[i] that contains the deque of indexes to bwt that contains character ith.
        char[] bwtSorted = new char[N];
        ArrayDeque<Integer>[] lastCharIndexes = (ArrayDeque<Integer>[]) new ArrayDeque[R];
        for (int i = 0; i < N; i++) {
            // miserably unreadable for a minor optimization
            bwtSorted[count[bwt.charAt(i)]++] = bwt.charAt(i);

            if (lastCharIndexes[bwt.charAt(i)] == null)
                lastCharIndexes[bwt.charAt(i)] = new ArrayDeque<>();

            lastCharIndexes[bwt.charAt(i)].addLast(i);
        }

        int[] next = new int[N];
        for (int i = 0; i < N; i++) {
            next[i] = lastCharIndexes[bwtSorted[i]].removeFirst();
        }

        BinaryStdOut.write(bwtSorted[first]);
        for (int i = 1, j = first; i < N; i++, j = next[j]) {
            BinaryStdOut.write(bwtSorted[next[j]]);
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            BurrowsWheeler.transform();
        }
        if (args[0].equals("+")) {
            BurrowsWheeler.inverseTransform();
        }
    }
}























