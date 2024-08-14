import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.ArrayDeque;

public class BurrowsWheeler {
    private static final int R = 256;        // extended ASCII

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        StringBuilder word = new StringBuilder();
        while (!BinaryStdIn.isEmpty()) {
            word.append(BinaryStdIn.readChar());
        }
        BinaryStdIn.close();

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
        while (!BinaryStdIn.isEmpty()) {
            int first = BinaryStdIn.readInt();        // row index at which the original string appears
            String bwt = BinaryStdIn.readString();

            // use key-index counting to get the first column of the sorted rotation
            // which is just the sorted version of the bwt
            int N = bwt.length();
            int[] count = new int[R + 1];
            // count frequencies
            for (int i = 0; i < N; i++)     count[bwt.charAt(i) + 1]++;
            // compute the cumulates
            for (int r = 0; r < R; r++)     count[r + 1] += count[r];


            int[] next = new int[N];
            for (int i = 0; i < bwt.length(); i++) {
                int sortedOrderPosition = count[bwt.charAt(i)];
                count[bwt.charAt(i)]++;
                next[sortedOrderPosition] = i;
            }
            int currentIndex = first;
            for (int i = 0; i < next.length - 1; i++) {
                char origStringChar = bwt.charAt(next[currentIndex]);
                BinaryStdOut.write(origStringChar);
                currentIndex = next[currentIndex];
            }
            BinaryStdOut.write(bwt.charAt(first));
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























