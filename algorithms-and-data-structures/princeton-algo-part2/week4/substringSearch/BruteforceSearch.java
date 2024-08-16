public class BruteforceSearch {
    private final String searchText;

    public BruteforceSearch(String text) {
        searchText = text;
    }

    // produces the starting index of the first pattern occurrence in the searchText
    //      - produces the length of the searchText if pattern does not exist in text
    public int search(String pattern) {
        int N = searchText.length();
        int M = pattern.length();

        for (int i = 0; i <= N - M; i++) {
            int j;
            for (j = 0; j < M; j++) {
                if (searchText.charAt(i + j) != pattern.charAt(j))
                    break;
            }
            if (j == M)     return i;
        }
        return N;
    }

    public static void main(String[] args) {
        String text = "ABACADABRAC";
        String pattern = "ABRA";

        BruteforceSearch bf = new BruteforceSearch(text);
        System.out.println(bf.search(pattern));
    }
}
