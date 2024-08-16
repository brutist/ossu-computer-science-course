import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    private static final int R = 256;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        // Approach : Maintain an alphabet[i] which contains the alphabet symbol at index i.
        //              To move a symbol to the front, move an element over to the right until
        //              you replace the target symbol. Do this for all symbols in the stream.
        int[] alphabet = new int[R];
        for (int i = 0; i < R; i++)
            alphabet[i] = i;

        // move the element over to the right until you replace the target symbol
        while (!BinaryStdIn.isEmpty()) {
            int c = BinaryStdIn.readChar();
            int current = alphabet[0];
            int j = 1;
            while (current != c) {
                if (j == R)     throw new IllegalArgumentException("mtf is out of bounds");

                int temp = current;
                current = alphabet[j];
                alphabet[j++] = temp;
            }

            alphabet[0] = c;    // move to front
            BinaryStdOut.write(j - 1, 8);
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        int[] alphabet = new int[R];
        for (int i = 0; i < R; i++)
            alphabet[i] = i;

        // move the element over to the right until you replace the target symbol
        while (!BinaryStdIn.isEmpty()) {
            int i = BinaryStdIn.readChar();
            int c = alphabet[i];

            int current = alphabet[0];
            int j;
            for (j = 0; j <= i; j++) {
                int temp = alphabet[j];
                alphabet[j] = current;
                current = temp;
            }

            alphabet[0] = c;    // move to front
            BinaryStdOut.write(c, 8);
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            MoveToFront.encode();
        }
        if (args[0].equals("+")) {
            MoveToFront.decode();
        }
    }
}