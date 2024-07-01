import java.util.Arrays;

public class Heap<Key extends Comparable<Key>> {
    public void sort(Key[] toSort) {
        // assumes this is a 1-based index max-heap
        // Nth is the last index
        int N = toSort.length;
        for (int i = N / 2; i >= 1; i--) {
            sink(toSort, i, N);
        }

        while (N > 1) {
            swap(toSort, 1, N);
            sink(toSort, 1, --N);
        }
    }

    // enforces the invariant that all the ith index children are smaller
    // assumes a 1 based-index
    private void sink(Key[] toSort,int i, int N) {
        while (i * 2 <= N) {
            int j = i * 2;
            if (j < N && less(toSort, j, j + 1)) {
                j++;
            }
            if (less(toSort, i, j)) {
                swap(toSort, i, j);
                i = j;
            }
            else {
                break;
            }
        }
    }

    // assumes that the given index is 1-based array
    // but will compare the keys in a 0-based array
    private boolean less(Key[] unsorted, int i, int j) {
        // since we will swap on a 0-based index we decrement the two indexes
        i--;
        j--;
        return unsorted[i].compareTo(unsorted[j]) < 0;
    }

    // will be given a 1-based index array
    // but will swap the 0-based index unsorted array
    private void swap(Key[] unsorted, int i, int j) {
        // since we will swap on a 0-based index we decrement the two indexes
        i--;
        j--;

        Key temp = unsorted[i];
        unsorted[i] = unsorted[j];
        unsorted[j] = temp;
    }

    private String printArray(Key[] toSort) {
        StringBuilder rep = new StringBuilder();
        for (Key k : toSort) {
            rep.append(" ").append(k);
        }
        return String.valueOf(rep);
    }

    public static void main(String[] args) {
        String[] answer = {"T", "P", "R", "N", "H", "O", "A", "E", "I", "G"};
        Arrays.sort(answer);
        String[] testInput = {"T", "P", "R", "N", "H", "O", "A", "E", "I", "G"};

        Heap<String> heap = new Heap<>();
        heap.sort(testInput);

        if (Arrays.equals(answer, testInput)) {
            System.out.println("Heap sort test SUCCESS");
        }
        else {
            System.out.println("Heap sort test FAILED");
            System.out.printf("Got: %s instead of %s", heap.printArray(testInput), heap.printArray(answer)) ;
        }

    }
}
