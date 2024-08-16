import edu.princeton.cs.algs4.StdIn;

public class StacksUsingLinkedList<Item> {
    private Node topMostNode;
    private int stackSize;

    private class Node {
        Item item;
        Node next;
    }
    public StacksUsingLinkedList(){
        topMostNode = null;
        stackSize = 0;
    }

    public void push (Item item) {
        Node newTop = new Node();
        newTop.item = item;
        newTop.next = topMostNode;
        topMostNode = newTop;
        stackSize++;
    }

    public Item pop () {
        if (isEmpty()) {
            throw new IllegalCallerException("pop is not allowed on empty stack");
        }
        else {
            Item topItem = topMostNode.item;
            topMostNode = topMostNode.next;
            stackSize--;
            return topItem;
        }
    }

    public boolean isEmpty() {
        return topMostNode == null;
    }

    public int size () {
        return stackSize;
    }

    public static void main (String[] args) {
        int size = 0;
        boolean testResult = true;
        StacksUsingLinkedList<String> stack = new StacksUsingLinkedList<String>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("-")) {
                System.out.printf("%s ", stack.pop());
                if (size != 0) {
                    size--;
                }
            }
            else {
                stack.push(s);
                size++;
            }

            if (size != stack.size()) {
                testResult = false;
                System.out.printf("--- Stack size: %d should be size %d\n", stack.size(), size);
            }
        }
        System.out.print("\n");

        if (testResult && stack.isEmpty()) {
            System.out.println("size() and isEmpty() tests passed");
        }
    }
}
