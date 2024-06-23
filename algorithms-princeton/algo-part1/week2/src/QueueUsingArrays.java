import edu.princeton.cs.algs4.StdIn;

public class QueueUsingArrays<Item> {
    Item[] items;
    int head = 0, tail = 0, size = 0;

    public QueueUsingArrays() {
        int initialSize = 2;
        items = (Item[]) new Object[initialSize];
    }

    private void resize(int to) {
        // resets head and tail indexes and create a *to-length array
        Item[] newItems = (Item[]) new Object[to];
        System.arraycopy(items, head, newItems, 0, size);
        items = newItems;
        head = 0;
        tail = size;
    }


    public void enqueue(Item item) {
        if (isEmpty()) {
            head = 0;
            tail = 0;
        }

        if (head + size == items.length) {
            resize(items.length * 2);
        }

        items[tail] = item;
        tail++;
        size++;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new IllegalCallerException("can't dequeue in empty queue object");
        }

        if (size < items.length / 4) {
            resize(items.length / 2);
        }

        Item firstItem = items[head];
        items[head] = null;
        head++;
        size--;

        return firstItem;
    }

    public boolean isEmpty() {
        return size == 0;
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
