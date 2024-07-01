import edu.princeton.cs.algs4.StdRandom;

import java.util.Random;

public class Sorter<Comparables extends Comparable<Comparables>>{
    public static final Random rand = new Random();

    private boolean less(Comparables objectA, Comparables objectB) {
        return objectA.compareTo(objectB) < 0;
    }

    private boolean isSorted(Comparables[] items) {

        for (int i = 1; i < items.length; i++) {
            if (less(items[i], items[i - 1])) {
                return false;
            }
        }
        return true;
    }

    private boolean isSorted(Comparables[] items, int low, int high) {
        for (int i = low + 1; i < high; i++) {
            if (less(items[i], items[i - 1])) {
                return false;
            }
        }
        return true;
    }

    // produces an array of size n by randomly filling each slots from [0, n] inclusive
    private Integer[] unsortedNumbers(int n) {
        Integer[] ints = new Integer[n];

        for (int i = 0; i < ints.length; i++) {
            int randInt = StdRandom.uniformInt(n + 1);
            ints[i] = randInt;
        }

        return ints;
    }

    private void printArray(Comparables[] toPrint) {
        for (Comparables item : toPrint) {
            System.out.printf("%s ", item);
        }
        System.out.print("\n");
    }

    // swaps the index i and j in the source array
    private void swap(Object[] source, int i, int j) {
        Object temp = source[i];
        source[i] = source[j];
        source[j] = temp;
    }

    public void selectionSort(Comparables[] toSort) {
        for (int i = 0; i < toSort.length; i++) {
            // index of the current 'minimum' object in the list
            int min = i;
            for (int j = i; j < toSort.length; j++) {
                if (less(toSort[j], toSort[min])) {
                    min = j;
                }
            }
            swap(toSort, i, min);
        }
    }

    public void insertionSort(Comparables[] toSort) {
        for (int i = 1; i < toSort.length; i++) {
            int curr = i;
            int prev = i - 1;
            while (less(toSort[curr], toSort[prev])) {
                swap(toSort, curr, prev);
                curr--;
                if (curr > 0) {
                    prev--;
                }
            }
        }
    }

    public void shellSort(Comparables[] toSort) {
        // solve for initial interval (highest knuth's interval using eq. 3x + 1)
        int initialInterval = 1;
        int nextInterval =  (3 * initialInterval) + 1;
        while (nextInterval < toSort.length) {
            initialInterval = nextInterval;
            nextInterval = (3 * initialInterval) + 1;
        }

        int currInterval = initialInterval;
        while(currInterval > 0) {
            shellSort(toSort, currInterval);

            // calculate the next interval
            currInterval = (currInterval - 1) / 3;
        }

    }

    private void shellSort(Comparables[] toSort, int interval) {
        // the interval is always smaller than the length of the array
        for (int i = interval; i < toSort.length; i++) {
            int curr = i;
            int prev = i - interval;
            while (less(toSort[curr], toSort[prev])) {
                swap(toSort, curr, prev);
                curr = prev;
                int nextPrev = prev - interval;
                if (nextPrev >= 0) {
                    prev = nextPrev;
                }
            }
        }
    }

    public void mergeSortBU(Comparables[] toSort) {
        Comparables[] aux = (Comparables[]) new Comparable[toSort.length];
        int n = toSort.length;

        // double the size of arrays to merge each time
        for (int mergeSize = 1; mergeSize <= n; mergeSize = (mergeSize * 2)) {
            for (int l = 0; l < n - mergeSize; l += (mergeSize * 2)) {
                // must have a merge() that accepts mid [the border of two sorted arrays] because
                //  the two sorted arrays may not be of equal lengths
                merge(toSort, aux, l, l + mergeSize -1 ,Math.min(l + (mergeSize * 2) - 1, n -1));
            }
        }
    }

    public void mergeSort(Comparables[] toSort) {
        Comparables[] aux = (Comparables[]) new Comparable[toSort.length];
        mergeSort(toSort, aux, 0, toSort.length - 1);
    }

    private void mergeSort(Comparables[] toSort, Comparables[] aux, int low, int high) {
        if (low >= high) {
            return;
        }
        int mid = low + ((high - low) / 2);
        mergeSort(toSort, aux, low, mid);
        mergeSort(toSort, aux, mid + 1, high);
        merge(toSort, aux, low, mid ,high);
    }


    private void merge(Comparables[] toSort, Comparables[] aux, int low, int mid, int high) {

        assert isSorted(toSort, low, mid);
        assert isSorted(toSort, mid + 1, high);

        for (int n = low; n <= high; n++) {
            aux[n] = toSort[n];
        }

        int i = low, j = mid + 1;
        for (int k = low; k <= high; k++) {
            if (i > mid)                           { toSort[k] = aux[j++];  }
            else if (j > high)                     { toSort[k] = aux[i++];  }
            else if (less(aux[i], aux[j]))         { toSort[k] = aux[i++];  }
            else                                   { toSort[k] = aux[j++];  }
        }

        assert isSorted(toSort, low, high);
    }

    public void shuffleSort(Object[] toSort) {
        if (toSort == null) {
            throw new IllegalArgumentException("null item are not allowed");
        }
        for (int i = 0; i < toSort.length; i++) {
            int r = rand.nextInt(i + 1);
            swap(toSort, i, r);
        }
    }

    public void quickSort(Comparables[] toSort) {
        shuffleSort(toSort);
        quickSort(toSort, 0, toSort.length - 1);
    }

    private void quickSort(Comparables[] toSort, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(toSort, lo, hi);
        // don't include the partitioning element, it is already in the right position
        quickSort(toSort, lo, j - 1);
        quickSort(toSort, j + 1, hi);
    }

    private int partition(Comparables[] toSort, int lo, int hi) {
        // first element as partitioning element
        int i = lo;
        int j = hi + 1;

        while (true) {

            while (less(toSort[++i], toSort[lo])) {
                if (i == hi) break;
            }

            while (less(toSort[lo], toSort[--j])) {
                if (j == lo) break;
            }

            if (i >= j) break;
            swap(toSort, i, j);
        }

        swap(toSort, lo, j);
        return j;
    }

    public Comparables kthLargest(Comparables[] toSearch, int k) {
        if (k > toSearch.length) {
            throw new IllegalArgumentException("k should not be larger than the number of items in the array");
        }
        // probabilistic performance guarantee
        shuffleSort(toSearch);
        int lo = 0;
        int hi = toSearch.length - 1;

        while (hi > lo) {
            int j = partition(toSearch, lo, hi);
            if      (j < k)     lo = j + 1;
            else if (j > k)     hi = j - 1;
            else                return toSearch[k];
        }
        return toSearch[k];
    }

    public static void main(String[] args) {
        // testing isSorted()
        Integer[] sourceA = {1, 2, 3, 4};
        String[] sourceB = {"A", "B", "C", "D"};
        Sorter<Integer> integerSorter = new Sorter<Integer>();
        Sorter<String> stringSorter = new Sorter<String>();
        if (integerSorter.isSorted(sourceA) && stringSorter.isSorted(sourceB)) {
            Integer[] unsorted = integerSorter.unsortedNumbers(20);
            if (!integerSorter.isSorted(unsorted)) {
                System.out.println("isSorted() tests PASSED");
            }
        }
        else {
            System.out.println("isSorted() tests FAILED");
        }


        // testing selectionSort(), insertionSort()
        int trials = 50;
        int SsortCounter = 0;
        int IsortCounter = 0;
        int ShsortCounter = 0;
        int MsortCounter = 0;
        int MsortBUCounter = 0;
        int QsortCounter = 0;
        boolean testSucess = true;

        for (int i = 0; i < trials; i++) {
            int arraySize = StdRandom.uniformInt(101) + 20;
            Integer[] unsorted = integerSorter.unsortedNumbers(arraySize);
            if (integerSorter.isSorted(unsorted)) {
                System.out.println("Reshuffling failed");
            }

            // testing selectionSort()
            integerSorter.selectionSort(unsorted);
            SsortCounter++;
            // integerSorter.printArray(unsorted);
            if (!integerSorter.isSorted(unsorted)) {
                testSucess = false;
                System.out.printf("selectionSort() tests %s FAILED\n", SsortCounter);
            }


            // reshuffle the array
            integerSorter.shuffleSort(unsorted);
            if (integerSorter.isSorted(unsorted)) {
                System.out.println("Reshuffling failed");
            }
            // testing insertionSort()
            integerSorter.insertionSort(unsorted);
            IsortCounter++;

            if (!integerSorter.isSorted(unsorted)) {
                testSucess = false;
                System.out.printf("insertionSort() tests %s FAILED\n", IsortCounter);
            }

            // reshuffle the array
            integerSorter.shuffleSort(unsorted);
            if (integerSorter.isSorted(unsorted)) {
                System.out.println("Reshuffling failed");
            }
            // testing shellSort()
            integerSorter.shellSort(unsorted);
            ShsortCounter++;

            if (!integerSorter.isSorted(unsorted)) {
                testSucess = false;
                System.out.printf("shellSort() tests %s FAILED\n", IsortCounter);
            }


            // reshuffle the array
            integerSorter.shuffleSort(unsorted);
            if (integerSorter.isSorted(unsorted)) {
                System.out.println("Reshuffling failed");
            }
            // testing mergeSort()
            integerSorter.mergeSort(unsorted);
            MsortCounter++;

            if (!integerSorter.isSorted(unsorted)) {
                testSucess = false;
                System.out.printf("mergeSort() tests %s FAILED\n", MsortCounter);
            }

            // reshuffle the array
            integerSorter.shuffleSort(unsorted);
            if (integerSorter.isSorted(unsorted)) {
                System.out.println("Reshuffling failed");
            }
            // testing mergeSortBU()
            integerSorter.mergeSortBU(unsorted);
            MsortBUCounter++;

            if (!integerSorter.isSorted(unsorted)) {
                testSucess = false;
                System.out.printf("mergeSortBU() tests %s FAILED\n", MsortCounter);
            }

            // reshuffle the array
            integerSorter.shuffleSort(unsorted);
            if (integerSorter.isSorted(unsorted)) {
                System.out.println("Reshuffling failed");
            }
            // testing quickSort()
            integerSorter.quickSort(unsorted);
            QsortCounter++;

            if (!integerSorter.isSorted(unsorted)) {
                testSucess = false;
                System.out.printf("quickSort() tests %s FAILED\n", QsortCounter);
            }

        }
        if (testSucess) {
            System.out.printf("selectionSort() %s tests PASSED\n" +
                              "insertionSort() %s tests PASSED\n" +
                              "shellSort()     %s tests PASSED\n" +
                              "mergeSort()     %s tests PASSED\n" +
                              "mergeSortBU()   %s tests PASSED\n" +
                              "quickSort()     %s tests PASSED\n",
                    SsortCounter, IsortCounter, ShsortCounter, MsortCounter, MsortBUCounter, QsortCounter);
        }
    }
}
