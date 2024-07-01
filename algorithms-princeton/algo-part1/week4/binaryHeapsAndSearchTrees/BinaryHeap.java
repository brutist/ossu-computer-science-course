public class BinaryHeap<Key extends Comparable<Key>> {
    private Key[] heapArray;
    private int N;

    public BinaryHeap() {
        int initialSize = 2;
        heapArray = (Key[]) new Comparable[initialSize];
        // disregard index zero
        N = 0;
    }

    public Key delMax() {
        // index zero is not considered
        Key max = heapArray[1];
        swap(1, N--);
        sink(1);
        // prevent loitering
        heapArray[N + 1] = null;

        // resize down if the array is almost empty
        if (N < heapArray.length / 4) {
            heapResize(heapArray.length / 2);
        }

        return max;
    }

    public void insert(Key key) {
        heapArray[++N] = key;
        swim(N);

        // resize the heap if full
        if (N == heapArray.length - 1) {
            heapResize(heapArray.length  * 2);
        }
    }

    public boolean isEmpty() {
        return N == 0;
    }

    // promote child to its level of incompetence
    private void swim(int index) {
        // index of the parent is index/2 (int division)
        while (index > 1 && less(index / 2, index)) {
            swap(index, index / 2);
            index = index / 2;
        }
    }

    // sink parent to its level of incompetence
    private void sink(int parent) {
        // since index 0 is not considered, highest index in a heap of size N is N
        while (parent * 2 <= N) {
            int child = parent * 2;
            // j is the index with the biggest key
            if (child < N && less(child, child + 1)) {
                child++;
            }
            if (less(parent, child)) {
                swap(parent, child);
                parent = child;
            }
            else {
                break;
            }
        }
    }

    // return true if heapArray[j] < heapArray[j], otherwise false
    private boolean less(int i, int j) {
        return heapArray[i].compareTo(heapArray[j]) < 0;
    }

    private void swap(int i, int j) {
        Key temp = heapArray[i];
        heapArray[i] = heapArray[j];
        heapArray[j] = temp;
    }


    private void heapResize(int newSize) {
        Key[] newHeapArray = (Key[]) new Comparable[newSize];
        // copy previous heap array to another array with the new size
        System.arraycopy(heapArray, 0, newHeapArray, 0, heapArray.length);
        heapArray = newHeapArray;
    }

    private void printHeapArray(String op, Key inserted) {
        System.out.printf("%s %s: ", op ,inserted);
        for (Key k : heapArray) {
            if (k != null) {
                System.out.printf(" %s ", k);
            }
        }
        System.out.print("\n");
    }

    public static void main(String[] args) {
        String[] testInput = {"T", "P", "R", "N", "H", "O", "A", "E", "I", "G"};
        BinaryHeap<String> heap = new BinaryHeap<>();
        for (String s : testInput) {
            heap.insert(s);
        }

        // manual checking against lecture notes
        heap.insert("S");
        heap.printHeapArray("Inserted","S");

        heap.delMax();
        heap.printHeapArray("Deleted", "max");

        heap.delMax();
        heap.printHeapArray("Deleted", "max");

        heap.insert("S");
        heap.printHeapArray("Inserted","S");
    }
}
