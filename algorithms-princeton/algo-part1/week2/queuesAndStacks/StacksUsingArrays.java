import edu.princeton.cs.algs4.StdIn;

public class StacksUsingArrays<Item> {
    private Item[] arr;
    private int topMostIndex = 0;

    public StacksUsingArrays() {
        int initialSize = 2;
        arr = (Item[]) new Object[initialSize];
    }

    private void resize(int to) {
        Item[] newArr = (Item[]) new Object[to];
        System.arraycopy(arr, 0, newArr, 0, topMostIndex);
        arr = newArr;
    }

    public void push(Item item) {
        // add 1 first to the top index then check if that exceeds the length
        topMostIndex++;

        if (topMostIndex == arr.length) {
            resize(arr.length * 2);
        }
        arr[topMostIndex] = item;
    }

    public Item pop() {
        Item lastElem = arr[topMostIndex];
        arr[topMostIndex] = null;

        // resize array if topMostIndex >= arr.length/4
        if (topMostIndex <= arr.length / 4) {
            resize(arr.length / 2);
        }

        topMostIndex--;
        return lastElem;
    }

    public boolean isEmpty() {
        return topMostIndex == 0;
    }

    public int size() {
        return topMostIndex;
    }

    public static void main (String[] args) {
        int size = 0;
        boolean testResult = true;
        StacksUsingArrays<String> stack = new StacksUsingArrays<String>();
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
