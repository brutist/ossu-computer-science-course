import edu.princeton.cs.algs4.StdRandom;

public class KMP {
    private final String searchText;
    private static final int R = 256;

    public KMP(String text) {
        searchText = text;
    }

    public int search(String pattern) {
        int N = searchText.length();
        int M = pattern.length();
        int[][] dfa = precomputeDFA(pattern);

        int i, j;
        for (i = 0, j = 0; i < N && j < M; i++)
            j = dfa[searchText.charAt(i)][j];

        if (j == M)     return i - M;
        else            return N;
    }

    // what a glorious thing!!!
    private int[][] precomputeDFA(String pattern) {
        int[][] dfa = new int[R][pattern.length()];
        // if first character is found in state 0 then go to state 1
        dfa[pattern.charAt(0)][0] = 1;

        for (int X = 0, j = 1;j < pattern.length(); j++) {

            for (int c = 0; c < R; c++)
                dfa[c][j] = dfa[c][X];          // copy mismatch cases

            dfa[pattern.charAt(j)][j] = j + 1;  // set match cases
            X = dfa[pattern.charAt(j)][X];      // update restart state
        }

        return dfa;
    }

    private static String randomString(int length) {
        // only produces ascii letters
        int lowASCII = 97;
        int highASCII = 123;

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = (char) StdRandom.uniformInt(lowASCII, highASCII);
            result.append(c);
        }
        return result.toString();
    }

    public static void main(String[] args) {
        // comparing bruteforce method and KMP
        String randomLongText = KMP.randomString(100);
        KMP kmp = new KMP(randomLongText);
        BruteforceSearch bf = new BruteforceSearch(randomLongText);

        int testCounter = 0;
        String failedMessage = "TEST %d FAILED: bruteforce (%d)  KMP (%d) with pattern %s \n";
        String successMessage = "TEST %d -> %d SUCCESS\n";
        while (true) {
            String pattern = KMP.randomString(4);
            int kmpResult = kmp.search(pattern);
            int bruteResult = bf.search(pattern);
            if (kmpResult != bruteResult) {
                System.out.printf(failedMessage, testCounter, bruteResult, kmpResult, pattern);
                System.out.print(randomLongText);
                break;
            }

            int TESTRANGE = 1000000;
            if (testCounter > 0 && testCounter % TESTRANGE == 0)
                System.out.printf(successMessage, testCounter - TESTRANGE, testCounter);

            testCounter++;
        }
    }
}
