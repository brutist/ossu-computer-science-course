public class Node implements Comparable<Node>{
    private final char ch;
    private final int freq;
    private final Node left, right;

    public Node(char ch, int freq, Node r, Node l) {
        this.ch = ch;
        this.freq = freq;
        left = l;
        right = r;
    }

    public boolean isLeaf(Node node) {
        return node.right == null && node.left == null;
    }

    public int compareTo(Node that) {
        return this.freq - that.freq;
    }
}
