public class RWayTries<Value> {
    private static final int R = 256;   // extended ASCII
    private Node root = new Node();

    private static class Node {
        private Object value;       // use Objects since no generic array in java
        private Node[] next;        // characters are implicitly defined by link index

        public Node() {
            value = null;
            next = new Node[R];
        }
    }

    public void put(String key, Value val) {
        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Value val, int d) {
        
    }


}
