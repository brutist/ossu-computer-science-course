import java.util.HashMap;

public class CodonCounter {
    private HashMap<String,Integer> codonMap;
    private String mainDna;

    public CodonCounter(String dna) {
        codonMap = new HashMap<>();
        mainDna = dna;
    }
    public void buildCodonMap(int start, String dna) {
        codonMap.clear();
        dna = dna.trim();
        int dnaLength = dna.length();
        for (int i = start; i < dnaLength; i+=3) {
            boolean condonPossible = !(i+3 > dnaLength);
            if (condonPossible) {
                String currCodon = dna.substring(i, i+3);
                if (codonMap.containsKey(currCodon)) {
                    int val = codonMap.get(currCodon);
                    codonMap.put(currCodon,val+1);
                }
                else {
                    codonMap.put(currCodon,1);
                }
            }
        }
    }
    public String getMostCommonCodon() {
        int largestCodonOccurence = 0;
        String mostCommonCodon = null;

        for (String key : codonMap.keySet()) {
            int keyOccurence = codonMap.get(key);
            if (keyOccurence > largestCodonOccurence) {
                largestCodonOccurence = keyOccurence;
                mostCommonCodon = key;
            }
        }
        return mostCommonCodon;
    }
    public void printCodonCounts(int start, int end) {
        for (String key : codonMap.keySet()) {
            int keyOccurence = codonMap.get(key);
            boolean withinRange = (start <= keyOccurence && keyOccurence <= end);
            if (withinRange) {
                System.out.println(String.format("%s occured %d times", key, keyOccurence));
            }
        }
    }
    public void tester() {
        int START = 4;
        int END = 4;
        String mostCommonCodon = "";
        for (int i = 0; i < 3; i++) {
            buildCodonMap(i, mainDna);
            mostCommonCodon = getMostCommonCodon();
            System.out.println(String.format("Counts of codons between %d and %d inclusive are:",START,END));
            System.out.println(String.format("Reading frame starting %d results in %d",i,codonMap.size()));
            System.out.println(String.format(" and most common codon is %s with count %d",mostCommonCodon,codonMap.get(mostCommonCodon)));
            printCodonCounts(START, END);
        }
    }
    public static void main(String[] args) {
        String dnaMystery1 = "ATTAATACTTTGTTTAACAGTAATTATTCAACTATTAAATATTTAAATAATTAAGT" +
                             "TATTAAACTATTAAGTACAGGGCCTGTATCTCTGATGCTGAACTATGATGTGTGACT" +
                             "TAAGCCCCCAAATACATCATGTTATTTGGATCCAAGGTGCTGCACAGAACGCTGACC" +
                             "CTCTCTAAGAGCTGGGTATTACT";
        CodonCounter cc = new CodonCounter(dnaMystery1);
        cc.tester();
    }
}
