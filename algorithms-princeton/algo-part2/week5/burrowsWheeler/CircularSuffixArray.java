import java.util.Arrays;

public class CircularSuffixArray {
    private final String word;
    private final int[] index;

    private class CircularSuffix implements Comparable<CircularSuffix> {
        private final int startIndex;

        public CircularSuffix(int start) {
            this.startIndex = start;
        }

        public int compareTo(CircularSuffix that) {
            int s = this.startIndex, v = that.startIndex;
            while (word.charAt(s) == word.charAt(v)) {
                s++;
                v++;
                if (s > word.length())      s = 0;
                if (v > word.length())      v = 0;
            }

            return word.charAt(s) - word.charAt(v);
        }
    }

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        String message = "constructor argument must not be null";
        if (s == null)  throw new IllegalArgumentException(message);

        // create a suffix of the words
        word = s;
        int N = s.length();
        CircularSuffix[] suffixes = new CircularSuffix[N];
        for (int i = 0; i < N; i++)
            suffixes[i] = new CircularSuffix(i);

        // sort the suffixes alphabetically
        Arrays.sort(suffixes);

        // get the start index of the sorted suffixes
        index = new int[N];
        for (int i = 0; i < N; i++)
            index[i] = suffixes[i].startIndex;
    }

    // length of s
    public int length() {
        return index.length;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        validateIndex(index.length, i);
        return index[i];
    }

    private void validateIndex(int limit, int i) {
        if (i < 0 || i >= limit) {
            String validateIndexError = "index given out of range";
            throw new IllegalArgumentException(validateIndexError);
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        String test = "ABRACADABRA!";
        CircularSuffixArray csa = new CircularSuffixArray(test);
        for (int i = 0; i < csa.length(); i++)
            System.out.printf("%d  ",csa.index(i));
    }
}