import edu.princeton.cs.algs4.StdIn;

public class QueueUsingLinkedList<Item> {

    Node firstNode, lastNode;
    int queueSize;

    private class Node {
        Item item;
        Node previous;
    }

    public QueueUsingLinkedList() {
        firstNode = null;
        lastNode = null;
        queueSize = 0;
    }

    public void enqueue(Item item) {
        // create a new node
        Node last = new Node();
        last.item = item;
        last.previous = null;

        if (isEmpty()) {
            firstNode = last;
        }
        else {
            // set the old last previous node to the new node
            lastNode.previous = last;
        }

        lastNode = last;
        queueSize++;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new IllegalCallerException("can't dequeue in empty queue object");
        }

        Item firstItem = firstNode.item;
        firstNode = firstNode.previous;

        queueSize--;
        return firstItem;
    }

    public boolean isEmpty() {
        return firstNode == null;
    }

    public int size() {
        return queueSize;
    }

    public static void main(String[] args) {
        int size = 0;
        boolean testResult = true;
        QueueUsingLinkedList<String> queue = new QueueUsingLinkedList<String>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("-")) {
                System.out.printf("%s ", queue.dequeue());
                if (size != 0) {
                    size--;
                }
            }
            else {
                queue.enqueue(s);
                size++;
            }

            if (size != queue.size()) {
                testResult = false;
                System.out.printf("--- Queue size: %d should be size %d\n", queue.size(), size);
            }
        }
        System.out.print("\n");

        if (testResult && queue.isEmpty()) {
            System.out.println("size() and isEmpty() tests passed");
        }
    }
}
