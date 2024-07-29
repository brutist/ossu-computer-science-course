import java.util.Arrays;

public class KeyIndexCounting {
    private int[] toSort;

    public KeyIndexCounting(int[] toSort) {
        this.toSort = toSort;
    }

    public int[] getSorted() {
        return toSort;
    }

    public void countingSort() {
        // identify the largest key in the toSort array
        int max = 0;
        for (int j : toSort)    max = Math.max(max, j);

        int N = toSort.length;
        int[] count = new int[max + 2];     // offset by 1
        // count the number of each key 'i' and put it in count[i + 1]
        for (int i = 0; i < N; i++)
            count[toSort[i] + 1]++;

        // compute cumulates
        for (int i = 0; i < max; i++)
            count[i + 1] += count[i];

        // move items to aux in order
        int[] aux = new int[N];
        for (int i = 0; i < N; i++)
            aux[count[toSort[i]]++] = toSort[i];

        System.arraycopy(aux, 0, toSort, 0, N);
    }

    public static void main(String[] args) {
        int[] test = {2, 3, 2, 4, 4, 5, 6, 6, 2};
        KeyIndexCounting kc = new KeyIndexCounting(test);
        kc.countingSort();
        System.out.println(Arrays.toString(kc.getSorted()));
    }
}
