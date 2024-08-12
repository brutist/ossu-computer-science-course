import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {

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

    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            BurrowsWheeler.transform();
        }
    }
}
