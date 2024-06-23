import edu.princeton.cs.algs4.StdIn;

public class QueueUsingArrays<Item> {
    Item[] items;
    int head = 0, tail = 0, size = 0;

    public QueueUsingArrays() {
        items = (Item[]) new Object[2];
    }

    private void resize() {
        int newSize = tail - head;
        Item[] newItems = (Item[]) new Object[newSize];
        for (int i = head; i <= newSize; i++) {
            items[i];
        }
    }


    public void enqueue(Item item) {
        if (isEmpty()) {
            head = 0;
            tail = 0;
            items[head] = item;
        }
        else {
            items[tail] = item;
        }
        tail++;
        size++;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new IllegalCallerException("can't dequeue in empty queue object");
        }

        Item firstItem = items[head];
        head++;
        size--;
        return firstItem;
    }

    public boolean isEmpty() {
        return head - tail == 0;
    }

    public int size() {
        return size;
    }

    public static void main(String[] args) {
        int size = 0;
        boolean testResult = true;
        QueueUsingArrays<String> queue = new QueueUsingArrays<String>();
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
