import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

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

        // create a sorted bwt, which is a reference (index) to the bwt which corresponds to
        //  an alphabetically sorted bwt
        char[] bwtSorted = new char[N];
        for (int i = 0; i < N; i++)
            bwtSorted[count[bwt.charAt(i)]++] = bwt.charAt(i);



    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            BurrowsWheeler.transform();
        }
    }
}























