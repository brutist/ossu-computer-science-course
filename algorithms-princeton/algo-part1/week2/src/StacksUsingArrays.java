import edu.princeton.cs.algs4.StdIn;

public class StacksUsingArrays {
    private String[] arr;
    private int topMostIndex = 0;

    public StacksUsingArrays() {
        int initialSize = 2;
        arr = new String[initialSize];
    }

    private void resize() {
        String[] newArr = new String[arr.length * 2];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        arr = newArr;
    }

    public void push(String item) {
        // add 1 first to the top index then check if that exceeds the length
        topMostIndex++;

        if (topMostIndex == arr.length) {
            resize();
        }
        arr[topMostIndex] = item;
    }

    public String pop() {
        return arr[topMostIndex--];
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
        StacksUsingArrays stack = new StacksUsingArrays();
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
