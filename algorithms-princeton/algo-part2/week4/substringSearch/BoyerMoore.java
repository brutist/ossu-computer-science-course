import java.util.Arrays;

public class BoyerMoore {
    private final String searchText;
    private static final int R = 256;

    public BoyerMoore(String text) {
        searchText = text;
    }

    public int search(String pattern) {
        int[] rightMosts = precomputeRightMost(pattern);
        int N = searchText.length();
        int M = pattern.length();

        int skip;
        for (int i = 0; i <= N - M; i += skip) {
            skip = 0;

            // start comparing right ot left
            for (int j = M - 1; j >= 0; j--) {
                if (pattern.charAt(j) != searchText.charAt(i + j)) {
                    // what a fucking glorious use of max, it's really glorious
                    //  what an elegant and concise solution for several different cases
                    //  specifically  - this short section avoids going backwards
                    //                  also make sure to align the pattern by the text
                    //                  this also guards against the possibility of negative j - r[]
                    // this is just ingenious!!!
                    skip = Math.max(1, j - rightMosts[searchText.charAt(i + j)]);
                    break;
                }
            }
            // if there was no need to skip, then we found a match
            if (skip == 0)  return i;
        }

        return N;
    }

    // precompute the index of rightmost occurrence of character c in pattern
    //  -1 if character not in pattern
    private int[] precomputeRightMost(String pattern) {
        int[] right = new int[R];

        // all letters set to not be in the pattern
        Arrays.fill(right, -1);

        // set right[c] to the index of c,
        //  since we are going from left to right, the index of c in right[] will
        //  end up with the right most index
        for (int c = 0; c < pattern.length(); c++)
            right[pattern.charAt(c)] = c;

        return right;
    }
}
