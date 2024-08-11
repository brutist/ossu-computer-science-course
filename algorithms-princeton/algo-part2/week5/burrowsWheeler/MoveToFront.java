import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    private static final int R = 256;

    // each node corresponds to the characters in the extended ASCII alphabet
    private static class Node {
        private char key;           // character in the alphabet
        private Node next;          // next node in the alphabet

        public static Node createAlphabet() {
            // create the extended ASCII alphabet using nodes
            Node start = new Node();
            Node current = start;
            for (int i = 0; i < R; i++) {
                current.key = (char) i;
                current.next = new Node();
                current = current.next;
            }

            for (Node next = start; next != null; next = next.next)
                System.out.printf("%s ", next.key);

            return start;
        }

        // move the Node containing c to be the root of the given node,
        public static Node moveNodeToFront(Node root, char c) {
            int index = 0;
            for (Node current = root, prev = null; current.next != null; current = current.next, prev = current) {
                if (c == current.key && prev != null) {
                    prev.next  = current.next;          // connect the previous node and the next node
                    current.next = root;                // move current to front
                    BinaryStdOut.write(index, 8);
                    return current;
                }
                index++;
            }
            throw new IllegalArgumentException("input character in not in extended ASCII");
        }
    }

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        Node root = Node.createAlphabet();

        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            root = Node.moveNodeToFront(root, c);
        }
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {

    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            MoveToFront.encode();
        }
    }
}